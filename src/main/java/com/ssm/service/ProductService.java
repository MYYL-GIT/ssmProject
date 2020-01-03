package com.ssm.service;

import com.ssm.dto.ProductExecution;
import com.ssm.entity.Product;
import com.ssm.exceptions.ProductCategoryOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface ProductService {

    /**
     * 添加商品信息以及图片处理
     *
     * @param product
     * @param file
     * @param productImgList
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductExecution addProduct(Product product, CommonsMultipartFile file, List<CommonsMultipartFile> productImgList)
            throws ProductCategoryOperationException;

    /**
     * 根据商品ID查询商品信息
     *
     * @param productId
     * @return
     */
    Product queryProductById(long productId);

    /**
     *更新商品信息以及图片处理
     *
     * @param product
     * @param file
     * @param productImgList
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductExecution modifyProduct(Product product, CommonsMultipartFile file, List<CommonsMultipartFile> productImgList)
            throws ProductCategoryOperationException;

    /**
     * 根据prodcutCondition分页返回相应列表数据
     *
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);
}
