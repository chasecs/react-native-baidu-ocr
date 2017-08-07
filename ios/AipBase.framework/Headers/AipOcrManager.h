//
// Created by chenxiaoyu on 17/2/7.
// Copyright (c) 2017 baidu. All rights reserved.
//
// Ocr主要接口类

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>


@interface AipOcrManager : NSObject

- (instancetype) init __attribute__((unavailable(
"Use 'initWithLicenseFileData' or 'initWithAK:andSK' instead")));

- (instancetype) initWithLicenseFileData: (NSData *)licenseFileContent;

- (instancetype) initWithAK: (NSString *)ak andSK: (NSString *)sk;

- (instancetype) initWithToken: (NSString *)token;

/**
 * 通用位置识别
 * @param image
 * @param options
 * @param successHandler
 * @param failHandler
 */
- (void) detectTextFromImage: (UIImage*)image
                 withOptions: (NSDictionary *)options
              successHandler: (void (^)(id result))successHandler
                 failHandler: (void (^)(NSError* err))failHandler;

/**
 * 通用文字识别 不含位置信息版
 * @param image
 * @param options
 * @param successHandler
 * @param failHandler
 */
- (void) detectTextBasicFromImage: (UIImage*)image
                      withOptions: (NSDictionary *)options
                   successHandler: (void (^)(id result))successHandler
                      failHandler: (void (^)(NSError* err))failHandler;


- (void) detectIdCardFromImage: (UIImage*)image
                   withOptions: (NSDictionary *)options
                successHandler: (void (^)(id result))successHandler
                   failHandler: (void (^)(NSError* err))failHandler;


- (void) detectBankCardFromImage: (UIImage*)image
                  successHandler: (void (^)(id result))successHandler
                     failHandler: (void (^)(NSError* err))failHandler;

/**
 * 通用文字识别 含生僻字版
 * @param image
 * @param options
 * @param successHandler
 * @param failHandler
 */
- (void) detectTextEnhancedFromImage: (UIImage *)image
                         withOptions: (NSDictionary *)options
                      successHandler: (void (^)(id result))successHandler
                         failHandler: (void (^)(NSError* err))failHandler;

/**
 * 网图识别
 * @param image
 * @param options
 * @param successHandler
 * @param failHandler
 */
- (void) detectWebImageFromImage: (UIImage *)image
                     withOptions: (NSDictionary *)options
                  successHandler: (void (^)(id result))successHandler
                     failHandler: (void (^)(NSError* err))failHandler;

- (void) clearCache;
@end
