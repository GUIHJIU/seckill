package com.example.seckillproduct.services.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.seckillproduct.entity.Product;
import com.example.seckillproduct.mapper.ProductMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckillproduct.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl extends ServiceImpl< ProductMapper, Product > implements ProductService {

    @Override
    // 在 updateStock 方法中启用乐观锁
    @Transactional
    public boolean updateStock(Long productId, Integer quantity) {
        Product product = getById(productId);
        if (product == null || product.getStock() < quantity) {
            return false;
        }

        // 2. 扣减库存
        product.setStock(product.getStock() - quantity);

        // 3. 更新数据库（自动处理乐观锁）
        return updateById(product);
    }

}
