-- 数据库初始化脚本

--创建数据库
CREATE DATABASE seckill;
--使用数据库
USE seckill;
--创建秒杀库存表
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill` (
  `name` varchar(120) NOT NULL,
  `number` int(11) NOT NULL,
  `start_time` timestamp NOT NULL ,
  `end_time` timestamp NOT NULL ,
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`seckill_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

INSERT INTO seckill(name,number,start_time,end_time)
VALUES
     ('ipad air 2秒杀','100','2017-11-29 00:00:00','2017-11-30 00:00:00'),
     ('iphone7秒杀','200','2017-11-30 00:00:00','2017-12-01 00:00:00'),
     ('小米5秒杀','300','2017-11-29 00:00:00','2017-11-30 00:00:00'),
     ('华为p9秒杀','400','2017-11-29 00:00:00','2017-11-30 00:00:00');

--秒杀成功明细表
DROP TABLE IF EXISTS `success_killed`;
CREATE TABLE `success_killed` (
  `seckill_id` bigint(20) NOT NULL,
  `user_phone` bigint(20) NOT NULL,
  `state` tinyint(4) NOT NULL DEFAULT '-1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`seckill_id`,`user_phone`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--连接数据库控制台

mysql -uroot -p