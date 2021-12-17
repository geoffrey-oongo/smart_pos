import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:hello/hello.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  // Platform messages are asynchronous, so we initialize in an async method.

  Future<void> printer() async {
    try {
      await Hello.printTxReceipt('', '', '', '', '', '', '', '');
    } on PlatformException {
      print('errrr');
    } catch (e) {
      print(e);
    }
  }

  // Future<void> initPlatformState() async {
  //   String platformVersion;

  //   // Platform messages may fail, so we use a try/catch PlatformException.
  //   try {
  //     platformVersion = await Hello.platformVersion;
  //   } on PlatformException {
  //     platformVersion = 'Failed to get platform version.';
  //   }

  //   // If the widget was removed from the tree while the asynchronous platform
  //   // message was in flight, we want to discard the reply rather than calling
  //   // setState to update our non-existent appearance.
  //   if (!mounted) return;

  //   setState(() {
  //     _platformVersion = platformVersion;
  //   });
  // }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          actions: [IconButton(icon: Icon(Icons.print), onPressed: printer)],
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('gg'),
        ),
      ),
    );
  }
}
