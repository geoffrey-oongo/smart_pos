import 'dart:async';

import 'package:flutter/services.dart';

class Hello {
  static const MethodChannel _channel = const MethodChannel('hello');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<bool> printTxReceipt(
      String transDate,
      String weight,
      String totalWeight,
      String farmerName,
      String transactionId,
      String clerk,
      String variety,
      String farmerNo
      ) async {
    Map<String, String> params = {
      'no' :farmerNo,
      'variety' :variety,
      'date': transDate,
      'weight': weight,
      'totalWeight': totalWeight,
      'farmerName': farmerName,
      'transactionId': transactionId,
      'clerk': clerk
    };
    final bool returned = await _channel.invokeMethod('obtainPrint', params);
    return returned;
  }
}
