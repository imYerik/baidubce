本示例为通过STS授权方式访问VOD；

注：如果使用普通的授权方式，不使用STS获取的临时AK SK，则直接修改vod-v1-sample.php文件中的AK、SK为自己BCE账号的AK、SK即可，sessionToken保持为空；


STS服务可以生成一个临时的AK SK，这个临时的AK SK有有效期，从而保证安全性，多用于移动终端、web前端的方式访问BCE的服务；
目前开放云的STS服务只提供了Java SDK，如果使用其他语言的开发者，只能参考官方的开源SDK以及REST API文档编写，STS服务的使用方式与BCE的大多数的服务类似，通过签名算法来实现；

关于STS的详细介绍不再赘述，可以参考BCE文档：https://bce.baidu.com/doc/BOS/API.html#.E8.AE.BF.E9.97.AE.E6.8E.A7.E5.88.B6

sts-v1.1-vod.php	获取STS服务生成的临时AK、SK以及sessionToken	
vod-v1-sample.php	通过STS生成的验证信息访问VOD服务，演示查询某个媒资信息；

使用方法：
1、将sts-v1.1-vod.php中的AK、SK改成自己的BCE账号的AK、SK ，运行该文件可以获取一个STS服务生成的AK、SK以及sessionToken；
注意：VOD对STS得到的ak/sk只提供新增媒资和获取单个媒资的权限，且不可设置，所以其中传递给STS服务的json body权限设置为空！

2、修改vod-v1-sample.php文件中的AK、SK、sessionToken，值为第一步STS获取的AK、SK以及sessionToken；修改mediaId为自己创建的媒资ID；

3、运行vod-v1-sample.php 文件，将返回自己的媒资信息；
