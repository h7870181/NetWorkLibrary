package com.soaic.network.listener;

/**
 * 网络请求监听
 * Created by XiaoSai on 2016/11/3.
 */
public abstract class OnResultListener<T> {

    /** 请求成功*/
    public abstract void onSuccess(T t);

    /** 请求失败 根据需求实现*/
    public abstract void onFailure(Throwable err);

}
