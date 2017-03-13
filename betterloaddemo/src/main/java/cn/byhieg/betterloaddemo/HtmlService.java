package cn.byhieg.betterloaddemo;


import cn.byhieg.betterloaddemo.bean.FileBean;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by byhieg on 17/3/4.
 * Contact with byhieg@gmail.com
 */

public interface HtmlService {

    @GET("/")
    Call<String> getHtml();


    @GET("/api/v1/download/files")
    Call<FileBean> getFiles();

}
