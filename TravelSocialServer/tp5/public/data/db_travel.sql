-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2020-07-01 18:01:13
-- 服务器版本： 10.4.11-MariaDB
-- PHP 版本： 7.4.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `db_travel`
--

-- --------------------------------------------------------

--
-- 表的结构 `tb_admin`
--

CREATE TABLE `tb_admin` (
  `uid` int(50) NOT NULL COMMENT '管理员id',
  `num` varchar(15) COLLATE utf8_unicode_ci NOT NULL COMMENT '账号',
  `password` varchar(16) COLLATE utf8_unicode_ci NOT NULL COMMENT '密码',
  `nickname` varchar(12) COLLATE utf8_unicode_ci NOT NULL COMMENT '昵称',
  `sex` varchar(4) COLLATE utf8_unicode_ci NOT NULL COMMENT '性别'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `tb_admin`
--

INSERT INTO `tb_admin` (`uid`, `num`, `password`, `nickname`, `sex`) VALUES
(1, '15823130287', 'abc', '这是个正经的昵称', '');

-- --------------------------------------------------------

--
-- 表的结构 `tb_apply`
--

CREATE TABLE `tb_apply` (
  `aid` int(50) NOT NULL COMMENT '词条申请id',
  `uid` int(50) NOT NULL COMMENT '用户id',
  `csid` int(50) NOT NULL COMMENT '城市特色id',
  `datetime` datetime NOT NULL COMMENT '申请时间',
  `e_state` int(2) NOT NULL DEFAULT 0 COMMENT '申请状态（已审核1/未审核0）',
  `s_sign` int(2) NOT NULL COMMENT '申请成功标记（成功1/失败0）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `tb_apply_examine`
--

CREATE TABLE `tb_apply_examine` (
  `aeid` int(50) NOT NULL COMMENT '词条申请审核id',
  `uid` int(50) NOT NULL COMMENT '审核管理员id',
  `content` varchar(1000) COLLATE utf8_unicode_ci NOT NULL COMMENT '审核回复内容',
  `datetime` datetime NOT NULL COMMENT '审核时间',
  `s_sign` int(2) NOT NULL COMMENT '申请审核通过标记（通过1/拒绝0）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `tb_chat`
--

CREATE TABLE `tb_chat` (
  `chid` int(50) NOT NULL COMMENT '聊天消息id',
  `fuid` int(50) NOT NULL COMMENT '发送方用户id',
  `suid` int(50) NOT NULL COMMENT '接收方用户id',
  `content` varchar(1000) COLLATE utf8_unicode_ci NOT NULL COMMENT '聊天消息内容',
  `datetime` datetime NOT NULL COMMENT '聊天消息发送时间',
  `d_state` int(2) NOT NULL DEFAULT 0 COMMENT '删除状态（均未删0/发送方删1/接收方删2/均删3）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `tb_city`
--

CREATE TABLE `tb_city` (
  `cid` int(50) NOT NULL COMMENT '城市id',
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '城市名称',
  `position` varchar(40) COLLATE utf8_unicode_ci NOT NULL COMMENT '地理位置',
  `introduction` varchar(1000) COLLATE utf8_unicode_ci NOT NULL COMMENT '城市简介',
  `overview` varchar(10000) COLLATE utf8_unicode_ci NOT NULL COMMENT '城市综述',
  `heat` int(20) DEFAULT NULL COMMENT '城市热度',
  `proposal` varchar(10000) COLLATE utf8_unicode_ci NOT NULL COMMENT '旅游建议'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `tb_city_specialty`
--

CREATE TABLE `tb_city_specialty` (
  `csid` int(50) NOT NULL COMMENT '城市特色id',
  `cid` int(50) NOT NULL COMMENT '城市id',
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '特色名称',
  `category` varchar(8) COLLATE utf8_unicode_ci NOT NULL COMMENT '特色类别（景点/美食/特产）',
  `introduction` varchar(1000) COLLATE utf8_unicode_ci NOT NULL COMMENT '特色简述',
  `overview` varchar(10000) COLLATE utf8_unicode_ci NOT NULL COMMENT '特色综述',
  `proposal` varchar(10000) COLLATE utf8_unicode_ci NOT NULL COMMENT '建议',
  `picture` blob NOT NULL COMMENT '图片',
  `m_sign` int(2) NOT NULL DEFAULT 0 COMMENT '主要特色标记',
  `e_sign` int(2) NOT NULL DEFAULT 0 COMMENT '词条审核标记（通过1/未通过0）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `tb_collection`
--

CREATE TABLE `tb_collection` (
  `coid` int(50) NOT NULL COMMENT '收藏id',
  `uid` int(50) NOT NULL COMMENT '用户id',
  `type` varchar(8) COLLATE utf8_unicode_ci NOT NULL COMMENT '被收藏类型（路线图/景点/日志）',
  `bhid` int(50) NOT NULL COMMENT '被收藏（路线图/景点/日志）id',
  `colid` int(50) NOT NULL COMMENT '收藏夹id',
  `datetime` datetime NOT NULL COMMENT '收藏时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `tb_collection_list`
--

CREATE TABLE `tb_collection_list` (
  `colid` int(50) NOT NULL COMMENT '收藏夹id',
  `uid` int(50) NOT NULL COMMENT '用户id',
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '收藏夹名称',
  `datetime` datetime NOT NULL COMMENT '收藏夹新建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='收藏夹表';

-- --------------------------------------------------------

--
-- 表的结构 `tb_comment`
--

CREATE TABLE `tb_comment` (
  `coid` int(50) NOT NULL COMMENT '评论id',
  `uid` int(50) NOT NULL COMMENT '用户id',
  `type` varchar(8) COLLATE utf8_unicode_ci NOT NULL COMMENT '被评论类型（路线图/回答/日志/评论）',
  `bhid` int(50) NOT NULL COMMENT '被评论（路线图/回答/日志/评论）id',
  `content` varchar(1000) COLLATE utf8_unicode_ci NOT NULL COMMENT '评论内容',
  `datetime` datetime NOT NULL COMMENT '评论时间',
  `f_state` int(2) NOT NULL DEFAULT 0 COMMENT '屏蔽状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='评论表';

-- --------------------------------------------------------

--
-- 表的结构 `tb_like`
--

CREATE TABLE `tb_like` (
  `liid` int(50) NOT NULL COMMENT '点赞id',
  `uid` int(50) NOT NULL COMMENT '点赞用户id',
  `type` varchar(8) COLLATE utf8_unicode_ci NOT NULL COMMENT '被点赞类型（回答/日志/评论）',
  `bhid` int(50) NOT NULL COMMENT '被点赞（回答/日志/评论）id',
  `datetime` datetime NOT NULL COMMENT '点赞时间',
  `c_sign` int(2) DEFAULT 0 COMMENT '点赞取消标记（点赞取消1）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='点赞表';

-- --------------------------------------------------------

--
-- 表的结构 `tb_log`
--

CREATE TABLE `tb_log` (
  `lid` int(50) NOT NULL COMMENT '日志id',
  `uid` varchar(15) COLLATE utf8_unicode_ci NOT NULL COMMENT '发布用户id',
  `cid` int(50) NOT NULL COMMENT '城市id',
  `tid` int(30) DEFAULT NULL COMMENT '话题id',
  `title` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '日志标题',
  `datetime` datetime NOT NULL COMMENT '发布时间',
  `f_sign` int(2) NOT NULL DEFAULT 0 COMMENT '发布标记（已发布1/未发布0）',
  `m_sign` int(2) NOT NULL DEFAULT 0 COMMENT '私密标记（仅自己可见1/公开0）',
  `d_state` int(2) NOT NULL DEFAULT 0 COMMENT '删除状态',
  `blank_content` int(15) NOT NULL DEFAULT 0 COMMENT '判断是否是无内容旅行日志'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `tb_log`
--

INSERT INTO `tb_log` (`lid`, `uid`, `cid`, `tid`, `title`, `datetime`, `f_sign`, `m_sign`, `d_state`, `blank_content`) VALUES
(143, '15823130287', 0, NULL, '体会耳听八方温柔经有6我就4翁人', '2020-06-30 05:07:26', 0, 0, 0, 0),
(144, '15823130287', 0, NULL, '拜访耳边风而不吴逢波嗯嗯特别耳边风而不', '2020-06-30 05:08:55', 0, 0, 0, 1),
(145, '15823130287', 0, NULL, '儿童哈特', '2020-06-30 05:09:53', 0, 0, 0, 1),
(146, '15823130287', 0, NULL, '容易被特别', '2020-06-30 05:11:14', 0, 0, 0, 0),
(147, '15823130287', 0, NULL, '他和发表如果不让我', '2020-06-30 05:16:10', 0, 0, 0, 1),
(148, '15823130287', 0, NULL, '把我反而福耳边风', '2020-06-30 05:16:33', 0, 0, 0, 1),
(149, '15823130287', 0, NULL, '个人个而非', '2020-06-30 05:16:57', 0, 0, 0, 1),
(150, '15823130287', 0, NULL, '变得工农业', '2020-06-30 05:17:15', 0, 0, 0, 1),
(151, '15823130287', 0, NULL, '好人和淘宝体而后', '2020-06-30 05:17:34', 0, 0, 0, 1),
(152, '15823118322', 0, NULL, '和他北部湾', '2020-06-30 05:22:10', 0, 0, 0, 0),
(153, '15823118322', 0, NULL, '贝尔宾呢改变', '2020-06-30 05:22:25', 0, 0, 0, 1),
(154, '15823118322', 0, NULL, '怪不得个巴尔干巴尔干', '2020-06-30 05:23:58', 0, 0, 0, 0),
(155, '15823118322', 0, NULL, '交易日不得不戈壁', '2020-06-30 05:24:17', 0, 0, 0, 1),
(156, '15823118322', 0, NULL, '交易日呢戈贝尔服务费', '2020-06-30 05:25:27', 0, 0, 0, 0),
(157, '15823118322', 0, NULL, '虎年大公报如果你人', '2020-06-30 05:26:13', 0, 0, 0, 0),
(158, '15823118322', 0, NULL, '容易被哦特别特别台湾半个吻', '2020-06-30 05:26:37', 0, 0, 0, 0),
(159, '15823118322', 0, NULL, '容易被哦特别特别台湾半个吻', '2020-06-30 05:26:39', 0, 0, 0, 0),
(160, '15823118322', 0, NULL, '大公报大部分儿童', '2020-06-30 05:26:53', 0, 0, 0, 1),
(161, '15823118322', 0, NULL, '人格童年然后呢', '2020-06-30 05:27:14', 0, 0, 0, 1),
(162, '15823118322', 0, NULL, '你的改变访问', '2020-06-30 05:27:46', 0, 0, 0, 0),
(163, '15823118322', 0, NULL, '一日本我认为别人', '2020-06-30 05:29:00', 0, 0, 0, 1),
(164, '15823118322', 0, NULL, '很多敢不敢笨手笨脚', '2020-06-30 05:29:25', 0, 0, 0, 1),
(165, '15823118322', 0, NULL, '今儿特巴尔改变无法', '2020-06-30 05:29:55', 0, 0, 0, 1),
(166, '15823118322', 0, NULL, '依然博格巴哦', '2020-06-30 05:30:48', 0, 0, 0, 0),
(167, '15823118322', 0, NULL, '听不到贝尔亚金娜2', '2020-06-30 05:31:08', 0, 0, 0, 0),
(168, '15823118322', 0, NULL, '以后如果不儿童', '2020-06-30 05:31:58', 0, 0, 0, 0),
(169, '15823118322', 0, NULL, '5哈达巴特尔把我', '2020-06-30 05:32:26', 0, 0, 0, 0),
(170, '15823118322', 0, NULL, '他和不同', '2020-06-30 05:32:55', 0, 0, 0, 0),
(171, '15823130287', 0, NULL, '共渡难关茹贝尔官本位如果温柔歌舞团', '2020-06-30 05:43:06', 0, 0, 0, 1),
(172, '15823130287', 0, NULL, '共渡难关茹贝尔官本位如果温柔歌舞团', '2020-06-30 05:43:08', 0, 0, 0, 1),
(173, '15823130287', 0, NULL, '给你日本人个', '2020-06-30 05:46:26', 0, 0, 0, 1),
(174, '15823118322', 0, NULL, '图片先', '2020-06-30 16:07:27', 0, 0, 0, 0),
(175, '15823118322', 0, NULL, '图片先2', '2020-06-30 16:11:25', 0, 0, 0, 1),
(176, '15823118322', 0, NULL, '图片先3', '2020-06-30 16:13:46', 0, 0, 0, 1),
(177, '15823118322', 0, NULL, '很长', '2020-06-30 16:16:30', 0, 0, 0, 0),
(178, '15823118322', 0, NULL, '人听不到一百个博尔特', '2020-06-30 18:54:47', 0, 0, 0, 0),
(179, '15823118322', 0, NULL, '人听不到一百个博尔特', '2020-06-30 18:55:43', 0, 0, 0, 0),
(180, '15823118322', 0, NULL, '人听不到一百个博尔特', '2020-06-30 18:56:17', 0, 0, 0, 1),
(181, '15823118322', 0, NULL, '和特别萨多娃', '2020-06-30 19:05:37', 0, 0, 0, 0),
(182, '15823118322', 0, NULL, '不特别是无法', '2020-06-30 19:11:50', 0, 0, 0, 0),
(183, '15823118322', 0, NULL, '代表不认同博尔特', '2020-06-30 19:14:35', 0, 0, 0, 1),
(184, '15823118322', 0, NULL, '4一2个人2个2人', '2020-06-30 19:15:31', 0, 0, 0, 1),
(185, '15823118322', 0, NULL, '他和特巴尔逃不过', '2020-06-30 19:57:36', 0, 0, 0, 1),
(186, '15823130287', 0, NULL, '3呵呵特巴尔无法访问', '2020-06-30 19:59:57', 0, 0, 0, 1),
(187, '15823118322', 0, NULL, '我来了', '2020-06-30 20:04:05', 0, 0, 0, 1),
(188, '15823118322', 0, NULL, '我用什么才能留住你', '2020-06-30 21:23:22', 0, 0, 0, 0),
(189, '15823118322', 0, NULL, '特花天酒地巴尔干', '2020-06-30 22:33:41', 0, 0, 0, 1),
(190, '15823118322', 0, NULL, '呵呵戈贝尔发表', '2020-06-30 22:34:46', 0, 0, 0, 1),
(191, '15823118322', 0, NULL, ' 大哥各方', '2020-06-30 22:35:51', 0, 0, 0, 1),
(192, '15823118322', 0, NULL, 'hhdjdj', '2020-06-30 22:37:36', 0, 0, 0, 0),
(193, '15823118322', 0, NULL, 'ghhj', '2020-06-30 22:39:18', 0, 0, 0, 1),
(194, '15823118322', 0, NULL, 'fghb', '2020-06-30 22:40:49', 0, 0, 0, 1),
(195, '15823118322', 0, NULL, 'fbbb', '2020-06-30 22:41:21', 0, 0, 0, 1),
(196, '15823118322', 0, NULL, '这是一个正经旅游日志', '2020-07-01 11:53:55', 0, 0, 0, 0),
(197, '15823118322', 0, NULL, 'dfgg', '2020-07-01 16:02:12', 0, 0, 0, 1),
(198, '15823118322', 0, NULL, 'ffgg', '2020-07-01 16:09:00', 0, 0, 0, 0),
(199, '15823118322', 0, NULL, 'fvv', '2020-07-01 16:10:40', 0, 0, 0, 1),
(203, '15823118322', 0, NULL, 'fgh', '2020-07-01 16:17:15', 0, 0, 0, 1),
(204, '15823118322', 0, NULL, 'dgvb', '2020-07-01 16:18:58', 0, 0, 0, 1),
(205, '15823118322', 0, NULL, 'cgh', '2020-07-01 16:19:36', 0, 0, 0, 1),
(206, '15823118322', 0, NULL, 'fhh', '2020-07-01 16:36:48', 0, 0, 0, 1),
(207, '15823118322', 0, NULL, 'gh', '2020-07-01 16:37:15', 0, 0, 0, 1),
(208, '15823118322', 0, NULL, 'gdnfbsgnsgbsfbsfwfba', '2020-07-01 16:45:23', 0, 0, 0, 0),
(209, '15823118322', 0, NULL, '来啦蝴蝶', '2020-07-01 16:57:14', 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `tb_log_images`
--

CREATE TABLE `tb_log_images` (
  `liid` int(15) NOT NULL,
  `lid` varchar(15) NOT NULL,
  `ipath` varchar(500) NOT NULL COMMENT '图片存储路径',
  `back_img` int(10) NOT NULL COMMENT '判断是否是背景图片',
  `ti_order` int(50) NOT NULL COMMENT '旅行日志图文顺序'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `tb_log_images`
--

INSERT INTO `tb_log_images` (`liid`, `lid`, `ipath`, `back_img`, `ti_order`) VALUES
(143, '143', '20200630/15934648465efa580e6f2ed.jpg', 1, 0),
(144, '143', '20200630/15934648465efa580e70375.jpg', 0, 2),
(145, '144', '20200630/15934649355efa58673f5e0.jpg', 1, 0),
(146, '145', '20200630/15934649935efa58a17e8a9.jpg', 1, 0),
(147, '146', '20200630/15934650745efa58f2636bc.jpg', 1, 0),
(148, '147', '20200630/15934653705efa5a1a3de67.jpg', 1, 0),
(149, '148', '20200630/15934653935efa5a3154e04.jpg', 1, 0),
(151, '150', '20200630/15934654355efa5a5bb4680.jpg', 1, 0),
(152, '151', '20200630/15934654545efa5a6ea2cb7.jpg', 1, 0),
(153, '152', '20200630/15934657305efa5b8219e5e.jpg', 1, 0),
(154, '152', '20200630/15934657305efa5b821b592.jpg', 0, 2),
(155, '153', '20200630/15934657455efa5b91816c2.jpg', 1, 0),
(156, '154', '20200630/15934658385efa5beec4315.jpg', 1, 0),
(157, '154', '20200630/15934658385efa5beec56af.jpg', 0, 2),
(158, '154', '20200630/15934658385efa5beec6d72.jpg', 0, 4),
(159, '155', '20200630/15934658575efa5c016de0e.jpg', 1, 0),
(160, '156', '20200630/15934659275efa5c474e873.jpg', 1, 0),
(161, '156', '20200630/15934659275efa5c474f9ac.jpg', 0, 2),
(162, '156', '20200630/15934659275efa5c47509ec.jpg', 0, 4),
(163, '156', '20200630/15934659275efa5c475181e.jpg', 0, 6),
(164, '157', '20200630/15934659735efa5c7536d9b.jpg', 1, 0),
(165, '158', '20200630/15934659975efa5c8d41c5f.jpg', 1, 0),
(166, '159', '20200630/15934659995efa5c8f3577d.jpg', 1, 0),
(167, '160', '20200630/15934660135efa5c9d8ac46.jpg', 1, 0),
(168, '161', '20200630/15934660345efa5cb23ada0.jpg', 1, 0),
(169, '162', '20200630/15934660665efa5cd20eed5.jpg', 1, 0),
(170, '162', '20200630/15934660665efa5cd20fd06.jpg', 0, 2),
(171, '163', '20200630/15934661405efa5d1c1c2ae.jpg', 1, 0),
(172, '164', '20200630/15934661655efa5d351989b.jpg', 1, 0),
(173, '165', '20200630/15934661955efa5d53711e3.jpg', 1, 0),
(174, '166', '20200630/15934662485efa5d887c298.jpg', 1, 0),
(175, '167', '20200630/15934662685efa5d9c523ff.jpg', 1, 0),
(176, '168', '20200630/15934663185efa5dce8640e.jpg', 1, 0),
(177, '169', '20200630/15934663465efa5dea91420.jpg', 1, 0),
(178, '170', '20200630/15934663755efa5e0782955.jpg', 1, 0),
(179, '171', '20200630/15934669865efa606a2a425.jpg', 1, 0),
(180, '172', '20200630/15934669885efa606c123cd.jpg', 1, 0),
(181, '173', '20200630/15934671865efa613223803.jpg', 1, 0),
(182, '174', '20200630/15935044475efaf2bf967d1.jpg', 1, 0),
(183, '174', '20200630/15935044475efaf2bf9786b.jpg', 0, 2),
(184, '174', '20200630/15935044475efaf2bf9893e.jpg', 0, 4),
(185, '175', '20200630/15935046855efaf3add69fc.jpg', 1, 0),
(186, '176', '20200630/15935048265efaf43ad8410.jpg', 1, 0),
(187, '177', '20200630/15935049905efaf4de9c596.jpg', 1, 0),
(188, '178', '20200630/15935144875efb19f725c6f.jpg', 1, 0),
(189, '179', '20200630/15935145435efb1a2faa03e.jpg', 1, 0),
(190, '180', '20200630/15935145775efb1a518f393.jpg', 1, 0),
(191, '181', '20200630/15935151375efb1c81df623.jpg', 1, 0),
(192, '181', '20200630/15935151375efb1c81e6114.jpg', 0, 2),
(193, '182', '20200630/15935155105efb1df639437.jpg', 1, 0),
(194, '182', '20200630/15935155105efb1df63f436.jpg', 0, 2),
(195, '183', '20200630/15935156755efb1e9b51792.jpg', 1, 0),
(196, '184', '20200630/15935157315efb1ed3cb37a.jpg', 1, 0),
(197, '185', '20200630/15935182565efb28b067a7d.jpg', 1, 0),
(198, '186', '20200630/15935183975efb293d6b8ca.jpg', 1, 0),
(199, '187', '20200630/15935186455efb2a356f848.jpg', 1, 0),
(200, '188', '20200630/15935234025efb3cca8635d.jpg', 1, 0),
(201, '189', '20200630/15935276215efb4d45e7ba4.jpg', 1, 0),
(202, '190', '20200630/15935276865efb4d861a669.jpg', 1, 0),
(203, '191', '20200630/15935277515efb4dc774fd0.jpg', 1, 0),
(204, '192', '20200630/15935278565efb4e309a988.jpg', 1, 0),
(205, '193', '20200630/15935279585efb4e967e7b6.jpg', 1, 0),
(206, '194', '20200630/15935280495efb4ef166c8a.jpg', 1, 0),
(207, '195', '20200630/15935280815efb4f11ba9ba.jpg', 1, 0),
(208, '196', '20200701/15935756355efc08d3307f4.jpg', 1, 0),
(209, '196', '20200701/15935756355efc08d33be6e.jpg', 0, 2),
(210, '196', '20200701/15935756355efc08d33df30.jpg', 0, 4),
(211, '197', '20200701/15935905325efc4304bff99.jpg', 1, 0),
(212, '198', '20200701/15935909405efc449cda16f.jpg', 1, 0),
(213, '199', '20200701/15935910405efc45003222c.jpg', 1, 0),
(217, '203', '20200701/15935914355efc468b27349.jpg', 1, 0),
(218, '204', '20200701/15935915385efc46f2886ff.jpg', 1, 0),
(219, '205', '20200701/15935915765efc4718d259c.jpg', 1, 0),
(220, '206', '20200701/15935926085efc4b20b6570.jpg', 1, 0),
(221, '207', '20200701/15935926355efc4b3bc64e3.jpg', 1, 0),
(222, '208', '20200701/15935931235efc4d233efb2.jpg', 1, 0),
(223, '208', '20200701/15935931235efc4d23456b4.jpg', 0, 2),
(224, '208', '20200701/15935931235efc4d2346afa.jpg', 0, 4),
(225, '209', '20200701/15935938345efc4fea7c79b.jpg', 1, 0);

-- --------------------------------------------------------

--
-- 表的结构 `tb_log_text`
--

CREATE TABLE `tb_log_text` (
  `ltid` int(15) NOT NULL,
  `lid` int(15) NOT NULL COMMENT '日志ID',
  `content` text NOT NULL COMMENT '日志内容',
  `ti_order` int(50) NOT NULL COMMENT '旅行日志图文顺序'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `tb_log_text`
--

INSERT INTO `tb_log_text` (`ltid`, `lid`, `content`, `ti_order`) VALUES
(73, 133, 'fghgdஐgghஐஐhhjhஐgghhஐஐgfggஐஐghhcஐஐ', 1),
(75, 143, '天后宫贝尔宾温柔给我如果2让ஐ如果我认为让我ஐ', 1),
(76, 143, 'ஐ和让人温柔让我荣辱观二哥ஐ国庆日我认为法人股日瓦格高尔夫球放弃而非企鹅豌豆访问人我认为服务费温柔沙发到我热爱岛如厕青春1如果2沙发分身乏术访问人百万富翁反而特别特别是分布图儿童3倍儿淘宝体瓦布尔特别是个体和他巴尔干不舒服国庆日和他我不是46局外人已经啊哦特别强哦加入维也纳软硬件无烟日机器人一年中风格46那如意男人一年冉冉一家人云南省发给你污染源就容易男人一47全家6和无人机74', 3),
(77, 146, '高难度宝贝3特别是个体户外广告词语言学习中国人中国心情感部落叶松柏林赫塔菲菲菲菲菲菲菲菲菲菲菲菲菲尔彻尔康健康快乐的卡通戈尔曼谷杀手乔治希尔后代价值观念叨咕咕是只猫腻子孙后代表作为何处世上网卡拉', 1),
(78, 152, '？哦二哥能改变而非巴尔福倍儿童哈特别是个体户外广告词语文课题记忆碎片66ஐ', 1),
(79, 152, 'ஐ', 3),
(80, 154, '投巴尔干巴尔干ஐ', 1),
(81, 154, 'ஐ别人如果不ஐ个2如果2个3t给我个体32他和ஐ2如果微如果不如果不要让ஐ无污染核问题霸王条款ஐஐ', 3),
(82, 154, 'ஐ', 5),
(83, 156, '他和倍儿特别ஐ温柔歌舞团3特别ஐஐegwrwbsfeefw踩我认为冉冉2ஐஐ', 1),
(84, 156, 'ஐ发表哦特巴尔淘宝网ஐ和他特别ஐ', 3),
(85, 156, 'ஐ给我和他和他ஐ温柔个发表特别无任何ஐஐ', 5),
(86, 156, 'ஐ', 7),
(87, 157, '5各方巴尔福蔚然成风哦特别特别核问题好人好玩如果2哦敢不敢不舒服务费用心良苦难道理由衷肠6', 1),
(88, 158, '热那亚金洱镛贝尔宾', 1),
(89, 159, '热那亚金洱镛贝尔宾', 1),
(90, 162, '同时人生个博尔特和他ஐ', 1),
(91, 162, 'ஐ', 3),
(92, 166, '如果是被告人ஐ反而费尔特胡伊岑ஐ超人气问题我认为人ஐ给我如果温柔我ஐ感情二哥热播ஐ温柔热爱温柔虽然而我认为包头市宝石拌饭哦祝福虽然四人帮儿童少年游大白天我避而不谈悲情歌声3得不到反而不少人不是特巴尔是否会让我ஐ个我认为问题是个白头翁爱人把我ஐ人格不舒服不', 1),
(93, 167, '一35何必讨说法', 1),
(94, 168, '4个人和戈贝尔也不', 1),
(95, 169, '还是防不胜防不舒服务费', 1),
(96, 170, '问候封神榜人生4歌舞团贝尔宾日本5ஐ', 1),
(97, 174, 'ஐ', 1),
(98, 174, 'ஐbdbdbdஐbxbxnxஐஐ', 3),
(99, 174, 'ஐ', 5),
(100, 177, 'fbfbfbfஐஐdhfhjfஐhdbdஐஐfhfhஐhdhdjஐஐfhfhfjஐஐfhjfஐஐfhfjhffஐhffஐஐfhbfjfjfnஐfhfhhfjfஐஐjfjfjfjfjஐஐfbfbbffஐfbfbbfஐஐfbbfbfbfஐ', 1),
(101, 181, 'ஐ', 1),
(102, 181, 'ஐ', 3),
(103, 182, '和特别温柔ஐ', 1),
(104, 182, 'ஐ', 3),
(105, 188, '我给你贫穷的街道ஐ绝望的日落ஐ破败郊区的月亮ஐ我给你一个久望着孤月的人的悲哀', 1),
(106, 192, 'ryhj', 1),
(107, 196, '新裤子我爱你ஐஐ', 1),
(108, 196, 'ஐ嘿嘿ஐஐ', 3),
(109, 196, 'ஐ我来试试，表情。嘿嘿????！', 5),
(110, 198, 'drfgg', 1),
(111, 208, 'stbdgndgndfbfe645915945一口子lz1啊maxJS我1啊在yddg1里咯系咯自己也1啊自己提咯HK一lz一卷子了自己ஐ固体胶一句一句ஐ', 1),
(112, 208, 'ஐ一口子lz1lz1了ஐஐ', 3),
(113, 208, 'ஐ', 5),
(114, 209, '这是啥蝴蝶来着？', 1);

-- --------------------------------------------------------

--
-- 表的结构 `tb_question`
--

CREATE TABLE `tb_question` (
  `qid` int(50) NOT NULL COMMENT '问题id',
  `uid` int(50) NOT NULL COMMENT '提问用户id',
  `title` varchar(40) COLLATE utf8_unicode_ci NOT NULL COMMENT '问题题目',
  `content` varchar(2000) COLLATE utf8_unicode_ci NOT NULL COMMENT '问题描述',
  `area` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '问题相关地点',
  `datetime` datetime NOT NULL COMMENT '提问时间',
  `f_state` int(2) NOT NULL COMMENT '屏蔽标记',
  `g_sign` int(2) NOT NULL COMMENT '精选标记'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='问答提问表';

-- --------------------------------------------------------

--
-- 表的结构 `tb_question_answer`
--

CREATE TABLE `tb_question_answer` (
  `qaid` int(50) NOT NULL COMMENT '回答id',
  `uid` int(50) NOT NULL COMMENT '回答用户id',
  `qid` int(50) NOT NULL COMMENT '问题id',
  `content` varchar(10000) COLLATE utf8_unicode_ci NOT NULL COMMENT '回答描述',
  `rid` int(50) NOT NULL COMMENT '路线图id',
  `datetime` datetime NOT NULL COMMENT '回答时间',
  `d_state` int(2) NOT NULL DEFAULT 0 COMMENT '删除状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='问答回答表';

-- --------------------------------------------------------

--
-- 表的结构 `tb_report`
--

CREATE TABLE `tb_report` (
  `reid` int(50) NOT NULL COMMENT '举报id',
  `uid` int(50) NOT NULL COMMENT '举报用户id',
  `buid` int(50) NOT NULL COMMENT '被举报用户id',
  `type` varchar(8) COLLATE utf8_unicode_ci NOT NULL COMMENT '被举报类型（路线图/评论/提问/日志）',
  `bhid` int(50) NOT NULL COMMENT '被举报（路线图/评论/提问/日志）id',
  `reason` varchar(1000) COLLATE utf8_unicode_ci NOT NULL COMMENT '举报原因',
  `datetime` datetime NOT NULL COMMENT '举报时间',
  `e_state` int(2) NOT NULL DEFAULT 0 COMMENT '举报状态（已审核1/未审核0）',
  `s_sign` int(2) NOT NULL COMMENT '举报成功标记（成功1/失败0）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='举报表';

-- --------------------------------------------------------

--
-- 表的结构 `tb_report_examine`
--

CREATE TABLE `tb_report_examine` (
  `reeid` int(50) NOT NULL COMMENT '举报审核id',
  `reid` int(50) NOT NULL COMMENT '举报id',
  `uid` int(50) NOT NULL COMMENT '审核管理员id',
  `content` varchar(1000) COLLATE utf8_unicode_ci NOT NULL COMMENT '审核回复内容',
  `datetime` datetime NOT NULL COMMENT '审核时间',
  `s_sign` int(2) NOT NULL COMMENT '举报审核通过标记（通过1/拒绝0）',
  `duration` int(30) NOT NULL DEFAULT 0 COMMENT '被举报用户封禁时长（单位：小时）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='举报审核表';

-- --------------------------------------------------------

--
-- 表的结构 `tb_rm_custom`
--

CREATE TABLE `tb_rm_custom` (
  `rcid` int(50) NOT NULL COMMENT '条目id',
  `rid` int(50) NOT NULL COMMENT '路线图id',
  `title` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '条目标题',
  `content` varchar(10000) COLLATE utf8_unicode_ci NOT NULL COMMENT '条目内容',
  `c_sign` int(2) NOT NULL COMMENT '付费标记'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='路线图自定义条目表';

-- --------------------------------------------------------

--
-- 表的结构 `tb_rm_evaluate`
--

CREATE TABLE `tb_rm_evaluate` (
  `reid` int(50) NOT NULL COMMENT '评价id',
  `uid` int(50) NOT NULL COMMENT '用户id',
  `rid` int(50) NOT NULL COMMENT '路线图id',
  `agree` int(2) NOT NULL COMMENT '赞同1/反对0',
  `datetime` datetime NOT NULL COMMENT '评价时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='路线图评价表';

-- --------------------------------------------------------

--
-- 表的结构 `tb_rm_purchase`
--

CREATE TABLE `tb_rm_purchase` (
  `rpid` int(50) NOT NULL COMMENT '购买记录id',
  `uid` int(50) NOT NULL COMMENT '用户id',
  `rid` int(50) NOT NULL COMMENT '路线图id',
  `datetime` datetime NOT NULL COMMENT '购买时间',
  `price` int(8) NOT NULL COMMENT '购买金额',
  `d_state` int(2) NOT NULL DEFAULT 0 COMMENT '删除状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='路线图购买记录表';

-- --------------------------------------------------------

--
-- 表的结构 `tb_rm_trip`
--

CREATE TABLE `tb_rm_trip` (
  `rtid` int(50) NOT NULL COMMENT '行程id',
  `rtnum` int(30) NOT NULL COMMENT '行程编号',
  `rid` int(50) NOT NULL COMMENT '路线图id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='路线图行程表';

-- --------------------------------------------------------

--
-- 表的结构 `tb_rm_tripsite`
--

CREATE TABLE `tb_rm_tripsite` (
  `trsid` int(50) NOT NULL COMMENT '行程节点id',
  `rtsnum` int(30) NOT NULL COMMENT '行程节点编号',
  `rtid` int(50) NOT NULL COMMENT '行程id',
  `site` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '节点名称',
  `vehicle` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '到达节点交通工具',
  `t_explain` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '说明'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='路线图行程节点表';

-- --------------------------------------------------------

--
-- 表的结构 `tb_road_map`
--

CREATE TABLE `tb_road_map` (
  `rid` int(50) NOT NULL COMMENT '路线图id',
  `rnum` int(30) NOT NULL COMMENT '路线图编号',
  `uid` int(50) NOT NULL COMMENT '用户id',
  `city` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '城市名称',
  `duration` varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT '游览时长',
  `tid` int(50) DEFAULT NULL COMMENT '话题id',
  `r_introduction` varchar(2000) COLLATE utf8_unicode_ci NOT NULL COMMENT '路线图介绍',
  `price` int(8) NOT NULL COMMENT '定价',
  `datetime` datetime NOT NULL COMMENT '发布时间',
  `picture` blob NOT NULL COMMENT '封面图片',
  `f_state` int(2) NOT NULL DEFAULT 0 COMMENT '折叠状态',
  `g_sign` int(2) NOT NULL DEFAULT 0 COMMENT '精选标记',
  `d_state` int(2) NOT NULL DEFAULT 0 COMMENT '删除状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='路线图表';

-- --------------------------------------------------------

--
-- 表的结构 `tb_topic`
--

CREATE TABLE `tb_topic` (
  `tid` int(50) NOT NULL COMMENT '话题id',
  `uid` int(50) NOT NULL COMMENT '管理员id',
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '话题名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='tb_topic';

-- --------------------------------------------------------

--
-- 表的结构 `tb_travel_activity`
--

CREATE TABLE `tb_travel_activity` (
  `taid` int(50) NOT NULL COMMENT '活动id',
  `uid` int(50) NOT NULL COMMENT '用户id',
  `title` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '活动标题',
  `start_date` date NOT NULL COMMENT '活动开始日期',
  `end_date` date NOT NULL COMMENT '活动结束日期',
  `area` varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT '活动地点',
  `content` varchar(2000) COLLATE utf8_unicode_ci NOT NULL COMMENT '活动内容',
  `picture` blob NOT NULL COMMENT '封面图片',
  `o_state` int(2) NOT NULL DEFAULT 0 COMMENT '活动状态（过期1/未过期0）',
  `d_state` int(2) NOT NULL DEFAULT 0 COMMENT '删除状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `tb_user`
--

CREATE TABLE `tb_user` (
  `uid` int(50) NOT NULL COMMENT '用户id',
  `phone` varchar(15) COLLATE utf8_unicode_ci NOT NULL COMMENT '账号',
  `password` varchar(16) COLLATE utf8_unicode_ci NOT NULL COMMENT '密码',
  `Nickname` varchar(12) COLLATE utf8_unicode_ci NOT NULL COMMENT '昵称',
  `sex` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '出生年月',
  `area` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '长住地区',
  `head` blob NOT NULL COMMENT '头像',
  `p_introduction` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '个人介绍',
  `datetime` datetime NOT NULL COMMENT '注册时间',
  `f_state` int(2) NOT NULL DEFAULT 0 COMMENT '账号冻结标记',
  `code` varchar(41) COLLATE utf8_unicode_ci NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户信息表';

--
-- 转存表中的数据 `tb_user`
--

INSERT INTO `tb_user` (`uid`, `phone`, `password`, `Nickname`, `sex`, `birthday`, `area`, `head`, `p_introduction`, `datetime`, `f_state`, `code`) VALUES
(19, '15823118322', 'asd', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-06 21:07:43', 0, '77d83fc9f3b3399c06cb934ca0f96f7cc918d340'),
(20, '15823118321', 'asd', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-06 21:13:34', 0, ''),
(21, '15823118323', 'asd', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-06 21:16:23', 0, ''),
(22, '15823118324', 'asd', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-07 13:48:11', 0, ''),
(23, '13042312061', '2017051604003', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-08 18:35:26', 0, ''),
(24, '15823130288', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:47', 1, ''),
(25, '15823130289', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:48', 0, ''),
(26, '15823130280', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:49', 0, ''),
(27, '15823130280', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:49', 0, ''),
(28, '15823130280', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:49', 0, ''),
(29, '15823130280', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:49', 0, ''),
(30, '15823130280', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:49', 0, ''),
(31, '15823130280', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:49', 0, ''),
(32, '15823130280', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:49', 0, ''),
(33, '15823130280', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:49', 0, ''),
(34, '15823130280', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:49', 0, ''),
(35, '15823130288', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:47', 1, ''),
(36, '15823130288', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:47', 1, ''),
(37, '15823130288', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:47', 1, ''),
(38, '15823130288', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:47', 1, ''),
(39, '15823130288', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:47', 1, ''),
(40, '15823130288', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:47', 1, ''),
(41, '15823130288', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:47', 1, ''),
(42, '15823130288', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:47', 1, ''),
(43, '15823130288', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:47', 1, ''),
(44, '15823130288', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:47', 1, ''),
(45, '15823130218', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:47', 1, ''),
(46, '15823130228', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:47', 0, ''),
(47, '15823130238', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:47', 1, ''),
(48, '15823130248', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 01:07:47', 1, ''),
(49, '15823118329', 'abc', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-10 20:51:31', 0, ''),
(50, '15823130287', '123', '昵称', NULL, NULL, NULL, '', NULL, '2020-06-28 20:48:25', 0, '');

-- --------------------------------------------------------

--
-- 表的结构 `tb_user_follow`
--

CREATE TABLE `tb_user_follow` (
  `fid` int(50) NOT NULL COMMENT '用户关注id',
  `uid` int(50) NOT NULL COMMENT '用户id',
  `fuid` int(50) NOT NULL COMMENT '关注用户id',
  `qid` int(50) NOT NULL COMMENT '关注提问id',
  `taid` int(50) NOT NULL COMMENT '关注旅行活动id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户关注信息表';

--
-- 转储表的索引
--

--
-- 表的索引 `tb_admin`
--
ALTER TABLE `tb_admin`
  ADD PRIMARY KEY (`uid`);

--
-- 表的索引 `tb_apply`
--
ALTER TABLE `tb_apply`
  ADD PRIMARY KEY (`aid`);

--
-- 表的索引 `tb_apply_examine`
--
ALTER TABLE `tb_apply_examine`
  ADD PRIMARY KEY (`aeid`);

--
-- 表的索引 `tb_chat`
--
ALTER TABLE `tb_chat`
  ADD PRIMARY KEY (`chid`);

--
-- 表的索引 `tb_city`
--
ALTER TABLE `tb_city`
  ADD PRIMARY KEY (`cid`);

--
-- 表的索引 `tb_city_specialty`
--
ALTER TABLE `tb_city_specialty`
  ADD PRIMARY KEY (`csid`);

--
-- 表的索引 `tb_collection`
--
ALTER TABLE `tb_collection`
  ADD PRIMARY KEY (`coid`);

--
-- 表的索引 `tb_collection_list`
--
ALTER TABLE `tb_collection_list`
  ADD PRIMARY KEY (`colid`);

--
-- 表的索引 `tb_comment`
--
ALTER TABLE `tb_comment`
  ADD PRIMARY KEY (`coid`);

--
-- 表的索引 `tb_like`
--
ALTER TABLE `tb_like`
  ADD PRIMARY KEY (`liid`);

--
-- 表的索引 `tb_log`
--
ALTER TABLE `tb_log`
  ADD PRIMARY KEY (`lid`);

--
-- 表的索引 `tb_log_images`
--
ALTER TABLE `tb_log_images`
  ADD PRIMARY KEY (`liid`);

--
-- 表的索引 `tb_log_text`
--
ALTER TABLE `tb_log_text`
  ADD PRIMARY KEY (`ltid`);

--
-- 表的索引 `tb_question`
--
ALTER TABLE `tb_question`
  ADD PRIMARY KEY (`qid`);

--
-- 表的索引 `tb_question_answer`
--
ALTER TABLE `tb_question_answer`
  ADD PRIMARY KEY (`qaid`);

--
-- 表的索引 `tb_report`
--
ALTER TABLE `tb_report`
  ADD PRIMARY KEY (`reid`);

--
-- 表的索引 `tb_report_examine`
--
ALTER TABLE `tb_report_examine`
  ADD PRIMARY KEY (`reeid`);

--
-- 表的索引 `tb_rm_custom`
--
ALTER TABLE `tb_rm_custom`
  ADD PRIMARY KEY (`rcid`);

--
-- 表的索引 `tb_rm_evaluate`
--
ALTER TABLE `tb_rm_evaluate`
  ADD PRIMARY KEY (`reid`);

--
-- 表的索引 `tb_rm_purchase`
--
ALTER TABLE `tb_rm_purchase`
  ADD PRIMARY KEY (`rpid`);

--
-- 表的索引 `tb_rm_trip`
--
ALTER TABLE `tb_rm_trip`
  ADD PRIMARY KEY (`rtid`);

--
-- 表的索引 `tb_rm_tripsite`
--
ALTER TABLE `tb_rm_tripsite`
  ADD PRIMARY KEY (`trsid`);

--
-- 表的索引 `tb_road_map`
--
ALTER TABLE `tb_road_map`
  ADD PRIMARY KEY (`rid`);

--
-- 表的索引 `tb_travel_activity`
--
ALTER TABLE `tb_travel_activity`
  ADD PRIMARY KEY (`taid`);

--
-- 表的索引 `tb_user`
--
ALTER TABLE `tb_user`
  ADD PRIMARY KEY (`uid`);

--
-- 表的索引 `tb_user_follow`
--
ALTER TABLE `tb_user_follow`
  ADD PRIMARY KEY (`fid`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `tb_admin`
--
ALTER TABLE `tb_admin`
  MODIFY `uid` int(50) NOT NULL AUTO_INCREMENT COMMENT '管理员id', AUTO_INCREMENT=2;

--
-- 使用表AUTO_INCREMENT `tb_apply`
--
ALTER TABLE `tb_apply`
  MODIFY `aid` int(50) NOT NULL AUTO_INCREMENT COMMENT '词条申请id';

--
-- 使用表AUTO_INCREMENT `tb_apply_examine`
--
ALTER TABLE `tb_apply_examine`
  MODIFY `aeid` int(50) NOT NULL AUTO_INCREMENT COMMENT '词条申请审核id';

--
-- 使用表AUTO_INCREMENT `tb_chat`
--
ALTER TABLE `tb_chat`
  MODIFY `chid` int(50) NOT NULL AUTO_INCREMENT COMMENT '聊天消息id';

--
-- 使用表AUTO_INCREMENT `tb_city`
--
ALTER TABLE `tb_city`
  MODIFY `cid` int(50) NOT NULL AUTO_INCREMENT COMMENT '城市id';

--
-- 使用表AUTO_INCREMENT `tb_city_specialty`
--
ALTER TABLE `tb_city_specialty`
  MODIFY `csid` int(50) NOT NULL AUTO_INCREMENT COMMENT '城市特色id';

--
-- 使用表AUTO_INCREMENT `tb_collection`
--
ALTER TABLE `tb_collection`
  MODIFY `coid` int(50) NOT NULL AUTO_INCREMENT COMMENT '收藏id';

--
-- 使用表AUTO_INCREMENT `tb_collection_list`
--
ALTER TABLE `tb_collection_list`
  MODIFY `colid` int(50) NOT NULL AUTO_INCREMENT COMMENT '收藏夹id';

--
-- 使用表AUTO_INCREMENT `tb_comment`
--
ALTER TABLE `tb_comment`
  MODIFY `coid` int(50) NOT NULL AUTO_INCREMENT COMMENT '评论id';

--
-- 使用表AUTO_INCREMENT `tb_like`
--
ALTER TABLE `tb_like`
  MODIFY `liid` int(50) NOT NULL AUTO_INCREMENT COMMENT '点赞id';

--
-- 使用表AUTO_INCREMENT `tb_log`
--
ALTER TABLE `tb_log`
  MODIFY `lid` int(50) NOT NULL AUTO_INCREMENT COMMENT '日志id', AUTO_INCREMENT=210;

--
-- 使用表AUTO_INCREMENT `tb_log_images`
--
ALTER TABLE `tb_log_images`
  MODIFY `liid` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=226;

--
-- 使用表AUTO_INCREMENT `tb_log_text`
--
ALTER TABLE `tb_log_text`
  MODIFY `ltid` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=115;

--
-- 使用表AUTO_INCREMENT `tb_question`
--
ALTER TABLE `tb_question`
  MODIFY `qid` int(50) NOT NULL AUTO_INCREMENT COMMENT '问题id';

--
-- 使用表AUTO_INCREMENT `tb_question_answer`
--
ALTER TABLE `tb_question_answer`
  MODIFY `qaid` int(50) NOT NULL AUTO_INCREMENT COMMENT '回答id';

--
-- 使用表AUTO_INCREMENT `tb_report`
--
ALTER TABLE `tb_report`
  MODIFY `reid` int(50) NOT NULL AUTO_INCREMENT COMMENT '举报id';

--
-- 使用表AUTO_INCREMENT `tb_report_examine`
--
ALTER TABLE `tb_report_examine`
  MODIFY `reeid` int(50) NOT NULL AUTO_INCREMENT COMMENT '举报审核id';

--
-- 使用表AUTO_INCREMENT `tb_rm_custom`
--
ALTER TABLE `tb_rm_custom`
  MODIFY `rcid` int(50) NOT NULL AUTO_INCREMENT COMMENT '条目id';

--
-- 使用表AUTO_INCREMENT `tb_rm_evaluate`
--
ALTER TABLE `tb_rm_evaluate`
  MODIFY `reid` int(50) NOT NULL AUTO_INCREMENT COMMENT '评价id';

--
-- 使用表AUTO_INCREMENT `tb_rm_purchase`
--
ALTER TABLE `tb_rm_purchase`
  MODIFY `rpid` int(50) NOT NULL AUTO_INCREMENT COMMENT '购买记录id';

--
-- 使用表AUTO_INCREMENT `tb_travel_activity`
--
ALTER TABLE `tb_travel_activity`
  MODIFY `taid` int(50) NOT NULL AUTO_INCREMENT COMMENT '活动id';

--
-- 使用表AUTO_INCREMENT `tb_user`
--
ALTER TABLE `tb_user`
  MODIFY `uid` int(50) NOT NULL AUTO_INCREMENT COMMENT '用户id', AUTO_INCREMENT=51;

--
-- 使用表AUTO_INCREMENT `tb_user_follow`
--
ALTER TABLE `tb_user_follow`
  MODIFY `fid` int(50) NOT NULL AUTO_INCREMENT COMMENT '用户关注id';
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
