package com.yourname.cloudclient.util;

import com.yourname.cloudclient.config.Config;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.CompletableFuture;

public class TranslateUtil {
    private static final String BAIDU_API_URL = "https://fanyi-api.baidu.com/api/trans/vip/translate";

    public static void translateAsync(String text, Callback callback) {
        CompletableFuture.runAsync(() -> {
            try {
                String result = translate(text);
                callback.onResult(result);
            } catch (Exception e) {
                e.printStackTrace();
                callback.onResult(null);
            }
        });
    }

    private static String translate(String text) throws Exception {
        String appId = Config.baiduAppId;
        String secret = Config.baiduSecret;
        if (appId.isEmpty() || secret.isEmpty()) {
            return "[未配置百度翻译 API]";
        }

        String salt = String.valueOf(System.currentTimeMillis());
        String sign = MD5Util.md5(appId + text + salt + secret);

        String urlStr = BAIDU_API_URL + "?q=" + URLEncoder.encode(text, "UTF-8")
                + "&from=zh&to=en&appid=" + appId + "&salt=" + salt + "&sign=" + sign;

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        conn.disconnect();

        JsonObject json = new JsonParser().parse(sb.toString()).getAsJsonObject();
        if (json.has("trans_result")) {
            return json.getAsJsonArray("trans_result").get(0).getAsJsonObject().get("dst").getAsString();
        } else {
            return "[翻译失败: " + json.toString() + "]";
        }
    }

    public interface Callback {
        void onResult(String translated);
    }

    private static class MD5Util {
        public static String md5(String input) {
            try {
                java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
                byte[] digest = md.digest(input.getBytes("UTF-8"));
                StringBuilder sb = new StringBuilder();
                for (byte b : digest) {
                    sb.append(String.format("%02x", b & 0xff));
                }
                return sb.toString();
            } catch (Exception e) {
                return "";
            }
        }
    }
}