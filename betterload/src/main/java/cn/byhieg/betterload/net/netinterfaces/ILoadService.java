package cn.byhieg.betterload.net.netinterfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by byhieg on 17/3/2.
 * Contact with byhieg@gmail.com
 */

public interface ILoadService {


    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
