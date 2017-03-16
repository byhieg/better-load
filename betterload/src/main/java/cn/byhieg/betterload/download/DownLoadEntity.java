package cn.byhieg.betterload.download;

import java.io.Serializable;

import okhttp3.MediaType;

/**
 * Created by byhieg on 17/3/13.
 * Contact with byhieg@gmail.com
 */

public class DownLoadEntity implements Serializable{

    //保存的文件名字
    private String fileName;
    private String url;
    private long loadedData;
    private long start;
    private long end;
    private long total;
    private MediaType mediaType;

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public long getLoadedData() {
        return loadedData;
    }

    public void setLoadedData(long loadedData) {
        this.loadedData = loadedData;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
