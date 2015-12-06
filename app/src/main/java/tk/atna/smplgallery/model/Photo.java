package tk.atna.smplgallery.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photo {

    public int id;
    public String name;
    public String description;
    public double rating;

    @SerializedName("times_viewed")
    public int viewed;

    @SerializedName("created_at")
    public String created;

    public List<Image> images;


    public String getDefaultUrl() {
        if(images != null) {
            for(Image item : images) {
                if(item.size == Image.DEFAULT_SIZE)
                    return item.url;
            }
        }
        return null;
    }

    private class Image {

        static final int DEFAULT_SIZE = 600;

        public int size;
        public String url;


    }

}
