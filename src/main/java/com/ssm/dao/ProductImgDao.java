package com.ssm.dao;

import com.ssm.entity.ProductImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductImgDao {

    /**
     * 批量添加商品详情图片
     *
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 批量删除商品详情图片
     *
     * @param productId
     * @return
     */
    int batchDeleteProductImg(@Param("productId") long productId);

    /**
     * 根据商品ID查询商品详情图的地址
     *
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgById(long productId);

}
