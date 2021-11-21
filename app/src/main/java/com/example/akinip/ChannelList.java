package com.example.akinip;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChannelList extends Activity implements Runnable{
    Retrofit client;
    DataEntity dataList;
    @Override
    public void run() {
        int i=0;
            try{
                client = new Retrofit.Builder().baseUrl("https://iptvapi.herokuapp.com").addConverterFactory(GsonConverterFactory.create()).build();
                GetDataService proxy = client.create(GetDataService.class);
                Call<DataEntity> callSync = proxy.allData();
                Response<DataEntity> response = callSync.execute();
                while(i<3){
                    if(response.isSuccessful()){
                            dataList = response.body();
                            break;


                    }
                    else{
                        callSync = proxy.allData();
                        response = callSync.execute();
                        i++;
                    }
                }
                if (i == 3) {
                    //Toast.makeText(getApplicationContext(), "Connection Problem or Link Error", Toast.LENGTH_LONG).show();
                    dataList=null;
                }


            }
        catch (Error | IOException error){
            System.out.println(error.getMessage().toString());
        }
        if(dataList.getLink().isEmpty()) {
            dataList = null;
        }

    }

    public DataEntity getDataList() {
        return dataList;
    }

    public void setDataList(DataEntity dataList) {
        this.dataList = dataList;
    }
}
