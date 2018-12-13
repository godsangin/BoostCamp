package com.msproject.myhome.boostcamp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoadingThread extends Thread{
    String text;
    String jsonString;

    String clientId = "czu_PRY2ilBm5txkTC7W";//애플리케이션 클라이언트 아이디값";
    String clientSecret = "VHKTwqCM4C";//애플리케이션 클라이언트 시크릿값";

//    Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            Bundle bun = msg.getData();
//            jsonString = bun.getString("JSON_DATA");
//
//            Log.d("jsonString==", jsonString);
//        }
//    };

    public LoadingThread(String text){
        this.text = text;
    }
    public void run() {
        synchronized (this) {
            jsonString = getDataString(text);

            Bundle bun = new Bundle();
//            bun.putString("JSON_DATA", naverHtml);

//            Message msg = handler.obtainMessage();
//            msg.setData(bun);
//            handler.sendMessage(msg);
            notify();
        }
    }

    public String getDataString(String text){
        String result = "";
        try{
            String encodeText = URLEncoder.encode(text, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/movie.json?query="+ encodeText; // json 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            Log.d("response==",response.toString());
            result = response.toString();
        }catch (Exception e) {

        }
        return result;
    }

    public synchronized String getJsonString(){
        return this.jsonString;
    }
}
