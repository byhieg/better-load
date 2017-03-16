package cn.byhieg.betterload.download;

import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;
import java.net.SocketTimeoutException;

import cn.byhieg.betterload.network.NetService;
import cn.byhieg.betterload.utils.FailureMessage;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by byhieg on 17/3/14.
 * Contact with byhieg@gmail.com
 */

public class DownLoadTask implements Runnable{

    private final String TAG = DownLoadTask.class.getSimpleName();
    private String saveFileName;
    private IDownLoadTaskListener listener;
    private Call<ResponseBody> call;
    private long fileSizeDownloaded;
    private long needDownSize;
    private DownLoadEntity entity;
    private final long CALL_BACK_LENGTH = 1024;
    private FailureMessage failureMessage;


    public DownLoadTask(DownLoadEntity entity, IDownLoadTaskListener listener) {
        this.entity = entity;
        this.listener = listener;
        this.saveFileName = entity.getFileName();
        this.needDownSize = entity.getEnd() - (entity.getStart() + entity.getLoadedData());
        failureMessage = new FailureMessage();
    }


    @Override
    public void run() {
        call = NetService.getInstance().getDownLoadService().downloadFile(entity.getUrl(),
                "bytes=" + entity.getStart() + "-" + entity.getEnd());

        ResponseBody result = null;
        try{
            Response response = call.execute();
            result = (ResponseBody) response.body();
            Logger.e(result.bytes().length + "");
            if (response.isSuccessful()) {
                if (writeToFile(result,entity.getStart(),entity.getLoadedData())){
                    onCompleted();
                }
            }else {
                onError(new Throwable(response.message()));
            }
        } catch (IOException e) {
            onError(new Throwable(e.getMessage()));
        } finally {
            if (result != null) {
                result.close();
            }
        }
    }

    private boolean writeToFile(ResponseBody body, long startSet, long mDownedSet) {
        try {
            File tmpFile = new File(saveFileName);
            if (!tmpFile.exists()) {
                if(!tmpFile.createNewFile()){
                    return false;
                }
            }
            RandomAccessFile oSavedFile = new RandomAccessFile(tmpFile, "rwd");
            oSavedFile.seek(startSet + mDownedSet);
            InputStream inputStream = body.byteStream();
            while (needDownSize >= 0) {
                try{
                    BufferedSource source = Okio.buffer(Okio.source(inputStream));
                    byte [] bytes = source.readByteArray(1024);
                    fileSizeDownloaded += bytes.length;
                    oSavedFile.write(bytes);
                }finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            }

//            try {
//                inputStream = body.byteStream();
//                while (fileSizeDownloaded < needDownSize) {
//                    Logger.e("fileSizeDownloaded = " + fileSizeDownloaded + " needDownSize = " + needDownSize );
//                    Logger.e("inputstream" + inputStream.available());
//                    BufferedSource source = Okio.buffer(Okio.source(inputStream));
//
//                    oSavedFile.write(baos.toByteArray());
//
//                    fileSizeDownloaded += read;
//                    Logger.e("fileSizeDownloaded = " + fileSizeDownloaded);
//                    if (fileSizeDownloaded >= CALL_BACK_LENGTH) {
//                        onDownLoading(fileSizeDownloaded);
//                        needDownSize -= fileSizeDownloaded;
//                        fileSizeDownloaded = 0;
//                    } else {
//                        if (needDownSize < CALL_BACK_LENGTH) {
//                            if (fileSizeDownloaded - 1 == needDownSize) {
//                                onDownLoading(fileSizeDownloaded);
//                                break;
//                            }
//                        }
//                    }
//                }
//                return true;
//            } finally {
//                oSavedFile.close();
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//            }
        } catch (IOException e) {
            if (e instanceof InterruptedIOException && !(e instanceof SocketTimeoutException)) {
                onCancel();
            } else {
                onError(e);
            }
            return false;
        }
    }

    private void onStart(){
        listener.onStart();
    }

    private void onCancel(){
        call.cancel();
        call = null;
        listener.onCancel(entity);
    }

    private void onCompleted(){
        call = null;
        listener.onCompleted(entity);
    }

    private void onError(Throwable throwable){
        failureMessage.clear();
        failureMessage.setResultCode(-1);
        failureMessage.setFailureMessage(throwable.getMessage());
        listener.onError(entity,failureMessage);
    }

    private void onDownLoading(long downSize){
        listener.onDownLoading(downSize);
        entity.setLoadedData(entity.getLoadedData() + downSize);
    }


    public static final class Builder{
        private DownLoadEntity entity;
        private IDownLoadTaskListener listener;

        public Builder downLoadEntity(DownLoadEntity entity) {
            this.entity = entity;
            return this;
        }

        public Builder IDownLoadTaskListener(IDownLoadTaskListener downLoadTaskListener){
            this.listener = downLoadTaskListener;
            return this;
        }

        public DownLoadTask build(){
            if (entity.getUrl().isEmpty()) {
                throw new IllegalStateException("DownLoad URL required.");
            }

            if (listener == null) {
                throw new IllegalStateException("DownLoadTaskListener required.");
            }

            if (entity.getEnd() == 0) {
                throw new IllegalStateException("End required.");
            }
            return new DownLoadTask(entity, listener);
        }
    }

}
