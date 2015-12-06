package tk.atna.smplgallery.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import tk.atna.smplgallery.R;
import tk.atna.smplgallery.adapter.TypedListAdapter;
import tk.atna.smplgallery.model.GalleryType;
import tk.atna.smplgallery.stuff.ContentManager;
import tk.atna.smplgallery.stuff.OnLoadListener;
import tk.atna.smplgallery.view.LoadableListView;

public class TypedFragment extends InteractiveFragment
                            implements OnLoadListener, AdapterView.OnItemClickListener {

    private static final String TYPE = "type";
    private GalleryType type;

    private LoadableListView lvData;
    private TypedListAdapter adapter;


    public static Bundle dataToBundle(GalleryType type) {
        Bundle args = new Bundle();
        args.putSerializable(TYPE, type);
        return args;
    }

    public static GalleryType bundleToData(Bundle args) {
        if(args != null)
            return (GalleryType) args.getSerializable(TYPE);

        return null;
    }

    public TypedFragment() {
    }

    @Override
    void processArguments(@NonNull Bundle args) {
        type = bundleToData(args);
        if (type == null)
            throw new IllegalArgumentException("Can't instantiate "
                    + this.getClass().getSimpleName() + "with no argument");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_typed, container, false);
        lvData = (LoadableListView) view.findViewById(R.id.lv_data);
        lvData.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvData.setCacheColorHint(0);
        lvData.setVerticalFadingEdgeEnabled(false);
        lvData.setAdapter(adapter == null ?
                adapter = new TypedListAdapter(getContext(), type, this) : adapter);
        lvData.showFooterProgress(true);
        lvData.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getPhotos(adapter.getCount());
    }

    @Override
    public void onReceive(int action, Bundle data) {
        switch (action) {
            case ContentManager.Actions.GET_PHOTOS:
                refreshAdapter();
                lvData.showFooterProgress(false);
                break;
//            case ContentManager.Actions.GET_PHOTOS_EXCEPTION:
//                break;
        }
    }

    @Override
    public void onNeedLoad(int count) {
        getPhotos(count);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle data = new Bundle();
        data.putSerializable(ContentManager.Arguments.TYPE, type);
        data.putInt(ContentManager.Arguments.POSITION, (int) id);
        makeFragmentAction(ACTION_NEXT, data);
    }

    private void getPhotos(int count) {
        // show progress
        lvData.showFooterProgress(true);
        // load new data
        Bundle data = new Bundle();
        data.putSerializable(ContentManager.Arguments.TYPE, type);
        data.putInt(ContentManager.Arguments.COUNT, count);
        contentManager.makeActionAsync(ContentManager.Actions.GET_PHOTOS, data);
    }

    private void refreshAdapter() {
        if(adapter != null)
            adapter.refreshData();
    }
}
