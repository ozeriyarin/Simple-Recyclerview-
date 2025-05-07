package com.example.myapplicationrv.models;

public class CharacterData {
    private final String name;
    private final String actor;
    private final String imageUrl;

    public CharacterData(String name, String description,String imageUrl) {
        this.name = name;
        this.actor = description;
        this.imageUrl = imageUrl;
    }
    public String getName()        { return name; }
    public String getActor() { return actor; }
    public String getImageUrl()    { return imageUrl; }
}
