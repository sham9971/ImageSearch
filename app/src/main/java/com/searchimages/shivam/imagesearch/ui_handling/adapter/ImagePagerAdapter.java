package com.searchimages.shivam.imagesearch.ui_handling.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import com.searchimages.shivam.imagesearch.ui_handling.fragement.ImageFragment;
import com.searchimages.shivam.imagesearch.api_handling.pojo.Value;

public class ImagePagerAdapter extends FragmentStatePagerAdapter {
    private List<Value> arrayImages;
    private int viewFrom;

    public ImagePagerAdapter(Fragment fragment,List<Value> arrayImages,int viewFrom) {
        super(fragment.getChildFragmentManager());
        this.arrayImages=arrayImages;
        this.viewFrom=viewFrom;
    }

    @Override
    public int getCount() {
        return arrayImages.size();
    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(arrayImages.get(position),viewFrom);
    }
}

