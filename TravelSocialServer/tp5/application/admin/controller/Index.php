<?php
namespace application\admin\controller;

//
//use think\View;
//use think\Config;

use think\Controller;
//use application\admin\model\Admin;

header("Content-Type:application/json;charset=utf-8");
class Index extends Controller
{
//后台首页
    public function index(){
        //动态创建
        //$view = new View();
       // $res=\think\Config::get();
        //静态创建
        //$view = View::instance();
        //模板赋值
        //$view -> assign('domain','www.php.cn');
        //$this->view->engine->layout('layout');
        //渲染模板
       // dump($res);
        //return $view -> fetch('index');
        //return view('index');

        return $this->fetch('index');
    }

}
