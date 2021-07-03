package com.github.petkovicdanilo.ghviewer.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.petkovicdanilo.ghviewer.api.ApiHelper;
import com.github.petkovicdanilo.ghviewer.api.GitHubService;
import com.github.petkovicdanilo.ghviewer.api.dto.RepositoryDto;
import com.github.petkovicdanilo.ghviewer.api.dto.git.BranchDto;
import com.github.petkovicdanilo.ghviewer.api.dto.git.TreeDto;

import java.util.Stack;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryViewModel extends ViewModel {
    @Getter
    private MutableLiveData<RepositoryDto> repository = new MutableLiveData<>();

    @Getter
    private String currentBranch;

    @Getter
    private MutableLiveData<TreeDto> currentTree = new MutableLiveData<>();

    private Stack<TreeDto> parentTrees = new Stack<>();

    private static final String TAG = "RepositoryViewModel";

    private final GitHubService gitHubService = ApiHelper.getInstance().getGitHubService();

    public void loadRepository(String owner, String name) {
        parentTrees = new Stack<>();
        currentTree.setValue(null);

        Call<RepositoryDto> call = gitHubService.getRepository(owner, name);
        call.enqueue(new Callback<RepositoryDto>() {
            @Override
            public void onResponse(Call<RepositoryDto> call, Response<RepositoryDto> response) {
                repository.setValue(response.body());
                loadBranch(repository.getValue().getDefaultBranch());
            }

            @Override
            public void onFailure(Call<RepositoryDto> call, Throwable t) {
                Log.e(TAG, "Failed to load repository");
            }
        });
    }

    public void loadBranch(String newBranch) {
        currentBranch = newBranch;

        Call<BranchDto> call = gitHubService.getBranch(repository.getValue().getOwner().getLogin(),
                repository.getValue().getName(), currentBranch);
        call.enqueue(new Callback<BranchDto>() {
            @Override
            public void onResponse(Call<BranchDto> call, Response<BranchDto> response) {
                String treeSha =
                        response.body().getCommit().getCommit().getTree().getSha();
                loadTree(treeSha);
            }

            @Override
            public void onFailure(Call<BranchDto> call, Throwable t) {
                Log.e(TAG, "Failed to load branch");
            }
        });
    }

    public void loadTree(String sha) {
        Call<TreeDto> call = gitHubService.getTree(repository.getValue().getOwner().getLogin(),
                repository.getValue().getName(), sha);
        call.enqueue(new Callback<TreeDto>() {
            @Override
            public void onResponse(Call<TreeDto> call, Response<TreeDto> response) {
                TreeDto tree = response.body();

                if(currentTree.getValue() != null) {
                    parentTrees.push(currentTree.getValue());
                    tree.getTree().add(0, TreeDto.TreeItem.parentTree());
                }

                currentTree.postValue(tree);
            }

            @Override
            public void onFailure(Call<TreeDto> call, Throwable t) {
                Log.e(TAG, "Failed to load tree");
            }
        });
    }

    public void loadParentTree() {
        if(parentTrees.size() == 0) {
            return;
        }

        TreeDto parentTree = parentTrees.pop();
        currentTree.postValue(parentTree);
    }
}
