package com.xinxing.cixi_community;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.xinxing.cixi_community.videochoose.TCPicturePickerActivity;
import com.xinxing.cixi_community.videochoose.TCVideoPickerActivity;
import com.xinxing.cixi_community.videorecord.TCVideoRecordActivity;
import com.tencent.qcloud.ugckit.DataStore;
import com.tencent.qcloud.ugckit.VideoListener;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity implements VideoListener {
    private static final String TAG = "MainActivity";
    private  String  geturl;

    String   C_NAME="tx_video_editor";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataStore.getInstance().setVideoListener(this);
        new MethodChannel(getFlutterEngine().getDartExecutor().getBinaryMessenger(),C_NAME).setMethodCallHandler(
                new MethodChannel.MethodCallHandler() {


                    @Override
                    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {


                        if(methodCall.method.equals("initSdk")){
                            result.success("初始化");

                        }




                        if(methodCall.method.equals("openPicEditor")){

                            Intent intent2 = new Intent(getActivity(), TCPicturePickerActivity.class);
                            startActivity(intent2);
                            if(!TextUtils.isEmpty(DataStore.getInstance().getVideourl())){
                                result.success(DataStore.getInstance().getVideourl());
                            }
                        }
                        if(methodCall.method.equals("openVideoEditor")){
                            Intent intent = new Intent(getActivity(), TCVideoPickerActivity.class);
                            startActivity(intent);

                            if(!TextUtils.isEmpty(DataStore.getInstance().getVideourl())){
                                result.success(DataStore.getInstance().getVideourl());
                            }

                        }
                        if(methodCall.method.equals("openCameraEditor")){
                            startActivity(new Intent(getActivity(), TCVideoRecordActivity.class));
                            if(!TextUtils.isEmpty(DataStore.getInstance().getVideourl())){
                                result.success(DataStore.getInstance().getVideourl());
                            }

                        }

                        else  {
                            result.success("I don't know what you say");
                        }
                    }
                }
        );
        GeneratedPluginRegistrant.registerWith(new FlutterEngine(this));
    }


    @Override
    public void getUrlSuccess(String url) {
        DataStore.getInstance().setVideourl(url);
        Log.e(TAG, "getUrlSuccess:url  ----  >   "+url );
        geturl=url;
        Log.e(TAG, "getUrlSuccess: geturl --- >  "+geturl);
    }

    @Override
    public void getUrlError() {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
