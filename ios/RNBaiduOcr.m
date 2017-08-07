
#import "RNBaiduOcr.h"
#import <AipOcrSdk/AipOcrSdk.h>
@implementation RNBaiduOcr

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

- (UIImage*)pathToImage: (NSString*) imagePath
{
//    去掉开头的 file://
    NSString *path = [imagePath substringFromIndex:7];
    UIImage *finalImage = [UIImage imageWithContentsOfFile:path];

    return finalImage;

}

//授权文件认证
RCT_EXPORT_METHOD(authWithLicenseFileData)
{
    NSString *licenseFile = [[NSBundle mainBundle] pathForResource:@"aip" ofType:@"license"];
    NSData *licenseFileData = [NSData dataWithContentsOfFile:licenseFile];
    [[AipOcrService shardService] authWithLicenseFileData:licenseFileData];
}

//API Key / Secret Key
RCT_EXPORT_METHOD(authWithAKSK: (NSString *)ak SK: (NSString *)sk)
{
    [[AipOcrService shardService] authWithAK:ak andSK:sk];
}

//AccessToken
RCT_EXPORT_METHOD(authWithToken: (NSString *)token)
{
    [[AipOcrService shardService] authWithToken:token];
}

//通用文字识别
RCT_EXPORT_METHOD(recognizeGeneral: (NSString *)image
                  successResolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    UIImage *finalImage = [self pathToImage:image];
    NSDictionary *options = @{@"language_type": @"CHN_ENG", @"detect_direction": @"true"};
    [[AipOcrService shardService] detectTextFromImage:finalImage withOptions:options successHandler:^(id result) {
        // 成功识别的后续逻辑
        resolve(result);
    } failHandler:^(NSError *err) {
        // 失败的后续逻辑
        reject(@"detect error", @"err", err);
    }];

}

//通用文字识别（不带位置信息版）
RCT_EXPORT_METHOD(recognizeGeneralBasic: (NSString *)image
                  successResolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    UIImage *finalImage = [self pathToImage:image];

    NSDictionary *options = @{@"language_type": @"CHN_ENG", @"detect_direction": @"true"};
    [[AipOcrService shardService] detectTextBasicFromImage:finalImage withOptions:options successHandler:^(id result) {
        // 成功识别的后续逻辑
        resolve(result);
    } failHandler:^(NSError *err) {
        // 失败的后续逻辑
        reject(@"detect error", @"err", err);
    }];

}

//通用文字识别 （含生僻字）
RCT_EXPORT_METHOD(recognizeGeneralEnhanced: (NSString *)image
                  successResolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    UIImage *finalImage = [self pathToImage:image];

    NSDictionary *options = @{@"language_type": @"CHN_ENG", @"detect_direction": @"true"};
    [[AipOcrService shardService] detectTextEnhancedFromImage:finalImage withOptions:options successHandler:^(id result) {
        // 成功识别的后续逻辑
        resolve(result);
    } failHandler:^(NSError *err) {
        // 失败的后续逻辑
        reject(@"detect error", @"err", err);
    }];

}

//网图识别
RCT_EXPORT_METHOD(recognizeWebimage: (NSString *)image
                  successResolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    UIImage *finalImage = [self pathToImage:image];

    NSDictionary *options = @{@"language_type": @"CHN_ENG", @"detect_direction": @"true"};
    [[AipOcrService shardService] detectWebImageFromImage:finalImage withOptions:options successHandler:^(id result) {
        // 成功识别的后续逻辑
        resolve(result);
    } failHandler:^(NSError *err) {
        // 失败的后续逻辑
        reject(@"detect error", @"err", err);
    }];

}

//银行卡识别
RCT_EXPORT_METHOD(recognizeBankCard:(NSString *)image
                  successResolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    UIImage *finalImage = [self pathToImage:image];

    [[AipOcrService shardService] detectBankCardFromImage:finalImage successHandler:^(id result) {
        //成功
        resolve(result);
    } failHandler:^(NSError *err) {
        //失败
        reject(@"detect error", @"err", err);

    }];

}



//身份证识别
RCT_EXPORT_METHOD(recognizeIDCard: (NSString *)imagePath
                  successResolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{

    UIImage *finalImage = [self pathToImage:imagePath];

    [[AipOcrService shardService] detectIdCardFrontFromImage:finalImage withOptions:nil successHandler:^(id result) {
        // 成功
        resolve(result);
    } failHandler:^(NSError *err) {
        // 失败
        reject(@"detect error", @"err", err);
    }];
}



@end
