package Entity;

import com.baomidou.mybatisplus.annotation.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@TableName("product")
public class Product {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("name")
    @NotBlank(message = "商品名称不能为空")
    @Size(max = 100, message = "商品名称最长100字符")
    private String name;

    @TableField("description")
    @NotBlank(message = "商品描述不能为空")
    @Size(max = 500, message = "商品描述最长500字符")
    private String description;

    @TableField("main_image")
    @NotBlank(message = "主图不能为空")
    private String mainImage;

    @TableField("sub_images")
    private String subImages; // 存储格式: ["url1","url2"]

    @TableField("price")
    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格最小0.01元")
    private BigDecimal price;

    @TableField("stock")
    @Min(value = 0, message = "库存不能为负数")
    private Integer stock;

    @TableField("status")
    private Integer status; // 0-下架, 1-上架

    @TableField("category_id")
    @NotNull(message = "分类不能为空")
    private Long categoryId;

    @TableField("start_time")
    private Date startTime; // 秒杀开始时间

    @TableField("end_time")
    private Date endTime; // 秒杀结束时间

    @TableField("seckill_price")
    @DecimalMin(value = "0.01", message = "秒杀价格最小0.01元")
    private BigDecimal seckillPrice;

    @TableLogic
    @TableField("deleted")
    private Integer deleted; // 0-未删除, 1-已删除

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}