//
// OCR识别结果delegate
// Created by chenxiaoyu on 17/3/2.
// Copyright (c) 2017 baidu. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol AipOcrDelegate <NSObject>

@optional
- (void) ocrOnIdCardSuccessful:(id)result;

@optional
- (void) ocrOnBankCardSuccessful:(id)result;

@optional
- (void) ocrOnGeneralSuccessful:(id)resut;

@optional
- (void) ocrOnFail:(NSError *)error;
@end
