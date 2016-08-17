README.txt

一、安装依赖模块
$ npm install bce-sdk-js
$ npm install formidable
$ npm install express


二、运行vod_main.js
$ node vod_main.js


三、接口概览
列出媒资：GET /listMedias
获取媒资信息：GET /getMediaInfo?mediaId=mda-gggdqvb92w85jd1u 
删除媒资：DELETE /deleteMedia?mediaId=mda-gggdqvb92w85jd1u
停用媒资：POST /stopMediaResource?mediaId=mda-ghmm4a9jgxb7cwr9
发布媒资：POST /publishMediaResource?mediaId=mda-ghmm4a9jgxb7cwr9
获取播放代码：/getPlayerCode?mediaId=mda-ghmm4a9jgxb7cwr9&width=480&height=320&autoStart=true
获取播放url：GET /getPlayableUrl?mediaId=mda-ghmm4a9jgxb7cwr9
播放媒资：GET /playMedia?mediaId=mda-gbbhmgupzx9f1h0m

四、接口详细介绍
1、列出所有媒资：
http://xxx.xxx.xxx.xxx:8888/listMedias
请求示例：GET /listMedias
响应示例：
{
    "media": [
    {
            "mediaId": "mda-ghmm4a9jgxb7cwr9",
            "status": "PUBLISHED",
            "attributes": {
                "title": "test title from vod",
                "description": "test description",
                "sourceExtension": ""
            },
            "meta": {
                "sourceSizeInBytes": 4510436,
                "durationInSeconds": 30,
                "sizeInBytes": 10717637
            },
            "publishTime": "2016-08-12T11:38:47Z",
            "createTime": "2016-08-12T11:36:01Z",
            "playableUrlList": [
                {
                    "url": "http://gcikt8uzsimpsu6ydvu.exp.bcevod.com/mda-ghmm4a9jgxb7cwr9/mda-ghmm4a9jgxb7cwr9.m3u8"
                }
            ],
            "thumbnailList": [
                "http://gcikt8uzsimpsu6ydvu.exp.bcevod.com/mda-ghmm4a9jgxb7cwr9/mda-ghmm4a9jgxb7cwr9.jpg"
            ]
        },
        {
           ...
        },
         ...
        ],    
    "pageNo": 0,
    "pageSize": 0,
    "totalCount": 0
}

2、文件上传（调用createMedia接口）
http://xxx.xxx.xxx.xxx:8888/upload.html

3、创建媒资（本示例通过获取表单信息，使用stream的方式上传）
http://xxx.xxx.xxx.xxx:8888/createMedia


4、获取媒资信息
http://xxx.xxx.xxx.xxx:8888/getMediaInfo
请求示例：GET /getMediaInfo?mediaId=mda-gggdqvb92w85jd1u 
响应示例：
{
  "mediaId": "mda-gggdqvb92w85jd1u",
  "status": "PUBLISHED",
  "attributes": {
    "title": "lss-ggfk0ijvj91svthc/recording_20160707111506",
    "description": "lss recording, info:lss-ggfk0ijvj91svthc/recording_20160707111506",
    "sourceExtension": "mp4"
  },
  "meta": {
    "sourceSizeInBytes": 17280773,
    "durationInSeconds": 45,
    "sizeInBytes": 50741013
  },
  "publishTime": "2016-07-07T03:18:39Z",
  "createTime": "2016-07-07T03:15:52Z"
}

5、删除媒资
http://xxx.xxx.xxx.xxx:8888/deleteMedia?mediaId=mda-gggdqvb92w85jd1u
请求示例：DELETE /deleteMedia?mediaId=mda-gggdqvb92w85jd1u
响应示例：无

6、停用媒资
http://xxx.xxx.xxx.xxx:8888/stopMediaResource?mediaId=mda-ghmm4a9jgxb7cwr9
请求示例：POST /stopMediaResource?mediaId=mda-ghmm4a9jgxb7cwr9
响应示例：无

7、发布媒资
http://xxx.xxx.xxx.xxx:8888/publishMediaResource
请求示例：POST /publishMediaResource?mediaId=mda-ghmm4a9jgxb7cwr9
响应示例：无


8、获取播放代码
http://xxx.xxx.xxx.xxx:8888/getPlayerCode?mediaId=mda-ghmm4a9jgxb7cwr9&width=480&height=320&autoStart=true
请求示例：GET /getPlayerCode?mediaId=mda-ghmm4a9jgxb7cwr9&width=480&height=320&autoStart=true
响应示例：
{
  "codes": [
    {
      "codeType": "html",
      "sourceCode": "PGRpdiBpZD0icGxheWVyY29udGFpbmVyIj48L2Rpdj4NCjxzY3JpcHQgdHlwZT0idGV4dC9qYXZhc2NyaXB0IiBzcmM9Imh0dHA6Ly9yZXNvdXJjZS5iY2V2b2QuY29tL3ZvZC92MS9vbmxpbmUvYWRhcHRvci5qcyI+PC9zY3JpcHQ+DQo8c2NyaXB0IHR5cGU9InRleHQvamF2YXNjcmlwdCI+DQogICAgbmV3IHZvZFBsYXllcigicGxheWVyY29udGFpbmVyIiwgew0KICAgICAgICAiYXV0b19zdGFydCI6IHRydWUsDQogICAgICAgICJtZWRpYV9pZCI6ICdtZGEtZ2htbTRhOWpneGI3Y3dyOScsDQogICAgICAgICJ3aWR0aCI6IDQ4MCwNCiAgICAgICAgImhlaWdodCI6IDMyMA0KICAgIH0pOw0KPC9zY3JpcHQ+DQo="
    },
    {
      "codeType": "flash",
      "sourceCode": "PG9iamVjdCB0eXBlPSJhcHBsaWNhdGlvbi94LXNob2Nrd2F2ZS1mbGFzaCIgd2lkdGg9IjQ4MCIgaGVpZ2h0PSIzMjAiIGlkPSJjb3B5X2Zyb21fYmNlX3ZvZF9tZGEtZ2htbTRhOWpneGI3Y3dyOSIgZGF0YT0iaHR0cDovL3Jlc291cmNlLmJjZXZvZC5jb20vdm9kL3YxL29ubGluZS9QbGF5ZXIuc3dmIj4NCiAgICA8cGFyYW0gbmFtZT0ic3JjIiB2YWx1ZT0iaHR0cDovL3Jlc291cmNlLmJjZXZvZC5jb20vdm9kL3YxL29ubGluZS9QbGF5ZXIuc3dmIiAvPg0KICAgIDxwYXJhbSBuYW1lPSJmbGFzaFZhcnMiIHZhbHVlPSJtZWRpYV9pZD1tZGEtZ2htbTRhOWpneGI3Y3dyOSZhdXRvX3N0YXJ0PXRydWUiIC8+DQogICAgPHBhcmFtIG5hbWU9ImFsbG93TmV0d29ya2luZyIgdmFsdWU9ImFsbCIgLz4NCiAgICA8cGFyYW0gbmFtZT0iYWxsb3dGdWxsU2NyZWVuIiB2YWx1ZT0idHJ1ZSIgLz4NCiAgICA8cGFyYW0gbmFtZT0id21vZGUiIHZhbHVlPSJ3aW5kb3ciIC8+DQo8L29iamVjdD4="
    },
    {
      "codeType": "url",
      "sourceCode": "http://resource.bcevod.com/vod/v1/online/Player.swf?media_id=mda-ghmm4a9jgxb7cwr9&auto_start=true"
    }
  ]
}

9、获取播放URL
http://xxx.xxx.xxx.xxx:8888/getPlayableUrl
请求示例：GET /getPlayableUrl?mediaId=mda-ghmm4a9jgxb7cwr9
响应示例：
{
  "success": true,
  "result": {
    "title": null,
    "duration": null,
    "media_id": "mda-ghmm4a9jgxb7cwr9",
    "file": "http://gcikt8uzsimpsu6ydvu.exp.bcevod.com/mda-ghmm4a9jgxb7cwr9/mda-ghmm4a9jgxb7cwr9.m3u8",
    "cover": "http://gcikt8uzsimpsu6ydvu.exp.bcevod.com/mda-ghmm4a9jgxb7cwr9/mda-ghmm4a9jgxb7cwr9.jpg",
    "source": null
  }
}

10、播放媒资(可直接跳转到Baidu Web播放器页面)：
http://xxx.xxx.xxx.xxx:8888/playMedia
http://xxx.xxx.xxx:8888/playMedia?mediaId=mda-gbbhmgupzx9f1h0m

