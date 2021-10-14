package com.example.akinip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmHolder> {
    private ArrayList<String> filmName;
    private ArrayList<String> filmImage;
    public ArrayList<String> link;
    private Context context;
    public VideoView videoView;
    public Thread thread1;
    public VideoRun videoRun;
    public FilmAdapter(ArrayList<String> filmName,ArrayList<String> filmImage,
                       Context context,ArrayList<String> link,VideoView videoView){
        this.filmName = filmName;
        this.filmImage = filmImage;
        this.link = link;
        //this.filmName = new ArrayList<String>();
        //this.filmImage = new ArrayList<String>();
        //this.filmName.addAll(filmName);
        //this.filmImage.addAll(filmImage);
        this.context = context;
        this.videoView = videoView;

    }

    public ArrayList<String> getFilmName() {
        return filmName;
    }

    public void setFilmName(ArrayList<String> filmName) {
        this.filmName = filmName;
    }

    public ArrayList<String> getLink() {
        return link;
    }

    public void setLink(ArrayList<String> link) {
        this.link = link;
    }

    @NonNull
    @Override
    public FilmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.category_item,parent,false);

        return new FilmHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmHolder holder, @SuppressLint("RecyclerView") int position) {

        try {
            holder.setData(filmName.get(position));
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
                //System.out.println("burada: " + link.get(position));


                videoRun = new VideoRun();
                videoRun.setPosition(position);

                Thread thread1 = new Thread(videoRun);
                thread1.setPriority(Thread.MAX_PRIORITY);
                thread1.run();


            }
        });

    }

    @Override
    public int getItemCount() {
        return filmName.size();
    }

    public class FilmHolder extends RecyclerView.ViewHolder{
        public TextView tvFilmName;
        public ImageView ivFilm;
        public LinearLayout linearLayout;

        public FilmHolder(View itemView){
            super(itemView);
            tvFilmName = (TextView) itemView.findViewById(R.id.tvCategoryName);
            ivFilm       = (ImageView) itemView.findViewById(R.id.ivlinear);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.LinearLayoutItem);


        }
        public void setData(String name) throws IOException {
            tvFilmName.setText(name);

            ivFilm.setImageResource(android.R.drawable.btn_star_big_on);



        }
    }

    public class VideoRun implements Runnable {
        private int position;

        @Override
        public void run() {
            videoView.pause();
            Uri uri = Uri.parse(link.get(position));
            videoView.setVideoURI(uri);
            videoView.start();
        }
        public void setPosition(int position){
            this.position = position;
        }
    }
}
