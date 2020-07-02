<?php


namespace application\admin\model;
use think\Model;
use think\Db;

class Travel extends Model
{
    //后台查看旅行日志--一篇篇
//    public function return_one_log($lid)
//    {
//        $arrData1 = Db::name('log')->orderRaw('lid desc')->select();
//        //return $arrData1;
//        $count=count($arrData1);
//        return $count;
//        for ($i=0;$i<$count;$i++){
//            $img=1;
//            $text=1;
//            $lid=$arrData1[$i]['lid'];
//            $arrData2=Db::table('tb_log_images')->where('lid',$lid)->select();
//            if (empty($arrData2)){
//                $img=0;
//            }
//            $arrData3=Db::table('tb_log_text')->where('lid',$lid)->select();
//            if (empty($arrData3)){
//                $text=0;
//            }
//            $arrData4[$i] = $this->admin_return_Complete_log($lid);
//        }
//        $arrData4 = $this->admin_return_Complete_log($lid);
//        return $arrData4;
//    }
    //查看一个用户的旅行日志或者查看一堆旅行日志
    public function return_user_log($data){
        $count=count($data);
        for ($i=1;$i<=$count;$i++){
            //$start =$this->admin_return_Complete_log($data[$i-1]['lid']);
            $lid[$i]=$this->admin_log($data[$i-1]['lid']);
//            $user_log[$i]=array(
//                'start'=>$start,
//                'contentList'=>$contentList
//            );
        }
        return $lid;
    }
    public function admin_log($num){
        $arrData = Db::name('log')->where('lid',$num)->select();
        $title=$arrData[0]['title'];//日志标题
        $arrData1=Db::name('log_images')->where('lid',$num)->where('back_img',1)->select();
        $titleimg_path=$arrData1[0]['ipath'];//日志背景图路径
        $titleimg=$this->admin_img_path($titleimg_path);

        $img=1;
        $text=1;
        $arrData_img=Db::table('tb_log_images')->where('lid',$num)->where('back_img',0)->select();
        if (empty($arrData_img)){
            $img=0;
        }
        $arrData_text=Db::table('tb_log_text')->where('lid',$num)->select();
        if (empty($arrData_text)){
            $text=0;
        }
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
            'lid'=>$num,
            'title'=>$title,
            'titleimg'=>$titleimg,
            'contentList'=>$contentList
        );
        return $result;
    }

    //返回标题+背景图片的旅行日志
    public function admin_return_Complete_log($num){
        $arrData = Db::name('log')->where('lid',$num)->select();
        $title=$arrData[0]['title'];//日志标题
        $arrData1=Db::name('log_images')->where('lid',$num)->where('back_img',1)->select();
        $titleimg_path=$arrData1[0]['ipath'];//日志背景图路径
        $titleimg=$this->admin_img_path($titleimg_path);
        //$more=$this->admin_return_content_log($num);
        //$contentList=$more['contentList'];
        //$length=$more['length'];
        //$contentList=$this->admin_return_content_log($num);
        $result=array(
            //'length'=>$length,
            'lid'=>$num,
            'title'=>$title,
            'titleimg'=>$titleimg,
        );
        return $result;
    }
    //返回内容的旅行日志
    public function admin_return_content_log($num){
        $img=1;
        $text=1;
        $arrData_img=Db::table('tb_log_images')->where('lid',$num)->where('back_img',0)->select();
        if (empty($arrData_img)){
            $img=0;
        }
        $arrData_text=Db::table('tb_log_text')->where('lid',$num)->select();
        if (empty($arrData_text)){
            $text=0;
        }
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
//        $result=array(
//            'length'=>$count,
//            'contentList'=>$contentList
//        );
 //       return $result;

        return $contentList;
    }
    //返回图片路径
    public function admin_img_path($titleimg_path){
        //服务器地址
        $ip='http://192.168.43.25';
        $new_file = ''.$ip.'/Travel2/tp5/public/upload/TravelBlog/'.$titleimg_path.'';
        return $new_file;
    }



}