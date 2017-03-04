package cn.byhieg.betterloaddemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.byhieg.betterload.network.IResonseListener;
import cn.byhieg.betterload.network.NetService;
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
        NetService.getInstance().init("https://www.baidu.com");
        textView = (TextView) findViewById(R.id.text);

    }

    @Override
    public void onClick(View v) {
        Call<String> call = NetService.getInstance().create(HtmlService.class).getHtml();
        NetService.getInstance().asynRequest(call, new IResonseListener<String>() {
            @Override
            public void onSuccess(String response) {
               textView.setText(response);
            }

            @Override
            public void onFailure(String message) {
                textView.setText(message);
            }
        });
    }
}
