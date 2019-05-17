package com.example.bracketsol.sparrow.Retrofit;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {






//    @FormUrlEncoded
//    @POST("users/register")
//    Call<ResponseBody> Register(@Field("username") String name,
//                                @Field("email") String email,
//                                @Field("password") String password);
//
//
////    @FormUrlEncoded
////    @POST("auth/login")
////    Call<User> Login(@Field("username") String username,
////                     @Field("password") String password);
//
//
//    @Headers("Content-Type: application/json")
//    @POST("auth/login")
//    Call<User> Login(@Body JSONObject body);
//
//
////    Call<ResponseBody> Login(@Body User user);


    @GET("api/messages/chat/")
    Call<ResponseBody> getAllMessage();

    @GET("api/announcements/announcement")
    Call<ResponseBody> getSocialLife();


    @GET("api/messages/chat-specific/")
    Call<ResponseBody> getSpecificMessage(@Query("sender_id") int sender_id,
                                          @Query("receiver_id") int receiver_id);
}
