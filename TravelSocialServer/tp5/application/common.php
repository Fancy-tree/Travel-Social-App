<?php
use Think\Db;
/**
 * 获取数据，返回json格式数据
 * @param
 * $code：标识符
 * $msg：消息
 *$data：返回数据
 * @return j-son格式值
 */

function returnJson($code,$msg,$data=array()){
    $result_json = array(
        'code' => $code,
        'message' => $msg,
        'result' => $data
    );
    return json($result_json);
}



//function GetRandCode($len) {
//    $chars = array("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k","l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v","w", "x", "y", "z","0", "1", "2","3", "4", "5", "6", "7", "8", "9");
//    $charsLen = count($chars) - 1;
//    shuffle($chars);
//    $output = "";
//    for ($i=0; $i<$len; $i++){
//        $output .= $chars[mt_rand(0, $charsLen)];
//    }
//    return $output;
//}
/**
 * 生成随机数--标识符（手机号+加密算法）
 * @param $len
 * @return string
 */
function setAppLoginToken($phone = '') {
    $str = md5(uniqid(md5(microtime(true)), true));
    $str = sha1($str.$phone);
    return $str;
}

