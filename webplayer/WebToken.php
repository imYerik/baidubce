<?php

$userId = "33x13c17k965928xa8dbc6360722558d"; //账号的用户userid，通过console右上角的账号信息获取
$userKey = "ef9ce14f8c3d49ba";   //User Key，目前console还没有获取方式，需要提工单获取
$expireTime = time() + 3600;
$mediaId = "job-gjchoc1rugvzztys";  //如果是VOD，则使用mediaId;如果是MCT则使用job id；

$signature = hash_hmac("sha256",sprintf("/%s/%s",$mediaId,$expireTime),$userKey);
echo json_encode(array("token" => sprintf("%s_%s_%s",$signature,$userId,$expireTime)));
exit;

?>
