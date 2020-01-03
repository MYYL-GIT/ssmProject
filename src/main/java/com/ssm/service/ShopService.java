package com.ssm.service;

import com.ssm.dto.ShopExecution;
import com.ssm.entity.Shop;
import com.ssm.exceptions.ShopOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface ShopService {
    /**
     * 根据shopCondition分页返回相应列表数据
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
    /**
     * 通过店铺Id获取店铺信息
     *
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * 更新店铺信息，包括对图片的处理
     * @param shop
     * @param file
     * @return
     * @throws ShopOperationException
     */
    ShopExecution modifyShop(Shop shop, CommonsMultipartFile file) throws ShopOperationException;

    /**
     * 添加店铺信息，包括对图片的处理
     * @param shop
     * @param shopImg
     * @return
     * @throws ShopOperationException
     */
    ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) throws ShopOperationException;

}
