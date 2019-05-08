package com.example.bracketsol.sparrow.Interceptor;

import android.content.Context;

import com.example.bracketsol.sparrow.Activities.Welcome;
import com.example.bracketsol.sparrow.MyApp;
import com.example.bracketsol.sparrow.Utils.Prefs;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain)
            throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("authorization", "Bearer "+ Prefs.getUserToken(MyApp.getContext()))
                .build();
        okhttp3.Response response = chain.proceed(request);
        return response;
    }
}