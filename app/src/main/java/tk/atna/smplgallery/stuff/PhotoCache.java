package tk.atna.smplgallery.stuff;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import tk.atna.smplgallery.model.GalleryType;
import tk.atna.smplgallery.model.Photo;

public class PhotoCache {

    private static final SparseArray<List<Photo>> PHOTO_CACHE;


    static {
        PHOTO_CACHE = new SparseArray<>();
        // init photo cache
        for(GalleryType type : GalleryType.values()) {
            PHOTO_CACHE.put(type.ordinal(), new ArrayList<Photo>());
        }
    }

    public static List<Photo> getPhotoList(GalleryType type) {
        if(type != null)
            return PHOTO_CACHE.get(type.ordinal());

        return null;
    }

    public static void updateCache(GalleryType type, List<Photo> newOnes) {
        if(type != null
                && newOnes != null)
            PHOTO_CACHE.get(type.ordinal()).addAll(newOnes);
    }

}
