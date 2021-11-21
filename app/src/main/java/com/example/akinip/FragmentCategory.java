package com.example.akinip;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class FragmentCategory extends Fragment {
    String[] channelGroup;
    ListView lvFrag;
    FilmAdapter filmAdapter;
    RecyclerView recyclerView;


    ArrayList<String> category,filmArr,linkArr;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_layout,container,false);


        lvFrag = (ListView) view.findViewById(R.id.lvFrag);
         ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1,channelGroup);
        lvFrag.setAdapter(adapter);
        listViewClick();

        return view;

    }
    public  void listViewClick(){
        lvFrag.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String txtName= channelGroup[position];
                ArrayList<String> filmName = new ArrayList<String>();
                ArrayList<String> link = new ArrayList<String>();
                //System.out.println("txt" +txtName);

                //System.out.println("filmName: "+category.get(0));
                for(int i=0;i<filmArr.size() && i<category.size() && i<linkArr.size();i++){

                        if(category.get(i).compareTo(txtName)==0){
                            //System.out.println("girdi");
                            filmName.add(filmArr.get(i));
                            link.add(linkArr.get(i));

                    }

                }
                //System.out.println("filmName: "+filmName.get(0));
                filmAdapter.setFilmName(filmName);
                filmAdapter.setLink(link);
                recyclerView.setAdapter(filmAdapter);

                getActivity().getFragmentManager().beginTransaction().remove(FragmentCategory.this).commit();
                getActivity().getFragmentManager().popBackStack();



            }
        });
    }

    public  void setFilmArr(ArrayList<String> filmArr) {
        this.filmArr = filmArr;
    }

    public void setLinkArr(ArrayList<String> linkArr) {
        this.linkArr = linkArr;
    }

    public void setCategoryItem(String[] channelGroup, FilmAdapter filmAdapter, ArrayList<String> category){
        this.channelGroup =channelGroup;
        this.filmAdapter = filmAdapter;
        this.category = category;

    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}
