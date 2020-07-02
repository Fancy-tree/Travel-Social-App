<?php


namespace application\admin\controller;
session_start();
use think\Db;//引入系统数据库类
use think\Controller;
use application\admin\model\Travel;

class Userinfo extends Controller
{
    //查看用户
    public function checkuser(){
        $limit=5;
       $data = Db::name('user')->orderRaw('uid desc')->paginate($limit,false,['query' => request()->param(),'type' => 'page\Page','var_page'  => 'page']);
        $page = $data->render();
        $this->assign([
            'limit' =>$limit,
            'page' =>$page,
            'data' =>$data

        ]);
        return view();
    }

    //冻结账户
    public function freeze_user(){
        if (request()->isGet()) {
            $date = input('get.');
            $phone=$date['lid'];
            $end=Db::table('tb_user')->where('phone', $phone)->update(['f_state' => 1]);
            if (empty($end)){
                $this->error('账户冻结失败','index/index');
            }else{
                $this->success('账户冻结成功', 'index/index');
            }
        }
        return $this->fetch('index');
    }

    //解冻账户
    public function unfreeze_user(){
        if (request()->isGet()) {
            $date = input('get.');
            $phone=$date['lid'];
            $end=Db::table('tb_user')->where('phone', $phone)->update(['f_state' => 0]);
            if (empty($end)){
                $this->error('账户解冻失败','index/index');
            }else{
                $this->success('账户解冻成功', 'index/index');
            }
        }
        return $this->fetch('index');
    }
    //查看一个用户旅行日志
    public function only_user_log(){
       // $phone=15823130287;
        if (request()->isGet()) {
            $date = input('get.');
            $phone = $date['phone'];
            $arrData = Db::query("select * from tb_log where uid='" . $phone . "' order by lid desc");
            if (empty($arrData)) {
                $this->error('该用户没有旅行日志', 'index/index');
            }
            $log = new Travel();
            $user_log_all = $log->return_user_log($arrData);
            $limit = 1;
            $data = Db::name('log')->where('uid', $phone)->orderRaw('lid desc')->paginate($limit, false, ['query' => request()->param(), 'type' => 'page\Page', 'var_page' => 'page']);
            $page = $data->render();
            $this->assign([
                'limit' => $limit,
                'page' => $page,
                'data' => $data,
                'user_log_all' => $user_log_all
            ]);
            return view();
        }
        $this->error('请求错误','index/index');
    }

}