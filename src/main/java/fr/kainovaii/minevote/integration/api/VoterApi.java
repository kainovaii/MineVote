package fr.kainovaii.minevote.integration.api;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface VoterApi {
    @GET("fetch-voter")
    Call<List<Voter>> getVoters();
}