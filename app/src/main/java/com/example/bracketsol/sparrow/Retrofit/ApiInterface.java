package com.example.bracketsol.sparrow.Retrofit;

import android.net.Uri;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

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


    @GET("api/posts")
    Call<ResponseBody> getAllPosts(@Query("page") int page);

    @FormUrlEncoded
    @POST("api/posts/like")
    Call<ResponseBody> hitLike(@Field("post_id") int post_id);

    @DELETE("api/posts/like")
    Call<ResponseBody> hitDisLike(@Query("post_id") int post_id);


    @FormUrlEncoded
    @POST("api/posts/comment")
    Call<ResponseBody> addComment(@Field("post_id") int post_id,
                                  @Field("content") String content);

    @GET("api/posts/comment/")
    Call<ResponseBody> getPreviousComment(@Query("post_id") int post_id);


    @GET("api/messages/chat-specific/")
    Call<ResponseBody> getSpecificMessage(@Query("sender_id") int sender_id,
                                          @Query("receiver_id") int receiver_id);


    //token
    @FormUrlEncoded
    @POST("api/firebase/add-device/")
    Call<ResponseBody> sendTokenToServer(@Field("token") String token,
                                        @Field("platform_os") String platform,
                                        @Field("user_id") int userid);

    @Multipart
    @POST("api/posts")
    Call<ResponseBody> sendMessagevideo(
                                @Part MultipartBody.Part fileUpload);

}
