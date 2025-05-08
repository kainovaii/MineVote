package fr.kainovaii.minevote.utils;

import fr.kainovaii.minevote.integration.api.VoterApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient
{
    private static VoterApi api;

    public static void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(VoterApi.class);
    }

    public static VoterApi getApi() {
        return api;
    }
}