package fr.kainovaii.minevote.http.api;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface VoterApi {
    @GET("v1/c604c8b8-8b95-4dc1-a647-e0754bf8321a")
    Call<List<Voter>> getVoters();
}