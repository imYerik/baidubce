var koa = require('koa');
var logger = require('koa-logger');
var router = require('koa-router')();
var app = koa();
var url = require('url');

var STS = require('bce-sdk-js').STS;

var kCredentials = {
    ak: 'i4b7b21c9d0844b388eac1521d7c943d4',
    sk: '70005e84e6c9497788012bf1d3475ca6'
};
var kRegion = 'bj';

function safeParse(text) {
    try {
        return JSON.parse(text);
    }
    catch (ex) {
        return null;
    }
}
app.use(logger());

router
    .get('/sts', function *(next){
        var stsClient = new STS({
            credentials: kCredentials,
            region: kRegion,
            protocol: 'http'
        });
        var res = yield stsClient.getSessionToken(6000, {
            accessControlList: [{
                service: 'bce:bos',
                resource: ['bce-javascript-sdk-demo-test'],
                region: '*',
                effect: 'Allow',
                permission: ['READ', 'WRITE']
            }]
        });
        this.body = JSON.stringify(res.body);
    });

app.use(router.routes())
    .use(router.allowedMethods());

app.listen(3000);
