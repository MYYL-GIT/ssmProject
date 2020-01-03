package com.ssm.dao;

import com.ssm.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {
    /**
     * 通过shopId 查询店铺商品类别
     * @param shopId
     * @return List<ProductCategory>
     */
    List<ProductCategory>queryProductCategoryById(@Param("shopId")long shopId);

    /**
     * 批量新增商品类别
     *
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 删除指定商品类别
     * @param productCategory
     * @param shopId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategory,@Param("shopId")long shopId);

}
