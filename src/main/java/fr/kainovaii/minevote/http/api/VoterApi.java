package fr.kainovaii.minevote.http.api;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface VoterApi {
    @GET("fetch-voter")
    Call<List<Voter>> getVoters();
}