package com.example.sss.infinity.api;

import com.google.android.gms.common.api.Api;

public class ApiUtils {
    public static final String NEWS_URL="http://shamgar.online/infinity/";

    public static CategoryApi getCategoryApi()
    {
        return RetrofitClient.getClient(NEWS_URL).create(CategoryApi.class);
    }

}
