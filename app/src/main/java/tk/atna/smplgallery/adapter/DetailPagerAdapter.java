package tk.atna.smplgallery.adapter;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import tk.atna.smplgallery.fragment.DetailFragment;
import tk.atna.smplgallery.model.GalleryType;
import tk.atna.smplgallery.model.Photo;
import tk.atna.smplgallery.stuff.OnLoadListener;
import tk.atna.smplgallery.stuff.PhotoCache;

public class DetailPagerAdapter extends FragmentPagerAdapter {

    private OnLoadListener listener;
    private boolean isLoading = false;

    private GalleryType type;
    private List<Photo> photos;


    public DetailPagerAdapter(FragmentManager fm, GalleryType type, OnLoadListener listener) {
        super(fm);
        this.type = type;
        this.photos = PhotoCache.getPhotoList(type);
        this.listener = listener;
    }

    @Override
    public Fragment getItem(int position) {
        loadNextPage(position);
        return DetailFragment.newInstance(
                DetailFragment.class, DetailFragment.dataToBundle(type, position));
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    public void refreshData() {
        photos = PhotoCache.getPhotoList(type);
        notifyDataSetChanged();
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                isLoading = false;
            }
        }, 500);
    }

    private void loadNextPage(int position) {
        int count = getCount();
        if(position > 0
                && position > count - 5
                && !isLoading) {
            if(listener != null) {
                listener.onNeedLoad(count);
                isLoading = true;
            }
        }
    }

}
