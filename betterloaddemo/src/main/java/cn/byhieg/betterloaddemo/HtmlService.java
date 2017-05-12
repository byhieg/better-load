package cn.byhieg.betterloaddemo;



import cn.byhieg.betterloaddemo.bean.FileBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by byhieg on 17/3/4.
 * Contact with byhieg@gmail.com
 */

public interface HtmlService {

    @GET("/")
    Call<String> getHtml();


    @GET("download/files")
    Call<FileBean> getFiles();

    @GET
    Observable<FileBean> rxGet(@Url String url);

}
