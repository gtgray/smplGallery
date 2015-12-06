package tk.atna.smplgallery.stuff;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.widget.ImageView;

import tk.atna.smplgallery.model.GalleryType;
import tk.atna.smplgallery.model.Inbound;
import tk.atna.smplgallery.network.HttpHelper;
import tk.atna.smplgallery.receiver.LocalBroadcaster;

public class ContentManager {

    private static ContentManager INSTANCE;

    private Context context;
    private HttpHelper httpHelper;


    private ContentManager(Context context) {
        this.context = context;
        this.httpHelper = new HttpHelper(context);
    }

    /**
     * Initializes content manager.
     * It is better to give it an application context
     *
     * @param context application context
     */
    public static synchronized void init(Context context) {
        if(context == null)
            throw new NullPointerException("Can't create instance with null context");
        if(INSTANCE != null)
            throw new IllegalStateException("Can't initialize ContentManager twice");

        INSTANCE = new ContentManager(context);
    }

    /**
     * Gets only instance of content manager.
     * Can't be called from non UI thread
     *
     * @return content manager instance
     */
    public static ContentManager get() {
        if(Looper.myLooper() != Looper.getMainLooper())
            throw new IllegalStateException("Method get() must be called from UI thread");

        if(INSTANCE == null)
            throw new IllegalStateException("ContentManager is null. It must have been"
                                          + " created at application init");

        return INSTANCE;
    }

    public void makeActionAsync(int action, Bundle data) {
        switch (action) {
            case Actions.GET_PHOTOS:
                getPhotos(data);
                break;
        }
    }

    private void getPhotos(Bundle data) {
        if(data != null) {
            final GalleryType type = (GalleryType) data.getSerializable(Arguments.TYPE);
            final int count = data.getInt(Arguments.COUNT);
            if(type != null) {
                (new Worker.SimpleTask() {
                    @Override
                    public void run() {
                        try {
                            Inbound.Feed feed = httpHelper.getPhotos(type, count);
                            PhotoCache.updateCache(type, feed.photos);

                        } catch (HttpHelper.ServerException | IllegalArgumentException
                                    | NullPointerException ex) {
                            ex.printStackTrace();
                            this.exception = ex;
                        }
                    }
                }).execute(new Worker.SimpleTask.Callback() {
                    @Override
                    public void onComplete(Exception ex) {
                        notifyChanges(ex == null ? Actions.GET_PHOTOS
                                                 : Actions.GET_PHOTOS_EXCEPTION);
                    }
                });
            }
        }
    }

    public void placePhoto(String url, ImageView view) {
        httpHelper.loadImage(url, view);
    }

    private void notifyChanges(int action) {
        LocalBroadcaster.sendLocalBroadcast(action, null, context);
    }


    public interface Actions {

        int GET_PHOTOS = 0x0000ca11;
        int GET_PHOTOS_EXCEPTION = 0x00000ca12;

    }


    public interface Arguments {

        String TYPE = "type";
        String COUNT = "count";
        String POSITION = "position";

    }

}
