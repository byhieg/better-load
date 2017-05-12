package cn.byhieg.betterloaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import cn.byhieg.betterload.network.BaseApiService;
import cn.byhieg.betterload.network.NetService;
import cn.byhieg.betterload.operator.BaseFlatMapOp;
import cn.byhieg.betterload.operator.BaseSubscriber;
import cn.byhieg.betterloaddemo.bean.FileBean;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class SecondActivity extends AppCompatActivity {

    public TextView result;
    public HtmlService service;
    public BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        result = (TextView) findViewById(R.id.result);
        NetService.getInstance().init("http://115.159.145.201:8079/api/v1");
        service = NetService.getInstance().create(HtmlService.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Observable<FileBean> observable = service.rxGet("download/files");
        Subscriber<FileBean.FileListBean> subcribe = new BaseSubscriber<FileBean.FileListBean>() {
            @Override
            public void onCompleted() {
                Toast.makeText(getApplicationContext(),"已经完成",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(FileBean.FileListBean fileListBean) {
                result.append(fileListBean.getFileName() + "\n");
            }
        };

//                new Subscriber<FileBean.FileListBean>() {
//            @Override
//            public void onCompleted() {
//                Toast.makeText(getApplicationContext(),"已经完成",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(FileBean.FileListBean fileListBean) {
//                result.append(fileListBean.getFileName() + "\n");
//            }
//        };

            NetService.getInstance().rxRequest(observable, subcribe, new BaseFlatMapOp<FileBean, FileBean.FileListBean>() {
                @Override
                public Observable<FileBean.FileListBean> call(FileBean fileBean) {
                    return Observable.from(fileBean.getFileList());
                }
            });
//        observable.subscribeOn(Schedulers.io()).
//                unsubscribeOn(Schedulers.io()).
//                flatMap(new Func1<FileBean, Observable<FileBean.FileListBean>>() {
//                    @Override
//                    public Observable<FileBean.FileListBean> call(FileBean fileBean) {
//                        return Observable.from(fileBean.getFileList());
//                    }
//                }).
//                observeOn(AndroidSchedulers.mainThread()).
//                subscribe(subcribe);


//        NetService.getInstance().asynRequest(service.getFiles(), new IResponseListener<FileBean>() {
//            @Override
//            public void onSuccess(FileBean response) {
//                for (int i = 0; i < response.getFileList().size(); i++) {
//                    result.append(response.getFileList().get(i).getFileName() + "\n");
//                }
//            }
//
//            @Override
//            public void onFailure(String message) {
//                Log.e("失败信息" ,message);
//            }
//        });
    }
}
