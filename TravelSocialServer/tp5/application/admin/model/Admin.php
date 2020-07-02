<?php
namespace application\admin\model;

use think\Model;
use think\Db;

class Admin extends Model
{
    public function login($data){
        $admin=Db::name('admin')->where('num','=',$data['admin_name'])->find();
        if($admin){
            if($admin['password'] == $data['password']){
                session('admin_name',$admin['num']);
                session('nickname',$admin['nickname']);
                return 3; //信息正确
            }else{
                return 2;//密码错误
            }
        }else{
            return 1;//用户不存在
        }
    }

    public function nickname($num,$nickname){
        //$admin_Data = Db::query("select * from tb_admin where num='" . $num . "'");
        $insert_arr=Db::table('tb_admin')->where('num', $num)->update(['nickname' =>$nickname]);
        if (empty($insert_arr)){
            return 0;//参数错误
        }else{
            session('nickname',$nickname);
            return 1;//设置昵称成功
        }
    }
}













