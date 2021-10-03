package com.example.akinip;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackGroundService extends IntentService {
    Retrofit client;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BackGroundService(String name) {
        super(name);
    }
    DataEntity dataList;
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

}
