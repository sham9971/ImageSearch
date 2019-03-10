package com.searchimages.shivam.imagesearch.ui_handling.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import com.searchimages.shivam.imagesearch.CommonFunctions;
import com.searchimages.shivam.imagesearch.ui_handling.MainActivity;
import com.searchimages.shivam.imagesearch.R;
import com.searchimages.shivam.imagesearch.ui_handling.adapter.ImagePagerAdapter;
import com.searchimages.shivam.imagesearch.api_handling.pojo.Value;

/**
 * A fragment for displaying a pager of images.
 */
public class ImagePagerFragment extends Fragment {

    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewPager = (ViewPager) inflater.inflate(R.layout.fragement_pager, container, false);
        List<Value> arrayImages = (List<Value>) getArguments().get(CommonFunctions.imagesData);
        viewPager.setAdapter(new ImagePagerAdapter(this, arrayImages, getArguments().getInt(CommonFunctions.viewFrom)));
        // Set the current position and add a listener that will update the selection coordinator when
        // paging the images.
        viewPager.setCurrentItem(MainActivity.currentPosition);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                MainActivity.currentPosition = position;
            }
        });

        prepareSharedElementTransition();

        // Avoid a postponeEnterTransition on orientation change, and postpone only of first creation.
        if (savedInstanceState == null) {
            postponeEnterTransition();
        }

        return viewPager;
    }


    private void prepareSharedElementTransition() {


        setEnterSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        Fragment currentFragment = (Fragment) viewPager.getAdapter()
                                .instantiateItem(viewPager, MainActivity.currentPosition);
                        View view = currentFragment.getView();
                        if (view == null) {
                            return;
                        }

                        // Map the first shared element name to the child ImageView.
                        sharedElements.put(names.get(0), view.findViewById(R.id.image));
                    }
                });
    }
}

