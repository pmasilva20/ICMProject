package com.example.icmproject.notification;

import android.os.AsyncTask;

import com.google.gson.JsonObject;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NotificationManager extends AsyncTask<String, Integer, Void> {

    private static final String TAG = "notificationManager";

    public void sendNotification(String receiverToken,String title,String body,String dataValue) {
        HttpURLConnection urlConnection = null;

        try {
            JsonObject object = new JsonObject();
            object.addProperty("to",receiverToken);
            JsonObject data = new JsonObject();
            data.addProperty("content",dataValue);

            JsonObject notification = new JsonObject();
            notification.addProperty("title",title);
            notification.addProperty("body",body);

            object.add("notification",notification);
            object.add("data",data);

            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Authorization", "key=AAAA2UbOTb8:APA91bFfcZhfuMCq8jznXACp1c0rnGGVist_aq7d4hU6CdgJGsPtGqloWSIVaQiqS0vRtqP7D1t_1b7Dmr0Pve39MI_pjUBY6AzpzmSRhELtSD1GtwfLSXIfZ9zkhxkOaM-RskDAPAKX");
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setChunkedStreamingMode(0);


            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    out, "UTF-8"));
            writer.write(object.toString());
            writer.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    @Override
    protected Void doInBackground(String... strings) {
        sendNotification(strings[0],strings[1],strings[2],strings[3]);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
