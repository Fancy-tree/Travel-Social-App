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

//上传图片
//function base64imgsave($img_base64,$lid,$if_back){
//    //文件夹日期
//    $ymd = date("Ymd");
//    //图片存储路径地址
//    $basedir = ROOT_PATH.'public/upload/TravelBlog/'.$ymd.'';
//    $fullpath = $basedir;
//    if (empty($img_base64)){
//        //空的图片，
//        return 2;
//    }else{
//        if(!is_dir($fullpath)){
//            //如果图片存储路径不存在，则新建文件夹，0777，意味着最大可能的访问权
//            mkdir($fullpath,0777,true);
//        }
//
//        if(preg_match('/^(data:\s*image\/(\w+);base64,)/', $img_base64, $result)){
//            //正则判断
//            $type = $result[2];//传过来的图片后缀
//            if(in_array($type,array('pjpeg','jpeg','jpg','gif','bmp','png'))){
//                //判断穿过来的数据，有没有其中的后缀
//                $new_name = time().uniqid();//唯一的名称
//                $new_file = $fullpath.'/'.$new_name.'.'.$type;
//                $base64_img = str_replace($result[1],'', $img_base64);//替换
//                if(file_put_contents($new_file, base64_decode($base64_img))){
//                    //把base64换回来，抓取
//                    $img_storage=Db::execute("INSERT INTO tb_log_images (lid, ipath,back_img) VALUES ('".$lid."','".$new_file."','".$if_back."')");
//                    if(empty($img_storage) ) {
//                        return false;
//                    } else {
//                        return true;//存储文件名到数据库成功
//                    }
//                }else{
//                    return 3;//存储图片到规定路径失败
//                }
//
//            }else{
//                //文件类型错误
//                return 4;
//            }
//        }else{
//                //文件错误
//            return 5;
//        }
//    }
//}

function base64imgsave($img_base64,$lid,$if_back,$order){
    //文件夹日期
    $ymd = date("Ymd");
    //图片存储路径地址
    $basedir = 'D:/xampp/htdocs/Travel2/tp5/public/upload/TravelBlog/'.$ymd.'';
    $fullpath = $basedir;
    if (empty($img_base64)){
        //空的图片，
        return 2;
    }else{
        if(!is_dir($fullpath)){
            //如果图片存储路径不存在，则新建文件夹，0777，意味着最大可能的访问权
            mkdir($fullpath,0777,true);
        }
                $type='jpg';
                $new_name = time().uniqid();//唯一的名称
                $file = $fullpath.'/'.$new_name.'.'.$type;
                if(file_put_contents($file, base64_decode($img_base64))){
                    $new_file=$ymd.'/'.$new_name.'.'.$type;
                    $img_storage=Db::execute("INSERT INTO tb_log_images (lid, ipath,back_img,ti_order) VALUES ('".$lid."','".$new_file."','".$if_back."','".$order."')");
                    if(empty($img_storage) ) {
                        return false;
                    } else {
                        return true;//存储文件名到数据库成功
                    }
                }else{
                    return false;//存储图片到规定路径失败
                }

    }
}

/**
 * 旅行日志中的文字内容上传到数据库，上传文字
 * @param
 * $content：文字内容
 * $lid：旅行日志ID号
 *$order：该字段在文本中的位置即序号
 * @return1 上传成功则返回真，失败则返回假
 */
function text_save($content,$lid,$order){
    if (empty($content)){
        return false;
    }else{
        $img_storage=Db::execute("INSERT INTO tb_log_text (lid,content,ti_order) VALUES ('".$lid."','".$content."','".$order."')");
        if (empty($img_storage)){
            return false;
        }else{
            return true;
        }
    }
}

/**
 * 根据用户标识符获取用户账号
 * @param
 * $token：用户标识符
 * @return1 返回用户账户
 */
//function return_user($token){
//
//   $arrData = Db::query("select * from tb_user where code='".$token."'");
//    if(empty($arrData)){
//        //该用户不存在
//       return 0;
//        //return 1;
//    }else{
//        $phone=$arrData[0]['phone'];
//        return 1;
//    }
//
//}

/**
 * 返回n条旅行日志信息(标题+背景图片）
 * @param
 * $num：每次开始获取的旅行日志ID号
 * @return1 返回n条旅行日志信息
 */
//function return_log($num,$isCover){
//    $result=array();
//    $one_log=array();
//    $result_all=array();
//    $end_log=0;
//    $log_end= Db::query("select * from `tb_log` order by lid desc LIMIT 1");
//    $log_end_num=$log_end[0]['lid'];
////    if ($isCover=='1'){
//        $n=5;
//        $m=1;
//        for ($i=1;$i<=$n;$i+=1){
//            $one_log=return_one_log($num,1);
//            if (empty($one_log)){
//                if ($log_end_num==$num-1){
//                    //return 1;
//                    //continue;
//                    $end_log=1;
//                    break;
//                }
//                else{
//                    $i--;
//                }
//            }else{
//                $result[$i]=$one_log;
//                $m++;
//            }
//            $num+=1;
//        }
//        //$num+=1;
//
//        $result_all=array(
//            'num'=>$m-1,
//            'serial_number'=>$num,
//            'end_log'=>$end_log,
//            'result_log'=>$result
//        );
////    }else{
////        $one_log=return_one_log($num,0);
////        $result_all=$one_log;
////    }
//
//    return $result_all;
//}

//首页，有内容

function return_home_log($lid_all,$lid_end,$is_one){
    //判断是否是第一条开始
    $end_log=0;
    $end_lid_all = array();
    $count_lid = count($lid_all);
    //return $lid_all;
    if ($is_one == 1) {
        for ($i = 0; $i < $count_lid - 1; $i+= 1) {
            $end_lid_all[$i] = $lid_all[$i];
        }
    } else {
        for ($i= 0; $i < $count_lid-1; $i+= 1) {
            $end_lid_all[$i] = $lid_all[$i + 1];
        }
    }
    //判断是否是最后一条
    if ($lid_end == $lid_all[$i]) {
        $end_log = 1;
    }
    //开始选五条日志加载
    $m = 1;
    $result = array();
    $one_log = array();
    $result_all = array();
    //return 1;
    //return $lid_all[$j-1];
//    $one_log=return_one_log(0,0,$lid_all[4],1);
    //return $result=$one_log;
    for ($k = 1; $k <= $i; $k += 1) {
        //$result[$k]=$lid_all[$k-1];
        $one_log = return_one_log(0, 0, $end_lid_all[$k - 1], 1);
        //return 1;
//        $result[$k]=$one_log;
//        $m++;
        if (empty($one_log)) {
            if ($end_log == 1) {
                //return 1;
                break;
                //return 1;
            } else {
                $k--;
            }
        } else {
            $result[$k] = $one_log;
            $m++;
        }
    }
    //return $i;
    $result_all = array(
        'num' => $m - 1,
        'serial_number' => $end_lid_all[$i-1],
        'end_log' => $end_log,
        'result_log' => $result
    );
    return $result_all;
}
//仅仅用户自己可以看见的
function return_personal_log($phone,$num,$isCover,$is_one)
{

    //获取该用户的所有日志，并存储
    $arrData = Db::query("select * from tb_log where uid='". $phone ."' order by lid desc");
    $count = count($arrData);
    //$blank_content=$arrData[0]['blank_content'];
    //return $count;
    $lid_all = array();
    $j = 0;
    $end_log = 0;
    $lid_end = $arrData[$count - 1]['lid'];

    for ($i = 0; $i < $count; $i += 1) {
        //$lid_all[$i]=$arrData[$i]['lid'];
        if ($num == $arrData[$i]['lid']) {
            $lid_all[$j] = $arrData[$i]['lid'];
            $j += 1;
            if ($j == 6) {
                break;
            } else {
                if ($num == $lid_end) {
                    //return $num;
                    break;
                    //return 1;
                }
                $num = $arrData[$i + 1]['lid'];

            }
        }
    }
    //判断是否是第一条开始
    $end_lid_all = array();
    $count_lid = count($lid_all);
    //return $lid_all;
    if ($is_one == 1) {
        for ($i = 0; $i < $count_lid - 1; $i+= 1) {
            $end_lid_all[$i] = $lid_all[$i];
        }
    } else {
        for ($i= 0; $i < $count_lid-1; $i+= 1) {
            $end_lid_all[$i] = $lid_all[$i + 1];
        }
    }
        //return $end_lid_all;
        //判断是否是最后一条
        if ($lid_end == $lid_all[$i]) {
            $end_log = 1;
        }
        //开始选五条日志加载
        $m = 1;
        $result = array();
        $one_log = array();
        $result_all = array();
        //return $lid_all[$j-1];
//    $one_log=return_one_log(0,0,$lid_all[4],1);
        //return $result=$one_log;
        for ($k = 1; $k <= $i; $k += 1) {
            //$result[$k]=$lid_all[$k-1];
            $one_log = return_one_log(0, 0, $end_lid_all[$k - 1], $isCover);
            //return 1;
//        $result[$k]=$one_log;
//        $m++;
            if (empty($one_log)) {
                if ($end_log == 1) {
                    //return 1;
                    break;
                    //return 1;
                } else {
                    $k--;
                }
            } else {
                $result[$k] = $one_log;
                $m++;
            }
        }
        //return $i;
        $result_all = array(
            'num' => $m - 1,
            'serial_number' => $end_lid_all[$i-1],
            'end_log' => $end_log,
            'result_log' => $result
        );
        return $result_all;


}

/**
 * 返回一条旅行日志
 * @param
 * $num：每次开始获取的旅行日志ID号
 * $isCover：判断是否是封面信息，是则只返回标题和封面
 * @return1 返回一条旅行日志信息，格式如下
 * [
 *      {"1":{"title":"","titleimg":"","contentList":{"1":{"content":"","type":0},"2":{"content":"","type":1}}}},
 *      {"2":{"title":"","titleimg":"","contentList":{"1":{"content":"","type":0},"2":{"content":"","type":1}}}},
 *      {"3":{"title":"","titleimg":"","contentList":{"1":{"content":"","type":0},"2":{"content":"","type":1}}}},
 *      {"4":{"title":"","titleimg":"","contentList":{"1":{"content":"","type":0},"2":{"content":"","type":1}}}},
 *      {"5":{"title":"","titleimg":"","contentList":{"1":{"content":"","type":0},"2":{"content":"","type":1}}}},
 * ]
 * title   ：一篇旅行日志的标题
 * titleimg：一篇旅行日志的背景图片
 * contentList：一篇旅行日志的内容部分
 * content  ：内容部分：图片或者文本
 * type  ：判断内容的格式：图片或者文本
 */
function return_one_log($img,$text,$num,$isCover){
    //return 1;
    $arrData = Db::query("select * from tb_log where lid='".$num."' ");
    //return 2;
    if (empty($arrData)){
        return null;
    }else{
        $state=$arrData[0]['d_state'];
        $result=array();

        if ($state==0){
            $title=$arrData[0]['title'];//日志标题
            //return $num;
            $arrData1=Db::query("select * from tb_log_images where lid='".$num."' and back_img='1' ");
            $titleimg_path=$arrData1[0]['ipath'];//日志背景图路径
            //$titleimg=return_base64_images($titleimg_path);
            //return 1;
            $titleimg=return_img_path($titleimg_path);
            if ($isCover=='1'){
                //如果是封面
                $result=array(
                    'lid'=>$num,
                    'title'=>$title,
                    'titleimg'=>$titleimg,
                );
            }else{
                //获取一条完整的旅行日志信息
                //return 1;
                $more=return_Complete_log($img,$text,$num);
                $contentList=$more['contentList'];
                $length=$more['length'];
                $result=array(
                    'length'=>$length,
                    'title'=>$title,
                    'titleimg'=>$titleimg,
                    'contentList'=>$contentList
                );
            }
        }
        return $result;
    }

}

function return_Complete_log($img,$text,$num){
    if ($img==0 and $text==0){
        $count=0;
        $contentList=array();
    }else {
        //只有图片
        if ($img == 1 and $text == 0) {
            $arrData2=Db::table('tb_log_images')->where('lid',$num)->where('back_img',0)->select();
            $count = count($arrData2);
            $j = 0;
            for ($i = 1; $i <= $count; $i++) {
                if ($arrData2[$j]['ti_order'] == $i) {
                    $content = return_img_path($arrData2[$j]['ipath']);
                    $type = 1;
                    $j++;
                }
                $contentList[$i] = array(
                    'content' => $content,
                    'type' => $type
                );
            }
        }
        //只有文字
        if ($img == 1 and $text == 0) {
            $arrData3=Db::table('tb_log_text')->where('lid',$num)->select();
            $count=count($arrData3);
            $j = 0;
            for ($i = 1; $i <= $count; $i++) {
                if ($arrData3[$j]['ti_order']==$i){
                    $content=$arrData3[$j]['content'];
                    $type=0;
                    $j++;
                }
                $contentList[$i] = array(
                    'content' => $content,
                    'type' => $type
                );
            }
        } else{//图片加文字
            $arrData2=Db::table('tb_log_images')->where('lid',$num)->where('back_img',0)->select();
            $arrData3=Db::table('tb_log_text')->where('lid',$num)->select();
            //return 1;
            $count2=count($arrData2);
            $count3=count($arrData3);
            $count=$count2+$count3;
            for ($i=1;$i<=$count;$i+=1){
                for ($j=0;$j<$count2;$j+=1) {
                    if ($arrData2[$j]['ti_order'] == $i) {
                        $content = return_img_path($arrData2[$j]['ipath']);
                        $type = 1;
                    }
                }
                for ($j=0;$j<$count3;$j+=1) {
                    if ($arrData3[$j]['ti_order'] == $i) {
                        $content = $arrData3[$j]['content'];
                        $type = 0;
                    }
                }
                $contentList[$i]=array(
                    'content'=>$content,
                    'type'=>$type
                );
            }

        }
    }
    $result=array(
        'length'=>$count,
        'contentList'=>$contentList
    );
    return $result;
}

/**
 * 读取图片路径，转化为base64格式
 * @param
 * $titleimg_path：得到图片路径
 * @return1 返回base64格式图片 $img_base64
 */
function return_base64_images($titleimg_path){
    $new_file = 'D:/xampp/htdocs/Travel2/tp5/public/upload/TravelBlog/'.$titleimg_path.'';
    //return $new_file;
    if($fp = fopen("$new_file","rb", 0))
    {
        $gambar = fread($fp,filesize($new_file));
        fclose($fp);
        //背景图片转化成base64格式
        $img_base64 = chunk_split(base64_encode($gambar));
    }
//    $aaa='hello';
//    return $aaa;
   return $img_base64;
}

//返回图片地址,直接返回图片地址，客户端下载

function return_img_path($titleimg_path){
    $new_file = '/Travel2/tp5/public/upload/TravelBlog/'.$titleimg_path.'';
    return $new_file;
}