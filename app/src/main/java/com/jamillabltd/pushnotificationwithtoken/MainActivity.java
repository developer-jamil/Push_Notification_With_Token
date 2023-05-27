package com.jamillabltd.pushnotificationwithtoken;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TOPIC = "/topics/myTopic2";
    private static final String TAG = "MainActivity";
    private EditText etTitle, etMessage, etToken;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etMessage = findViewById(R.id.etMessage);
        etToken = findViewById(R.id.etToken);
        btnSend = findViewById(R.id.btnSend);

        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);

        //get token and sent push specific person
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
            FirebaseService.setToken(token);
            etToken.setText(token);
        });

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        btnSend.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String message = etMessage.getText().toString();
            String recipientToken = etToken.getText().toString();
            if (!title.isEmpty() && !message.isEmpty() && !recipientToken.isEmpty()) {
                NotificationData notificationData = new NotificationData(title, message);
                PushNotification pushNotification = new PushNotification(notificationData, recipientToken);
                sendNotification(pushNotification);
            }
        });
    }

    private void sendNotification(PushNotification notification) {
        RetrofitInstance.getApi().postNotification(notification).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body() != null ? response.body().string() : "";
                        Log.d(TAG, "Response: " + responseBody);
                        showToast("Push notification sent successfully");
                    } catch (IOException e) {
                        Log.e(TAG, "IOException: " + e.getMessage());
                        showToast("Failed to send push notification");
                    }
                } else {
                    Log.e(TAG, Objects.requireNonNull(response.errorBody()).toString());
                    showToast("Failed to send push notification");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                showToast("Failed to send push notification");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
