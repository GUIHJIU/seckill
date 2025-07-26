package Services;

import Entity.Product;
import Mapper.ProductMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl extends ServiceImpl< ProductMapper, Product >
        implements ProductService {

    @Override
    @Transactional
    public boolean updateStock(Long productId, Integer quantity) {
        return lambdaUpdate()
                .setSql("stock = stock - " + quantity)
                .eq(Product::getId, productId)
                .gt(Product::getStock, quantity)
                .update();
    }
}
