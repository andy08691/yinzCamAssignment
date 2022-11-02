package com.example.yinzcamassignment.tools;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import androidx.lifecycle.MutableLiveData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Repository {
    private static Repository instance;
    private final OkHttpClient client = new OkHttpClient();
    private MutableLiveData<JsonObject> response = new MutableLiveData<>();
    public static Repository getInstance() {
        if(instance == null) {
            instance = new Repository();
        }
        return instance;
    }
    private Repository() {}

    public MutableLiveData<JsonObject> getResponse() {
        return response;
    }

    public void setResponse(JsonObject response) {
        this.response.postValue(response);
    }

    public void callApi(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("callApi onFailure", e.getLocalizedMessage());
                e.printStackTrace();
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }
                    String data = responseBody.string();
                    JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
                    setResponse(jsonObject);
                }
            }
        });
    }

}
