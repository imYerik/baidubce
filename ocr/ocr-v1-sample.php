<?php
/* vim: set expandtab tabstop=4 shiftwidth=4: */
// +----------------------------------------------------------------------+
// | PHP version 5                                                        |
// +----------------------------------------------------------------------+
// | Copyright (c) 1997-2004 The PHP Group                                |
// +----------------------------------------------------------------------+
// | This source file is subject to version 3.0 of the PHP license,       |
// | that is bundled with this package in the file LICENSE, and is        |
// | available through the world-wide-web at the following url:           |
// | http://www.php.net/license/3_0.txt.                                  |
// | If you did not receive a copy of the PHP license and are unable to   |
// | obtain it through the world-wide-web, please send a note to          |
// | license@php.net so we can mail you a copy immediately.               |
// +----------------------------------------------------------------------+
// | Authors: Original Author < yerik.gao@gmail.com >                     |
// |          Your Name < Mr Gao >                                        |
// +----------------------------------------------------------------------+
//
// $Id:$

class HttpUtil {
    // 根据RFC 3986，除了：
    //   1.大小写英文字符
    //   2.阿拉伯数字
    //   3.点'.'、波浪线'~'、减号'-'以及下划线'_'
    // 以外都要编码
    public static $PERCENT_ENCODED_STRINGS;
    //填充编码数组
    public static function __init() {
        HttpUtil::$PERCENT_ENCODED_STRINGS = array();
        for ($i = 0; $i < 256; ++$i) {
            HttpUtil::$PERCENT_ENCODED_STRINGS[$i] = sprintf("%%%02X", $i);
        }
        //a-z不编码
        foreach (range('a', 'z') as $ch) {
            HttpUtil::$PERCENT_ENCODED_STRINGS[ord($ch) ] = $ch;
        }
        //A-Z不编码
        foreach (range('A', 'Z') as $ch) {
            HttpUtil::$PERCENT_ENCODED_STRINGS[ord($ch) ] = $ch;
        }
        //0-9不编码
        foreach (range('0', '9') as $ch) {
            HttpUtil::$PERCENT_ENCODED_STRINGS[ord($ch) ] = $ch;
        }
        //以下4个字符不编码
        HttpUtil::$PERCENT_ENCODED_STRINGS[ord('-') ] = '-';
        HttpUtil::$PERCENT_ENCODED_STRINGS[ord('.') ] = '.';
        HttpUtil::$PERCENT_ENCODED_STRINGS[ord('_') ] = '_';
        HttpUtil::$PERCENT_ENCODED_STRINGS[ord('~') ] = '~';
    }
    //在uri编码中不能对'/'编码
    public static function urlEncodeExceptSlash($path) {
        return str_replace("%2F", "/", HttpUtil::urlEncode($path));
    }
    //使用编码数组编码
    public static function urlEncode($value) {
        $result = '';
        for ($i = 0; $i < strlen($value); ++$i) {
            $result.= HttpUtil::$PERCENT_ENCODED_STRINGS[ord($value[$i]) ];
        }
        return $result;
    }
    //生成标准化QueryString
    public static function getCanonicalQueryString(array $parameters) {
        //没有参数，直接返回空串
        if (count($parameters) == 0) {
            return '';
        }
        $parameterStrings = array();
        foreach ($parameters as $k => $v) {
            //跳过Authorization字段
            if (strcasecmp('Authorization', $k) == 0) {
                continue;
            }
            if (!isset($k)) {
                throw new InvalidArgumentException("parameter key should not be null");
            }
            if (isset($v)) {
                //对于有值的，编码后放在=号两边
                $parameterStrings[] = HttpUtil::urlEncode($k) . '=' . HttpUtil::urlEncode((string)$v);
            } else {
                //对于没有值的，只将key编码后放在=号的左边，右边留空
                $parameterStrings[] = HttpUtil::urlEncode($k) . '=';
            }
        }
        //按照字典序排序
        sort($parameterStrings);
        //使用'&'符号连接它们
        return implode('&', $parameterStrings);
    }
    //生成标准化uri
    public static function getCanonicalURIPath($path) {
        //空路径设置为'/'
        if (empty($path)) {
            return '/';
        } else {
            //所有的uri必须以'/'开头
            if ($path[0] == '/') {
                return HttpUtil::urlEncodeExceptSlash($path);
            } else {
                return '/' . HttpUtil::urlEncodeExceptSlash($path);
            }
        }
    }
    //生成标准化http请求头串
    public static function getCanonicalHeaders($headers) {
        //如果没有headers，则返回空串
        if (count($headers) == 0) {
            return '';
        }
        $headerStrings = array();
        foreach ($headers as $k => $v) {
            //跳过key为null的
            if ($k === null) {
                continue;
            }
            //如果value为null，则赋值为空串
            if ($v === null) {
                $v = '';
            }
            //trim后再encode，之后使用':'号连接起来
            $headerStrings[] = HttpUtil::urlEncode(strtolower(trim($k))) . ':' . HttpUtil::urlEncode(trim($v));
        }
        //字典序排序
        sort($headerStrings);
        //用'\n'把它们连接起来
        return implode("\n", $headerStrings);
    }
}
HttpUtil::__init();
function getSigner($host,$httpMethod, $path, $parms, $timestamp) {
    //	date_default_timezone_set('UTC');
    $AK = "4b7b21x9d08449388eau1521d7c943d4";    //替换成BCE的AK
    $SK = "70005e84e6c949778p0123f1d3475ca6";    //替换成BCE的SK
    $expirationPeriodInSeconds = "3600";
    //$timestamp = date("Y-m-d")."T".date("H:i:s")."Z";
    $authStringPrefix = "bce-auth-v1" . "/" . $AK . "/" . $timestamp . "/" . $expirationPeriodInSeconds;
    $SigningKey = hash_hmac('SHA256', $authStringPrefix, $SK);
    $CanonicalHeaders1 = "host;" . "x-bce-date";
    $CanonicalHeaders2 = "host:".$host."\n" . "x-bce-date:" . urlencode($timestamp); //
    //print "CanonicalHeaders2:" . $CanonicalHeaders2 . "\n";
    $httputil = new HttpUtil();
    $CanonicalString = $httputil->getCanonicalQueryString($parms);
    $CanonicalURI = $path;
    $Method = $httpMethod;
    $CanonicalRequest = $Method . "\n" . $CanonicalURI . "\n" . $CanonicalString . "\n" . $CanonicalHeaders2;
    //print "CanonicalRequest:" . $CanonicalRequest . "\n";
    $Signature = hash_hmac('SHA256', $CanonicalRequest, $SigningKey);
    //print "Signature:" . $Signature . "\n";
    $Authorization = "bce-auth-v1/{$AK}/" . $timestamp . "/{$expirationPeriodInSeconds}/{$CanonicalHeaders1}/{$Signature}";
    print "Authorization:" . $Authorization . "\n";
    return $Authorization;
    //$Authorization:
    //bce-auth-v1/97659fdf3ab547e2a9f71dfcd6659a8b2015-08-27T03:50:33Z/3600/content-length;host;x-bce-date;/5290e5669befd7f44dd362e00c2a4cc5edbee0bc925bceb15e8a9fcb91389201
    
}

//查询媒资信息示例

$host= "ocr.bj.baidubce.com";
$path = "/v1/recognize/text";
$url = "http://".$host.$path;
$method = "POST";
$parms = array();

date_default_timezone_set('UTC');
$timestamp = date("Y-m-d") . "T" . date("H:i:s") . "Z";
$Authorization = getSigner($host,$method,$path, $parms, $timestamp);


$data = array(
           'bosPath' => 'yerik-ocr/ocr.png',
        );

$data_string = json_encode($data);
$head = array(
    "Content-Type:application/json",
    "Authorization:{$Authorization}",
    "x-bce-date:{$timestamp}",
);
$curlp = curl_init();
curl_setopt($curlp, CURLOPT_URL, $url);
curl_setopt($curlp, CURLOPT_HTTPHEADER, $head);
curl_setopt($curlp, CURLOPT_POSTFIELDS, $data_string);
curl_setopt($curlp, CURLOPT_POST, 1);
curl_setopt($curlp, CURLOPT_RETURNTRANSFER, 1);
$output = curl_exec($curlp);
curl_close($curlp);
echo $output;
print "\n";
?>
