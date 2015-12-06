package tk.atna.smplgallery.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import tk.atna.smplgallery.R;
import tk.atna.smplgallery.adapter.TypedPagerAdapter;
import tk.atna.smplgallery.fragment.InteractiveFragment;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_photos);
        viewPager.setAdapter(new TypedPagerAdapter(this, getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onAction(int action, Bundle data) {
        switch (action) {
            case InteractiveFragment.ACTION_NEXT:
                display(this, DetailActivity.class, data);
                break;
        }
    }

}
