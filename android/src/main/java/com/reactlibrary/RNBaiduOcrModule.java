
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.Arguments;

import java.io.File;
import android.util.Log;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.BankCardParams;
import com.baidu.ocr.sdk.model.BankCardResult;
import com.baidu.ocr.sdk.model.GeneralParams;

import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.GeneralBasicParams;
import com.baidu.ocr.sdk.model.Word;
import com.baidu.ocr.sdk.model.WordSimple;


public class RNBaiduOcrModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNBaiduOcrModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNBaiduOcr";
  }

  // 此种身份验证方案直接使用开发者提供的AccessToken。
  @ReactMethod
  public void authWithToken(String accessToken){
    OCR.getInstance().initWithToken(reactContext.getApplicationContext(), accessToken);
  }

  // 此种身份验证方案使用AK/SK获得AccessToken。
  @ReactMethod
  public void authWithAKSK(String apiKey, String secretKey){
    OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
        @Override
        public void onResult(AccessToken result) {
            // 调用成功，返回AccessToken对象
            String token = result.getAccessToken();
        }
        @Override
        public void onError(OCRError error) {
            // 调用失败，返回OCRError子类SDKError对象
        }
    }, reactContext.getApplicationContext(), apiKey, secretKey);

  }

  // 使用授权文件获得AccessToken
  @ReactMethod
  public void authWithLicenseFileData(){
    OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
        @Override
        public void onResult(AccessToken result) {
            // 调用成功，返回AccessToken对象
            String token = result.getAccessToken();
        }
        @Override
        public void onError(OCRError error) {
            // 调用失败，返回OCRError子类SDKError对象
        }
    }, reactContext.getApplicationContext());

  }

  /*
  *convert GeneralResult into WritableMap
  *@param GeneralResult result
  */
  private WritableMap resultToWritableMap(GeneralResult result){
    WritableMap response = Arguments.createMap();
    WritableArray wordList = Arguments.createArray();;
    for (WordSimple wordSimple : result.getWordList()) {
        WordSimple word = wordSimple;
        WritableMap wordItem = Arguments.createMap();
        wordItem.putString("words", word.getWords());
        wordList.pushMap(wordItem);
    }
    response.putArray("words_result", wordList);
    response.putInt("words_result_num", result.getWordsResultNumber());
    response.putInt("direction", result.getDirection());
    return response;
  }

  // 通用文字识别（含位置信息版）
  @ReactMethod
  public void recognizeGeneral(String filePath, final Promise promise) {
    GeneralParams param = new GeneralParams();
    param.setDetectDirection(true);
    param.setImageFile(new File(filePath));
    OCR.getInstance().recognizeGeneral(param, new OnResultListener<GeneralResult>() {
        @Override
        public void onResult(GeneralResult result) {
            // 调用成功，返回GeneralResult对象
            // for (WordSimple wordSimple : result.getWordList()) {
            //     // Word类包含位置信息
            //     Word word = (Word) wordSimple;
            //     sb.append(word.getWords());
            //     sb.append("\n");
            // }
            promise.resolve(resultToWritableMap(result));
        }
        @Override
        public void onError(OCRError error) {
            // 调用失败，返回OCRError对象
            promise.reject(error.getMessage());
        }
    });
  }
  // 通用文字识别
  @ReactMethod
  public void recognizeGeneralBasic(String filePath, final Promise promise) {
    GeneralBasicParams param = new GeneralBasicParams();
    param.setDetectDirection(true);
    param.setImageFile(new File(filePath));

    // 调用通用文字识别服务
    OCR.getInstance().recognizeGeneralBasic(param, new OnResultListener<GeneralResult>() {
        @Override
        public void onResult(GeneralResult result) {
            promise.resolve(resultToWritableMap(result));
        }
        @Override
        public void onError(OCRError error) {
            // 调用失败，返回OCRError对象
        }
    });
  }
  //  通用文字识别(含生僻字版)
  @ReactMethod
  public void recognizeGeneralEnhanced(String filePath, final Promise promise) {
    GeneralBasicParams param = new GeneralBasicParams();
    param.setDetectDirection(true);
    param.setImageFile(new File(filePath));
    // 调用通用文字识别服务
    OCR.getInstance().recognizeGeneralEnhanced(param, new OnResultListener<GeneralResult>() {
        @Override
        public void onResult(GeneralResult result) {
            promise.resolve(resultToWritableMap(result));
        }
        @Override
        public void onError(OCRError error) {
            // 调用失败，返回OCRError对象
        }
    });
  }

  // 网络图片文字识别
  @ReactMethod
  public void recognizeWebimage(String filePath, final Promise promise) {
    GeneralBasicParams param = new GeneralBasicParams();
    param.setDetectDirection(true);
    param.setImageFile(new File(filePath));

    // 调用通用文字识别服务
    OCR.getInstance().recognizeWebimage(param, new OnResultListener<GeneralResult>() {
        @Override
        public void onResult(GeneralResult result) {
            if (result != null) {
              promise.resolve(resultToWritableMap(result));
            }
        }
        @Override
        public void onError(OCRError error) {
            // 调用失败，返回OCRError对象
        }
    });
  }

  //银行卡识别
  @ReactMethod
  public void recognizeBankCard(String filePath, final Promise promise) {
    BankCardParams param = new BankCardParams();
    param.setImageFile(new File(filePath));

    // 调用银行卡识别服务
    OCR.getInstance().recognizeBankCard(param, new OnResultListener<BankCardResult>() {
        @Override
        public void onResult(BankCardResult result) {
            // 调用成功，返回BankCardResult对象
            // promise.resolve(result.toString());
            if (result != null) {
              WritableMap response = Arguments.createMap();

              WritableMap item = Arguments.createMap();
              item.putString("bank_card_type", result.getBankCardType().toString());
              item.putString("bank_card_number", result.getBankCardNumber());
              // Unknown(0),Debit(1),Credit(2);
              item.putString("bank_name", result.getBankName());
              response.putMap("result",item);

              promise.resolve(response);
            }
        }
        @Override
        public void onError(OCRError error) {
            // 调用失败，返回OCRError对象
            promise.reject(error.getMessage());
        }
    });
  }



  // 识别身份证文字
  @ReactMethod
  public void recognizeIDCard(String filePath, final Promise promise) {
    IDCardParams param = new IDCardParams();
    param.setImageFile(new File(filePath));
    param.setIdCardSide(param.ID_CARD_SIDE_FRONT);
    OCR.getInstance().recognizeIDCard(param, new OnResultListener<IDCardResult>() {
        @Override
        public void onResult(IDCardResult result) {
            // 调用成功，返回IDCardResult对象
            if (result != null) {
              WritableMap response = Arguments.createMap();
              WritableMap finalResult = Arguments.createMap();
              // 只能一个个封装
              WritableMap item1 = Arguments.createMap();
              item1.putString("words", result.getName().toString());
              finalResult.putMap("姓名", item1);

              WritableMap item2 = Arguments.createMap();
              item2.putString("words", result.getIdNumber().toString());
              finalResult.putMap("公民身份号码", item2);

              WritableMap item3 = Arguments.createMap();
              item3.putString("words", result.getGender().toString());
              finalResult.putMap("性别", item3);

              WritableMap item4 = Arguments.createMap();
              item4.putString("words", result.getEthnic().toString());
              finalResult.putMap("民族", item4);

              WritableMap item5 = Arguments.createMap();
              item5.putString("words", result.getAddress().toString());
              finalResult.putMap("住址", item5);

              response.putMap("result", finalResult);
              promise.resolve(response);
            }
        }
        @Override
        public void onError(OCRError error) {
          promise.reject(error.getMessage());
        }
    });
  }
}
