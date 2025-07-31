package com.example.seckillinventory.controller;

import com.example.seckillinventory.entity.Inventory;
import com.example.seckillinventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    /**
     * 预扣减库存
     * @param productId 商品ID
     * @param quantity 扣减数量
     * @return 是否扣减成功
     */
    @PostMapping("/preDeduct")
    public ResponseEntity<Boolean> preDeductInventory(@RequestParam Long productId, @RequestParam Integer quantity) {
        if (productId == null || quantity == null || quantity <= 0) {
            return ResponseEntity.badRequest().build();
        }
        boolean result = inventoryService.preDeductInventory(productId, quantity);
        return ResponseEntity.ok(result);
    }

    /**
     * 确认扣减库存
     * @param productId 商品ID
     * @param quantity 扣减数量
     * @return 是否确认成功
     */
    @PostMapping("/confirmDeduct")
    public ResponseEntity<Boolean> confirmDeductInventory(@RequestParam Long productId, @RequestParam Integer quantity) {
        if (productId == null || quantity == null || quantity <= 0) {
            return ResponseEntity.badRequest().build();
        }
        boolean result = inventoryService.confirmDeductInventory(productId, quantity);
        return ResponseEntity.ok(result);
    }

    /**
     * 回滚库存
     * @param productId 商品ID
     * @param quantity 回滚数量
     * @return 是否回滚成功
     */
    @PostMapping("/rollback")
    public ResponseEntity<Boolean> rollbackInventory(@RequestParam Long productId, @RequestParam Integer quantity) {
        if (productId == null || quantity == null || quantity <= 0) {
            return ResponseEntity.badRequest().build();
        }
        boolean result = inventoryService.rollbackInventory(productId, quantity);
        return ResponseEntity.ok(result);
    }

    /**
     * 获取库存信息
     * @param productId 商品ID
     * @return 库存信息
     */
    @GetMapping("/getByProductId")
    public ResponseEntity<Inventory> getInventoryByProductId(@RequestParam Long productId) {
        if (productId == null) {
            return ResponseEntity.badRequest().build();
        }
        Inventory inventory = inventoryService.getInventoryByProductId(productId);
        return inventory != null ? ResponseEntity.ok(inventory) : ResponseEntity.notFound().build();
    }

    /**
     * 初始化库存
     * @param productId 商品ID
     * @param quantity 库存数量
     * @return 是否初始化成功
     */
    @PostMapping("/init")
    public ResponseEntity<Boolean> initInventory(@RequestParam Long productId, @RequestParam Integer quantity) {
        if (productId == null || quantity == null || quantity <= 0) {
            return ResponseEntity.badRequest().build();
        }
        boolean result = inventoryService.initInventory(productId, quantity);
        return ResponseEntity.ok(result);
    }

    /**
     * 锁定库存
     * @param productId 商品ID
     * @param quantity 锁定数量
     * @return 是否锁定成功
     */
    @PostMapping("/lock")
    public ResponseEntity<Boolean> lockInventory(@RequestParam Long productId, @RequestParam Integer quantity) {
        if (productId == null || quantity == null || quantity <= 0) {
            return ResponseEntity.badRequest().build();
        }
        boolean result = inventoryService.lockInventory(productId, quantity);
        return ResponseEntity.ok(result);
    }

    /**
     * 解锁库存
     * @param productId 商品ID
     * @param quantity 解锁数量
     * @return 是否解锁成功
     */
    @PostMapping("/unlock")
    public ResponseEntity<Boolean> unlockInventory(@RequestParam Long productId, @RequestParam Integer quantity) {
        if (productId == null || quantity == null || quantity <= 0) {
            return ResponseEntity.badRequest().build();
        }
        boolean result = inventoryService.unlockInventory(productId, quantity);
        return ResponseEntity.ok(result);
    }

}