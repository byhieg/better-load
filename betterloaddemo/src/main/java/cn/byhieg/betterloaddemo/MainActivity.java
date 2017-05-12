package cn.byhieg.betterloaddemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.io.File;
import java.text.DecimalFormat;

import cn.byhieg.betterload.download.DownLoadEntity;
import cn.byhieg.betterload.download.BetterLoad;
import cn.byhieg.betterload.download.IDownLoadListener;
import cn.byhieg.betterload.network.IResponseListener;
import cn.byhieg.betterload.network.NetService;
import cn.byhieg.betterload.utils.FailureMessage;
import retrofit2.Call;

public class MainActivity extends Activity implements View.OnClickListener {

    public Button button;
    public TextView textView;
    public DonutProgress mProgressBar;
    public TextView next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.download);
        button.setOnClickListener(this);
        next = (TextView) findViewById(R.id.next);

        next.setOnClickListener(this);
        NetService.getInstance().init("http://wx2.sinaimg.cn");
        textView = (TextView) findViewById(R.id.text);
        mProgressBar = (DonutProgress) findViewById(R.id.donut_progress);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next) {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        }else{
            DownLoadEntity entity = new DownLoadEntity();
            entity.setUrl("http://dlsw.baidu.com/sw-search-sp/soft/70/17456/BaiduAn_Setup_8.1.0.7141.1459396875.exe");
            entity.setFileName(Environment.getExternalStorageDirectory().getAbsolutePath() + File
                    .separator + "3.exe");
            BetterLoad.getInstance().download(entity, new IDownLoadListener() {
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
                    DecimalFormat df = new DecimalFormat("######0.00");
                    int value = (int) (percent * 100);
                    Log.e("value",value + "");
                    textView.setText(df.format(percent * 100));
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
}
