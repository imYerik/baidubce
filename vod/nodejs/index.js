var server = require('./server.js');
var router = require('./router.js');
var requestHandlers = require('./requestHandler.js');

var handle = {}
handle["/"] = requestHandlers.start; 
handle["/start"] = requestHandlers.start; 
handle["/upload"] = requestHandlers.upload;
handle["/getMediaInfo"] = requestHandlers.getMediaInfo;
handle["/createMedia"] = requestHandlers.createMedia;
handle["/listMedias"] = requestHandlers.listMedias;
handle["/deleteMedia"] = requestHandlers.deleteMedia;
handle["/stopMedia"] = requestHandlers.stopMedia;
handle["/publishMedia"] = requestHandlers.publishMedia;
handle["/getPlayUrl"] = requestHandlers.getPlayUrl;

server.start(router.route, handle);
