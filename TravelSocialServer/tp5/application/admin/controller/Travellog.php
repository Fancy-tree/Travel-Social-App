<?php

namespace application\admin\controller;
session_start();
use think\Db;//引入系统数据库类
use think\Controller;
use application\admin\model\Travel;

class Travellog extends Controller
{
    //查看旅行日志基本信息
    public function checklog()
    {
        //设置一页存放几条数据
        $limit = 5;
        //想要分页的数据表+库文件中的函数
        $data = Db::name('log')->orderRaw('lid desc')->paginate($limit, false, ['query' => request()->param(), 'type' => 'page\Page', 'var_page' => 'page']);
        //表格Render时，检查分页和参数情况，生成TFoot中的导航栏
        $page = $data->render();
        //传参数到同名的视图文件
        $this->assign([
            'limit' => $limit,
            'page' => $page,
            'data' => $data

        ]);
        return view();
    }
    //查看一条完整的旅行日志
    public function complete_log(){
        if (request()->isGet()) {
            $date = input('get.');
            $lid=$date['lid'];
            $log = new Travel();
            $start = $log->admin_return_Complete_log($lid);
            $contentList = $log->admin_return_content_log($lid);
            //return json($start);
            $limit = 1;
            $data = Db::name('log')->orderRaw('lid desc')->paginate($limit, false, ['query' => request()->param(), 'type' => 'page\Page', 'var_page' => 'page']);
            $page = $data->render();
            $this->assign([
                'limit' => $limit,
                'page' => $page,
                'data' => $data,
                'start' => $start,
                'contentList' => $contentList
            ]);
        }
        return view();
    }
    //修改旅行日志状态（删除）
    public function d_state_log(){
        if (request()->isGet()) {
            $date = input('get.');
            $lid = $date['lid'];
            $end=Db::table('tb_log')->where('lid', $lid)->update(['d_state' => 1]);
            if (empty($end)){
                $this->error('日志删除失败','index/index');
            }else{
                $this->success('日志删除成功', 'index/index');
            }
        }
        return $this->fetch('index');
    }


}