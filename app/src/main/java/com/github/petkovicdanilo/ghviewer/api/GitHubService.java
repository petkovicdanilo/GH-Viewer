package com.github.petkovicdanilo.ghviewer.api;

import com.github.petkovicdanilo.ghviewer.api.dto.CreateRepositoryDto;
import com.github.petkovicdanilo.ghviewer.api.dto.EmailDto;
import com.github.petkovicdanilo.ghviewer.api.dto.RepositoryDto;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("repos/{owner}/{repo}/languages")
    Call<Map<String, Integer>> listLanguages(@Path("owner") String owner, @Path("repo") String repo);

    @GET("user/emails")
    Call<List<EmailDto>> getEmails();

    @POST("user/repos")
    Call<RepositoryDto> createRepo(@Body() CreateRepositoryDto createRepo);
}

