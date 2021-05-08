package com.duantotnghiep.iwash.api;

import com.duantotnghiep.iwash.model.ScheduleBody;
import com.duantotnghiep.iwash.model.ServerResponse;

import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @POST("api/register")
    @FormUrlEncoded
    Call<ServerResponse> resgisterUser(@Field("fullName") String fullName,
                                       @Field("phoneNumber") String phoneNumber,
                                       @Field("address") String address,
                                       @Field("passWord") String password);

    @POST("api/login")
    @FormUrlEncoded
    Call<ServerResponse> login(@Field("phoneNumber") String phoneNumber,
                               @Field("passWord") String password,
                               @Field("tokenDevice") String tokenDevice);

    @GET("api/news")
    Call<ServerResponse> getNews();

    @GET("api/user")
    Call<ServerResponse> getUserInfo();

    @GET("api/vehicles")
    Call<ServerResponse> getVehicle();

    @GET("api/services")
    Call<ServerResponse> getServices();

    @POST("api/book")
    Call<ServerResponse> book(@Body ScheduleBody scheduleBody);

    @POST("api/add-vehicle")
    @FormUrlEncoded
    Call<ServerResponse> addVehicle(@Field("name") String name,
                                    @Field("type") String type,
                                    @Field("license") String license,
                                    @Field("color") String color,
                                    @Field("brand") String brand);

    @POST("api/vehicle/update/{id}")
    @FormUrlEncoded
    Call<ServerResponse> updateVehicle(@Path("id") String id,
                                       @Field("name") String name,
                                       @Field("type") String type,
                                       @Field("license") String license,
                                       @Field("color") String color,
                                       @Field("brand") String brand);

    @GET("api/vehicle/delete/{id}")
    Call<ServerResponse> deleteVehicle(@Path("id") String id);

    @GET("api/user/schedule")
    Call<ServerResponse> getSchedulesUser();

    @GET("api/user/notify")
    Call<ServerResponse> getNotifyUser();

    @GET("api/user/schedule/{id}")
    Call<ServerResponse> getSchedule(@Path("id") String id);

    @GET("api/schedules")
    Call<ServerResponse> getAllSchedule();

    @GET("api/schedules/pending")
    Call<ServerResponse> getSchedulePending();

    @POST("api/schedule/{id}/confirm")
    @FormUrlEncoded
    Call<ServerResponse> confirm(@Path("id") String id,
                                 @Field("status") String status);

    @POST("api/schedule/{id}/complete")
    @FormUrlEncoded
    Call<ServerResponse> complete(@Path("id") String id,
                                  @Field("status") String status);

    @POST("api/schedule/{id}/confirmVehicle")
    @FormUrlEncoded
    Call<ServerResponse> confirmVehicle(@Path("id") String id, @Field("vehicleStatus") boolean vehicleStatus);

    @POST("api/schedule/{id}/cancel")
    @FormUrlEncoded
    Call<ServerResponse> cancel(@Path("id") String id,
                                @Field("note") String note,
                                @Field("status") String status);

    @POST("api/user/updatePass")
    @FormUrlEncoded
    Call<ServerResponse> changePassword(@Field("currentPass") String currentPass,
                                        @Field("newPass") String newPass);

    @Multipart
    @POST("api/user/update")
    Call<ServerResponse> updateInfo(@Part("fullName") String fullName,
                                    @Part("address") String address,
                                    @Part MultipartBody.Part avatar);
}
