-- ./mysql/init.sql
CREATE DATABASE IF NOT EXISTS `nacos_config` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `nacos_config`;

-- -- Nacos配置表（官方表结构）
-- SOURCE /nacos/conf/mysql-schema.sql

-- 秒杀系统数据库
CREATE DATABASE IF NOT EXISTS `seckill_db` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `seckill_db`;

-- 商品表 (根据实体类调整)
CREATE TABLE `product` (
                           `id` BIGINT PRIMARY KEY COMMENT '商品ID',
                           `name` VARCHAR(100) NOT NULL COMMENT '商品名称',
                           `description` VARCHAR(500) COMMENT '商品描述',
                           `main_image` VARCHAR(255) NOT NULL COMMENT '主图URL',
                           `sub_images` VARCHAR(500) COMMENT '副图URL列表，格式为JSON数组',
                           `price` DECIMAL(10,2) NOT NULL COMMENT '原价',
                           `stock` INT NOT NULL DEFAULT 0 COMMENT '库存',
                           `status` TINYINT NOT NULL DEFAULT 1 COMMENT '0-下架,1-上架',
                           `category_id` BIGINT NOT NULL COMMENT '分类ID',
                           `start_time` DATETIME COMMENT '秒杀开始时间',
                           `end_time` DATETIME COMMENT '秒杀结束时间',
                           `seckill_price` DECIMAL(10,2) COMMENT '秒杀价',
                           `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '0-未删除,1-已删除',
                           `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `version` INT DEFAULT 0 COMMENT '乐观锁版本'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 订单表 (增加逻辑删除字段)
CREATE TABLE `order` (
                         `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                         `order_no` VARCHAR(32) NOT NULL COMMENT '订单号',
                         `user_id` BIGINT NOT NULL COMMENT '用户ID',
                         `product_id` BIGINT NOT NULL COMMENT '商品ID',
                         `quantity` INT NOT NULL DEFAULT 1 COMMENT '购买数量',
                         `amount` DECIMAL(10,2) NOT NULL COMMENT '支付金额',
                         `status` TINYINT NOT NULL DEFAULT 0 COMMENT '0-待支付,1-已支付,2-已取消',
                         `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '0-未删除,1-已删除',
                         `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
                         `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         UNIQUE KEY `uniq_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 库存流水表 (增加逻辑删除字段)
CREATE TABLE `inventory_log` (
                                 `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 `product_id` BIGINT NOT NULL,
                                 `order_no` VARCHAR(32) NOT NULL,
                                 `quantity` INT NOT NULL COMMENT '扣减数量',
                                 `status` TINYINT NOT NULL COMMENT '1-预扣减,2-已确认,3-已回退',
                                 `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '0-未删除,1-已删除',
                                 `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 初始化测试数据 (适配新结构)
INSERT INTO `product` (
    id, name, description, main_image, sub_images,
    price, stock, status, category_id,
    start_time, end_time, seckill_price
) VALUES (
             1,  -- 需替换为实际雪花ID
             'iPhone 15 Pro Max',
             '苹果旗舰手机',
             'http://example.com/main.jpg',
             '["http://example.com/sub1.jpg","http://example.com/sub2.jpg"]',
             8999.00,
             1000,
             1,  -- 上架状态
             101,-- 分类ID
             DATE_ADD(NOW(), INTERVAL 1 HOUR),
             DATE_ADD(NOW(), INTERVAL 3 HOUR),
             7999.00
         );