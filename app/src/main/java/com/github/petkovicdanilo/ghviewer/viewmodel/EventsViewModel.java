package com.github.petkovicdanilo.ghviewer.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.petkovicdanilo.ghviewer.api.ApiHelper;
import com.github.petkovicdanilo.ghviewer.api.GitHubService;
import com.github.petkovicdanilo.ghviewer.api.dto.EventDto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsViewModel extends ViewModel {

    @Getter
    private final MutableLiveData<List<EventDto>> events =
            new MutableLiveData<>(new ArrayList<>());

    @Getter
    private int nextPage = 1;
    private final int perPage = 10;

    @Getter
    private MutableLiveData<Boolean> done = new MutableLiveData<>(false);

    private final GitHubService gitHubService = ApiHelper.getInstance().getGitHubService();

    private static final String TAG = "EventsViewModel";

    public void loadNextPage() {
        if(done.getValue()) {
            Log.i(TAG, "Nothing more to load...");
            return;
        }

        String username = ApiHelper.getInstance().getCurrentUser().getLogin();
        Call<List<EventDto>> call = gitHubService.getEvents(username, nextPage, perPage);
        nextPage++;

        call.enqueue(new Callback<List<EventDto>>() {
            @Override
            public void onResponse(Call<List<EventDto>> call,
                                   Response<List<EventDto>> response) {
                List<EventDto> events = response.body();

                if(events.size() < perPage) {
                    done.postValue(true);
                }

                List<EventDto> currentEvents =
                        EventsViewModel.this.events.getValue();
                currentEvents.addAll(events);
                EventsViewModel.this.events.postValue(currentEvents);
            }

            @Override
            public void onFailure(Call<List<EventDto>> call, Throwable t) {
                Log.e(TAG, "Failed to load events");
            }
        });
    }
}
