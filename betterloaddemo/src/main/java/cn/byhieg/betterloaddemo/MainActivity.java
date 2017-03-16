package cn.byhieg.betterloaddemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

import cn.byhieg.betterload.download.DownLoadEntity;
import cn.byhieg.betterload.download.DownLoadManager;
import cn.byhieg.betterload.download.IDownLoadListener;
import cn.byhieg.betterload.network.NetService;
import cn.byhieg.betterload.utils.FailureMessage;

public class MainActivity extends Activity implements View.OnClickListener {

    public Button button;
    public TextView textView;
    public ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.download);
        button.setOnClickListener(this);
        NetService.getInstance().init("http://wx2.sinaimg.cn");
        textView = (TextView) findViewById(R.id.text);
        mProgressBar = (ProgressBar) findViewById(R.id.firstBar);
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
//        Call<ResponseBody> call = NetService.getInstance().getDownLoadService().
//                downloadFile("/mw600/005vbOHfgy1fdfnf5dom0j30rs15ok2l.jpg");
//
//        NetService.getInstance().asynRequest(call, new IResonseListener<ResponseBody>() {
//            @Override
//            public void onSuccess(ResponseBody response) {
//                BufferedInputStream bis = new BufferedInputStream(response.byteStream());
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                byte [] bytes = new byte[1024];
//                try {
//                    while (bis.read(bytes) >= 0) {
//                        baos.write(bytes);
//                    }
//                    Log.e("fileLength", baos.toByteArray().length + "");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(String message) {
//                textView.setText(message);
//            }
//        });

        DownLoadEntity entity = new DownLoadEntity();
        entity.setUrl("http://dlsw.baidu.com/sw-search-sp/soft/70/17456/BaiduAn_Setup_8.1.0.7141.1459396875.exe");
        entity.setFileName(Environment.getExternalStorageDirectory().getAbsolutePath() + File
                .separator + "3.exe");
        DownLoadManager.getInstance().download(entity, new IDownLoadListener() {
            @Override
            public void onStart(double percent) {
                textView.setText("准备下载");
                mProgressBar.setProgress((int)percent * 100);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onDownloading(double percent) {
                int value = (int)percent * 100;
                textView.setText(value +"");
                mProgressBar.setProgress(value);
            }

            @Override
            public void onCompleted() {
                textView.setText("下载完成");
            }

            @Override
            public void onError(DownLoadEntity downLoadEntity, FailureMessage message) {
                textView.setText(message.getFailureMessage());
            }
        });

    }
}
