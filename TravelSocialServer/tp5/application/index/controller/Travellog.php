<?php

namespace application\index\controller;
use think\Controller;
use think\Db;

header("Content-Type:application/json;charset=utf-8");
class Travellog extends Controller
{
    //旅行日志
    public function re_travel_log()
    {
        $postJson = file_get_contents('php://input');
        $jarray = json_decode($postJson, true);
        if (empty($jarray)) {
            //第一个if ,当$jarray的参数为空时
            $data = array(
                'text' => '这是测试1'
            );
            return returnJson('201', '参数错误', $data);
        } else {
            $result = $jarray['result'];//获取
            // return 1;
            //return json($result);
            //$code=$jarray['code'];
            $title = $result['title'];//日志标题
            $titleimg = $result['titleimg'];
            $token = $result['token'];
            //return json($titleimg);
            //$uid=return_user($token);
            $arrData = Db::query("select * from tb_user where code='" . $token . "'");

            $phone = $arrData[0]['phone'];

            if ($phone == false) {
                //该用户不存在
                $data = array(
                    'text' => '这是测试2'
                );
                return returnJson('109', '参数错误', $data);
                //return 1;
            }
            //return json($phone);
            $date = date('Y-m-d H:i:s', time());
            $blank_content=1;
            $d_state=1;
            $insert_log = Db::execute("INSERT INTO tb_log (uid, title,datetime,d_state,blank_content) VALUES ('" . $phone . "','" . $title . "','" . $date . "','" . $d_state . "','" . $blank_content . "')");
            //Db::execute("INSERT INTO tb_log (uid, title,datetime) VALUES ('".$phone."','".$title."','".$date."')");
            $arrData = Db::query("select * from tb_log where uid='" . $phone . "'and datetime='" . $date . "'");
            $lid = $arrData[0]['lid'];
            //return json($lid);
            if (empty($insert_log)) {
                $data = array(
                    'text' => '这是测试3'
                );
                return returnJson('203', '服务器内部错误', $data);
            } else {
                if (empty($titleimg)) {
                    $data = array(
                        'text' => '这是测试4'
                    );
                    return returnJson('201', '参数错误', $data);
                }
                //return 1;
                //$img_save=base64imgsave($titleimg,$uid);
                $text = base64imgsave($titleimg, $lid, '1', '0');
                //return 1;
                //return $text;
                if ($text == true) {
                    $contentList = $result['contentList'];
                    //return $contentList;
                    foreach ($contentList as $value) {
                        foreach ($value as $key => $value1) {
                            //return count($value);
                            if ($value1['type'] == '0') {
                                if ($value1['content'] == null and count($value)==1) {
                                    Db::table('tb_log')->where('lid', $lid)->update(['blank_content' => '1']);
                                    break 2;
                                }else{
                                    $result_text = text_save($value1['content'], $lid, $key);
                                    if ($result_text == false) {
                                        //如果文字插入失败，则旅行日志的删除状态为1
                                        Db::table('tb_log')->where('lid', $lid)->update(['d_state' => '1']);
                                        $data = array(
                                            'text' => '这是测试8'
                                        );
                                        return returnJson('203', '服务器错误', $data);
                                    }else{
                                        Db::table('tb_log')->where('lid', $lid)->update(['blank_content' => '0']);
                                    }
                                }
                            }
                            if ($value1['type'] == '1') {

                                if ($value1['content'] != null) {
                                    //return $value1;
                                    $result_img = base64imgsave($value1['content'], $lid, '0', $key);
                                    if ($result_img == false) {
                                        //如果图片插入失败，则旅行日志的删除状态为1
                                        Db::table('tb_log')->where('lid', $lid)->update(['d_state' => '1']);
                                        $data = array(
                                            'text' => '这是测试6'
                                        );
                                        return returnJson('203', '服务器错误', $data);
                                    }else{
                                        Db::table('tb_log')->where('lid', $lid)->update(['blank_content' => '0']);
                                    }
                                }

                            }
                        }
                    }
                    Db::table('tb_log')->where('lid', $lid)->update(['d_state' => '0']);
                    $data = array(
                        'text' => '这是测试10'
                    );
                    return returnJson('202', '上传成功', $data);
                } else {
                    $data = array(
                        'text' => '这是测试9'
                    );
                    return returnJson('201', '参数错误', $data);
                }
            }
        }
    }


//返回个人旅行日志  out_personal_log
    public function out_personal_log()
    {
        //return 1;
        $postJson = file_get_contents('php://input');
        $jarray = json_decode($postJson, true);
        if (empty($jarray)) {
            $data = array(
                'text' => '这是测试9'
            );
            return returnJson('205', '参数错误7', $data);
        } else {
            //从服务器返回旅行日志给客户端
            $code = $jarray['code'];
            if ($code == '202') {
                $result = $jarray['result'];//获取
                $token = $result['token'];
                $arrData = Db::query("select * from tb_user where code='" . $token . "'");
                $phone = $arrData[0]['phone'];
                if ($phone == false) {
                    //该用户不存在
                    $data = array(
                        'text' => '这是测试'
                    );
                    return returnJson('205', '参数错误6', $data);
                } else {
                    $num = $result['labelNumber'];//获取第几条旅行日志
                    if ($num == 1) {
                        //return 1;
                        $arrData = Db::query("select * from tb_log where uid='".$phone."' order by lid desc");
                        $num = $arrData[0]['lid'];
                        $data = return_personal_log($phone,$num, 1,1);
                        return returnJson('204', '返回数据成功', $data);
                    } else {
                        $serialNumber = $result['serialNumber'];
                        $num = $serialNumber;
                        $data =  return_personal_log($phone,$num, 1,0);
                        return returnJson('204', '返回数据成功', $data);
                    }
                }
            }
            if ($code == '203') {
                //return 1;
                $result = $jarray['result'];//获取
                $token = $result['token'];
                $arrData = Db::query("select * from tb_user where code='" . $token . "'");
                $phone = $arrData[0]['phone'];
                if ($phone == false) {
                    //该用户不存在
                    $data = array(
                        'text' => '这是测试'
                    );
                    return returnJson('205', '参数错误5', $data);
                    //return 1;
                } else {
                    $lid = $result['lid'];
                    $img=1;
                    $text=1;
                    $arrData2=Db::table('tb_log_images')->where('lid',$lid)->where('back_img',0)->select();
                    if (empty($arrData2)){
                        $img=0;
                    }
                    $arrData3=Db::table('tb_log_text')->where('lid',$lid)->select();
                    if (empty($arrData3)){
                        $text=0;
                    }
                    $data = return_one_log($img,$text,$lid, 0);
                    return returnJson('206', '返回数据成功', $data);

                }
            } else {
                $data = array(
                    'text' => '这是测试9'
                );
                return returnJson('205', '参数错误4', $data);
            }
        }
    }
//out_travel_log 首页
    public function out_travel_log ()
    {
        //return 1;
        $postJson = file_get_contents('php://input');
        $jarray = json_decode($postJson, true);
        if (empty($jarray)) {
            $data = array(
                'text' => '这是测试1'
            );
            return returnJson('205', '参数错误1', $data);
        } else {
            //从服务器返回旅行日志给客户端
            $code = $jarray['code'];
            if ($code == '204') {
                //return 1;
                $result = $jarray ['result'];//获取
                $num = $result['labelNumber'];//获取第几条旅行日志
                $serialNumber = $result['serialNumber'];
                $arrData1 = Db::table('tb_log')->where('m_sign', 0)->where('d_state', 0)->where('blank_content', 0)->orderRaw('lid desc')->select();
                //return json($arrData1);
                if ($num != 1) {
                    $num = $serialNumber;
                    //return 2;
                }
                if ($num == 1) {
                    $num = $arrData1[0]['lid'];
                    //return 1;
                }
                //return $num;
                $count = count($arrData1);
                $lid_all = array();
                $j = 0;
                $end_log = 0;
                $lid_end = $arrData1[$count - 1]['lid'];
                for ($i = 0; $i < $count; $i += 1) {
                    //$lid_all[$i]=$arrData[$i]['lid'];
                    if ($num == $arrData1[$i]['lid']) {
                        $lid_all[$j] = $arrData1[$i]['lid'];
                        $j += 1;
                        if ($j == 6) {
                            break;
                        } else {
                            if ($num == $lid_end) {
                                //return $num;
                                break;
                                //return 1;
                            }
                            $num = $arrData1[$i + 1]['lid'];

                        }
                    }
                }
                //return json($count);
                //return 1;
                $is_one = $result['labelNumber'];
                //return $aaa;
                //return json($lid_all);
                $data = return_home_log($lid_all, $lid_end, $is_one);
                //$data =  return_personal_log($phone,$num, 1);
                return returnJson('208', '返回数据成功', $data);
            }
            if ($code == '205') {
                //return 1;
                $result = $jarray['result'];//获取
                $token = $result['token'];
                $arrData = Db::query("select * from tb_user where code='" . $token . "'");
                $phone = $arrData[0]['phone'];
                if ($phone == false) {
                    //该用户不存在
                    $data = array(
                        'text' => '这是测试2'
                    );
                    return returnJson('205', '参数错误2', $data);
                    //return 1;
                } else {
                    $lid = $result['lid'];
                    $img=1;
                    $text=1;
                    $arrData2=Db::table('tb_log_images')->where('lid',$lid)->where('back_img',0)->select();
                    if (empty($arrData2)){
                        $img=0;
                    }
                    $arrData3=Db::table('tb_log_text')->where('lid',$lid)->select();
                    if (empty($arrData3)){
                        $text=0;
                    }
                    $data = return_one_log($img,$text,$lid, 0);
                    return returnJson('208', '返回数据成功', $data);

                }
            } else {
                $data = array(
                    'text' => '这是测试3'
                );
                return returnJson('205', '参数错误3', $data);
            }
        }
    }

}