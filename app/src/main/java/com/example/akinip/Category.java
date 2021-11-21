package com.example.akinip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.VolumeProvider;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.HashSet;

import retrofit2.Retrofit;

public class Category extends AppCompatActivity {
    Boolean installedData;
    //private final  String initialLink = "http://redson.xyz:3800/turgutluhlkegt/K56DwQmRLU/2";
    private FilmAdapter filmAdapter;
    RecyclerView recyclerView;
    private Retrofit client;
    private volatile DataEntity dataList;
    VideoView videoView;
    MediaController mediaController;
    RelativeLayout upLayout, btnScreen;
    RelativeLayout parentLayout;
    LinearLayout startStopLayout;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    RelativeLayout tmp;
    Gson gson;
    FragmentCategory fragmetCategory;
    FragmentManager fragmentManager,exitManager;
    FragmentExit fragmentExit;
    FragmentTransaction ft,ftExit;
    ListView lvFrag;
    int channelGroupIndex;
    HashSet<String> tmpHashSet;
    String [] strArr;
    Button btnRefresh,btnCategory;
    EditText etSearch;
    Bundle bundle;
    int fragmentAdded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_category);
        gson = new Gson();
        //System.out.println("onCreate");
        init();

        //videoView.setVideoPath(initialLink);

        try {
            orientation();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    @Override
    protected void onStart() {
        super.onStart();
        //System.out.println("onStart");

    }
    @Override
    protected void onResume() {
        super.onResume();

        //System.out.println("onResume");
        VideoRun videoRun = new VideoRun();
        Thread threatVideo = new Thread(videoRun);
        threatVideo.setPriority(Thread.MAX_PRIORITY);
        threatVideo.run();


        if(!sp.contains("dataList") && !sp.contains("installedData")){
            String json = gson.toJson(dataList);
            edt = sp.edit();
            edt.putString("dataList",json);
            edt.putBoolean("installedData",true);
            edt.commit();
        }

        fragmetCategory = new FragmentCategory();
        if(dataList != null){
            Toast.makeText(Category.this, String.valueOf(dataList.getChannelName().size()), Toast.LENGTH_LONG).show();
        }

        fragmentExit = new FragmentExit();



    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.stopPlayback();
        //System.out.println("onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();

        videoView.stopPlayback();
        //System.out.println("onStop");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        //System.out.println("onRestart");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //System.out.println("onDestroy");
    }





    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (Category.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            parentLayout.removeView(upLayout);

        }
        else{
            parentLayout.addView(upLayout);
        }



        //System.out.println("burada");
        //android:screenOrientation="fullSensor"
    }

    public void init(){

        recyclerView = (RecyclerView) findViewById(R.id.recycleLinear);
        videoView = (VideoView) findViewById(R.id.videoViewCategory);
        upLayout = (RelativeLayout) findViewById(R.id.UpLayout);
        parentLayout = (RelativeLayout) findViewById(R.id.parentLayout);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);

        btnCategory = (Button) findViewById(R.id.btnCategory);
        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        lvFrag = (ListView) findViewById(R.id.lvFrag);
        etSearch = (EditText) findViewById(R.id.etsearch);
        sp = this.getPreferences(Context.MODE_PRIVATE);

        if(sp.contains("installedData") && sp.contains("dataList")){
            installedData = true;
        }
        else {
            installedData = false;
        }
    }
    public void orientation() throws InterruptedException {
        if(Category.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            parentLayout.removeView(upLayout);
        }
        if(dataList == null && installedData==false){
            notInstalledData();
        }
        else if( installedData == true) {
            dataListAndTrueInstalledData();
        }

    }

    public void dataListAndTrueInstalledData() throws InterruptedException {
        if(sp.contains("dataList")){
            String json = sp.getString("dataList", "");
            dataList = gson.fromJson(json, DataEntity.class);
            RcyAdapter rcyAdapter = new RcyAdapter();
            Thread threadSetAdapter = new Thread(rcyAdapter);
            threadSetAdapter.setPriority(Thread.MIN_PRIORITY);
            threadSetAdapter.start();
            threadSetAdapter.join();

            //System.out.println("threadNameic: " + threadSetAdapter.getName());

            Toast.makeText(Category.this, "Okey", Toast.LENGTH_LONG).show();

            //videoView.start();
        }
        else {
            Toast.makeText(Category.this, "Kanallar Yuklenemedi", Toast.LENGTH_LONG).show();
        }

    }
    public void notInstalledData(){
            ChannelList channelList = new ChannelList();
            Thread thread1 = new Thread(channelList);
            //System.out.println("Not 2 Installed threadName: " + thread1.getName());
            RcyAdapter rcyAdapter = new RcyAdapter();
            Thread thread2 = new Thread(rcyAdapter);
            //System.out.println(" Not 2 Installed threadName : " + thread2.getName());
            thread1.start();

            try {
                thread1.join();
                dataList = channelList.getDataList();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            thread2.setPriority(Thread.MIN_PRIORITY);
            thread2.start();
            try {
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }





    }
    public void funRefresh(View view) throws InterruptedException {
            if(dataList == null){
                orientation();
            }
            else if( dataList != null){
                filmAdapter.setFilmName(dataList.getChannelName());
                filmAdapter.setLink(dataList.getLink());
                recyclerView.setAdapter(filmAdapter);
            }



    }
    public void setFilmAdapter(){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        filmAdapter = new FilmAdapter(dataList.getChannelName(), dataList.getPictureLink(), this,dataList.getLink(),videoView);
        recyclerView.setAdapter(filmAdapter);
    }

    public void doListView(){
        if(dataList !=null) {

            tmpHashSet = new HashSet<String>(dataList.getGroupName());
            strArr = new String[tmpHashSet.size()];
            tmpHashSet.toArray(strArr);

            fragmetCategory.setCategoryItem(strArr,filmAdapter,dataList.getGroupName());
            fragmetCategory.setRecyclerView(recyclerView);
            fragmetCategory.setFilmArr(dataList.getChannelName());
            fragmetCategory.setLinkArr(dataList.getLink());

            ft.add(R.id.parentLayout, fragmetCategory, "fragmentCategory");
            ft.addToBackStack("back1");
            ft.commit();
        }
    }
    public void funSearch(View view){
        String word;
        word= etSearch.getText().toString();
        ArrayList<String> sameWord = new ArrayList<String>();
        ArrayList<String> sameLink = new ArrayList<String>();
        if(!word.isEmpty()){
            int i=0;
            for(i=0; i <dataList.getChannelName().size();i++){
                if(dataList.getChannelName().get(i).toLowerCase().indexOf(word.toLowerCase()) >-1){
                    sameWord.add(dataList.getChannelName().get(i));
                    sameLink.add(dataList.getLink().get(i));
                }
            }
            filmAdapter.setLink(sameLink);
            filmAdapter.setFilmName(sameWord);
            recyclerView.setAdapter(filmAdapter);

        }
        else{
            filmAdapter.setFilmName(dataList.getChannelName());
            filmAdapter.setLink(dataList.getLink());
        }
    }
    public void funExit(View view){
        exitManager = getFragmentManager();
        ftExit = exitManager.beginTransaction();

        if(!fragmentExit.isAdded()) {
            ftExit.add(R.id.parentLayout, fragmentExit, "fragmentExit");
            ftExit.addToBackStack("exit1");
            ftExit.commit();
        }
        else{
            ftExit.remove(fragmentExit);
            exitManager.popBackStack();
            ftExit.commit();

        }




    }
    public void funCategory(View view){

        if(dataList !=null) {
            fragmentManager = getFragmentManager();
            ft = fragmentManager.beginTransaction();
            if(!fragmetCategory.isAdded()){
                doListView();
                fragmentAdded =1;

            }
            else if(fragmetCategory.isAdded()){
                ft.remove(fragmetCategory);

                //ft.detach(fragmetCategory);

                fragmentManager.popBackStack();
                fragmentAdded = 0;
                ft.commit();
            }
            else if(fragmentAdded ==2) {

                //ft.attach(fragmetCategory);
                ft.add(R.id.parentLayout, fragmetCategory, "fragmentCategory");
                ft.addToBackStack("back3");
                fragmentAdded = 1;
                ft.commit();
            }

        }
    }
    public void noFun(View view){
        exitManager = getFragmentManager();
        ftExit = exitManager.beginTransaction();
        if(fragmentExit.isAdded()) {
            ftExit.remove(fragmentExit);
            exitManager.popBackStack();
            ftExit.commit();
        }

    }

    public void yesFun(View view){
        exitManager = getFragmentManager();
        ftExit = exitManager.beginTransaction();
        if(dataList !=null){
            dataList.clearData();
        }

        ftExit.remove(fragmentExit);
        ftExit.commit();
        edt = sp.edit();
        edt.clear();
        edt.commit();
        Intent intent = new Intent(Category.this,Auth.class);
        intent.putExtra("exit",true);
        startActivity(intent);
        finish();

    }



    public class RcyAdapter implements Runnable {


        @Override
        public void run() {
            //System.out.println("6cahnnel" + dataList.getChannelName().get(0));

            if(dataList!=null){
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager manager = new LinearLayoutManager(Category.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(manager);
                filmAdapter = new FilmAdapter(dataList.getChannelName(), dataList.getPictureLink(), Category.this, dataList.getLink(), videoView);
                recyclerView.setAdapter(filmAdapter);

            }


        }

    }
    public class VideoRun implements Runnable {


        @Override
        public void run() {
            if(dataList!=null){
                videoView.setVideoPath(dataList.getLink().get(0));
                videoView.start();
            }


        }

    }


}