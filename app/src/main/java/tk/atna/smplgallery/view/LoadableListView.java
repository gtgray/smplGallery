package tk.atna.smplgallery.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import tk.atna.smplgallery.R;

public class LoadableListView extends ListView {

    private static final int RESOURCE = R.layout.loadable_list_view_progress;

    private View footer;


    public LoadableListView(Context context) {
        super(context);

        initProgress(context);
    }

    public LoadableListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initProgress(context);
    }

    public LoadableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initProgress(context);
    }

    public void showFooterProgress(boolean show) {
        if(show && getFooterViewsCount() == 0)
            addFooterView(footer, null, false);

        else if(!show && getFooterViewsCount() == 1)
            removeFooterView(footer);
    }

    private void initProgress(Context context) {
        footer = LayoutInflater.from(context).inflate(RESOURCE, this, false);
        addFooterView(footer, null, false);
    }

}