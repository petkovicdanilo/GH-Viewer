package com.github.petkovicdanilo.ghviewer.viewmodel;

import android.util.Log;

import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.petkovicdanilo.ghviewer.api.ApiHelper;
import com.github.petkovicdanilo.ghviewer.api.GitHubService;
import com.github.petkovicdanilo.ghviewer.api.dto.ActivityDto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitiesViewModel extends ViewModel {

    @Getter
    private final MutableLiveData<List<ActivityDto>> activities =
            new MutableLiveData<>(new ArrayList<>());

    @Getter
    private int nextPage = 1;
    private final int perPage = 10;

    @Getter
    private MutableLiveData<Boolean> done = new MutableLiveData<>(false);

    private final GitHubService gitHubService = ApiHelper.getInstance().getGitHubService();

    private static final String TAG = "ActivitiesViewModel";

    public void loadNextPage() {
        if(done.getValue()) {
            Log.i(TAG, "Nothing more to load...");
            return;
        }

        String username = ApiHelper.getInstance().getCurrentUser().getLogin();
        Call<List<ActivityDto>> call = gitHubService.getActivities(username, nextPage, perPage);
        nextPage++;

        call.enqueue(new Callback<List<ActivityDto>>() {
            @Override
            public void onResponse(Call<List<ActivityDto>> call,
                                   Response<List<ActivityDto>> response) {
                List<ActivityDto> activities = response.body();

                if(activities.size() < perPage) {
                    done.postValue(true);
                }

                List<ActivityDto> currentActivities =
                        ActivitiesViewModel.this.activities.getValue();
                currentActivities.addAll(activities);
                ActivitiesViewModel.this.activities.postValue(currentActivities);
            }

            @Override
            public void onFailure(Call<List<ActivityDto>> call, Throwable t) {
                Log.e(TAG, "Failed to load activities");
            }
        });
    }
}
