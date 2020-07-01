<?php
namespace application\index\controller;

use think\Controller;
//use think\Request;
use think\Db;

header("Content-Type:application/json;charset=utf-8");
class Index extends Controller
{
    //用户登录
    public function index()
    {
        $postJson = file_get_contents('php://input');
        $jarray = json_decode($postJson, true);
        if (empty($jarray)){
            //第一个if ,当$jarray的参数为空时
            unset($jarray);
            $data=array(
                'text'=>'这是测试'
            );
            return returnJson('101','参数错误',$data);

            unset ($data);
        }else{
            //如果不是一个空的json数组
            $result = $jarray['result'];//获取
            $phone = $result['phone'];
            $paswd = $result['password'];
            if ($phone == '' and $paswd == '') {
                unset($jarray);

                $data=array();
                return returnJson('101','参数错误',$data);
                unset ($data);
            }else{
                //第2个if,当有密码和账号时
                $arrData = Db::query("select * from tb_user where phone='".$phone."'");
                if(empty($arrData)){
                    //根据账号查询结果为空
                    unset($arrData);
                    unset($jarray);
                    unset($phone);
                    unset ($paswd);
                    $data=array(
                        'text'=>'这是测试'
                    );
                    return returnJson('103','账号或者密码错误',$data);

                    unset ($data);

                }else{
//                    //根据账号查询结果不为空
                    if ($arrData[0]['password'] == $paswd and $arrData[0]['phone']==$phone){
                        //该账户已经在别处登录过的时候
//                        if($arrData[0]['code']!=null){
//                            $data=array(
//                                'text'=>'这是测试'
//                            );
//                            return returnJson('101','该账户已在别的设备登录',$data);
//                        }
                        $token=setAppLoginToken($phone);
                        Db::table('tb_user')->where('phone', $phone)->update(['code' => $token]);
                        unset($arrData);
                        unset($jarray);
                        unset($phone);
                        unset ($paswd);
                        $data=array(
                            'token'=>$token,
                            'text'=>'这是测试'
                        );
                        return returnJson('102','登录成功',$data);
                        unset ($data);
                    }else{
                        //第三个if，账号和密码都不相等时
                        $data=array(
                            'text'=>'这是测试'
                        );

                        return returnJson('103','账号或者密码错误',$data);
                        unset ($data);
                    }

                }

            }

        }
    }

 

}
