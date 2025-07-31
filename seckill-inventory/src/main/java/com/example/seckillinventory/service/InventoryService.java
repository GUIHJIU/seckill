package com.example.seckillinventory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckillinventory.entity.Inventory;

public interface InventoryService extends IService<Inventory> {

    /**
     * 预扣减库存
     * @param productId 商品ID
     * @param quantity 扣减数量
     * @return 是否成功
     */
    boolean preDeductInventory(Long productId, Integer quantity);

    /**
     * 确认扣减库存
     * @param productId 商品ID
     * @param quantity 扣减数量
     * @return 是否成功
     */
    boolean confirmDeductInventory(Long productId, Integer quantity);

    /**
     * 回滚库存
     * @param productId 商品ID
     * @param quantity 回滚数量
     * @return 是否成功
     */
    boolean rollbackInventory(Long productId, Integer quantity);

    /**
     * 根据商品ID查询库存
     * @param productId 商品ID
     * @return 库存信息
     */
    Inventory getInventoryByProductId(Long productId);

    /**
     * 初始化库存
     * @param productId 商品ID
     * @param quantity 初始数量
     * @return 是否成功
     */
    boolean initInventory(Long productId, Integer quantity);

    /**
     * 锁定库存
     * @param productId 商品ID
     * @param quantity 锁定数量
     * @return 是否成功
     */
    boolean lockInventory(Long productId, Integer quantity);

    /**
     * 解锁库存
     * @param productId 商品ID
     * @param quantity 解锁数量
     * @return 是否成功
     */
    boolean unlockInventory(Long productId, Integer quantity);

}