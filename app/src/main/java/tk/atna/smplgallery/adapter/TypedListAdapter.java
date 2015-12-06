package tk.atna.smplgallery.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import tk.atna.smplgallery.R;
import tk.atna.smplgallery.model.GalleryType;
import tk.atna.smplgallery.model.Photo;
import tk.atna.smplgallery.stuff.OnLoadListener;
import tk.atna.smplgallery.stuff.PhotoCache;

public class TypedListAdapter extends ArrayAdapter<Photo> {

    private final static int ITEM_RES = R.layout.item_photo_list;

    private Context context;
    private LayoutInflater inflater;
    private OnLoadListener listener;
    private boolean isLoading = false;

    private GalleryType type;


    public TypedListAdapter(Context context, GalleryType type, OnLoadListener listener) {
        super(context, ITEM_RES, new ArrayList<Photo>());
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(ITEM_RES, parent, false);
            holder = new ItemViewHolder(convertView);
            convertView.setTag(holder);

        } else
            holder = (ItemViewHolder) convertView.getTag();

        Photo item = getItem(position);
        holder.tvTitle.setText(String.format(Locale.getDefault(),
                context.getString(R.string.photo_title) + ": %s", item.name));
        holder.tvCreated.setText(String.format(Locale.getDefault(),
                context.getString(R.string.photo_created) + ": %s", item.created));
        holder.tvRating.setText(String.format(Locale.getDefault(),
                context.getString(R.string.photo_rating) + ": %.2f", item.rating));
        holder.tvViewed.setText(String.format(Locale.getDefault(),
                context.getString(R.string.photo_viewed) + ": %d", item.viewed));

        loadNextPage(position);
        return convertView;
    }

    public void refreshData() {
        clear();
        addAll(PhotoCache.getPhotoList(type));
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
                && position > count - 10
                && !isLoading) {
            if(listener != null) {
                listener.onNeedLoad(count);
                isLoading = true;
            }
        }
    }


    private class ItemViewHolder {

        TextView tvTitle;
        TextView tvCreated;
        TextView tvRating;
        TextView tvViewed;


        ItemViewHolder(View v) {
            tvTitle = (TextView) v.findViewById(R.id.tv_title);
            tvCreated = (TextView) v.findViewById(R.id.tv_created);
            tvRating = (TextView) v.findViewById(R.id.tv_rating);
            tvViewed = (TextView) v.findViewById(R.id.tv_viewed);
        }

    }
}