package tk.atna.smplgallery.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import tk.atna.smplgallery.R;
import tk.atna.smplgallery.adapter.DetailPagerAdapter;
import tk.atna.smplgallery.model.GalleryType;
import tk.atna.smplgallery.receiver.LocalBroadcaster;
import tk.atna.smplgallery.stuff.ContentManager;
import tk.atna.smplgallery.stuff.OnLoadListener;

public class DetailActivity extends BaseActivity
                            implements OnLoadListener, LocalBroadcaster.LocalActionListener {

    private LocalBroadcaster broadcaster;

    private static final int DEFAULT_OFFSCREEN_PAGES_LIMIT = 7;

    private GalleryType type;
    private int position;
    private DetailPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        processArgs(savedInstanceState == null ?
                getIntent().getExtras() : savedInstanceState);

        if(type == null) {
            onBackPressed();
            return;
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_detail);
        viewPager.setAdapter(
                adapter = new DetailPagerAdapter(getSupportFragmentManager(), type, this));
        viewPager.setOffscreenPageLimit(DEFAULT_OFFSCREEN_PAGES_LIMIT);
        viewPager.setCurrentItem(position);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(ContentManager.Arguments.TYPE, type);
        outState.putInt(ContentManager.Arguments.POSITION, position);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // start listen to local broadcaster
        registerReceiver(broadcaster = new LocalBroadcaster(this),
                new IntentFilter(LocalBroadcaster.LOCAL_BROADCAST_FILTER));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcaster);
    }

    @Override
    public void onNeedLoad(int count) {
        Bundle data = new Bundle();
        data.putSerializable(ContentManager.Arguments.TYPE, type);
        data.putInt(ContentManager.Arguments.COUNT, count);
        ContentManager.get().makeActionAsync(ContentManager.Actions.GET_PHOTOS, data);
    }

    @Override
    public void onReceive(int action, Bundle data) {
        switch (action) {
            case ContentManager.Actions.GET_PHOTOS:
                refreshAdapter();
                break;
        }
    }

    private void refreshAdapter() {
        if(adapter != null)
            adapter.refreshData();
    }

    private void processArgs(Bundle args) {
        if (args != null) {
            type = (GalleryType) args.getSerializable(ContentManager.Arguments.TYPE);
            position = args.getInt(ContentManager.Arguments.POSITION);
        }
    }

}
