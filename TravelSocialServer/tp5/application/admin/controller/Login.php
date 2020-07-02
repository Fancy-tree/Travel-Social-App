<?php


namespace application\admin\controller;
use think\Controller;
use application\admin\model\Admin;
use think\Session;

class Login extends Controller
{
    //用户登录
    public function index()
    {
        if (request()->isPost()) {
            $admin = new Admin();
            $data = input('post.');
            $num = $admin->login($data);
            if ($num == 3) {
                $this->success('信息正确，正在为您跳转', 'index/index');
            } else {
                $this->error('用户名或密码错误');
            }
        }
        return $this->fetch('login');
    }

    //退出系统
    public function logout(){
        session(null);
        $this->success('退出成功', 'login/index');
    }

    //设置昵称
    public function nickname()
    {
        if (request()->isPost()) {
            $post_arr = input('post.');
            $nickname=$post_arr['nickname'];
            $num=Session::get('admin_name');
            if (empty($num)){
                $this->error('昵称不为空','nickname');
            }else{
                $admin = new Admin();
                $result=$admin->nickname($num,$nickname);
                if ($result == 1) {
                    $this->success('修改昵称成功正确，正在为您跳转', 'index/index');
                } else {
                    $this->error('修改失败','index/index');
                }
            }
        }
        return $this->fetch('nickname');
    }
}