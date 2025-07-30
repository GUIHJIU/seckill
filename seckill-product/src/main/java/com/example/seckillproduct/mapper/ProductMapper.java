package com.example.seckillproduct.mapper;

import com.example.seckillproduct.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper< Product > {
    // 自定义复杂查询
    @Select("SELECT * FROM product WHERE category_id = #{categoryId} AND deleted = 0")
    List< Product > selectByCategory(@Param("categoryId") Long categoryId);
}
