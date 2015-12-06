package tk.atna.smplgallery.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import tk.atna.smplgallery.R;
import tk.atna.smplgallery.model.GalleryType;
import tk.atna.smplgallery.model.Photo;
import tk.atna.smplgallery.stuff.PhotoCache;

public class DetailFragment extends BaseFragment {

    private static final String TYPE = "type";
    private static final String POSITION = "position";

    private GalleryType type;
    private int position;


    public static Bundle dataToBundle(GalleryType type, int position) {
        Bundle args = new Bundle();
        args.putSerializable(TYPE, type);
        args.putInt(POSITION, position);
        return args;
    }

    public DetailFragment() {
    }

    @Override
    void processArguments(@NonNull Bundle args) {
        type = (GalleryType) args.getSerializable(TYPE);
        position = args.getInt(POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ImageView ivPhoto = (ImageView) view.findViewById(R.id.iv_photo);
        contentManager.placePhoto(seekPhotoUrl(type, position), ivPhoto);
        return view;
    }

    private String seekPhotoUrl(GalleryType type, int position) {
        if(type != null
                && position >= 0) {
            Photo photo = PhotoCache.getPhotoList(type).get(position);
            if(photo != null)
                return photo.getDefaultUrl();
        }
        return null;
    }

}
