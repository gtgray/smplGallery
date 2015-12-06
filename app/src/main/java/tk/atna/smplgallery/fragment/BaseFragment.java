package tk.atna.smplgallery.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import tk.atna.smplgallery.stuff.ContentManager;

public abstract class BaseFragment extends Fragment {

    ContentManager contentManager = ContentManager.get();


	public static <T extends Fragment> T newInstance(Class<T> clazz) {
        return newInstance(clazz, new Bundle());
    }

	public static <T extends Fragment> T newInstance(Class<T> clazz, Bundle data) {
        try {
			T fragment = clazz.newInstance();
            fragment.setRetainInstance(true);
            // args are always present(not null),
            // even if they are not necessary
			fragment.setArguments(data);
			return fragment;

		} catch (InstantiationException | java.lang.InstantiationException
                        | IllegalAccessException e) {
			e.printStackTrace();
		}
        return null;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(getArguments() == null)
			throw new IllegalArgumentException("Use static newInstance() method to create "
					+ this.getClass().getSimpleName());
		else
			processArguments(getArguments());
	}

	void processArguments(@NonNull Bundle args) {
        // override to use
    }

}