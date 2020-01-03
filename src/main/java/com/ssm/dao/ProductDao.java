package com.ssm.dao;

import com.ssm.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

    /**
     * 插入商品
     *
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * 根据商品ID查询商品信息
     *
     * @param productId
     * @return
     */
    Product queryProductById(long productId);

    /**
     * 更新商品信息
     *
     * @param product
     * @return
     */
    int updateProduct(Product product);

    /**
     * 分页查询商品,可输入的条件有:商品名(模糊),商品状态,商品类别,所属商店
     *
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition")Product productCondition, @Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);

    /**
     *返回queryProductList总数
     *
     * @param productCondition
     * @return
     *
     */
    int queryProductCount(@Param("productCondition")Product productCondition);

    /**
     * 删除商品类别之前，将商品类别ID置为空
     *
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);
}
