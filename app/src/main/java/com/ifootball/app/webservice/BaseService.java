package com.ifootball.app.webservice;

import android.preference.PreferenceActivity;

import com.ifootball.app.framework.http.BetterHttp;
import com.ifootball.app.util.CustomerUtil;
import com.ifootball.app.util.ForgotPasswordUtil;
import com.ifootball.app.util.StringUtil;

import java.io.IOException;
import java.net.HttpURLConnection;

public class BaseService {

    private static final String XNEWEGGAPPID_KEY = "x-newegg-app-id";
    private static final String XNEWEGGAPPID_VALUE = "i-tiqiu.com";
    private static final String REQUEST_COOKIE_KEY = "Cookie";
    private static final String RESPONSE_COOKIE_KEY = "Set-Cookie";


    public static final int EnvironmentType_LANTestRelease = 0;
    public static final int EnvironmentType_WWWTestRelease = 1;
    public static final int EnvironmentType_ProductionRelease = 2;

    public static final int CURRENTEN_ENVIRONMENT_TYPE = EnvironmentType_LANTestRelease;
    protected static final String URL_EXTENSION = ".egg";
    public static String RESTFUL_SERVICE_HOST_WWW = null;
    public static String RESTFUL_SERVICE_HOST_SSL = null;

    static {
        switch (CURRENTEN_ENVIRONMENT_TYPE) {

            case EnvironmentType_LANTestRelease:
                /*RESTFUL_SERVICE_HOST_WWW = "http://182.150.28.110:9100/";
                RESTFUL_SERVICE_HOST_SSL = "http://182.150.28.110:9100/";*/
                RESTFUL_SERVICE_HOST_WWW = "http://192.168.60.101:9100/";
                RESTFUL_SERVICE_HOST_SSL = "http://192.168.60.101:9100/";
                break;
            case EnvironmentType_WWWTestRelease:
                RESTFUL_SERVICE_HOST_WWW = "http://app.kjt.com.pre/";
                RESTFUL_SERVICE_HOST_SSL = "http://app.kjt.com.pre/";
                break;
            case EnvironmentType_ProductionRelease:
                RESTFUL_SERVICE_HOST_WWW = "http://app.kjt.com/";
                RESTFUL_SERVICE_HOST_SSL = "http://app.kjt.com/";
                break;
        }
    }

    protected static String read(String urlString) throws IOException, ServiceException {
        HttpURLConnection httpClient = BetterHttp.getHttpClient(urlString, "GET", false);
        addAuthKeyHeader(httpClient);
        int statusCode = httpClient.getResponseCode();
        if (isSuccessful(statusCode)) {
            return BetterHttp.get(httpClient);
        }
        throw new ServiceException(statusCode);
    }

    protected static String create(String urlString, String bodyString)
            throws IOException, ServiceException {
        return create(urlString, bodyString, false);
    }

    protected static String create(String urlString, String bodyString, boolean isForm) throws IOException,
            ServiceException {
        HttpURLConnection httpClient = BetterHttp.getHttpClient(urlString, "POST", isForm);
        addAuthKeyHeader(httpClient);
        BetterHttp.setBody(httpClient, bodyString);
        int statusCode = httpClient.getResponseCode();
        if (isSuccessful(statusCode)) {
            return BetterHttp.get(httpClient);
        }
        throw new ServiceException(statusCode);
    }

    protected static String createRegister(String urlString, String bodyString)
            throws IOException, ServiceException {

        HttpURLConnection httpClient = BetterHttp.getHttpClient(urlString, "POST", false);
        addAuthKeyHeader(httpClient);
        BetterHttp.setBody(httpClient, bodyString);
        int statusCode = httpClient.getResponseCode();
        if (isSuccessful(statusCode)) {
            String cookie = httpClient.getHeaderField(RESPONSE_COOKIE_KEY);

            if (cookie != null) {
                CustomerUtil.cacheAuthenTick(formatCookie(cookie));
            }
            return BetterHttp.get(httpClient);
        }
        throw new ServiceException(statusCode);
    }

    protected static String login(String urlString, String bodyString)
            throws IOException, ServiceException {

        HttpURLConnection httpClient = BetterHttp.getHttpClient(urlString, "POST", true);
        httpClient.setRequestProperty(XNEWEGGAPPID_KEY, XNEWEGGAPPID_VALUE);
        BetterHttp.setBody(httpClient, bodyString);
        int statusCode = httpClient.getResponseCode();
        if (isSuccessful(statusCode)) {
            String cookie = httpClient.getHeaderField(RESPONSE_COOKIE_KEY);

            if (cookie != null) {
                CustomerUtil.cacheAuthenTick(formatCookie(cookie));
            }
            return BetterHttp.get(httpClient);
        }
        throw new ServiceException(statusCode);
    }

    protected static String forgotPassword(String urlString, String bodyString)
            throws IOException, ServiceException {
        HttpURLConnection httpClient = BetterHttp.getHttpClient(urlString, "POST", false);
        addForgotPasswordHeader(httpClient);
        BetterHttp.setBody(httpClient, bodyString);
        int statusCode = httpClient.getResponseCode();
        if (isSuccessful(statusCode)) {
            String cookie = httpClient.getHeaderField(RESPONSE_COOKIE_KEY);

            if (cookie != null) {
                ForgotPasswordUtil.cacheCookie(formatCookie(cookie));
            }
            return BetterHttp.get(httpClient);
        }
        throw new ServiceException(statusCode);
    }

    protected static String update(String urlString, String bodyString)
            throws IOException, ServiceException {
        return update(urlString, bodyString, false);
    }

    protected static String update(String urlString, String bodyString, boolean isForm) throws IOException, ServiceException {
        // TODO:
        // 虽然update和create都是post，但是他们有个标记:"connection.addRequestProperty("X-OP", "Update");"
        // 还是不一样，所以还需评估在这里是否可以直接调用create方法
        return create(urlString, bodyString, isForm);
    }

    protected static String delete(String urlString)
            throws IOException, ServiceException {
        HttpURLConnection httpClient = BetterHttp.getHttpClient(urlString, "DELETE", false);
        int statusCode = httpClient.getResponseCode();
        if (isSuccessful(statusCode)) {
            return BetterHttp.get(httpClient);
        }
        throw new ServiceException(statusCode);
    }

    private static boolean isSuccessful(int statusCode) {
        return statusCode >= HttpURLConnection.HTTP_OK && statusCode < 300;
    }

    private static void addForgotPasswordHeader(HttpURLConnection request) {
        if (!StringUtil.isEmpty(ForgotPasswordUtil.getCookie())) {
            request.setRequestProperty(REQUEST_COOKIE_KEY,
                    ForgotPasswordUtil.getCookie());
        }
        request.setRequestProperty(XNEWEGGAPPID_KEY, XNEWEGGAPPID_VALUE);
    }

    private static void addAuthKeyHeader(HttpURLConnection request) {
        String authKey = CustomerUtil.getAuthenTick();
        if (authKey != null && authKey.length() > 0) {
            request.setRequestProperty(REQUEST_COOKIE_KEY, authKey);
        }
        request.setRequestProperty(XNEWEGGAPPID_KEY, XNEWEGGAPPID_VALUE);
    }

    private static String formatCookie(String cookie) {
        if (!StringUtil.isEmpty(cookie)) {
            return cookie.substring(0, cookie.indexOf(";"));
        }

        return cookie;

    }
}
