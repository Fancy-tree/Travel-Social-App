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
        }else{
            //如果不是一个空的json数组
            $result = $jarray['result'];//获取
            $phone = $result['phone'];
            $paswd = $result['password'];
            if ($phone == '' and $paswd == '') {
                $data=array();
                return returnJson('101','参数错误',$data);
                unset ($data);
            }else{
                //第2个if,当有密码和账号时
                $arrData = Db::query("select * from tb_user where phone='".$phone."'");
                if(empty($arrData)){
                    //根据账号查询结果为空
                    $data=array(
                        'text'=>'这是测试'
                    );
                    return returnJson('103','账号或者密码错误',$data);
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

    //用户注册
    public function login()
    {
        $postJson = file_get_contents('php://input');
        $jarray = json_decode($postJson, true);
        if (empty($jarray)) {
            //第一个if ,当$jarray的参数为空时
            $data=array(
                'text'=>'这是测试'
            );
            return returnJson('106','参数错误',$data);
        }else{
            //如果不是一个空的json数组
            $result = $jarray['result'];//获取
            $phone = $result['phone'];
            $paswd = $result['password'];
            $arrData = Db::query("select * from tb_user where phone='".$phone."'");
            if (empty($arrData)){
                //没有该账号，可以注册成功
                $token=setAppLoginToken($phone);
                $Nickname='昵称';//默认昵称
                $date=date('Y-m-d H:i:s',time());
                $login_user=Db::execute("INSERT INTO tb_user (phone, password,Nickname,datetime,code) VALUES ('".$phone."','".$paswd."','".$Nickname."','".$date."','".$token."')");
                if(empty($login_user)){
                    //新增用户失败
                    $data=array();
                    return returnJson('107','服务器错误',$data);
                }else{
                    //新增用户成功
                    $data=array(
                        'token'=>$token,
                        'text'=>'这是测试'
                    );
                    return returnJson('104','注册成功',$data);
                }
            }else{
                $data=array(
                    'text'=>'这是测试'
                );
                return returnJson('105','账号已经被注册',$data);
            }
        }
    }

}
