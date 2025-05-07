package com.example.myapplicationrv.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvMazeService {
    @GET("shows/{id}")
    Call<ShowResponse> getShowWithCast(
            @Path("id") int showId,
            @Query("embed") String embed // pass "cast"
    );
}