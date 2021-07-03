package com.github.petkovicdanilo.ghviewer.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.petkovicdanilo.ghviewer.api.ApiHelper;
import com.github.petkovicdanilo.ghviewer.api.GitHubService;
import com.github.petkovicdanilo.ghviewer.api.dto.git.BlobDto;

import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlobViewModel extends ViewModel {

    @Getter
    private MutableLiveData<BlobDto> blob = new MutableLiveData<>();

    private String name;

    @Setter
    private OnBlobListener listener;

    private GitHubService gitHubService = ApiHelper.getInstance().getGitHubService();

    public void loadBlob(String owner, String repository, String sha, String name) {
        if(blob.getValue() != null && sha.equals(blob.getValue().getSha())) {
            listener.onBlobLoaded();
            return;
        }

        this.name = name;

        Call<BlobDto> call = gitHubService.getBlob(owner, repository, sha);
        call.enqueue(new Callback<BlobDto>() {
            @Override
            public void onResponse(Call<BlobDto> call, Response<BlobDto> response) {
                if (response.isSuccessful()) {
                    blob.setValue(response.body());
                    listener.onBlobLoaded();
                } else {
                    listener.onBlobFail();
                }
            }

            @Override
            public void onFailure(Call<BlobDto> call, Throwable t) {
                listener.onBlobFail();
            }
        });
    }

    public String getExtension() {
        int i = name.lastIndexOf('.');
        if (i == -1) {
            return "";
        }

        return name.substring(i + 1);
    }

    public interface OnBlobListener {
        void onBlobLoaded();

        void onBlobFail();
    }
}
