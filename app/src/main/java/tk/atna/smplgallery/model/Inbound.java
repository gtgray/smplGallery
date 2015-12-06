package tk.atna.smplgallery.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Inbound {


    public class Feed {

        public String feature;

        @SerializedName("current_page")
        public int page;

        @SerializedName("total_pages")
        public int totalPages;

        @SerializedName("total_items")
        public int totalItems;

        public List<Photo> photos;
    }

}
