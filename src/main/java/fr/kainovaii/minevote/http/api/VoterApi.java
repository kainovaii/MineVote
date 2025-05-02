package fr.kainovaii.minevote.http.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VoterApi {
    @GET("v1/e4e967ec-8599-4682-81b8-55e573134567")
    Call<Voter> getVoter();
}