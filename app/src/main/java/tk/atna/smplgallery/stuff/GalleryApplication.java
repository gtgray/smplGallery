package tk.atna.smplgallery.stuff;

import android.app.Application;

public class GalleryApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // init content manager
        ContentManager.init(this);

    }

}
