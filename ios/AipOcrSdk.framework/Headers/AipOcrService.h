//
// Created by chenxiaoyu on 17/2/21.
// Copyright (c) 2017 baidu. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>


@interface AipOcrService : NSObject

/**
 * 是否重试。默认为NO。YES会在失败时自动重试一次。
 */
@property (atomic, assign) bool retry;


- (void) authWithLicenseFileData: (NSData *)licenseFileContent;

- (void) authWithAK: (NSString *)ak andSK: (NSString *)sk;

- (void) authWithToken: (NSString *)token;


/**
 * 通用文字识别
 * @param image 需要识别的图片
 * @param options direction, language ... 详见开发者文档
 * @param successHandler 成功回调
 * @param failHandler 失败回调
 */
- (void) detectTextFromImage: (UIImage*)image
                 withOptions: (NSDictionary *)options
              successHandler: (void (^)(id result))successHandler
                 failHandler: (void (^)(NSError* err))failHandler;


/**
 * 通用文字识别(不含位置信息版)
 * @param image 需要识别的图片
 * @param options direction, language ... 详见开发者文档
 * @param successHandler 成功回调
 * @param failHandler 失败回调
 */
- (void) detectTextBasicFromImage: (UIImage*)image
                      withOptions: (NSDictionary *)options
                   successHandler: (void (^)(id result))successHandler
                      failHandler: (void (^)(NSError* err))failHandler;

/**
 * 通用文字识别（含生僻字版）
 * @param image 需要识别的图片
 * @param options direction, language ... 详见开发者文档
 * @param successHandler 成功回调
 * @param failHandler 失败回调
 */
- (void) detectTextEnhancedFromImage: (UIImage*)image
                         withOptions: (NSDictionary *)options
                      successHandler: (void (^)(id result))successHandler
                         failHandler: (void (^)(NSError* err))failHandler;


- (void) detectIdCardFromImage: (UIImage*)image
                   withOptions: (NSDictionary *)options
                successHandler: (void (^)(id result))successHandler
                   failHandler: (void (^)(NSError* err))failHandler;

/**
 * 身份证正面识别
 * @param image 需要识别的图片
 * @param options 参数，详见开发者文档
 * @param successHandler 成功回调
 * @param failHandler 失败回调
 */
- (void)detectIdCardFrontFromImage:(UIImage *)image
                       withOptions:(NSDictionary *)options
                    successHandler:(void (^)(id result))successHandler
                       failHandler:(void (^)(NSError *err))failHandler;

/**
 * 身份证背面识别
 * @param image 需要识别的图片
 * @param options 参数，详见开发者文档
 * @param successHandler 成功回调
 * @param failHandler 失败回调
 */
- (void)detectIdCardBackFromImage:(UIImage *)image
                      withOptions:(NSDictionary *)options
                   successHandler:(void (^)(id result))successHandler
                      failHandler:(void (^)(NSError *err))failHandler;

/**
 * 银行卡识别
 * @param image 需要识别的图片
 * @param successHandler 成功回调
 * @param failHandler 失败回调
 */
- (void) detectBankCardFromImage: (UIImage*)image
                  successHandler: (void (^)(id result))successHandler
                     failHandler: (void (^)(NSError* err))failHandler;


/**
 * 网图识别
 * @param image
 * @param options
 * @param successHandler
 * @param failHandler
 */
- (void)detectWebImageFromImage:(UIImage *)image
                    withOptions:(NSDictionary *)options
                 successHandler:(void (^)(id result))successHandler
                    failHandler:(void (^)(NSError *err))failHandler;

/**
 * 清空验证缓存
 * 出现验证过期等特殊情况调用
 */
- (void) clearCache;

+ (instancetype)shardService;

@end
