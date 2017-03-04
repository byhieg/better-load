package cn.byhieg.betterload.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.byhieg.betterload.utils.FailureMessage;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by byhieg on 17/3/2.
 * Contact with byhieg@gmail.com
 */

public class NetService {


    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private HttpLoggingInterceptor loggingInterceptor;

    private NetService() {
        loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private static NetService netService = new NetService();

    public static NetService getInstance() {
        return netService;
    }


    public NetService init(String baseUrl) {
        synchronized (this) {
            okHttpClient = new OkHttpClient.Builder().
                    readTimeout(15, TimeUnit.SECONDS).
                    writeTimeout(10, TimeUnit.SECONDS).
                    connectTimeout(10, TimeUnit.SECONDS).
                    addInterceptor(loggingInterceptor).
                    build();

            retrofit = new Retrofit.Builder().
                    addConverterFactory(ScalarsConverterFactory.create()).
                    addConverterFactory(GsonConverterFactory.create()).
                    baseUrl(baseUrl).
                    client(okHttpClient).
                    build();
        }

        return this;
    }


    public <T> T create(Class<T> clz) {
        return retrofit.create(clz);
    }


    public <T> void asynRequest(final Call<T> requestCall, final IResonseListener<T> resonseListener) {

        final FailureMessage failureMessage = new FailureMessage();
        if (resonseListener == null) {
            return;
        }

        Call<T> call;
        if (requestCall.isExecuted()) {
            call = requestCall.clone();
        } else {
            call = requestCall;
        }


        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                int resultCode = response.code();
                if (response.isSuccessful()) {
                    T result = response.body();
                    if (result == null) {
                        failureMessage.clear();
                        failureMessage.setResultCode(resultCode);
                        failureMessage.setFailureMessage("body为空");
                        resonseListener.onFailure(failureMessage.toString());
                    } else {
                        resonseListener.onSuccess(result);
                    }

                } else {
                    failureMessage.clear();
                    failureMessage.setResultCode(resultCode);
                    failureMessage.setFailureMessage(resultCode + "错误");
                    resonseListener.onFailure(failureMessage.toString());

                }


            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                failureMessage.clear();
                failureMessage.setResultCode(-1);
                failureMessage.setFailureMessage(t.getMessage());
                resonseListener.onFailure(failureMessage.toString());
            }
        });

    }

    public <T> void syncRequest(final Call<T> requestCall,final IResonseListener<T> resonseListener){

        final FailureMessage failureMessage = new FailureMessage();
        if (resonseListener == null) {
            return;
        }

        Call<T> call;
        if (requestCall.isExecuted()) {
            call = requestCall.clone();
        } else {
            call = requestCall;
        }

        try{
            Response<T> response = call.execute();
            int resultCode = response.code();
            if (response.isSuccessful()) {
                T result = response.body();
                if (result == null) {
                    failureMessage.clear();
                    failureMessage.setResultCode(resultCode);
                    failureMessage.setFailureMessage("body为空");
                    resonseListener.onFailure(failureMessage.toString());
                }else {
                    resonseListener.onSuccess(result);
                }
            }else {
                failureMessage.clear();
                failureMessage.setResultCode(resultCode);
                failureMessage.setFailureMessage(resultCode + "错误");
                resonseListener.onFailure(failureMessage.toString());
            }
        } catch (IOException e) {
            failureMessage.clear();
            failureMessage.setResultCode(-1);
            failureMessage.setFailureMessage(e.getMessage());
            resonseListener.onFailure(failureMessage.toString());
        }

    }




}
