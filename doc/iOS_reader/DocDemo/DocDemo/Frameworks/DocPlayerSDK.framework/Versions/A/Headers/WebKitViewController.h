//
//  WebKitViewController.h
//  DocPlayerSDK
//
//  Copyright © 2016年 Baidu. All rights reserved.

@import UIKit;

#ifdef __cplusplus
#define BDPS_EXTERN extern "C" __attribute__((visibility ("default")))
#else
#define BDPS_EXTERN     extern __attribute__((visibility ("default")))
#endif

/**
 * keys used to the json dictionary construction.
 */

// required, type NSString.
BDPS_EXTERN NSString * const BDocPlayerSDKeyDocID;

// required, type NSString.
BDPS_EXTERN NSString * const BDocPlayerSDKeyDocTitle;

// required, type NSString.
BDPS_EXTERN NSString * const BDocPlayerSDKeyTotalPageNum;

// required, type NSString.
BDPS_EXTERN NSString * const BDocPlayerSDKeyToken;

// required, type NSString.
BDPS_EXTERN NSString * const BDocPlayerSDKeyHost;

// required, type NSString.  "ppt"  or "doc" (not ppt)
BDPS_EXTERN NSString * const BDocPlayerSDKeyDocType;

// optional, type NSString.
// if specified the dir, SDK will load documents from there if documents is present.
// and when call downloadCurrentDocument method,  SDK will save the downloaded documents to this dir.
BDPS_EXTERN NSString * const BDocPlayerSDKeyFileDir;

// optional, type int.
// if specified the page number, SDK will load document and seek to the page number.
BDPS_EXTERN NSString * const BDocPlayerSDKeyPageNumber;

/**
 *  @brief DocPlayerSDK Version
 */
BDPS_EXTERN NSString * const BDPSDKVersion;

/**
 *  @brief 代理
 */
@protocol BDocPlayerDelegate <NSObject>

@optional

/**
 *  @brief 文档开始加载
 */
- (void)docLoadingStart;

/**
 *  @brief 文档结束结束。是否成功取决于error是否为空。
 *  @param error 错误。
 */
- (void)docLoadingEnded:(NSError*)error;

/**
 *  @brief 翻页事件。
 *  @param page 当前页。
 */
- (void)currentPageChanged:(NSInteger)page;

/**
 *  @brief 下载开始。
 */
- (void)downloadStart;

/**
 *  @brief 下载结束。是否成功取决于error是否为空。
 *  @param error 错误。
 */
- (void)downloadEnded:(NSError*)error;

@end

/**
 * This is the view controller for the main interface.
 * It also acts as a delegate for the web view.
 * call this methods on the main thread.
 */
@interface WebKitViewController : UIViewController

@property(nonatomic, weak) id<BDocPlayerDelegate> delegate;

/**
 *  load document.
 * usage:
 *      NSDictionary* json = @{
 *          BDocPlayerSDKeyDocID: @"833a2feeed630b0c58eeb591",
 *          BDocPlayerSDKeyDocTitle: @"神雕侠侣",
 *          BDocPlayerSDKeyTotalPageNum: @"6",
 *          BDocPlayerSDKeyToken: @"bb4c12b56d6633876e8c5c6610dbd663|1462334386",
 *          BDocPlayerSDKeyHost: @"doc.bce-testinternal.baidu.com",
 *          BDocPlayerSDKeyDocType: @"ppt",
 *          BDocPlayerSDKeyFileDir: @"/path/to/load/document",
 *          // any other keys
 *          // ...
 *      };
 */
- (void)loadDoc:(NSDictionary*)parameters;

@end

/**
 *  download the doc to local filesystem.
 */
@interface WebKitViewController (Download)

/**
 *  async method, will returned immediately.
 */
- (void)downloadDoc:(NSDictionary*)parameters;

/**
 *   cancel the download action.
 */
- (void)cancelDownload;

@end
