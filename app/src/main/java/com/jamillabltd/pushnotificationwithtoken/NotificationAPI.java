package com.jamillabltd.pushnotificationwithtoken;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificationAPI {

    @Headers({"Authorization: key=" + Constants.SERVER_KEY, "Content-Type:" + Constants.CONTENT_TYPE})
    @POST("fcm/send")
    Call<ResponseBody> postNotification(
            @Body PushNotification notification
    );
}
