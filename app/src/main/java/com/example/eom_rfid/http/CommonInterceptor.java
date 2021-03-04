package com.example.eom_rfid.http;




import com.example.eom_rfid.base.AppManager;
import com.example.eom_rfid.base.BaseApplication;
import com.example.eom_rfid.utils.Constants;
import com.example.eom_rfid.utils.DialogUtil;
import com.example.eom_rfid.utils.SPUtil;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 封装公共参数（Key和密码） url拦截 code 拦截
 */
public class CommonInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        /**
         * 请求中的Url
         */
        HttpUrl oldUrl = oldRequest.url();
        /**
         * 应用配置的服务器Url
         */
        String spBaseUrl = Constants.BASE_URL;
        /**
         * Request的url
         */
        HttpUrl parseUrl = null;

        if (oldUrl.toString().contains("eom-uploader")) {
//            parseUrl = HttpUrl.parse(oldUrl.toString().replace(Constants.BASE_URL, Constants.BASE_URL1));
        } else {
            /**
             * 对url 进行处理 当本地sp 存储的是和Constants不同的url 的时候进行替换BaseUrl的操作
             */
            if (!spBaseUrl.equals(oldUrl.toString())) {
                try {
                    //如果是Apk包下载链接，则无需转换，直接获取下载地址
                    if (oldUrl.toString().contains("apk")) {
                        parseUrl = HttpUrl.parse(oldUrl.toString());
                    } else {
                        //生成转换的url
                        parseUrl = HttpUrl.parse(spBaseUrl + oldUrl.toString().replace(Constants.BASE_URL, ""));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {//相同的则直接转换
                parseUrl = oldUrl;
            }
        }
        /**
         * Request 对象
         */
        Request newRequest = null;
        /**
         * 拦截器  登录的请求的时候不进行header 的设置
         */
        if (!oldUrl.toString().contains("login")) {
            // 普通请求
            newRequest = oldRequest.newBuilder()
                    //类型
                    .addHeader("Content-Type", "application/json")
                    //请求的token
                    .addHeader(Constants.TOKEN,
                            SPUtil.getToken(BaseApplication.getInstance().getApplicationContext()))
                    .method(oldRequest.method(), oldRequest.body())
                    .url(parseUrl)
                    .build();
        } else {
            // 登录请求
            newRequest = oldRequest.newBuilder()
                    //类型
                    .addHeader("Content-Type", "application/json")
                    .method(oldRequest.method(), oldRequest.body())
                    .url(parseUrl)
                    .build();
        }
        Response response = chain.proceed(newRequest);
        // 对返回code统一拦截
        if (response != null) {
            if (response.code() == Constants.TOKEN_INVALID_CODE) {
                DialogUtil.showCallbackDialog(AppManager.getAppManager().currentActivity(), response.message());
            }
        }
        return response;
    }
}
