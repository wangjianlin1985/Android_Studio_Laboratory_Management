/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : lab_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-07-28 22:16:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `classinfo`
-- ----------------------------
DROP TABLE IF EXISTS `classinfo`;
CREATE TABLE `classinfo` (
  `classNo` varchar(20) NOT NULL,
  `className` varchar(20) default NULL,
  `bornDate` datetime default NULL,
  `mainTeacher` varchar(20) default NULL,
  PRIMARY KEY  (`classNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classinfo
-- ----------------------------
INSERT INTO `classinfo` VALUES ('BJ001', '计算机3班', '2018-01-02 00:00:00', '宋大业');
INSERT INTO `classinfo` VALUES ('BJ002', '电子技术1班', '2018-01-02 00:00:00', '李明杰');

-- ----------------------------
-- Table structure for `course`
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `courseNo` varchar(20) NOT NULL,
  `classObj` varchar(20) default NULL,
  `courseName` varchar(20) default NULL,
  `courseHours` int(11) default NULL,
  `courseScore` float default NULL,
  `teacherObj` varchar(20) default NULL,
  `courseDesc` longtext,
  PRIMARY KEY  (`courseNo`),
  KEY `FK78A7CC3B2A751575` (`teacherObj`),
  KEY `FK78A7CC3BA91D8B03` (`classObj`),
  CONSTRAINT `FK78A7CC3B2A751575` FOREIGN KEY (`teacherObj`) REFERENCES `teacher` (`teacherNo`),
  CONSTRAINT `FK78A7CC3BA91D8B03` FOREIGN KEY (`classObj`) REFERENCES `classinfo` (`classNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('KC001', 'BJ001', '大学物理', '40', '3.5', 'TH001', '深入讲解力学和光学');
INSERT INTO `course` VALUES ('KC002', 'BJ002', '模拟电路基础', '48', '4', 'TH002', '指导学生掌握模拟电子电路基础知识');

-- ----------------------------
-- Table structure for `coursetest`
-- ----------------------------
DROP TABLE IF EXISTS `coursetest`;
CREATE TABLE `coursetest` (
  `testId` int(11) NOT NULL auto_increment,
  `courseObj` varchar(20) default NULL,
  `testName` varchar(30) default NULL,
  `weekObj` int(11) default NULL,
  `labTime` varchar(20) default NULL,
  `labObj` int(11) default NULL,
  `testMemo` longtext,
  PRIMARY KEY  (`testId`),
  KEY `FKB48EF6D8163FBE3` (`labObj`),
  KEY `FKB48EF6DC2BDF799` (`courseObj`),
  KEY `FKB48EF6D492EE347` (`weekObj`),
  CONSTRAINT `FKB48EF6D492EE347` FOREIGN KEY (`weekObj`) REFERENCES `weekinfo` (`weekId`),
  CONSTRAINT `FKB48EF6D8163FBE3` FOREIGN KEY (`labObj`) REFERENCES `labinfo` (`labId`),
  CONSTRAINT `FKB48EF6DC2BDF799` FOREIGN KEY (`courseObj`) REFERENCES `course` (`courseNo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coursetest
-- ----------------------------
INSERT INTO `coursetest` VALUES ('1', 'KC001', '力学加速度实验', '1', '下午1,2节', '1', '主要测试地球加速度数据！');
INSERT INTO `coursetest` VALUES ('2', 'KC002', '示波器的使用', '2', '下午3，4节', '2', '同学们准时来哈');
INSERT INTO `coursetest` VALUES ('3', 'KC002', '万用表常见工具使用', '3', '星期一下午1,2节', '1', '大家多多学习');

-- ----------------------------
-- Table structure for `device`
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `deviceId` int(11) NOT NULL auto_increment,
  `deviceName` varchar(40) default NULL,
  `labObj` int(11) default NULL,
  `devicePhoto` varchar(50) default NULL,
  `devicePrice` float default NULL,
  `deviceCount` int(11) default NULL,
  `deviceDesc` longtext,
  PRIMARY KEY  (`deviceId`),
  KEY `FK79D00A768163FBE3` (`labObj`),
  CONSTRAINT `FK79D00A768163FBE3` FOREIGN KEY (`labObj`) REFERENCES `labinfo` (`labId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES ('1', '秒表', '1', 'upload/B0644490955EBB8866B07BDABEAE0D14.jpg', '28.5', '10', '时间计时工具');

-- ----------------------------
-- Table structure for `labinfo`
-- ----------------------------
DROP TABLE IF EXISTS `labinfo`;
CREATE TABLE `labinfo` (
  `labId` int(11) NOT NULL auto_increment,
  `labName` varchar(60) default NULL,
  `labPhoto` varchar(50) default NULL,
  `labArea` float default NULL,
  `personNum` int(11) default NULL,
  `labAddress` varchar(50) default NULL,
  `labStateObj` varchar(20) default NULL,
  `labDesc` longtext,
  PRIMARY KEY  (`labId`),
  KEY `FK5F6783BB3F366187` (`labStateObj`),
  CONSTRAINT `FK5F6783BB3F366187` FOREIGN KEY (`labStateObj`) REFERENCES `latstate` (`stateId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of labinfo
-- ----------------------------
INSERT INTO `labinfo` VALUES ('1', '6A-203', 'upload/3A9D922B3D10CE5431FBB2F16B153226.jpg', '100', '90', '6教学楼A栋2楼', '1', '实验室设备先进，适合物理实验');
INSERT INTO `labinfo` VALUES ('2', '7B-201', 'upload/37A0B907702BD20B0F6210D64D2DE5E5.jpg', '120', '100', '7教学楼B栋2楼', '1', '电子技术实验室');

-- ----------------------------
-- Table structure for `latstate`
-- ----------------------------
DROP TABLE IF EXISTS `latstate`;
CREATE TABLE `latstate` (
  `stateId` varchar(20) NOT NULL,
  `stateName` varchar(20) default NULL,
  PRIMARY KEY  (`stateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of latstate
-- ----------------------------
INSERT INTO `latstate` VALUES ('1', '空闲');
INSERT INTO `latstate` VALUES ('2', '繁忙');

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `studentNo` varchar(20) NOT NULL,
  `password` varchar(30) default NULL,
  `classObj` varchar(20) default NULL,
  `name` varchar(20) default NULL,
  `gender` varchar(4) default NULL,
  `birthDate` datetime default NULL,
  `studentPhoto` varchar(50) default NULL,
  `telephone` varchar(20) default NULL,
  `email` varchar(50) default NULL,
  `address` varchar(80) default NULL,
  PRIMARY KEY  (`studentNo`),
  KEY `FKF3371A1BA91D8B03` (`classObj`),
  CONSTRAINT `FKF3371A1BA91D8B03` FOREIGN KEY (`classObj`) REFERENCES `classinfo` (`classNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('STU001', '123', 'BJ001', '双鱼林', '男', '2018-01-03 00:00:00', 'upload/0B5F35DC2BFD5BD24C816D5226085C2E.jpg', '13598342934', 'dashen@163.com', '四川成都红星路');
INSERT INTO `student` VALUES ('STU002', '123', 'BJ002', '王晓雪', '女', '2018-01-01 00:00:00', 'upload/C61DE920F57B840716924D1E59C5121F.jpg', '13958342342', 'xiaoxue@126.com', '四川南充人民南路13号');

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `teacherNo` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  `name` varchar(20) default NULL,
  `sex` varchar(4) default NULL,
  `inDate` datetime default NULL,
  `teacherPhoto` varchar(50) default NULL,
  `telephone` varchar(20) default NULL,
  `teacherDesc` longtext,
  PRIMARY KEY  (`teacherNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('TH001', '123', '李萌萌', '女', '2018-01-06 00:00:00', 'upload/8C97D1CB54202FA8D7E8624E8A3E5619.jpg', '13948083492', '美女老师教你玩转物理实验');
INSERT INTO `teacher` VALUES ('TH002', '123', '张萌英', '女', '2018-01-02 00:00:00', 'upload/068622CC30F53B264888F5E319D328A9.jpg', '13984082934', '精明能干的老师');

-- ----------------------------
-- Table structure for `weekinfo`
-- ----------------------------
DROP TABLE IF EXISTS `weekinfo`;
CREATE TABLE `weekinfo` (
  `weekId` int(11) NOT NULL auto_increment,
  `weekName` varchar(20) default NULL,
  PRIMARY KEY  (`weekId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of weekinfo
-- ----------------------------
INSERT INTO `weekinfo` VALUES ('1', '第1周');
INSERT INTO `weekinfo` VALUES ('2', '第2周');
INSERT INTO `weekinfo` VALUES ('3', '第3周');
INSERT INTO `weekinfo` VALUES ('4', '第4周');
INSERT INTO `weekinfo` VALUES ('5', '第5周');
INSERT INTO `weekinfo` VALUES ('6', '第6周');
INSERT INTO `weekinfo` VALUES ('7', '第7周');
INSERT INTO `weekinfo` VALUES ('8', '第8周');
INSERT INTO `weekinfo` VALUES ('9', '第9周');
INSERT INTO `weekinfo` VALUES ('10', '第10周');
INSERT INTO `weekinfo` VALUES ('11', '第11周');
INSERT INTO `weekinfo` VALUES ('12', '第12周');
INSERT INTO `weekinfo` VALUES ('13', '第13周');
INSERT INTO `weekinfo` VALUES ('14', '第14周');
INSERT INTO `weekinfo` VALUES ('15', '第15周');
INSERT INTO `weekinfo` VALUES ('16', '第16周');
INSERT INTO `weekinfo` VALUES ('17', '第17周');
INSERT INTO `weekinfo` VALUES ('18', '第18周');
