package cn.byhieg.betterload.network;

/**
 * Created by byhieg on 17/3/3.
 * Contact with byhieg@gmail.com
 */

public interface IResonseListener<T> {

     void onSucess(T response);

     void onFailure(String message);
}
