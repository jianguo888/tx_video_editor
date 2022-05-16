import 'dart:async';
import 'dart:ffi';
import 'dart:io';

import 'package:flutter/services.dart';

class TxVideoEditor {
  static const MethodChannel _channel = MethodChannel('tx_video_editor');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');

    return version;
  }

  static void _getChannel() async {
    String appKey = await _channel.invokeMethod(
      'videoEditFinish',
    );
    print("appKey：" + appKey);
  }

  static Function(String)? _onVieoEditFinish;

  static Future<dynamic> _methodCallback(MethodCall call) async {
    switch (call.method) {
      case "videoEditFinish":
        onVideoEditFinish(call.arguments);
        break;
    }
    return null;
  }

  static void onVideoEditFinish(String path) {
    if (_onVieoEditFinish != null) {
      _onVieoEditFinish!(path);
    }
  }

  static void initSdk() async {
    _channel.setMethodCallHandler(_methodCallback);
    if (Platform.isIOS) {
      await _channel.invokeMethod('initSdk');
    }
    if (Platform.isAndroid) {
      await _channel.invokeMethod('initSdk');

      String appKey = await _channel.invokeMethod(
        'initSdk',
      );
      print("appKey：" + appKey);
    }
  }

  static void openPicEditor(
      {required Function(String) onVieoEditFinish}) async {
    _onVieoEditFinish = onVieoEditFinish;
    if (Platform.isIOS) {
      _channel.invokeMethod('openPicEditor');
    }
    if (Platform.isAndroid) {
      _channel.invokeMethod('openPicEditor');
      String appKey = await _channel.invokeMethod(
        'openPicEditor',
      );
      print("appKey：" + appKey);
    }
  }

  static void openVideoEditor(
      {required Function(String) onVieoEditFinish}) async {
    _onVieoEditFinish = onVieoEditFinish;
    if (Platform.isIOS) {
      _channel.invokeMethod('openVideoEditor');
    }
    if (Platform.isAndroid) {
      _channel.invokeMethod('openVideoEditor');
      String appKey = await _channel.invokeMethod(
        'openVideoEditor',
      );
      print("appKey：" + appKey);
    }
  }

  static void openCameraEditor(
      {required Function(String) onVieoEditFinish}) async {
    _onVieoEditFinish = onVieoEditFinish;
    if (Platform.isIOS) {
      _channel.invokeMethod('openCameraEditor');
    }
    if (Platform.isAndroid) {
      _channel.invokeMethod('openCameraEditor');
      String appKey = await _channel.invokeMethod(
        'openCameraEditor',
      );
      print("appKey：" + appKey);
    }
  }
}