<?php

namespace application\index\controller;
use think\Controller;
use think\Db;

header("Content-Type:application/json;charset=utf-8");
class User extends Controller
{
        //退出登录
        public function exitLogin(){
            //return_user();
            $postJson = file_get_contents('php://input');
            $jarray = json_decode($postJson, true);
//            $data=array(
//                'text'=>'这是测试'
//            );
//            return returnJson('108','退出成功',$data);
            if (empty($jarray)) {
                //第一个if ,当$jarray的参数为空时
                $data=array(
                    'text'=>'这是测试'
                );
                return returnJson('109','参数错误',$data);
            }else{
                //如果不是一个空的json数组
                $result = $jarray['result'];//获取
                $token = $result['token'];
                //$phone=return_user($token);
                //return $phone;
               // $arrData=Db::table('tb_user')->where('code',$token)->find();
                $arrData = Db::query("select * from tb_user where code='".$token."'");
                $phone=$arrData[0]['phone'];
                if (empty($arrData)){
                    $data=array(
                        'text'=>'这是测试'
                    );
                    return returnJson('108','退出成功',$data);
                }

                //$uid=return_user($token);
                //return $uid;
                if($phone==false){
                    //该用户不存在
                    //return 1;
                    $data=array(
                        'text'=>'这是测试'
                    );
                    return returnJson('109','参数错误',$data);
                    //return 1;
                }else{
                    $token='';
                    $end=Db::table('tb_user')->where('phone', $phone)->update(['code' => $token]);
                    if(empty($end)){
                        $data=array();
                        return returnJson('107','服务器错误',$data);
                    }else{
                        $data=array(
                            'text'=>'这是测试'
                        );
                        return returnJson('108','退出成功',$data);
                    }
                    return 0;
                }
            }
        }


}