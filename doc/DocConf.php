<?php
/*
* Copyright (c) 2014 Baidu.com, Inc. All Rights Reserved
*
* Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
* the License. You may obtain a copy of the License at
*
* Http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
* an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
* specific language governing permissions and limitations under the License.
*/


// 报告所有 PHP 错误
error_reporting(-1);

$my_credentials = array(
		'ak' => '4b7b41c9d0844b38jeac1521d7c943d4',
		'sk' => '70009e8486c94l7788012bf1d3475ca6',
);
$g_doc_configs = array(
		'credentials' => $my_credentials,
		'endpoint' => 'doc.baidubce.com',
);
