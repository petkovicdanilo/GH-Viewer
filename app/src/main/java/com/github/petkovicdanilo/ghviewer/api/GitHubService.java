package com.github.petkovicdanilo.ghviewer.api;

import com.github.petkovicdanilo.ghviewer.api.dto.EventDto;
import com.github.petkovicdanilo.ghviewer.api.dto.CreateRepositoryDto;
import com.github.petkovicdanilo.ghviewer.api.dto.git.BlobDto;
import com.github.petkovicdanilo.ghviewer.api.dto.git.BranchDto;
import com.github.petkovicdanilo.ghviewer.api.dto.git.BranchSimpleDto;
import com.github.petkovicdanilo.ghviewer.api.dto.git.TreeDto;
import com.github.petkovicdanilo.ghviewer.api.dto.RepositoryDto;
import com.github.petkovicdanilo.ghviewer.api.dto.RepositorySearchResultDto;
import com.github.petkovicdanilo.ghviewer.api.dto.UserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {
    @POST("user/repos")
    Call<RepositoryDto> createRepo(@Body() CreateRepositoryDto createRepo);

    @GET("users/{username}/received_events")
    Call<List<EventDto>> getEvents(@Path("username") String username,
                                   @Query("page") int page, @Query("per_page") int perPage);

    @GET("user")
    Call<UserDto> getUser();

    @GET("search/repositories")
    Call<RepositorySearchResultDto> searchRepositories(@Query("q") String query,
                                                       @Query("page") int page,
                                                       @Query("per_page") int perPage);

    @GET("user/repos")
    Call<List<RepositoryDto>> getMyRepositories(@Query("page") int page,
                                                @Query("per_page") int perPage);

    @GET("repos/{owner}/{repo}")
    Call<RepositoryDto> getRepository(@Path("owner") String owner,
                                      @Path("repo") String repositoryName);

    @GET("repos/{owner}/{repo}/git/trees/{sha}")
    Call<TreeDto> getTree(@Path("owner") String owner,
                          @Path("repo") String repositoryName, @Path("sha") String sha);

    @GET("repos/{owner}/{repo}/branches/{branch}")
    Call<BranchDto> getBranch(@Path("owner") String owner,
                              @Path("repo") String repositoryName, @Path("branch") String branch);

    @GET("repos/{owner}/{repo}/branches")
    Call<List<BranchSimpleDto>> getBranches(@Path("owner") String owner,
                                            @Path("repo") String repositoryName);

    @GET("repos/{owner}/{repo}/git/blobs/{sha}")
    Call<BlobDto> getBlob(@Path("owner") String owner,
                          @Path("repo") String repositoryName, @Path("sha") String sha);
}

