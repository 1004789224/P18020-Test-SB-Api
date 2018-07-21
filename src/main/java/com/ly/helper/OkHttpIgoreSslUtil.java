package com.ly.helper;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;

public class OkHttpIgoreSslUtil {
    private static Logger LOG= LoggerFactory.getLogger(OkHttpIgoreSslUtil.class);
    private static final SSLContext trustAllSslContext;
    private static final TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };
    static {
        try {
            trustAllSslContext = SSLContext.getInstance("SSL");
            trustAllSslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }
    private static final SSLSocketFactory trustAllSslSocketFactory = trustAllSslContext.getSocketFactory();

    public static OkHttpClient trustAllSslClient(OkHttpClient client) {
        OkHttpClient.Builder builder = client.newBuilder();
        builder.sslSocketFactory(trustAllSslSocketFactory, (X509TrustManager)trustAllCerts[0]);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        return builder.build();
    }
    public  static  String  requestPostByForm(String actionUrl,HashMap<String,String> paramsMap) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                builder.add(key, paramsMap.get(key));
            }
            RequestBody formBody = builder.build();
            final Request request = addHeaders().url(actionUrl).post(formBody).build();
            OkHttpClient client = OkHttpIgoreSslUtil.trustAllSslClient(new OkHttpClient());
            final Call call = client.newCall(request);
            Response response = call.execute();
            return response.body().string();
        } catch (Exception e) {
            LOG.error(e.toString());
        }
        return null;
    }
    public  static  String  requestPostByJson(String actionUrl,HashMap<String,String> paramsMap) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                builder.add(key, paramsMap.get(key));
            }
            RequestBody formBody = builder.build();
            final Request request = addHeaders().url(actionUrl).post(formBody).build();
            OkHttpClient client = OkHttpIgoreSslUtil.trustAllSslClient(new OkHttpClient());
            final Call call = client.newCall(request);
            Response response = call.execute();
            return response.body().string();
        } catch (Exception e) {
            LOG.error(e.toString());
        }
        return null;
    }
    /**
     * 统一为请求添加头信息
     * @return
     */
    private static Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", "keep-alive");
        return builder;
    }
}
