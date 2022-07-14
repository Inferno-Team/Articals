package com.inferno.mobile.articals.services;

import com.inferno.mobile.articals.models.ApprovedArticle;
import com.inferno.mobile.articals.models.Article;
import com.inferno.mobile.articals.models.ArticlesResponse;
import com.inferno.mobile.articals.models.GetMyArticleResponse;
import com.inferno.mobile.articals.models.LoginResponse;
import com.inferno.mobile.articals.models.MasterArticleResponse;
import com.inferno.mobile.articals.models.MasterRequest;
import com.inferno.mobile.articals.models.MessageResponse;
import com.inferno.mobile.articals.models.User;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    String BASE_URL = "http://192.168.43.113:8000/";
    String PDF_URL = "http://192.168.43.113:8000/";
//    String BASE_URL = "http://192.168.1.6:8000/";
//    String PDF_URL = "http://192.168.1.6:8000";

    @POST("api/login")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @POST("api/signup")
    @FormUrlEncoded
    Call<LoginResponse> signup(@Field("first_name") String firstName, @Field("last_name") String lastName,
                               @Field("email") String email, @Field("password") String password,
                               @Field("type") String userType, @Field("field_id") int fieldId,
                               @Field("fcm_token") String fcmToken);

    @GET("api/get_fields")
    Call<ArrayList<com.inferno.mobile.articals.models.Field>> getAllFields();

    // common
    @GET("api/common/get_recent_articales")
    Call<ArticlesResponse> getRecentArticles(@Header("Authorization") String token,
                                             @Query("page")int pageNumber);

    @GET("api/common/get_article_details/{id}")
    Call<Article> getArticleDetails(@Header("Authorization") String token,
                                    @Path("id") int id);

    @GET("api/common/download_article_file/{id}")
    Call<Object> downloadPDF(@Header("Authorization") String token,
                             @Path("id") int id);

    //master
    @GET("api/master/get_my_articles")
    Call<GetMyArticleResponse> getMasterArticles(@Header("Authorization") String token);

    @GET("api/master/get_doctor_of_field")
    Call<ArrayList<User>> getDoctors(@Header("Authorization") String token);


    @Multipart
    @POST("api/master/add_artical")
    Call<MessageResponse<Article>> addArticle(
            @Header("Authorization") String token,
            @Part("name") RequestBody name,
            @Part("type") RequestBody type,
            @Part("university_name") RequestBody univName,
            @Part("doctor_id") RequestBody doctorId,
            @Part MultipartBody.Part file
    );


    @GET("api/doctor/get_my_articles")
    Call<GetMyArticleResponse> getDoctorArticles(@Header("Authorization") String token);

    @Multipart
    @POST("api/doctor/add_artical")
    Call<MessageResponse<Article>> addDoctorArticle(
            @Header("Authorization") String token,
            @Part("name") RequestBody name,
            @Part("type") RequestBody type,
            @Part("university_name") RequestBody univName,
            @Part MultipartBody.Part file
    );

    @GET("api/doctor/get_all_master_requests")
    Call<ArrayList<MasterRequest>> getAllMasterRequests(@Header("Authorization") String token);

    @POST("api/doctor/approve_artical")
    @FormUrlEncoded
    Call<MessageResponse<ApprovedArticle>> approveArticle(
            @Header("Authorization") String token, @Field("artical_id") int id);


    @GET("api/admin/get_all_users_requests")
    Call<ArrayList<User>> getAllUsersRequests(@Header("Authorization") String token);

    @POST("api/admin/approve_user")
    @FormUrlEncoded
    Call<MessageResponse<Object>> approveUser(@Header("Authorization") String token,
                                              @Field("approved")String type,
                                              @Field("user_id")int id
                                              );
}
