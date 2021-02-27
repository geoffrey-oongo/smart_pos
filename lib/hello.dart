import 'dart:async';

import 'package:flutter/services.dart';

class Hello {
  static const MethodChannel _channel = const MethodChannel('hello');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<bool> printText(String transDate, String weight,
      String totalWeight, String farmerName, String transactionId) async {
    Map<String, String> params = {
      'date': transDate,
      'weight': weight,
      'totalWeight': totalWeight,
      'farmerName': farmerName,
      'transactionId': transactionId
    };
    final bool returned = await _channel.invokeMethod('obtainPrint', params);
    return returned;
  }
}
