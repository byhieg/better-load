package cn.byhieg.betterload.download;

import java.io.Serializable;

/**
 * Created by byhieg on 17/3/13.
 * Contact with byhieg@gmail.com
 */

public class DownLoadEntity implements Serializable{

    private String saveName;
    private String url;
    private long downedData;
    private long start;
    private long end;
    private long total;


    public long getDownedData() {
        return downedData;
    }

    public void setDownedData(long downedData) {
        this.downedData = downedData;
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

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
