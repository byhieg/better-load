package cn.byhieg.betterloaddemo;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cn.byhieg.betterload.network.IResonseListener;
import cn.byhieg.betterload.network.NetService;
import cn.byhieg.betterloaddemo.bean.FileBean;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class MainActivity extends Activity implements View.OnClickListener {

    public Button button;
    public TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.download);
        button.setOnClickListener(this);
        NetService.getInstance().init("http://wx2.sinaimg.cn");
        textView = (TextView) findViewById(R.id.text);

    }

    @Override
    public void onClick(View v) {
//        Call<FileBean> call = NetService.getInstance().create(HtmlService.class).getFiles();
//        NetService.getInstance().asynRequest(call, new IResonseListener<FileBean>() {
//            @Override
//            public void onSuccess(FileBean response) {
//                textView.setText(response.getFileList().get(0).getFileName());
//            }
//
//            @Override
//            public void onFailure(String message) {
//                textView.setText(message);
//            }
//        });
        Call<ResponseBody> call = NetService.getInstance().getDownLoadService().
                downloadFile("/mw600/005vbOHfgy1fdfnf5dom0j30rs15ok2l.jpg");

        NetService.getInstance().asynRequest(call, new IResonseListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody response) {
                BufferedInputStream bis = new BufferedInputStream(response.byteStream());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte [] bytes = new byte[1024];
                try {
                    while (bis.read(bytes) >= 0) {
                        baos.write(bytes);
                    }
                    Log.e("fileLength", baos.toByteArray().length + "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                textView.setText(message);
            }
        });
    }
}
