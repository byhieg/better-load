package cn.byhieg.betterload;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import cn.byhieg.betterload.net.netinterfaces.ILoadService;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends Activity implements View.OnClickListener {

    public Button button;

    public Retrofit retrofit;
    public Call<ResponseBody> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.download);
        button.setOnClickListener(this);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder().
                addInterceptor(httpLoggingInterceptor).
                connectTimeout(1000, TimeUnit.MILLISECONDS);

        String baseUrl = "http://192.168.0.18:8079/";
        String url = baseUrl + "api/v1/download/file?" + "fileName=1488451796985headicon.jpg";
        retrofit = new Retrofit.Builder().
                baseUrl(baseUrl).
                client(httpClientBuilder.build()).
                build();
        ILoadService loadService = retrofit.create(ILoadService.class);
        call = loadService.downloadFile(url);


    }

    @Override
    public void onClick(View v) {

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                InputStream inputStream = response.body().byteStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                try {
                    while (inputStream.read(bytes) != -1) {
                        baos.write(bytes);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("content", baos.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("fail", "下载失败");
            }
        });
            

    }
}
