package com.example.myapplicationrv.network;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ShowResponse {
    @SerializedName("_embedded")
    private Embedded embedded;
    public Embedded getEmbedded() { return embedded; }

    public static class Embedded {
        private List<CastItem> cast;
        public List<CastItem> getCast() { return cast; }
    }

    public static class CastItem {
        private Person person;
        private Character character;
        public Person getPerson()       { return person; }
        public Character getCharacter() { return character; }
    }

    public static class Person {
        private String name;
        private Image image;
        public String getName()  { return name; }
        public Image  getImage() { return image; }
    }

    public static class Character {
        private String name;
        private Image image;
        public String getName()  { return name; }
        public Image  getImage() { return image; }
    }

    public static class Image {
        private String medium;
        public String getMedium() { return medium; }
    }
}
