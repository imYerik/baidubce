var express = require('express');
var sdk = require('bce-sdk-js');
var config = require('./config.js');
var VodClient = sdk.VodClient;
var client = new VodClient(global.config);
var fs = require("fs");
var app = express();
var formidable = require("formidable");

//  主页GET请求默认跳转到媒资列表
app.get('/', function (req, res) {
   console.log("主页 GET 请求");
   res.redirect(301,'/listMedias');
})
//上传页面
app.get('/upload.html', function (req, res) {
   console.log("上传文件请求");
   res.sendFile( __dirname + "/" + "upload.html" ); 
})

//播放页面
app.get('/player.html', function (req, res) {
   console.log("播放请求");
   res.sendFile( __dirname + "/" + "player.html" ); 
})
app.get('/player/cyberplayer.js', function (req, res) {
   console.log("加载cyberplayer.js文件");
   res.sendFile( __dirname + "/" + "player/cyberplayer.js" ); 
})
app.get('/player/cyberplayer.flash.swf', function (req, res) {
   console.log("加载cyberplayer.flash.swf文件");
   res.sendFile( __dirname + "/" + "player/cyberplayer.flash.swf" ); 
})

//  /deleteMedia页面响应
app.delete('/deleteMedia', function (req, res) {
   console.log("/deleteMedia 响应 DELETE 请求");
   var mediaId = req.query.mediaId;
   console.log('媒资Id：'+mediaId);
   client.deleteMediaResource(mediaId)
    .then(function (response) {
        // 删除成功
        var body = response.body;
        console.log(response)
        res.send(body);
        //res.send(mediaId + '删除成功'); 
    })
    .catch(function (error) {
        console.log(error);
        // 删除错误
        res.send(error);
    });
})

//  /listMedias 页面 GET 请求
app.get('/listMedias', function (req, res) {
   console.log("/listMedias GET 请求");
   client.listMediaResources()
    .then(function (response) {
        // 查询成功
        for (var i = 0; i < response.body.media.length; i++) {
            console.log(response.body.media[i])
        }
        var body = response.body;
        res.send(body);
    })
    .catch(function (error) {
        console.log(error);
        // 查询错误
        res.send(error);
    });
})


//获取媒资信息
app.get('/getMediaInfo', function(req, res) {   
   console.log("/getMediaInfo GET 请求");
   var mediaId = req.query.mediaId;
   console.log('媒资Id：'+mediaId);
   client.getMediaResource(mediaId)
          .then(function (response) {
        // 查询成功
        var body = response.body;
        console.log(body);
        res.send(body);
    })
    .catch(function (error) {
        console.log(error);
        // 查询错误
        res.send(error);
    });
})

//上传并创建媒资
app.post('/createMedia',function(req,res){
    console.log("/createMedia POST请求");
    var form = new formidable.IncomingForm();
    form.parse(req,function(err,fields,files){
        console.log('媒资标题:'+fields.title);
        console.log('媒资描述:'+fields.description);
        console.log('文件列表：'+files);
        var title = fields.title ;
        var description = fields.description ;
        fs.readFile(files.upload.path,function(err,data){
           client.createMediaResource(title, description,data)
                  // Node.js中<data>可以为一个Stream、<pathToFile>；在浏览器中<data>为一个Blob对象
                 .then(function (response) {
                     // 上传完成
                     console.log('媒资ID:'+response.body.mediaId);
                     res.send('媒资ID:'+response.body.mediaId); 
                 })
                 .catch(function (error) {
                     console.log(error);
                     // 上传错误
                     res.send(error);
                 });
           });
        
     });
})

//停用媒资
app.post('/stopMediaResource',function(req,res){
    console.log('/stopMediaResource POST请求');
    var mediaId = req.query.mediaId; 
    client.stopMediaResource(mediaId)
    .then(function (response) {
        // 查询成功
        var body = response.body;
        console.log(body);
        res.send(body);
    })
    .catch(function (error) {
        console.log(error);
        // 查询错误
        res.send(error);
    }); 
})

//启用媒资
app.post('/publishMediaResource',function(req,res){
    console.log('/publishMediaResource POST请求');
    var mediaId = req.query.mediaId;
    client.publishMediaResource(mediaId)
    .then(function (response) {
        // 查询成功
        var body = response.body;
        console.log(body);
        res.send(body);
    })  
    .catch(function (error) {
        console.log(error);
        // 查询错误
        res.send(error);
    });
})

//更新媒资title和description
app.put('/updateMediaResource',function(req,res){
    console.log('/updateMediaResource POST请求');
    var mediaId = req.query.mediaId;
    var title = req.query.title;
    var description = req.query.description;
    
    client.updateMediaResource(mediaId, title,description)
    .then(function (response) {
        // 更新成功
        console.log(response)
        res.send(response.body);
    })
    .catch(function (error) {
        console.log(error);
        // 更新错误
        res.send(error);
    });
})



//通过媒资ID播放媒资
app.get('/playMedia',function(req,res){
    console.log('/playMedia GET请求');
    var mediaId = req.query.mediaId;
    client.getPlayableUrl(mediaId)
    .then(function (response) {
        var body = response.body;
        console.log(body);
        //res.send(body);
        res.redirect(301,'/player.html?file='+response.body.result.file);
    });
})

//获取播放地址
app.get('/getPlayableUrl',function(req,res){
    console.log('/getPlayableUrl GET请求');
    var mediaId = req.query.mediaId;
    client.getPlayableUrl(mediaId)
    .then(function (response) {
        var body = response.body;
        console.log(body);
        res.send(body);
    });
})


//获取播放代码
app.get('/getPlayerCode',function(req,res){
    console.log('getPlayerCode GET请求');
    var mediaId = req.query.mediaId;
    var width = req.query.width;
    var height = req.query.height;
    var autoStart = req.query.autoStart;
    console.log(mediaId);
    console.log(width);
    console.log(height);
    console.log(autoStart);
    client.getPlayerCode(mediaId, width, height, autoStart)
    .then(function (response) {
        var body = response.body;
        console.log(body);
        res.send(body);
    });
})

//创建Server
var server = app.listen(8888, function () {

  var host = server.address().address
  var port = server.address().port

  console.log("应用实例，访问地址为 http://%s:%s", host, port)

})
