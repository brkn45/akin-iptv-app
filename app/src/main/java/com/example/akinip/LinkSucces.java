package com.example.akinip;

public class LinkSucces {
    public String link;
    public Boolean authLink;

    public LinkSucces(String link,Boolean authLink){
        this.link = link;
        this.authLink = authLink;
    }
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getAuthLink() {
        return authLink;
    }

    public void setAuthLink(Boolean authLink) {
        this.authLink = authLink;
    }
}
