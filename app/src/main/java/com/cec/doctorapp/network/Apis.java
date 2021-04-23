
package com.cec.doctorapp.network;


import com.cec.doctorapp.model.request.GetBookData;
import com.cec.doctorapp.model.request.GetNotificationModel;
import com.cec.doctorapp.model.request.GetOtpModel;
import com.cec.doctorapp.model.request.NotificationModel;
import com.cec.doctorapp.model.response.BookingDataModel;
import com.cec.doctorapp.model.response.DocInfoModel;
import com.cec.doctorapp.model.response.GetAvailabilityModel;
import com.cec.doctorapp.model.response.GetAllPriceModel;
import com.cec.doctorapp.model.response.GetBookResponseBeanModel;
import com.cec.doctorapp.model.response.GetConsentform;
import com.cec.doctorapp.model.response.GetFeedModel;
import com.cec.doctorapp.model.response.NotificationItemModel;
import com.cec.doctorapp.model.response.OtpRegisterResponseModelBean;
import com.cec.doctorapp.model.response.PatientRegisterModel;
import com.cec.doctorapp.model.response.PatientRegisterResponseBean;
import com.cec.doctorapp.model.response.ResultModel;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface Apis {

//    patient reg
    @POST("patient_register")
    Observable<ResultModel<PatientRegisterResponseBean>> patientregister(@Body PatientRegisterModel request);

    @POST("verify_otp")
    Observable<ResultModel<OtpRegisterResponseModelBean>> getOtp(@Body GetOtpModel request);

    //   pricing
    @GET("availability")
    Observable<ResultModel <GetAvailabilityModel>> getavailabilty();


    @GET("get_prices")
    Observable<ResultModel<ArrayList<GetAllPriceModel>>> getallprice();
    @GET("get_doctor_info")
    Observable<ResultModel<DocInfoModel>>getDoctorInfo();
    //    @GET("/{event_name}")
//    Observable<FacebookPageId> getPageId(@Path("event_name") String eventName, @Query("access_token") String accessToken);

//    @GET("/{page_id}/posts/")
//    Observable<ResultModel<ArrayList<GetFeedModel>>> getPosts(@Path("page_id") String pageId, @Query("fields") String fields, @Query("access_token") String accessToken);
    @POST("post_device_token")
    Observable<ResultModel<GetNotificationModel>> notification(@Body GetNotificationModel request);


    @POST("get_notificatios_data")
    Observable<ResultModel<ArrayList<NotificationItemModel>>>getnotification(@Body NotificationModel request);

    @GET("/{page_id}/feed?fields=id,created_time,message,full_picture,permalink_url")
    Observable<ResultModel<ArrayList<GetFeedModel>>> getfeed(@Path("page_id") String pageId,
                                                              @Query("fields") String fields,
                                                              @Query("access_token") String accessToken);
    @POST("get_booking_data")
    Observable<ResultModel<ArrayList<GetBookResponseBeanModel>>> get_booking_data(@Body GetBookData request);


    @POST("get_booking_times")
    Observable<ResultModel<ArrayList<BookingDataModel>>> get_booking_times(@Body GetBookData request);
    @GET("get_consent_form")
    Observable<ResultModel<GetConsentform>> getconsentform();

    @POST("save_pdf_data")
    Observable<ResultModel> savepdfdata(
            @Body MultipartBody body);

}
