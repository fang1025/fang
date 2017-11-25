

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_function_tbl
-- ----------------------------
DROP TABLE IF EXISTS `sys_function_tbl`;
CREATE TABLE `sys_function_tbl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createBy` varchar(255) NOT NULL,
  `createById` bigint(20) NOT NULL,
  `createTime` datetime NOT NULL,
  `enable` int(11) NOT NULL,
  `lastUpdateBy` varchar(255) NOT NULL,
  `lastUpdateById` bigint(20) NOT NULL,
  `lastUpdateTime` datetime NOT NULL,
  `extraUrl` longtext COMMENT '后台地址',
  `functionClass` varchar(48) DEFAULT NULL COMMENT '功能菜单对应的class样式名称',
  `functionIcon` varchar(48) DEFAULT NULL COMMENT '功能菜单时对应的Icon图片地址',
  `functionId` varchar(128) DEFAULT NULL COMMENT '功能按钮对应的 节点的id',
  `functionName` varchar(30) NOT NULL COMMENT '功能名称',
  `functionUrl` varchar(128) DEFAULT NULL COMMENT '功能地址',
  `htmlStr` longtext COMMENT '功能元素',
  `lft` bigint(20) NOT NULL COMMENT '起始位置',
  `notes` varchar(255) DEFAULT NULL COMMENT '备注',
  `rgt` bigint(20) NOT NULL COMMENT '结束位置',
  `type` int(11) NOT NULL COMMENT '功能类型  1：左侧父功能   2：二级子功能（按钮）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='功能权限';

-- ----------------------------
-- Records of sys_function_tbl
-- ----------------------------
INSERT INTO `sys_function_tbl` VALUES ('1', 'admin', '1', '2015-11-27 18:34:14', '0', 'system', '0', '2017-11-25 18:18:08', ' ', '', '', '', ' 所有功能', '', '', '1', '', '66', '0');
INSERT INTO `sys_function_tbl` VALUES ('2', 'system', '0', '2017-06-09 11:26:34', '0', 'admin', '1', '2017-06-29 03:38:27', '', null, '', null, '系统管理', '', null, '2', null, '63', '1');
INSERT INTO `sys_function_tbl` VALUES ('4', 'admin', '11', '2017-06-10 15:28:10', '0', 'admin', '11', '2017-06-16 10:57:11', '', '', null, '', '系统用户', 'sys/userList.html', null, '3', '', '4', '2');
INSERT INTO `sys_function_tbl` VALUES ('5', 'admin', '11', '2017-06-10 15:28:44', '0', 'admin', '11', '2017-06-16 10:57:04', '', '', null, '', '功能', 'sys/functionList.html', null, '5', '', '6', '2');
INSERT INTO `sys_function_tbl` VALUES ('7', 'admin', '11', '2017-06-10 18:25:07', '0', 'admin', '1', '2017-06-29 03:38:27', '', '', null, '', '基础信息', '', null, '64', '', '65', '1');
INSERT INTO `sys_function_tbl` VALUES ('8', 'system', '0', '2017-06-12 10:31:11', '0', 'admin', '11', '2017-06-12 10:39:06', null, null, null, null, '部门', 'sys/deptList.html', null, '7', null, '16', '2');
INSERT INTO `sys_function_tbl` VALUES ('9', 'system', '0', '2017-06-12 10:31:11', '0', 'admin', '11', '2017-06-12 10:39:06', 'sys/dept/addDept', 'glyphicon-plus', null, 'showDeptPage', '新增', 'sys/deptAdd.html', null, '8', 'params=\"act=add\"', '9', '3');
INSERT INTO `sys_function_tbl` VALUES ('10', 'system', '0', '2017-06-12 10:31:11', '0', 'admin', '11', '2017-06-12 10:39:06', 'sys/dept/updateDept', 'glyphicon-edit', null, 'showDeptPage', '修改', 'sys/deptAdd.html', null, '10', 'params=\"act=edit\"', '11', '3');
INSERT INTO `sys_function_tbl` VALUES ('11', 'system', '0', '2017-06-12 10:31:11', '0', 'admin', '11', '2017-06-12 10:39:06', 'sys/dept/updateDeptEnable', 'glyphicon-remove', null, 'delDept', '删除', null, null, '12', null, '13', '3');
INSERT INTO `sys_function_tbl` VALUES ('12', 'system', '0', '2017-06-12 10:31:11', '0', 'admin', '11', '2017-06-12 10:39:06', null, 'glyphicon-th-large', null, 'trDblClick', '详情', null, null, '14', null, '15', '3');
INSERT INTO `sys_function_tbl` VALUES ('13', 'system', '0', '2017-06-12 16:19:35', '0', 'admin', '11', '2017-06-15 11:24:31', null, null, null, null, '字典', 'sys/dictList.html', null, '17', null, '28', '2');
INSERT INTO `sys_function_tbl` VALUES ('14', 'system', '0', '2017-06-12 16:19:35', '0', 'system', '0', '2017-06-12 16:19:35', 'sys/dict/addDict', 'glyphicon-plus', null, 'showDictPage', '新增', 'sys/dictAdd.html', null, '18', 'params=\"act=add\"', '19', '3');
INSERT INTO `sys_function_tbl` VALUES ('15', 'system', '0', '2017-06-12 16:19:35', '0', 'system', '0', '2017-06-12 16:19:35', 'sys/dict/updateDict', 'glyphicon-edit', null, 'showDictPage', '修改', 'sys/dictAdd.html', null, '20', 'params=\"act=edit\"', '21', '3');
INSERT INTO `sys_function_tbl` VALUES ('16', 'system', '0', '2017-06-12 16:19:35', '0', 'admin', '11', '2017-06-16 10:00:42', 'sys/dict/updateDictEnable', 'glyphicon glyphicon-lock', null, 'delDict', '禁用/启用', '', null, '22', '', '23', '3');
INSERT INTO `sys_function_tbl` VALUES ('17', 'system', '0', '2017-06-12 16:19:35', '0', 'system', '0', '2017-06-12 16:19:35', null, 'glyphicon-th-large', null, 'trDblClick', '详情', null, null, '24', null, '25', '3');
INSERT INTO `sys_function_tbl` VALUES ('18', 'admin', '11', '2017-06-15 11:24:31', '0', 'admin', '11', '2017-06-15 11:25:22', 'sys/dict/addDict', 'glyphicon-plus', null, 'showDictPage', '新增类别', 'sys/dictAdd.html', null, '26', 'params=\"act=addType\"', '27', '3');
INSERT INTO `sys_function_tbl` VALUES ('19', 'system', '0', '2017-06-05 09:08:22', '0', 'admin', '1', '2017-06-28 03:13:29', null, null, null, null, '角色', 'sys/roleList.html', null, '29', null, '40', '2');
INSERT INTO `sys_function_tbl` VALUES ('20', 'system', '0', '2017-06-05 09:08:22', '0', 'system', '0', '2017-06-05 09:08:22', 'sys/role/addRole', 'glyphicon-plus', null, 'showRolePage', '新增', 'sys/roleAdd.html', null, '30', 'params=\"act=add\"', '31', '3');
INSERT INTO `sys_function_tbl` VALUES ('21', 'system', '0', '2017-06-05 09:08:22', '0', 'system', '0', '2017-06-05 09:08:22', 'sys/role/updateRole', 'glyphicon-edit', null, 'showRolePage', '修改', 'sys/roleAdd.html', null, '32', 'params=\"act=edit\"', '33', '3');
INSERT INTO `sys_function_tbl` VALUES ('22', 'system', '0', '2017-06-05 09:08:22', '0', 'system', '0', '2017-06-05 09:08:22', 'sys/role/updateRoleEnable', 'glyphicon-remove', null, 'delRole', '删除', null, null, '34', null, '35', '3');
INSERT INTO `sys_function_tbl` VALUES ('23', 'system', '0', '2017-06-05 09:08:22', '0', 'system', '0', '2017-06-05 09:08:22', null, 'glyphicon-th-large', null, 'trDblClick', '详情', null, null, '36', null, '37', '3');
INSERT INTO `sys_function_tbl` VALUES ('24', 'system', '0', '2017-06-06 02:43:58', '0', 'admin', '1', '2017-06-29 03:38:27', null, null, null, null, '员工', 'sys/employeeList.html', null, '41', null, '52', '2');
INSERT INTO `sys_function_tbl` VALUES ('25', 'system', '0', '2017-06-06 02:43:58', '0', 'admin', '1', '2017-06-28 03:13:29', 'sys/employee/addEmployee', 'glyphicon-plus', null, 'showEmployeePage', '新增', 'sys/employeeAdd.html', null, '42', 'params=\"act=add\"', '43', '3');
INSERT INTO `sys_function_tbl` VALUES ('26', 'system', '0', '2017-06-06 02:43:58', '0', 'admin', '1', '2017-06-28 03:13:29', 'sys/employee/updateEmployee', 'glyphicon-edit', null, 'showEmployeePage', '修改', 'sys/employeeAdd.html', null, '44', 'params=\"act=edit\"', '45', '3');
INSERT INTO `sys_function_tbl` VALUES ('27', 'system', '0', '2017-06-06 02:43:58', '0', 'admin', '1', '2017-06-28 03:13:29', 'sys/employee/updateEmployeeEnable', 'glyphicon-remove', null, 'delEmployee', '删除', null, null, '46', null, '47', '3');
INSERT INTO `sys_function_tbl` VALUES ('28', 'system', '0', '2017-06-06 02:43:58', '0', 'admin', '1', '2017-06-28 03:13:29', null, 'glyphicon-th-large', null, 'trDblClick', '详情', null, null, '48', null, '49', '3');
INSERT INTO `sys_function_tbl` VALUES ('36', 'admin', '1', '2017-06-28 03:13:29', '0', 'admin', '1', '2017-06-28 04:22:50', '', 'glyphicon glyphicon-wrench', null, 'grantPrivilege', '分配权限', '', null, '38', '', '39', '3');
INSERT INTO `sys_function_tbl` VALUES ('37', 'admin', '1', '2017-06-29 03:38:27', '0', 'admin', '1', '2017-06-29 03:38:27', '', 'glyphicon glyphicon-wrench', null, 'assignRole', '分配角色', 'sys/assignRole.html', null, '50', '', '51', '1');
INSERT INTO `sys_function_tbl` VALUES ('38', 'system', '0', '2017-06-29 07:15:30', '0', 'system', '0', '2017-06-29 07:15:30', null, null, null, null, '部门', 'sys/deptList.html', null, '53', null, '62', '2');
INSERT INTO `sys_function_tbl` VALUES ('39', 'system', '0', '2017-06-29 07:15:30', '0', 'system', '0', '2017-06-29 07:15:30', 'sys/dept/addDept', 'glyphicon glyphicon-plus', null, 'showDeptPage', '新增', 'sys/deptAdd.html', null, '54', 'params=\"act=add\"', '55', '3');
INSERT INTO `sys_function_tbl` VALUES ('40', 'system', '0', '2017-06-29 07:15:30', '0', 'system', '0', '2017-06-29 07:15:30', 'sys/dept/updateDept', 'glyphicon glyphicon-edit', null, 'showDeptPage', '修改', 'sys/deptAdd.html', null, '56', 'params=\"act=edit\"', '57', '3');
INSERT INTO `sys_function_tbl` VALUES ('41', 'system', '0', '2017-06-29 07:15:30', '0', 'system', '0', '2017-06-29 07:15:30', 'sys/dept/updateDeptEnable', 'glyphicon glyphicon-remove', null, 'delDept', '删除', null, null, '58', null, '59', '3');
INSERT INTO `sys_function_tbl` VALUES ('42', 'system', '0', '2017-06-29 07:15:30', '0', 'system', '0', '2017-06-29 07:15:30', null, 'glyphicon glyphicon-th-large', null, 'trDblClick', '详情', null, null, '60', null, '61', '3');



-- ----------------------------
-- Table structure for sys_role_tbl
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_tbl`;
CREATE TABLE `sys_role_tbl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createBy` varchar(255) NOT NULL COMMENT '创建人 ',
  `createById` bigint(20) NOT NULL COMMENT '创建人用户ID ',
  `createTime` datetime NOT NULL COMMENT '创建时间 ',
  `enable` int(11) NOT NULL COMMENT '0:正常 1：删除 ',
  `lastUpdateBy` varchar(255) NOT NULL COMMENT '最后更新人员 ',
  `lastUpdateById` bigint(20) NOT NULL COMMENT '最后更新人员ID ',
  `lastUpdateTime` datetime NOT NULL COMMENT '最后更新时间 ',
  `notes` varchar(128) DEFAULT NULL COMMENT '备注 ',
  `roleCode` varchar(20) NOT NULL COMMENT '角色编号',
  `roleName` varchar(48) NOT NULL COMMENT '角色名称',
  `isfinal` int(11) DEFAULT NULL COMMENT '是否为不可更改 0：为可更改  	1: 为不可更改 ',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sbtjtdi95y3lrsoktnbq170ya` (`roleCode`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role_tbl
-- ----------------------------
INSERT INTO `sys_role_tbl` VALUES ('1', 'admin', '1', '2017-06-05 09:09:59', '0', 'admin', '1', '2017-06-05 09:09:59', '', 'manager', '主管', null);
INSERT INTO `sys_role_tbl` VALUES ('2', 'admin', '1', '2017-06-30 00:26:58', '0', 'admin', '1', '2017-06-30 00:26:58', '', 'leader', '领导', null);

-- ----------------------------
-- Table structure for sys_user_tbl
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_tbl`;
CREATE TABLE `sys_user_tbl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createBy` varchar(255) NOT NULL COMMENT '创建人 ',
  `createById` bigint(20) NOT NULL COMMENT '创建人用户ID ',
  `createTime` datetime NOT NULL COMMENT '创建时间 ',
  `enable` int(11) NOT NULL COMMENT '0:正常 1：删除 ',
  `lastUpdateBy` varchar(255) NOT NULL COMMENT '最后更新人员 ',
  `lastUpdateById` bigint(20) NOT NULL COMMENT '最后更新人员ID ',
  `lastUpdateTime` datetime NOT NULL COMMENT '最后更新时间 ',
  `email` varchar(56) DEFAULT NULL COMMENT '邮箱',
  `loginName` varchar(56) DEFAULT NULL COMMENT '登陆名',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机',
  `name` varchar(128) DEFAULT NULL COMMENT '姓名',
  `password` varchar(56) NOT NULL COMMENT '密码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `type` int(11) DEFAULT NULL COMMENT '用户类型,0:默认，1：员工，2：客户',
  PRIMARY KEY (`id`),
  KEY `index_email` (`email`),
  KEY `index_mobile` (`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user_tbl
-- ----------------------------
INSERT INTO `sys_user_tbl` VALUES ('1', 'system', '0', '2017-06-17 08:26:06', '0', 'system', '0', '2017-06-05 08:28:34', 'admin@126.com', 'admin', '', 'admin', 'cbd856d6b8771bf268104e35ab4d454c', null, '0');

-- ----------------------------
-- Table structure for sys_dict_tbl
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_tbl`;
CREATE TABLE `sys_dict_tbl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createBy` varchar(255) NOT NULL COMMENT '创建人 ',
  `createById` bigint(20) NOT NULL COMMENT '创建人用户ID ',
  `createTime` datetime NOT NULL COMMENT '创建时间 ',
  `enable` int(11) NOT NULL COMMENT '0:正常 1：删除 ',
  `lastUpdateBy` varchar(255) NOT NULL COMMENT '最后更新人员 ',
  `lastUpdateById` bigint(20) NOT NULL COMMENT '最后更新人员ID ',
  `lastUpdateTime` datetime NOT NULL COMMENT '最后更新时间 ',
  `dictCode` varchar(32) NOT NULL COMMENT '字典编码',
  `dictName` varchar(64) NOT NULL COMMENT '字典名称',
  `dictText` varchar(32) NOT NULL COMMENT '字典内容',
  `dictValue` varchar(16) NOT NULL COMMENT '字典值',
  `sortNo` int(11) DEFAULT NULL COMMENT '排序值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKafu9rccmme6qf7p5c9g45br43` (`dictCode`,`dictValue`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='字典';

-- ----------------------------
-- Records of sys_dict_tbl
-- ----------------------------
INSERT INTO `sys_dict_tbl` VALUES ('1', 'admin', '1', '2017-06-05 08:33:45', '0', 'admin', '1', '2017-06-05 08:33:45', 'gender', '性别', '男', '1', '1');
INSERT INTO `sys_dict_tbl` VALUES ('2', 'admin', '1', '2017-06-05 08:34:01', '0', 'admin', '1', '2017-06-05 08:34:01', 'gender', '性别', '女', '2', '2');
INSERT INTO `sys_dict_tbl` VALUES ('3', 'admin', '1', '2017-06-20 05:55:31', '0', 'admin', '1', '2017-06-21 08:17:12', 'deptType', '部门类型', '后勤', '01', '1');
INSERT INTO `sys_dict_tbl` VALUES ('4', 'admin', '1', '2017-06-20 05:55:57', '0', 'admin', '1', '2017-06-21 08:17:28', 'deptType', '部门类型', '技术', '02', '2');


