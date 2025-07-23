-- ./mysql/init.sql
CREATE DATABASE IF NOT EXISTS `nacos_config` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `nacos_config`;

-- -- Nacos配置表（官方表结构）
-- SOURCE /nacos/conf/mysql-schema.sql

-- 秒杀系统数据库
CREATE DATABASE IF NOT EXISTS `seckill_db` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `seckill_db`;

-- 商品表
CREATE TABLE `product` (
                           `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                           `name` VARCHAR(100) NOT NULL COMMENT '商品名称',
                           `description` VARCHAR(255) COMMENT '商品描述',
                           `price` DECIMAL(10,2) NOT NULL COMMENT '原价',
                           `seckill_price` DECIMAL(10,2) NOT NULL COMMENT '秒杀价',
                           `stock` INT NOT NULL COMMENT '总库存',
                           `seckill_stock` INT NOT NULL COMMENT '秒杀库存',
                           `start_time` DATETIME NOT NULL COMMENT '秒杀开始时间',
                           `end_time` DATETIME NOT NULL COMMENT '秒杀结束时间',
                           `version` INT DEFAULT 0 COMMENT '乐观锁版本',
                           `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 订单表
CREATE TABLE `order` (
                         `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                         `order_no` VARCHAR(32) NOT NULL COMMENT '订单号',
                         `user_id` BIGINT NOT NULL COMMENT '用户ID',
                         `product_id` BIGINT NOT NULL COMMENT '商品ID',
                         `quantity` INT NOT NULL DEFAULT 1 COMMENT '购买数量',
                         `amount` DECIMAL(10,2) NOT NULL COMMENT '支付金额',
                         `status` TINYINT NOT NULL DEFAULT 0 COMMENT '0-待支付,1-已支付,2-已取消',
                         `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                         `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         UNIQUE KEY `uniq_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 库存流水表
CREATE TABLE `inventory_log` (
                                 `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 `product_id` BIGINT NOT NULL,
                                 `order_no` VARCHAR(32) NOT NULL,
                                 `quantity` INT NOT NULL COMMENT '扣减数量',
                                 `status` TINYINT NOT NULL COMMENT '1-预扣减,2-已确认,3-已回退',
                                 `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 初始化测试数据
INSERT INTO `product` (name, description, price, seckill_price, stock, seckill_stock, start_time, end_time)
VALUES (
           'iPhone 15 Pro Max',
           '苹果旗舰手机',
           8999.00,
           7999.00,
           1000,
           100,
           DATE_ADD(NOW(), INTERVAL 1 HOUR),
           DATE_ADD(NOW(), INTERVAL 3 HOUR)
       );