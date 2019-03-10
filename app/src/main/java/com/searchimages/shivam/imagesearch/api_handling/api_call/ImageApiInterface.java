package com.searchimages.shivam.imagesearch.api_handling.api_call;

import android.content.Context;

import com.searchimages.shivam.imagesearch.api_handling.retrofit.APIInterface;
import com.searchimages.shivam.imagesearch.api_handling.pojo.ImageData;
import retrofit2.Call;

public interface ImageApiInterface {

    interface onApiFinishedListener {
        void onApiSuccess(ImageData response, String searchTerm);

        void onApiFailure(String message);
    }

    void cancelRequest(Context mContext,ImageApiInterface.onApiFinishedListener listener);

    void imageSearchAPI(Context mContext,String searchTerm, int offset, int count, ImageApiInterface.onApiFinishedListener listener, APIInterface api,
                        Call<ImageData> call);
}
