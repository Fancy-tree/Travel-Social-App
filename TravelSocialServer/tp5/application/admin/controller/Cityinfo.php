<?php


namespace application\admin\controller;
use think\Db;//引入系统数据库类
use think\Controller;
session_start();

class Cityinfo extends Controller
{
    public function checkcity()
    {
        //return $this->fetch('checkuser');
        $limit = 5;
        $data1 = Db::name('log')->select();
        //$count = count($data1);
        $data = Db::name('log')->paginate($limit, false, ['query' => request()->param(), 'type' => 'page\Page', 'var_page' => 'page']);
        // 把分页数据赋值给模板变量list
        //$data = Db::name('user')->paginate($limit,false,['query' => request()->param()]);
        //$this->assign('data',$data);
        $page = $data->render();

//        //分页1
//        $params=$this->request->param();
//
//        $list->appends($params);

        $this->assign([
            'limit' => $limit,
            'page' => $page,
            'data' => $data

        ]);
        return view();
    }

}