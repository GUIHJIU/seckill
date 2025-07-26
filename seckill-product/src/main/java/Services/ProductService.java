package Services;

import Entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ProductService extends IService< Product > {
    // 自定义业务方法
    boolean updateStock(Long productId, Integer quantity);
}


