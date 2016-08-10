var mediaResource = require('./mediaResource.js');
var querystring = require('querystring');
var fs = require('fs');
var formidable = require('formidable');

function start(response, request){
    console.log("Request handler 'start' was called.");
   var body = '<html>'+
'<head>'+
'<meta charset="utf-8"/>'+
'<title>+Hello VOD ! </title>'+
'</head>'+

'<body>'+
'<form action="/upload" enctype="multipart/form-data" method="post">'+
'<input type="file" name="upload" multiple="multiple">'+
'<input type="submit" value="Submit text" />'+

'<p>'+
'<a href=http://www.yerik.cn:8888/getMediaInfo>获取媒资信息</a>'+
'<p>'+
'<a href=http://www.yerik.cn:8888/listMedias>列出所有媒资</a>'+
'<p>'+
'<a href=http://www.yerik.cn:8888/createMedia>创建媒资</a>'+
'<p>'+
'<a href=http://www.yerik.cn:8888/deleteMedia>删除媒资</a>'+
'<p>'+
'<a href=http://www.yerik.cn:8888/stopMedia>禁用媒资</a>'+
'<p>'+
'<a href=http://www.yerik.cn:8888/publishMedia>恢复媒资</a>'+
'<p>'+
'<a href=http://www.yerik.cn:8888/getPlayUrl>获取媒资播放地址</a>'+
'<p>'+
'</body>'+
'</html>';
    response.writeHead(200, {'Content-Type': 'text/html'});
    response.write(body);
    response.end();
}

function upload(response, request) {
    console.log("Request handler 'upload' was called.");
    var form = new formidable.IncomingForm();
    form.parse(request, function (error, fields, files) {
        fs.renameSync(files.upload.path, '/tmp/test.mp4');
        response.writeHead(200, {'Content-Type': 'text/html'});
        response.write('You\'ve sent: <br />');
       // response.write('<img src="/show" />');
        response.end();
    });
}

function getMediaInfo(response, request) {
    console.log("Request handler 'getMediaInfo' was called.");
    var content =  mediaResource.getMediaInfo(); 
    //var content = "getMedia INfo";
    response.writeHead(200, {'Content-Type': 'text/html'});
    response.write(content + '');
    response.end();
}

function createMedia() {
    console.log("Request handler 'createMedia' was called.");
    mediaResource.createMedia(); 
}

function listMedias() {
    console.log("Request handler 'listMedias' was called.");
    mediaResource.listMedias(); 
}
function deleteMedia() {
    console.log("Request handler 'deleteMedia' was called.");
    mediaResource.deleteMedia(); 
}
function stopMedia() {
    console.log("Request handler 'stopMedia' was called.");
    mediaResource.stopMedia(); 
}
function publishMedia() {
    console.log("Request handler 'publishMedia' was called.");
    mediaResource.publishMedia(); 
}
function getPlayUrl() {
    console.log("Request handler 'publishMedia' was called.");
    mediaResource.getPlayUrl(); 
}
exports.start = start;
exports.upload = upload;
exports.getMediaInfo = getMediaInfo;
exports.createMedia = createMedia;
exports.listMedias = listMedias;
exports.deleteMedia = deleteMedia;
exports.stopMedia = stopMedia;
exports.publishMedia = publishMedia;
exports.getPlayUrl= getPlayUrl;;
