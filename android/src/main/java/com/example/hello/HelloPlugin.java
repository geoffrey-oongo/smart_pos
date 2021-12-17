package com.example.hello;

import androidx.annotation.NonNull;
import android.os.Environment;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import android.text.Layout;

import com.zcs.sdk.DriverManager;
import com.zcs.sdk.Printer;
import com.zcs.sdk.SdkResult;
import com.zcs.sdk.print.PrnStrFormat;
import com.zcs.sdk.print.PrnTextFont;
import com.zcs.sdk.print.PrnTextStyle;
import com.zcs.sdk.util.LogUtils;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.zcs.sdk.Sys;

/** HelloPlugin */
public class HelloPlugin implements FlutterPlugin, MethodCallHandler {

  private static final String TAG = "HelloPlugin";
  /// The MethodChannel that will the communication between Flutter and native
  /// Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine
  /// and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;

  DriverManager mDriverManager = DriverManager.getInstance();
  Sys mSys = mDriverManager.getBaseSysDevice();
  Printer mPrinter = mDriverManager.getPrinter();

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    DriverManager.getInstance().getSingleThreadExecutor();
    _init();

    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "hello");
    channel.setMethodCallHandler(this);
  }

  // @Override
  // public void onCreate() {
  // super.onCreate();
  // sDriverManager = DriverManager.getInstance();

  // Config.init(this).setUpdate();

  // }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {

      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("obtainPrint")) {
      String famerName = call.argument("farmerName");
      String transactionId = call.argument("transactionId");
      String date = call.argument("date");
      String no = call.argument("no");
      String weight = call.argument("weight");
      String totalWeight = call.argument("totalWeight");
      String clerk = call.argument("clerk");
      String variety = call.argument("variety");
      _print(result, transactionId, famerName, date, weight, totalWeight, clerk, variety,no);
    } else {
      result.notImplemented();
    }

  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  private void _print(Result result, String transactionId, String farmer, String date, String weight,
      String totalWeight, String clerk, String variety, String no) {

    int printStatus = mPrinter.getPrinterStatus();
    if (printStatus == SdkResult.SDK_PRN_STATUS_PAPEROUT) {
    } else {
      PrnStrFormat format = new PrnStrFormat();
      format.setTextSize(30);
      format.setAli(Layout.Alignment.ALIGN_CENTER);
      format.setStyle(PrnTextStyle.BOLD);

      format.setFont(PrnTextFont.DEFAULT);
      mPrinter.setPrintAppendString("NGOMANO FC.S LTD", format);
      mPrinter.setPrintAppendString("--------------------------------- ", format);
      mPrinter.setPrintAppendString("Transaction Ticket", format);
      format.setTextSize(25);
      format.setStyle(PrnTextStyle.NORMAL);
      format.setAli(Layout.Alignment.ALIGN_NORMAL);
      mPrinter.setPrintAppendString(" ", format);
      mPrinter.setPrintAppendString("#Ref" + transactionId, format);
      mPrinter.setPrintAppendString("Farmer:" + farmer, format);
      mPrinter.setPrintAppendString("Farmer no:" + no, format);
      mPrinter.setPrintAppendString("Date: " + date, format);
      mPrinter.setPrintAppendString("weight: " + weight + " " + "KG", format);
      mPrinter.setPrintAppendString("OPERATOR :" + clerk, format);
      mPrinter.setPrintAppendString("Variety :" + variety, format);
      mPrinter.setPrintAppendString("--------------------------------- ", format);

      format.setTextSize(30);
      format.setStyle(PrnTextStyle.BOLD);
      mPrinter.setPrintAppendString("Total Weight:" + totalWeight + " " + "KG", format);

      format.setAli(Layout.Alignment.ALIGN_NORMAL);
      format.setStyle(PrnTextStyle.NORMAL);
      format.setTextSize(25);
      mPrinter.setPrintAppendString(" -----------------------------", format);
      mPrinter.setPrintAppendString(" ", format);
      mPrinter.setPrintAppendString(" ", format);
     
      mPrinter.setPrintStart();
    }
    result.success(true);
  }

  private void _init() {

    int statue = mSys.getFirmwareVer(new String[1]);
    if (statue != SdkResult.SDK_OK) {
      int sysPowerOn = mSys.sysPowerOn();
      Log.i(TAG, "sysPowerOn: " + sysPowerOn);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    int i = mSys.sdkInit();
    if (i == SdkResult.SDK_OK) {
    }
  }
}
