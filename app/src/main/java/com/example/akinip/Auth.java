package com.example.akinip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Auth extends AppCompatActivity {
    TextView tvAlert;
    EditText etLink;
    boolean installedData;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    Boolean rtValue,exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_auth);
        sp = this.getPreferences(Context.MODE_PRIVATE);
        etLink = (EditText) findViewById(R.id.etAuth);
        tvAlert = (TextView) findViewById(R.id.tvAlert);
        rtValue = false;
        exit = getIntent().getBooleanExtra("exit",false);
        if(exit){
            edt = sp.edit();
            edt.clear().apply();

        }
        if(sp.contains("install")){
            //System.out.println("1");
            installedData =sp.getBoolean("install", false);
            //System.out.println("1");
            if( installedData==false){
                //System.out.println("2");
                edt = sp.edit();
            }
            else if(installedData == true ) {
                //System.out.println("3");
                Intent intent = new Intent(Auth.this,Category.class);
                intent.putExtra("link",etLink.getText().toString());
                startActivity(intent);
                finish();

            }
        }
        else {
            //System.out.println("4");
            edt = sp.edit();
        }





    }
    public void funWatch(View view) throws InterruptedException {
        ClickAuth clickAuth = new ClickAuth();
        Thread thread1 = new Thread(clickAuth);
        thread1.join();

        thread1.start();


    }

    public class ClickAuth implements Runnable {

        @Override
        public void run() {
            rtValue = response();
            if (rtValue) {
                Intent intent = new Intent(Auth.this, Category.class);
                intent.putExtra("link", etLink.getText().toString());

                edt.putBoolean("install", true);


                edt.putString("link", etLink.getText().toString());
                edt.commit();
                startActivity(intent);

                finish();
            } else {
                //tvAlert.setVisibility(View.VISIBLE);
            }
        }


        private boolean response() {
            Retrofit client;
            int i = 0;
            String text = etLink.getText().toString();
            if (!text.isEmpty()) {
                try {
                    LinkSucces tmp;
                    client = new Retrofit.Builder().baseUrl("https://iptvapi.herokuapp.com").addConverterFactory(GsonConverterFactory.create()).build();
                    GetDataService proxy = client.create(GetDataService.class);
                    LinkSucces linkSucces = new LinkSucces(text, false);
                    Call<LinkSucces> callSync = proxy.auth(linkSucces);
                    Response<LinkSucces> response = callSync.execute();
                    while (i < 3) {
                        if (response.isSuccessful()) {
                            tmp = response.body();
                            rtValue = tmp.getAuthLink();
                            break;
                        } else {
                            callSync = proxy.auth(linkSucces);
                            response = callSync.execute();
                            i++;
                        }
                    }
                    if (i == 3) {
                        //System.out.println("baglanti sorunu");
                        rtValue = false;
                    }
                } catch (Error | IOException error) {
                    //System.out.println(error.getMessage().toString());
                }
                return rtValue;
            } else {
                //Toast.makeText(Auth.this, "Empty Textbox", Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }


}