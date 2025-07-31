package com.example.seckillinventory.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("inventory")
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 库存数量
     */
    private Integer quantity;

    /**
     * 已售数量
     */
    private Integer sold;

    /**
     * 锁定库存
     */
    private Integer locked;

    /**
     * 版本号，用于乐观锁
     */
    @Version
    private Integer version;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标志
     */
    @TableLogic
    private Integer deleted;
}

