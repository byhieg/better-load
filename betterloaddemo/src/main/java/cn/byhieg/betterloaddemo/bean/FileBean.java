package cn.byhieg.betterloaddemo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byhieg on 17/3/13.
 * Contact with byhieg@gmail.com
 */

public class FileBean implements Serializable {


    /**
     * result : 返回成功
     * fileList : [{"fileName":"1488293094330?????.md","path":"/home/ubuntu/file_directory/","size":"932B"},{"fileName":"1488293007405index.html","path":"/home/ubuntu/file_directory/","size":"50KB"},{"fileName":"1488292937431JavaTutorial??.png","path":"/home/ubuntu/file_directory/","size":"172KB"},{"fileName":"1488293023613index2.html","path":"/home/ubuntu/file_directory/","size":"65KB"},{"fileName":"1488451498663headicon.jpg","path":"/home/ubuntu/file_directory/","size":"18KB"},{"fileName":"1488293038104README.md","path":"/home/ubuntu/file_directory/","size":"4KB"},{"fileName":"1488126587466Java?????.png","path":"/home/ubuntu/file_directory/","size":"64KB"}]
     * code : 0
     */

    private String result;
    private int code;
    private List<FileListBean> fileList;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<FileListBean> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileListBean> fileList) {
        this.fileList = fileList;
    }

    public static class FileListBean {
        /**
         * fileName : 1488293094330?????.md
         * path : /home/ubuntu/file_directory/
         * size : 932B
         */

        private String fileName;
        private String path;
        private String size;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}
