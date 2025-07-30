package com.example.seckillproduct.services;

import com.example.seckillproduct.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

@Service
public interface ProductService extends IService< Product > {
    // 自定义业务方法
    boolean updateStock(Long productId, Integer quantity);
}


