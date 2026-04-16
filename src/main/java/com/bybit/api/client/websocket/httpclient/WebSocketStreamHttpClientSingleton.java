package com.bybit.api.client.websocket.httpclient;

import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import static com.bybit.api.client.log.Slf4jLoggingInterceptor.HandleLoggingInterceptor;

@Getter
public final class WebSocketStreamHttpClientSingleton {
    private final boolean debugMode;
    private final String logOption;

    private WebSocketStreamHttpClientSingleton(boolean debugMode, String logOption) {
        this.debugMode = debugMode;
        this.logOption = logOption;
    }

    public static WebSocketStreamHttpClientSingleton createInstance(boolean debugMode, String logOption) {
        return new WebSocketStreamHttpClientSingleton(debugMode, logOption);
    }

    public OkHttpClient createOkHttpClient(boolean debugMode, String logOption) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (debugMode) {
            HandleLoggingInterceptor(clientBuilder, logOption);
        }
        return clientBuilder.build();
    }

    public WebSocket createWebSocket(String url, WebSocketListener listener) {
        return createWebSocket(url, listener, false);
    }

    public WebSocket createWebSocket(String url, WebSocketListener listener, boolean demoTrading) {
        Request.Builder reqBuilder = new Request.Builder().url(url);
        if (demoTrading) {
            reqBuilder.addHeader("X-BAPI-DEMO-TRADING", "1");
        }
        Request request = reqBuilder.build();
        OkHttpClient okHttpClient = createOkHttpClient(debugMode, logOption);
        return okHttpClient.newWebSocket(request, listener);
    }
}
