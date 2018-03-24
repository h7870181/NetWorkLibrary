package com.soaic.networklibrary.net;

/**
 * TODO
 *
 * @author XiaoSai
 * @version V1.0
 * @since 2017/3/8.
 */
public abstract class OnResponseListener<T>{

    /** 请求成功*/
    public abstract void onSuccess(T content);
    
    /** 请求失败 */
    public abstract void onFailure(Throwable err);

}
