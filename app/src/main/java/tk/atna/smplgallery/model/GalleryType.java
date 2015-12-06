package tk.atna.smplgallery.model;

import android.content.Context;

import tk.atna.smplgallery.R;

public enum GalleryType {

    POPULAR(R.string.popular_upper),
    DAILY(R.string.daily_upper);

    private int title;


    GalleryType(int title) {
        this.title = title;
    }

    int getTitle(GalleryType type) {
        return type.title;
    }

    public static String[] getTitles(Context context) {
        if(context != null) {
            String[] titles = new String[values().length];
            for (GalleryType type : values()) {
                titles[type.ordinal()] = context.getString(type.title);
            }
            return titles;
        }
        return null;
    }

}
