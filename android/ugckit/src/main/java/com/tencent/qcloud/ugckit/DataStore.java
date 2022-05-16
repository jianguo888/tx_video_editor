package com.tencent.qcloud.ugckit;

public class DataStore {

    private static  DataStore instance=null;




    private DataStore() {
    }

    public  static  DataStore getInstance(){
        if(instance==null){
            synchronized (DataStore.class){
                if(instance==null){
                    instance=new DataStore();
                }
            }

        }
        return  instance;
    }

  private  String videourl;

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    private VideoListener videoListener;

    public VideoListener getVideoListener() {
        return videoListener;
    }

    public void setVideoListener(VideoListener videoListener) {
        this.videoListener = videoListener;
    }
}
