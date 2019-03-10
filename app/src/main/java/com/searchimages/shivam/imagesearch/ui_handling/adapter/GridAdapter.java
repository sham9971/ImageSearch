package com.searchimages.shivam.imagesearch.ui_handling.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.searchimages.shivam.imagesearch.CommonFunctions;
import com.searchimages.shivam.imagesearch.R;
import com.searchimages.shivam.imagesearch.api_handling.pojo.Value;
import com.searchimages.shivam.imagesearch.ui_handling.MainActivity;
import com.searchimages.shivam.imagesearch.ui_handling.fragement.ImagePagerFragment;



public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ImageViewHolder> {
    List<Value> arrayImages;
    int gridWidth;
    int viewFrom;
    Context mContext;


    private interface ViewHolderListener {

        void onLoadCompleted(ImageView view, int adapterPosition);

        void onItemClicked(View view, int adapterPosition, List<Value> values, int viewFrom);
    }

    private final ViewHolderListener viewHolderListener;


    public GridAdapter(Context mContext, Fragment fragment, List<Value> arrayImages, int gridWidth, int viewFrom) {
        this.gridWidth = gridWidth;
        this.mContext = mContext;
        this.viewFrom = viewFrom;
        this.arrayImages = arrayImages;
        this.viewHolderListener = new ViewHolderListenerImpl(fragment);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_cart, parent, false);
        return new ImageViewHolder(mContext, view, viewHolderListener, arrayImages, gridWidth, viewFrom);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.onBind();
    }

    @Override
    public int getItemCount() {
        return arrayImages.size();
    }

    private static class ViewHolderListenerImpl implements ViewHolderListener {

        private Fragment fragment;
        private AtomicBoolean enterTransitionStarted;

        ViewHolderListenerImpl(Fragment fragment) {
            this.fragment = fragment;
            this.enterTransitionStarted = new AtomicBoolean();
        }

        @Override
        public void onLoadCompleted(ImageView view, int position) {
            if (MainActivity.currentPosition != position) {
                return;
            }
            if (enterTransitionStarted.getAndSet(true)) {
                return;
            }
            fragment.startPostponedEnterTransition();
        }


        @Override
        public void onItemClicked(View view, int position, List<Value> values, int viewFrom) {

                        MainActivity.currentPosition = position;

            ((TransitionSet) fragment.getExitTransition()).excludeTarget(view, true);

            Fragment imagePagerFragment = new ImagePagerFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(CommonFunctions.imagesData, (Serializable) values);
            bundle.putInt(CommonFunctions.viewFrom, viewFrom);
            imagePagerFragment.setArguments(bundle);

            ImageView transitioningView = view.findViewById(R.id.card_image);
            fragment.getFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addSharedElement(transitioningView, transitioningView.getTransitionName())
                    .replace(R.id.fragment_container, imagePagerFragment, ImagePagerFragment.class
                            .getSimpleName())
                    .addToBackStack(null)
                    .commit();
        }
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        List<Value> arrayImages;
        int gridWidth;
        int viewFrom;
        Context mContext;
        private final ImageView image;
        private final ViewHolderListener viewHolderListener;

        ImageViewHolder(Context mContext, View itemView,
                        ViewHolderListener viewHolderListener, List<Value> arrayImages, int gridWidth, int viewFrom) {
            super(itemView);
            this.gridWidth = gridWidth;
            this.mContext = mContext;
            this.viewFrom = viewFrom;
            this.arrayImages = arrayImages;
            this.image = itemView.findViewById(R.id.card_image);
            this.viewHolderListener = viewHolderListener;
            itemView.findViewById(R.id.card_view).setOnClickListener(this);
        }

        void onBind() {
            int adapterPosition = getAdapterPosition();

            setImage(adapterPosition);

            image.setLayoutParams(new FrameLayout.LayoutParams(gridWidth, gridWidth));

            image.setTransitionName(arrayImages.get(adapterPosition).getThumbnailUrl());
        }

        void setImage(final int adapterPosition) {
            if (viewFrom == CommonFunctions.Online) {
                Picasso.with(mContext)
                        .load(arrayImages.get(adapterPosition).getThumbnailUrl())
                        .into(image, new Callback() {
                            @Override
                            public void onSuccess() {
                                viewHolderListener.onLoadCompleted(image, adapterPosition);
                            }

                            @Override
                            public void onError() {
                                viewHolderListener.onLoadCompleted(image, adapterPosition);
                            }
                        });
            } else {
                byte[] byteArray = arrayImages.get(adapterPosition).getImageByteArray();
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                image.setImageBitmap(bmp);
                viewHolderListener.onLoadCompleted(image, adapterPosition);
            }
        }

        @Override
        public void onClick(View view) {
            viewHolderListener.onItemClicked(view, getAdapterPosition(), arrayImages, viewFrom);
        }
    }

}