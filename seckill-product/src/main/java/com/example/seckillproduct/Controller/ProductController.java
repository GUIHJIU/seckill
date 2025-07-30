package com.example.seckillproduct.Controller;

import com.example.seckillproduct.services.ProductService;
import com.example.seckillproduct.entity.Product;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dto.Result;
import exception.BizException;
import exception.ErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Api(tags = "商品管理")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ApiOperation("创建商品")
    public Result< Product > createProduct(@Valid @RequestBody Product product) {
        productService.save(product);
        return Result.success(product);
    }

    @PutMapping("/{id}")
    @ApiOperation("更新商品")
    public Result<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product) {
        product.setId(id);
        productService.updateById(product);
        return Result.success(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除商品")
    public Result<Void> deleteProduct(@PathVariable Long id) {
        // 逻辑删除
        productService.removeById(id);
        return Result.success(  null);
    }

    @GetMapping("/{id}")
    @ApiOperation("获取商品详情")
    public Result<Product> getProduct(@PathVariable Long id) {
        Product product = productService.getById(id);
        if (product == null || product.getDeleted() == 1) {
            throw new BizException(ErrorCode.PRODUCT_NOT_EXIST,"商品不存在");
        }
        return Result.success(product);
    }

    @GetMapping
    @ApiOperation("分页查询商品")
    public Result< Page<Product> > listProducts(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {

        Page<Product> pageInfo = new Page<>(page, size);
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getDeleted, 0);

        if (StringUtils.isNotBlank(keyword)) {
            queryWrapper.like(Product::getName, keyword)
                    .or().like(Product::getDescription, keyword);
        }

        return Result.success(productService.page(pageInfo, queryWrapper));
    }
}
