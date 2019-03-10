package com.searchimages.shivam.imagesearch.api_handling.retrofit;

import com.searchimages.shivam.imagesearch.CommonFunctions;
import com.searchimages.shivam.imagesearch.api_handling.pojo.ImageData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("search?mkt=en-in&safeSearch=Strict")
    @Headers("Ocp-Apim-Subscription-Key:"+ CommonFunctions.BingKey)
    Call<ImageData> getListOfImages(@Query("q") String searchTerm, @Query("offset") String offset, @Query("count") String count);

}
