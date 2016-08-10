var sdk = require('bce-sdk-js');
var config = require('./config.js');
var VodClient = sdk.VodClient;
var client = new VodClient(global.config);



//获取媒资信息
var mediaId = 'mda-ghiip5y0nup7jve9';
function getMediaInfo() {
    console.log("Request handler 'getMediaInfo' was called.");
    client.getMediaResource(mediaId)
    .then(function (response) {
        // 查询成功
        console.log(response);
        //return response;
    })
    .catch(function (error) {
        console.log(error);
        //return error;
        // 查询错误
    });
 
}

//getMediaInfo();

//列出媒资
function listMedias(){
    console.log("Request handler 'listMedias' was called.");
    client.listMediaResources()
    .then(function (response) {
        // 查询成功
        for (var i = 0; i < response.body.media.length; i++) {
            console.log(response.body.media[i])
        }
    })
    .catch(function (error) {
        console.log(error);
        // 查询错误
    });
}

//创建媒资
var title = 'testMeidaAAA';
var description = 'this is a test mediaAAA';
var file = '/tmp/test.mp4';

function createMedia(){
    client.createMediaResource(title,description, file)
    // Node.js中<data>可以为一个Stream、<pathToFile>；在浏览器中<data>为一个Blob对象
    .then(function (response) {
        // 上传完成
        console.log(response.body.mediaId);
    })
    .catch(function (error) {
        console.log(error);
        // 上传错误
    });
}

//createMedia();

//删除媒资
var mediaId = 'mda-ghikar00m2rm45mi';
function deleteMedia(){
    client.deleteMediaResource(mediaId)
    .then(function (response) {
        // 查询成功
        console.log(response)
    })
    .catch(function (error) {
        console.log(error);
        // 查询错误
    });
}

//禁用媒资
var mediaId = 'mda-ghiisviri64tx0pa';
function stopMedia(){
    client.stopMediaResource(mediaId)
    .then(function (response) {
        // 查询成功
        console.log(response)
    })
    .catch(function (error) {
        console.log(error);
        // 查询错误
    });
}

//启用媒资
var mediaId = 'mda-ghiisviri64tx0pa';
function publishMedia(){
    client.publishMediaResource(mediaId)
    .then(function (response) {
        // 查询成功
        console.log(response)
    })
    .catch(function (error) {
        console.log(error);
        // 查询错误
    });
}

//获取媒资播放URL
var mediaId = 'mda-ghiisviri64tx0pa';
function getPlayUrl(){
    client.getPlayableUrl(mediaId)
    .then(function (response) {
        console.log(response);
    });
}


exports.getMediaInfo = getMediaInfo;
exports.createMedia=createMedia;
exports.listMedias=listMedias;
exports.deleteMedia=deleteMedia;
exports.stopMedia=stopMedia;
exports.publishMedia=publishMedia;
exports.getPlayUrl=getPlayUrl;;
