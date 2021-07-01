package com.github.petkovicdanilo.ghviewer.api;

import com.github.petkovicdanilo.ghviewer.api.dto.ActivityDto;
import com.github.petkovicdanilo.ghviewer.api.dto.CreateRepositoryDto;
import com.github.petkovicdanilo.ghviewer.api.dto.EmailDto;
import com.github.petkovicdanilo.ghviewer.api.dto.RepositoryDto;
import com.github.petkovicdanilo.ghviewer.api.dto.RepositorySearchResultDto;
import com.github.petkovicdanilo.ghviewer.api.dto.UserDto;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {
    @GET("repos/{owner}/{repo}/languages")
    Call<Map<String, Integer>> listLanguages(@Path("owner") String owner,
                                             @Path("repo") String repo);

    @GET("user/emails")
    Call<List<EmailDto>> getEmails();

    @POST("user/repos")
    Call<RepositoryDto> createRepo(@Body() CreateRepositoryDto createRepo);

    @GET("users/{username}/received_events")
    Call<List<ActivityDto>> getActivities(@Path("username") String username,
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
}

