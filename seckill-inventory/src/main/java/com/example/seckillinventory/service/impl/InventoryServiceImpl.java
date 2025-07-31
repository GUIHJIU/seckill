package com.example.seckillinventory.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckillinventory.entity.Inventory;
import com.example.seckillinventory.mapper.InventoryMapper;
import com.example.seckillinventory.service.InventoryService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private RedissonClient redissonClient;

    private static final String INVENTORY_LOCK_KEY_PREFIX = "inventory_lock:";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean preDeductInventory(Long productId, Integer quantity) {
        // 获取分布式锁
        String lockKey = INVENTORY_LOCK_KEY_PREFIX + productId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 尝试加锁，等待10秒，锁自动释放时间30秒
            boolean locked = lock.tryLock(10, 30, TimeUnit.SECONDS);
            if (!locked) {
                // 加锁失败
                return false;
            }

            // 查询库存
            Inventory inventory = inventoryMapper.selectByProductId(productId);
            if (inventory == null || inventory.getQuantity() < quantity) {
                // 库存不足
                return false;
            }

            // 预扣减库存
            int result = inventoryMapper.deductInventory(productId, quantity);
            return result > 0;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            // 释放锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirmDeductInventory(Long productId, Integer quantity) {
        // 获取分布式锁
        String lockKey = INVENTORY_LOCK_KEY_PREFIX + productId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 尝试加锁，等待10秒，锁自动释放时间30秒
            boolean locked = lock.tryLock(10, 30, TimeUnit.SECONDS);
            if (!locked) {
                // 加锁失败
                return false;
            }

            // 确认扣减库存
            int result = inventoryMapper.confirmDeductInventory(productId, quantity);
            return result > 0;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            // 释放锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean rollbackInventory(Long productId, Integer quantity) {
        // 获取分布式锁
        String lockKey = INVENTORY_LOCK_KEY_PREFIX + productId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 尝试加锁，等待10秒，锁自动释放时间30秒
            boolean locked = lock.tryLock(10, 30, TimeUnit.SECONDS);
            if (!locked) {
                // 加锁失败
                return false;
            }

            // 回滚库存
            int result = inventoryMapper.rollbackInventory(productId, quantity);
            return result > 0;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            // 释放锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    public Inventory getInventoryByProductId(Long productId) {
        return inventoryMapper.selectByProductId(productId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean initInventory(Long productId, Integer quantity) {
        Inventory inventory = new Inventory();
        inventory.setProductId(productId);
        inventory.setQuantity(quantity);
        inventory.setSold(0);
        inventory.setLocked(0);
        inventory.setVersion(0);
        inventory.setCreateTime(LocalDateTime.now());
        inventory.setUpdateTime(LocalDateTime.now());
        inventory.setDeleted(0);

        int result = inventoryMapper.insert(inventory);
        return result > 0;
    }

    /**
     * 锁定库存
     * @param productId 商品ID
     * @param quantity 锁定数量
     * @return 是否锁定成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean lockInventory(Long productId, Integer quantity) {
        // 获取分布式锁
        String lockKey = INVENTORY_LOCK_KEY_PREFIX + productId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 尝试加锁，等待10秒，锁自动释放时间30秒
            boolean locked = lock.tryLock(10, 30, TimeUnit.SECONDS);
            if (!locked) {
                // 加锁失败
                return false;
            }

            // 锁定库存
            int result = inventoryMapper.lockInventory(productId, quantity);
            return result > 0;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            // 释放锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * 解锁库存
     * @param productId 商品ID
     * @param quantity 解锁数量
     * @return 是否解锁成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unlockInventory(Long productId, Integer quantity) {
        // 获取分布式锁
        String lockKey = INVENTORY_LOCK_KEY_PREFIX + productId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 尝试加锁，等待10秒，锁自动释放时间30秒
            boolean locked = lock.tryLock(10, 30, TimeUnit.SECONDS);
            if (!locked) {
                // 加锁失败
                return false;
            }

            // 解锁库存
            int result = inventoryMapper.unlockInventory(productId, quantity);
            return result > 0;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            // 释放锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

}