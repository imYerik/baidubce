DOC 三步上传使用说明：

1、注册文档：注册文档接口用于生成文档的唯一标识documentId、用于存储源文档文件的BOS Bucket相关信息。注册成功后，对应文档状态为UPLOADING，对应Bucket对用户开放写权限，对于用户的BOS空间不可见。

2、上传BOS：根据注册文档返回的BOS信息，调用BOS API SDK上传文档到DOC服务的后端BOS存储中；

3、发布文档：用于对已完成注册和BOS上传的文档进行发布处理。仅对状态为UPLOADING的文档有效。处理过程中，文档状态为PROCESSING；处理完成后，状态转为PUBLISHED。



DocConf.php：用户配置信息（AK、SK、域名）	
BceSign.php：生成BCE签名
registerDoc.php：注册文档(指定文档的名称和格式，返回文档ID以及BOS信息)
publishDoc.php：发布文档(指定文档ID发布)
listDocs.php：列出所有文档
getDoc.php：查看指定文档


Demo使用方法：

1、将DocConf.php中的AK、SK替换成自己的BCE AK、SK；

2、运行registerDoc.php，注册文档；运行成功会返回一个documentId和相应的BOS Bucket信息；
返回示例：
{
    "documentId":"doc-ggct7nhqayj8crj",
    "bucket":"bktmgesc0rdf6bzcgklk",
    "object":"upload/doc-gwwt7chqaoj8crj.doc",
    "bosEndpoint":"http://bj.bcebos.com"
}

3、根据步骤2返回的信息，调用BOS的API SDK上传接口，上传到DOC服务后端的BOS中；(略，参考BCE BOS使用方法)

4、发布文档，将步骤2返回的documentId传入，运行即可发布文档，DOC服务端可以自动实现文档转码；

5、查看所有文档(listDocs.php)、查看指定文档（getDoc.php）;
