package com.example.seckillinventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.seckillinventory.entity.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface InventoryMapper extends BaseMapper<Inventory> {

    /**
     * 预扣减库存
     * @param productId 商品ID
     * @param quantity 扣减数量
     * @return 影响行数
     */
    @Update("UPDATE inventory SET quantity = quantity - #{quantity}, sold = sold + #{quantity}, version = version + 1, update_time = CURRENT_TIMESTAMP WHERE product_id = #{productId} AND quantity >= #{quantity} AND deleted = 0")
    int deductInventory(@Param("productId") Long productId, @Param("quantity") Integer quantity);

    /**
     * 回滚库存
     * @param productId 商品ID
     * @param quantity 回滚数量
     * @return 影响行数
     */
    @Update("UPDATE inventory SET quantity = quantity + #{quantity}, sold = sold - #{quantity}, version = version + 1, update_time = CURRENT_TIMESTAMP WHERE product_id = #{productId} AND sold >= #{quantity} AND deleted = 0")
    int rollbackInventory(@Param("productId") Long productId, @Param("quantity") Integer quantity);

    /**
     * 锁定库存
     * @param productId 商品ID
     * @param quantity 锁定数量
     * @return 影响行数
     */
    @Update("UPDATE inventory SET locked = locked + #{quantity}, version = version + 1, update_time = CURRENT_TIMESTAMP WHERE product_id = #{productId} AND quantity >= #{quantity} AND deleted = 0")
    int lockInventory(@Param("productId") Long productId, @Param("quantity") Integer quantity);

    /**
     * 解锁库存
     * @param productId 商品ID
     * @param quantity 解锁数量
     * @return 影响行数
     */
    @Update("UPDATE inventory SET locked = locked - #{quantity}, version = version + 1, update_time = CURRENT_TIMESTAMP WHERE product_id = #{productId} AND locked >= #{quantity} AND deleted = 0")
    int unlockInventory(@Param("productId") Long productId, @Param("quantity") Integer quantity);

    /**
     * 确认扣减库存
     * @param productId 商品ID
     * @param quantity 扣减数量
     * @return 影响行数
     */
    @Update("UPDATE inventory SET locked = locked - #{quantity}, sold = sold + #{quantity}, version = version + 1, update_time = CURRENT_TIMESTAMP WHERE product_id = #{productId} AND locked >= #{quantity} AND deleted = 0")
    int confirmDeductInventory(@Param("productId") Long productId, @Param("quantity") Integer quantity);

    /**
     * 根据商品ID查询库存
     * @param productId 商品ID
     * @return 库存信息
     */
    Inventory selectByProductId(@Param("productId") Long productId);

}
