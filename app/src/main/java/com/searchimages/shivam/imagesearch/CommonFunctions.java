package com.searchimages.shivam.imagesearch;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class CommonFunctions {

    public static final int DataBase = 1;
    public static final int Online = 2;

    public static final String imagesData = "Images";
    public static final String viewFrom = "viewFrom";

    public static final String BingKey = "3a255fea07d941ef97e436c8541eb038";

    public static boolean checkInternetConnection(Context mContext) {
        ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected();
    }

    public static int screenWidth(Activity mContext) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static void HideKeyboard(View edt_search_term) {
        if (edt_search_term != null) {
            InputMethodManager imm = (InputMethodManager) edt_search_term.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edt_search_term.getWindowToken(), 0);
        }
    }
}
