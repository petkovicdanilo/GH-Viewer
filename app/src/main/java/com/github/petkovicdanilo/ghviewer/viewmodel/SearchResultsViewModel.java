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

public class SearchResultsViewModel extends ViewModel {

    @Getter
    private final MutableLiveData<List<RepositoryDto>> searchResults =
            new MutableLiveData<>(new ArrayList<>());

    @Getter
    private MutableLiveData<String> query = new MutableLiveData<>("");

    @Getter
    private int nextPage = 1;
    private final int perPage = 10;

    @Getter
    private MutableLiveData<Boolean> done = new MutableLiveData<>(false);

    private final GitHubService gitHubService = ApiHelper.getInstance().getGitHubService();

    private static final String TAG = "SearchResultsViewModel";

    public void loadNextPage() {
        if (done.getValue()) {
            Log.i(TAG, "Nothing more to load...");
            return;
        }

        Call<RepositorySearchResultDto> call = gitHubService.searchRepositories(query.getValue(),
                nextPage, perPage);
        nextPage++;
        call.enqueue(new Callback<RepositorySearchResultDto>() {
            @Override
            public void onResponse(Call<RepositorySearchResultDto> call,
                                   Response<RepositorySearchResultDto> response) {
                List<RepositoryDto> repositories = response.body().getItems();

                if (repositories.size() < perPage) {
                    done.postValue(true);
                }

                List<RepositoryDto> currentRepositories = searchResults.getValue();
                currentRepositories.addAll(repositories);
                searchResults.postValue(currentRepositories);
            }

            @Override
            public void onFailure(Call<RepositorySearchResultDto> call, Throwable t) {
                Log.e(TAG, "Failed to search for repositories");
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void search() {
        nextPage = 1;
        done.setValue(false);
        searchResults.setValue(new ArrayList<>());

        loadNextPage();
    }
}
