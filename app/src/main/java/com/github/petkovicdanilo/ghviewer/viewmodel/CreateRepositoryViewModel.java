package com.github.petkovicdanilo.ghviewer.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.petkovicdanilo.ghviewer.api.ApiHelper;
import com.github.petkovicdanilo.ghviewer.api.GitHubService;
import com.github.petkovicdanilo.ghviewer.api.dto.CreateRepositoryDto;
import com.github.petkovicdanilo.ghviewer.api.dto.RepositoryDto;

import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRepositoryViewModel extends ViewModel {

    @Getter
    private MutableLiveData<String> name = new MutableLiveData<>();

    @Getter
    private MutableLiveData<String> description = new MutableLiveData<>();

    @Getter
    private MutableLiveData<String> homepage = new MutableLiveData<>();

    @Getter
    private MutableLiveData<Boolean> isPrivate = new MutableLiveData<>();

    private GitHubService gitHubService = ApiHelper.getInstance().getGitHubService();

    @Setter
    private OnCreateRepositoryListener onCreateRepositoryListener;

    private static final String TAG = "CreateRepositoryViewMod";

    public void create() {
        CreateRepositoryDto createRepositoryDto = CreateRepositoryDto.builder()
                .name(name.getValue())
                .description(description.getValue())
                .homepage(homepage.getValue())
                .privateRepo(isPrivate.getValue())
                .build();

        Call<RepositoryDto> call = gitHubService.createRepository(createRepositoryDto);
        call.enqueue(new Callback<RepositoryDto>() {
            @Override
            public void onResponse(Call<RepositoryDto> call, Response<RepositoryDto> response) {
                if (response.isSuccessful()) {
                    clear();
                    onCreateRepositoryListener.onRepositoryCreated();
                } else {
                    onCreateRepositoryListener.onRepositoryCreationFail("Repository not created");
                }
            }

            @Override
            public void onFailure(Call<RepositoryDto> call, Throwable t) {
                Log.e(TAG, "Failed to create repository");
                onCreateRepositoryListener.onRepositoryCreationFail(t.getMessage());
            }
        });
    }

    private void clear() {
        name.setValue("");
        description.setValue("");
        isPrivate.setValue(false);
        homepage.setValue("");
    }

    public interface OnCreateRepositoryListener {
        void onRepositoryCreated();

        void onRepositoryCreationFail(String message);
    }
}
