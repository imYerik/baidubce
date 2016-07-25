//
//  ViewController.m
//  DocDemo
//
//  Created by yerik on 16/7/22.
//  Copyright © 2016年 yerik. All rights reserved.
//

#import "ViewController.h"

#import "WebKitViewController.h"




@interface ViewController () {
    WebKitViewController* controller;
    NSDictionary* parameters;
}

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    
    
    NSString* docID = @"doc-gg0q392pixetppv";
    NSString* token = @"TOKEN";
    NSString* host = @"BCEDOC";
    NSString* documentPath = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0];
    NSString* dir = [documentPath stringByAppendingPathComponent:docID];
    parameters = @{
                                 BDocPlayerSDKeyDocID: docID,
                                 BDocPlayerSDKeyToken: token,
                                 BDocPlayerSDKeyHost: host,
                                 BDocPlayerSDKeyDocType: @"doc",
                                 BDocPlayerSDKeyFileDir: dir,
                                 BDocPlayerSDKeyTotalPageNum: @"1",
                                 BDocPlayerSDKeyDocTitle: @"README",
                                 BDocPlayerSDKeyPageNumber: @1
                                 };
    
    // 初始化
    controller = [[WebKitViewController alloc] init];
    
    [controller.view setFrame:CGRectMake(0,0,
                                         [[UIScreen mainScreen]bounds].size.width ,
                                         [[UIScreen mainScreen]bounds].size.height)];
    
    
    [self.view addSubview:controller.view];
    
    controller.delegate = self;
    
    // 下载文档
    [controller downloadDoc:parameters];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


/**
 *  @brief 文档开始加载
 */
- (void)docLoadingStart {
    NSLog(@"+++++ Loading Start +++++");
}

/**
 *  @brief 文档结束结束。是否成功取决于error是否为空。
 *  @param error 错误。
 */
- (void)docLoadingEnded:(NSError*)error{
    if(!error){
        NSLog(@"+++++ Loading Ended Sucessfully +++++");
    } else {
        NSLog(@"+++++ Loading Failed with error %@",error);
    }
    
}

/**
 *  @brief 翻页事件。
 *  @param page 当前页。
 */
- (void)currentPageChanged:(NSInteger)page {
    NSLog(@"+++++ Page Changed %ld +++++",(long)page);
}

/**
 *  @brief 下载开始。
 */
- (void)downloadStart {
    NSLog(@"+++++ Download Start +++++");
}


/**
 *  @brief 下载结束。是否成功取决于error是否为空。
 *  @param error 错误。
 */
- (void)downloadEnded:(NSError*)error {
    if(!error){
        NSLog(@"+++++ Download Ended Sucessfully +++++");
        [controller loadDoc:parameters];
    }
}

@end
