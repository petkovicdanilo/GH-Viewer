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

    private String usedQuery;

    @Getter
    private int nextPage = 1;
    private final int perPage = 10;

    private boolean done = false;

    private final GitHubService gitHubService = ApiHelper.getInstance().getGitHubService();

    private static final String TAG = "SearchResultsViewModel";

    public void loadNextPage() {
        if (done) {
            Log.i(TAG, "Nothing more to load...");
            return;
        }

        Call<RepositorySearchResultDto> call = gitHubService.searchRepositories(usedQuery,
                nextPage, perPage);
        nextPage++;
        call.enqueue(new Callback<RepositorySearchResultDto>() {
            @Override
            public void onResponse(Call<RepositorySearchResultDto> call,
                                   Response<RepositorySearchResultDto> response) {
                List<RepositoryDto> repositories = response.body().getItems();

                if (repositories.size() == 0) {
                    done = true;
                    return;
                }

                List<RepositoryDto> currentRepositories = searchResults.getValue();
                currentRepositories.addAll(repositories);
                searchResults.setValue(currentRepositories);
            }

            @Override
            public void onFailure(Call<RepositorySearchResultDto> call, Throwable t) {
                Log.e(TAG, "Failed to search for repositories");
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void search(String name) {
        String query = name + " in:name";

        nextPage = 1;
        usedQuery = query;
        done = false;

        loadNextPage();
    }
}
