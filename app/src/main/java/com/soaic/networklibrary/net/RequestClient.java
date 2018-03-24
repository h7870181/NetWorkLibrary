package com.soaic.networklibrary.net;

import android.accounts.NetworkErrorException;
import android.text.TextUtils;

import com.soaic.network.NetClient;
import com.soaic.network.listener.OnResultListener;
import com.soaic.networklibrary.SApplication;
import com.soaic.networklibrary.response.BaseResponse;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 网络请求类
 * @author XiaoSai
 * @version V1.0
 * @since 2017/3/7.
 */
public class RequestClient {
    private static class SingleLoader{
        private final static RequestClient INSTANCE = new RequestClient();
    }
    private static RequestClient getInstance(){
        return  SingleLoader.INSTANCE;
    }
    private NetClient.Builder mBuilder;
    private NetClient netClient;
    private RequestClient init(NetClient.Builder builder){
        this.mBuilder = builder;
        return this;
    }

    public <T extends BaseResponse> void getIntercept(Class<T> clazz, final OnResponseListener<T> listener){
        netClient = mBuilder.build()
                .get(clazz, new OnResultListener<T>(){
                    @Override
                    public void onSuccess(T content){
                        handlerData(listener,content);
                    }
                    @Override
                    public void onFailure(Throwable err){
                        listener.onFailure(err);
                    }
                });
    }

    public <T> void post(Class<T> clazz, final OnResponseListener<T> listener){
        netClient = mBuilder.build()
                .post(clazz, new OnResultListener<T>(){
                    @Override
                    public void onSuccess(T content){
                        listener.onSuccess(content);
                    }
                    @Override
                    public void onFailure(Throwable err){
                        listener.onFailure(err);
                    }

                });
    }

    public <T extends BaseResponse> void postIntercept(Class<T> clazz, final OnResponseListener<T> listener){
        netClient = mBuilder.build()
                .post(clazz, new OnResultListener<T>(){
                    @Override
                    public void onSuccess(T content){
                        handlerData(listener,content);
                    }
                    @Override
                    public void onFailure(Throwable err){
                        listener.onFailure(err);
                    }

                });
    }
    public <T extends BaseResponse> void postJSONIntercept(Class<T> clazz, final OnResponseListener<T> listener){
        netClient = mBuilder.build()
                .postJSON(clazz, new OnResultListener<T>(){
                    @Override
                    public void onSuccess(T content){
                        handlerData(listener,content);
                    }
                    @Override
                    public void onFailure(Throwable err){
                        listener.onFailure(err);
                    }

                });
    }

    public <T extends BaseResponse> void postUploadIntercept(Class<T> clazz, final OnResponseListener<T> listener){
        netClient = mBuilder.build()
                .postUpload(clazz, new OnResultListener<T>(){
                    @Override
                    public void onSuccess(T content){
                        handlerData(listener,content);
                    }
                    @Override
                    public void onFailure(Throwable err){
                        listener.onFailure(err);
                    }

                });
    }

    private <T extends BaseResponse> void handlerData(OnResponseListener<T> listener, T content){
        try {
            if(content.getCode()!=0){
                if(content.getCode() == 200)
                    listener.onSuccess(content);
                else
                    listener.onFailure(new NetworkErrorException(content.getMsg()));
            }else{
                listener.onFailure(new NetworkErrorException("system error"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFailure(e);
        }
    }

    public void cancelRequest(){
        if(netClient!=null)
            netClient.cancelRequest();
    }

    public void cleanCookies(){
        if(netClient!=null){
            netClient.getCookiesManager().cleanCookies();
        }
    }

    public int getCookiesSize(){
        int size = 0;
        if(netClient!=null){
            return netClient.getCookiesManager().getCookies().size();
        }
        return size;
    }
    
    public static class Builder{
        private String baseUrl;
        private String url;
        private String bodyJson;

        private LinkedHashMap<String, String> params = new LinkedHashMap<>();
        private LinkedHashMap<String, File> files = new LinkedHashMap<>();

        public Builder baseUrl(String baseUrl){
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder url(String  url){
            this.url = url;
            return this;
        }

        public Builder paramsJSON(String json){
            this.bodyJson = json;
            return this;
        }

        public Builder param(String key, String value){
            if(key!=null&&value!=null){
                params.put(key,value);
            }
            return this;
        }

        public Builder param(String key, String value, boolean isAdd){
            if(isAdd&&key!=null&&value!=null){
                params.put(key,value);
            }
            return this;
        }

        public Builder params(Map<String, String> maps){
            if(maps!=null&&!maps.containsKey(null)&&!maps.containsValue(null)){
                params.putAll(maps);
            }
            return this;
        }

        public Builder file(String key, File file){
            if(!TextUtils.isEmpty(key)&&file!=null&&file.exists()){
                files.put(key,file);
            }
            return this;
        }

        public RequestClient builder(){
            //处理BaseUrl为null
            try{
                if(TextUtils.isEmpty(baseUrl)&&!TextUtils.isEmpty(url)){
                    baseUrl = url.substring(0,url.lastIndexOf("/")+1);
                    url = url.substring(url.lastIndexOf("/")+1);
                }
            }catch(Exception e){ e.printStackTrace(); }

            NetClient.Builder builder = new NetClient.Builder(SApplication.mContext)
                    .baseUrl(baseUrl)
                    .url(url)
                    .params(params)
                    .paramsJSON(bodyJson)
                    .files(files);
            return RequestClient.getInstance().init(builder);
        }

    }
}
