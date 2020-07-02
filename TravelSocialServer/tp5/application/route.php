<?php
// +----------------------------------------------------------------------
// | ThinkPHP [ WE CAN DO IT JUST THINK ]
// +----------------------------------------------------------------------
// | Copyright (c) 2006~2018 http://thinkphp.cn All rights reserved.
// +----------------------------------------------------------------------
// | Licensed ( http://www.apache.org/licenses/LICENSE-2.0 )
// +----------------------------------------------------------------------
// | Author: liu21st <liu21st@gmail.com>
// +----------------------------------------------------------------------

return [
    '__pattern__' => [
        'name' => '\w+',
    ],
    '[hello]'     => [
        ':id'   => ['index/hello', ['method' => 'get'], ['id' => '\d+']],
        ':name' => ['index/hello', ['method' => 'post']],
    ],
    //api接口访问路由
    'api_login'       => 'index/index/login',
    'exit'        => 'index/user/exitLogin',
    're_travel_log'  =>'index/travellog/re_travel_log',
    'out_travel_log' =>'index/travellog/out_travel_log',
    'out_personal_log'=>'index/travellog/out_personal_log',

    //后台访问路由
    'login'       => 'admin/login/index',
    'logout'      =>'admin/login/logout',
    'nickname'    =>'admin/login/nickname',

    'checkuser'   =>'admin/userinfo/checkuser',
    'freeze_user'   =>'admin/userinfo/freeze_user',
    'unfreeze_user'   =>'admin/userinfo/unfreeze_user',
    'only_user_log'   =>'admin/userinfo/only_user_log',

    'checkcity'   =>'admin/cityinfo/checkcity',

    'checklog'   =>'admin/travellog/checklog',
    'complete_log'   =>'admin/travellog/complete_log',
    'd_state_log'   =>'admin/travellog/d_state_log',

];
