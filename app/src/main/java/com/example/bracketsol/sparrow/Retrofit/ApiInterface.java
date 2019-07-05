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
import retrofit2.http.PUT;
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
    Call<ResponseBody> getSocialLife(@Query("page") int page);

    @GET("api/posts")
    Call<ResponseBody> getAllPosts(@Query("page") int page);

    @FormUrlEncoded
    @POST("api/posts/like")
    Call<ResponseBody> hitLikePost(@Field("post_id") int post_id);

    @DELETE("api/posts/like")
    Call<ResponseBody> hitDisLikePost(@Query("post_id") int post_id);

    @FormUrlEncoded
    @POST("api/posts/comment")
    Call<ResponseBody> addPostComment(@Field("post_id") int post_id,
                                      @Field("content") String content);

    @GET("api/posts/comment/")
    Call<ResponseBody> getPreviousPostComment(@Query("post_id") int post_id);

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

    @GET("api/users/profile/")
    Call<ResponseBody> getProfile(@Query("user_id") int userid);

    @Multipart
    @POST("api/posts")
    Call<ResponseBody> sendPost(@Part("type") String type,
                                @Part("content") String content,
                                @Part("hasFile") int hasFile,
                                @Part("background") String background,
                                @Part MultipartBody.Part fileUpload);

    @Multipart
    @POST("api/announcements/announcement")
    Call<ResponseBody> sendAnnouncement(@Part("type") String type,
                                        @Part("statement") String statement,
                                        @Part("start_date") String start_date,
                                        @Part("end_date") String end_date,
                                        @Part("hasFile") int hasFile,
                                        @Part("background") String background,
                                        @Part MultipartBody.Part fileUpload);

    @Multipart
    @PUT("api/users/")
    Call<ResponseBody> updataProfileData(@Part("name") String name,
                                         @Part("email") String email,
                                         @Part("phone_no") String phone_no,
                                         @Part("profession") String profession,
                                         @Part("statement") String statement,
                                         @Part("blog") String blog,
                                         @Part("date_of_birth") String date_of_birth,
                                         @Part("gender") String gender,
                                         @Part("hasFile") int hasFile,
                                         @Part MultipartBody.Part fileUpload);

    @PUT("api/users/")
    Call<ResponseBody> updataProfileData(@Field("name") String name,
                                         @Field("email") String email,
                                         @Field("phone_no") String phone_no,
                                         @Field("profession") String profession,
                                         @Field("statement") String statement,
                                         @Field("blog") String blog,
                                         @Field("date_of_birth") String date_of_birth,
                                         @Field("gender") String gender);

    @FormUrlEncoded
    @POST("api/announcements/like")
    Call<ResponseBody> hitLikeAnnouncement(@Field("announcement_id") int announcement_id);

    @GET("api/announcements/comment/")
    Call<ResponseBody> getPreviousAnnouncementComment(@Query("announcement_id") int announcement_id);

    @FormUrlEncoded
    @POST("api/announcements/comment")
    Call<ResponseBody> addAnnouncementComment(@Field("announcement_id") int announcement_id,
                                      @Field("content") String content);

}