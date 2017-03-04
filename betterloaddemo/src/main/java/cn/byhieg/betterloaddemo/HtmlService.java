package cn.byhieg.betterloaddemo;


import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by byhieg on 17/3/4.
 * Contact with byhieg@gmail.com
 */

public interface HtmlService {

    @GET("/")
    Call<String> getHtml();

}
