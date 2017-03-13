package cn.byhieg.betterload.download;

import java.util.Iterator;
import java.util.List;

import cn.byhieg.betterload.utils.FailureMessage;

/**
 * Created by byhieg on 17/3/13.
 * Contact with byhieg@gmail.com
 */

public class DownLoadRequest {

    private DownLoadHandle downLoadHandle;

    private List<DownLoadEntity> list;
    private FailureMessage failMessage;

    private IDownLoadListener listener;

    public DownLoadRequest(List<DownLoadEntity> list,IDownLoadListener listener){
        this.list = list;
        this.listener = listener;
        failMessage = new FailureMessage();
    }


    public void start(){
        List<DownLoadEntity> queryList = downLoadHandle.queryDownLoadInfo(list);
        Iterator it = queryList.iterator();
        long totalFileSize = 0;
        long hasDownSize = 0;

        while (it.hasNext()) {
            final DownLoadEntity entry = (DownLoadEntity) it.next();
            hasDownSize += entry.getDownedData();
            if (entry.getTotal() == 0) {
                failMessage.clear();
                failMessage.setFailureMessage("文件读取失败");
                failMessage.setResultCode(-1);
                listener.onError(entry,failMessage);
                return;
            }
        }
    }
}
