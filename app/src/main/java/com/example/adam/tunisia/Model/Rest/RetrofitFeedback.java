package com.example.adam.tunisia.Model.Rest;

import android.util.Log;

import com.example.adam.tunisia.Model.Entities.Feedback;
import com.example.adam.tunisia.Model.Rest.Interfaces.IFeedback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFeedback {

    private static final String URL = "http://192.168.1.6:8083/projet_pfe_serveur/" ;
    private static final String TAG = "RetrofitFeedback";


    /* ***************************************************************
                        POSTING
    *************************************************************** */

    public void postFeedback(Feedback f, String Soc){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AdapterREST.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IFeedback apiService = retrofit.create(IFeedback.class);

        Call<Feedback> call = apiService.createUser(Soc,f);


        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Log.v(TAG, "ONRESPONS");
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                Log.v(TAG, "ONFAILURE");
                t.printStackTrace();
            }


        });
    }

}
