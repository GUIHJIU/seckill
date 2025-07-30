package Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
   @TableName("order")
   public class Order {
       @TableId(type = IdType.ASSIGN_ID)
       private Long id;
       private String orderNo;
       private Long userId;
       private Long productId;
       private Integer quantity;
       private BigDecimal amount;
       private Integer status;
       // 其他字段...
   }
