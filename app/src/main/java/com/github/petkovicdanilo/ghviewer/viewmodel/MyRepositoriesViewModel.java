package com.github.petkovicdanilo.ghviewer.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.petkovicdanilo.ghviewer.api.ApiHelper;
import com.github.petkovicdanilo.ghviewer.api.GitHubService;
import com.github.petkovicdanilo.ghviewer.api.dto.RepositoryDto;
import com.github.petkovicdanilo.ghviewer.api.dto.RepositorySearchResultDto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRepositoriesViewModel extends ViewModel {

    @Getter
    private final MutableLiveData<List<RepositoryDto>> myRepositories =
            new MutableLiveData<>(new ArrayList<>());

    @Getter
    private int nextPage = 1;
    private final int perPage = 10;

    private boolean done = false;

    private final GitHubService gitHubService = ApiHelper.getInstance().getGitHubService();

    private static final String TAG = "MyRepositoriesViewModel";

    public void loadNextPage() {
        if (done) {
            Log.i(TAG, "Nothing more to load...");
            return;
        }

        Call<List<RepositoryDto>> call = gitHubService.getMyRepositories(nextPage, perPage);
        nextPage++;

        call.enqueue(new Callback<List<RepositoryDto>>() {
            @Override
            public void onResponse(Call<List<RepositoryDto>> call,
                                   Response<List<RepositoryDto>> response) {
                List<RepositoryDto> newRepositories = response.body();

                if (newRepositories.size() == 0) {
                    done = true;
                    return;
                }

                List<RepositoryDto> currentRepositories = myRepositories.getValue();
                currentRepositories.addAll(newRepositories);
                myRepositories.setValue(currentRepositories);
            }

            @Override
            public void onFailure(Call<List<RepositoryDto>> call, Throwable t) {
                Log.e(TAG, "Failed to load my repositories");
            }
        });
    }

}
