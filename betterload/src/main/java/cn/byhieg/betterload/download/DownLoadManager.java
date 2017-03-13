package cn.byhieg.betterload.download;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.transform.dom.DOMLocator;

/**
 * Created by byhieg on 17/3/13.
 * Contact with byhieg@gmail.com
 */

public class DownLoadManager {

    private static DownLoadManager downLoadManager;
    private int threadNum = 1;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    private DownLoadManager(){}

    public static DownLoadManager getInstance(){
        if (downLoadManager == null) {
            synchronized (DownLoadManager.class) {
                if (downLoadManager == null) {
                    downLoadManager = new DownLoadManager();
                }
            }
        }
        return downLoadManager;
    }

    public void download(List<DownLoadEntity> list, IDownLoadListener listener, int threadNum) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                DownLoadRequest downLoadRequest = new DownLoadRequest();
            }
        });
    }
}