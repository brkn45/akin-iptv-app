package com.example.akinip;

import java.io.Serializable;
import java.util.ArrayList;

public class DataEntity implements Serializable {
    private ArrayList<String> pictureLink;
    private ArrayList<String> groupName;
    private ArrayList<String> channelName;
    private ArrayList<String> link;


    public DataEntity(ArrayList<String> pictureLink, ArrayList<String> groupName, ArrayList<String> channelName,
                      ArrayList<String> link) {
        this.pictureLink = new ArrayList<String>();
        this.groupName = new ArrayList<String>();
        this.channelName = new ArrayList<String>();
        this.link = new ArrayList<String>();

        this.pictureLink.addAll(pictureLink);
        this.groupName.addAll(groupName);
        this.channelName.addAll(channelName);
        this.link.addAll(link);
    }
    public DataEntity(){
        this.pictureLink = new ArrayList<String>();
        this.groupName = new ArrayList<String>();
        this.channelName = new ArrayList<String>();
        this.link = new ArrayList<String>();
    }

    public ArrayList<String> getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(ArrayList<String> pictureLink) {
        this.pictureLink = pictureLink;
    }

    public ArrayList<String> getGroupName() {
        return groupName;
    }

    public void setGroupName(ArrayList<String> groupName) {
        this.groupName = groupName;
    }

    public ArrayList<String> getChannelName() {
        return channelName;
    }

    public void setChannelName(ArrayList<String> channelName) {
        this.channelName = channelName;
    }

    public ArrayList<String> getLink() {
        return link;
    }

    public void setLink(ArrayList<String> link) {
        this.link = link;
    }

    public void clearData(){
        pictureLink.clear();
        channelName.clear();
        groupName.clear();
        link.clear();

    }
}
