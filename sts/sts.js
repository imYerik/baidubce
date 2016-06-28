var STS = require('bce-sdk-js').STS;

var config = {
    endpoint: 'http://sts.bj.baidubce.com',
    credentials: {
        ak: 'your bce ak',
        sk: 'your bce sk'
    }
};
var stsClient = new STS(config);
var res = stsClient.getSessionToken(6000, {
            accessControlList: [{
                service: 'bce:bos',
                resource: ['sourcebucket/*'],
                region: 'bj',
                effect: 'Allow',
                permission: ['READ', 'WRITE']
            }]
});

res.then(function(data){console.log(data)});   //res是一个异步promise对象
