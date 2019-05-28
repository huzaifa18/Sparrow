package com.example.bracketsol.sparrow.Retrofit;

import android.net.Uri;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @FormUrlEncoded
    @POST("api/messages/chat/")
    Call<ResponseBody> sendMessage(@Field("content") String content,
                                   @Field("sender_id") int sender_id,
                                   @Field("receiver_id") int receiver_id);



    @POST("api/messages/chat/")
    Call<ResponseBody> sendMessageWithFile(
                                            @Field("content") String content,
                                            @Field("sender_id") int sender_id,
                                            @Field("receiver_id") int receiver_id,
                                            @Field("token") String token,
                                            @Field("msgData") String msgData);



    @Multipart
    @POST("api/messages/chat/")
    Call<UploadObject> uploadFile(
                                    @Part("msgData") MultipartBody.Part msgData,
                                    @Part("content") String content,
                                    @Part("sender_id") int sender_id,
                                    @Part("receiver_id") int receiver_id,
                                    @Part("token") String token);



    @GET("api/messages/chat/")
    Call<ResponseBody> getAllMessage();

    @GET("api/announcements/announcement")
    Call<ResponseBody> getSocialLife();


    @GET("api/messages/chat-specific/")
    Call<ResponseBody> getSpecificMessage(@Query("sender_id") int sender_id,
                                          @Query("receiver_id") int receiver_id);


}
