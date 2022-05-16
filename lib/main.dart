import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';

import 'utils.dart';


void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      platformVersion =
          await TxVideoEditor.platformVersion ?? 'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: [
              const SizedBox(height: 30),
              TextButton(
                child: const Text("初始化sdk"),
                onPressed: () {
                  TxVideoEditor.initSdk();
                },
              ),
              const SizedBox(height: 30),
              TextButton(
                child: const Text("打开图片编辑"),
                onPressed: () {
                  TxVideoEditor.openPicEditor(onVieoEditFinish: (path) {
                    print("编辑完成，路径:" + path);
                  });
                },
              ),
              const SizedBox(height: 30),
              TextButton(
                child: const Text("打开视频编辑"),
                onPressed: () {
                  TxVideoEditor.openVideoEditor(onVieoEditFinish: (path) {
                    print("编辑完成，路径:" + path);
                  });
                },
              ),
              const SizedBox(height: 30),
              TextButton(
                child: const Text("打开摄像头录制"),
                onPressed: () {
                  TxVideoEditor.openCameraEditor(onVieoEditFinish: (path) {
                    print("编辑完成，路径:" + path);
                  });
                },
              ),
            ],
          ),
        ),
      ),
    );
  }
}
