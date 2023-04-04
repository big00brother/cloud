/*
 Navicat Premium Data Transfer

 Source Server         : mysql5.5
 Source Server Type    : MySQL
 Source Server Version : 50554
 Source Host           : localhost:3306
 Source Schema         : seata_stock

 Target Server Type    : MySQL
 Target Server Version : 50554
 File Encoding         : 65001

 Date: 18/06/2021 21:34:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for stock_tbl
-- ----------------------------
DROP TABLE IF EXISTS `stock_tbl`;
CREATE TABLE `stock_tbl`  (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `product_id` int(11) NULL DEFAULT NULL,
                              `count` int(11) NULL DEFAULT 0,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `seata_stock`.`stock_tbl`( `product_id`, `count`) VALUES ( 9, 100);

CREATE TABLE `undo_log` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `branch_id` bigint(20) NOT NULL,
                            `xid` varchar(100) NOT NULL,
                            `context` varchar(128) NOT NULL,
                            `rollback_info` longblob NOT NULL,
                            `log_status` int(11) NOT NULL,
                            `log_created` datetime NOT NULL,
                            `log_modified` datetime NOT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;