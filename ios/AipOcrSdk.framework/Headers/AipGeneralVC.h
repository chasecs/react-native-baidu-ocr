//
//  AipGeneralVC.h
//  OCRLib
//
//  Created by Yan,Xiangda on 2017/2/16.
//  Copyright © 2017年 Baidu. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <AipOcrSdk/AipOcrDelegate.h>

@interface AipGeneralVC : UIViewController

@property (nonatomic, weak) id<AipOcrDelegate> delegate;

+(UIViewController *)ViewControllerWithDelegate:(id<AipOcrDelegate>)delegate;

@end
