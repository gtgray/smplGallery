package tk.atna.smplgallery.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tk.atna.smplgallery.R;
import tk.atna.smplgallery.fragment.InteractiveFragment;

public abstract class BaseActivity extends AppCompatActivity
                                    implements InteractiveFragment.FragmentActionCallback {


    public static void display(Context context, Class clazz) {
        display(context, clazz, null);
    }

    public static void display(Context context, Class clazz, Bundle data) {
        if(context != null && clazz != null) {
            Intent intent = new Intent(context, clazz);
            // set additional data if needed
            if(data != null)
                intent.putExtras(data);
            // launch
            context.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // orientation change is available only for tablets
        if (!getResources().getBoolean(R.bool.isTablet)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAction(int action, Bundle data) {
        // override to use
    }

}
