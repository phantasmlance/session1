package com.example.session1.services;

import com.example.session1.constants.AppConstants;

public class APIUtilities {

    public static final String baseUrl = AppConstants.API_HOME;

    public static IDataClient getData() {
        return RetrofitConfig.getClient().create(IDataClient.class);
    }
}
