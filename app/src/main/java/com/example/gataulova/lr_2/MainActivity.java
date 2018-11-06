package com.example.gataulova.lr_2;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    TextView view;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button2);
        view = findViewById(R.id.textView2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeHttpRequest();
            }
        });
    }

    private void makeHttpRequest() {
        view.setText("Загрузка...");

        OkHttpClient client = new OkHttpClient();

        try {
            Request request = new Request.Builder()
                    .url("https://widgets.coinmarketcap.com/v2/ticker/1/?ref=widget&convert=USD")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    view.setText("Ошибка...");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        view.setText("Ошибка...");
                    }

                    String string = response.body().string();

                    try {
                        JSONObject json = new JSONObject(string);

                        view.setText(
                            "BTC/USD: " +
                            json
                                .getJSONObject("data")
                                .getJSONObject("quotes")
                                .getJSONObject("USD")
                                .getString("price")
                        );
                    } catch (JSONException e) {
                        view.setText("Ошибка...");
                    }
                }
            });
        } catch (Exception ex) {
            view.setText("Ошибка...");
        }
    }
}
