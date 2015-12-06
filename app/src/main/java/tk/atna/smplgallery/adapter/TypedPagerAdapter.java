package tk.atna.smplgallery.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tk.atna.smplgallery.fragment.TypedFragment;
import tk.atna.smplgallery.model.GalleryType;

public class TypedPagerAdapter extends FragmentPagerAdapter {

    private GalleryType[] types;
    private String[] titles;


    public TypedPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.types = GalleryType.values();
        this.titles = GalleryType.getTitles(context);
    }

    @Override
    public Fragment getItem(int position) {
        return TypedFragment.newInstance(
                TypedFragment.class, TypedFragment.dataToBundle(types[position]));
    }

    @Override
    public int getCount() {
        return types.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
