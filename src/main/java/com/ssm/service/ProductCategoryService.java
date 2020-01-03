package com.ssm.service;

import com.ssm.dto.ProductCategoryExecution;
import com.ssm.entity.ProductCategory;
import com.ssm.exceptions.ProductCategoryOperationException;

import java.util.List;

public interface ProductCategoryService {
    /**
     * 获取商品类别列表
     *
     * @param shopId
     * @return
     */
    ProductCategoryExecution getProductCategoryListById(long shopId);

    /**
     * 批量添加商品类别
     *
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException;

    /**
     * 将此类别下的商品里的类别id置为空，再删除掉该商品类别
     *
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId)
            throws ProductCategoryOperationException;
}
