package com.ssm.service.impl;

import com.ssm.dao.ProductCategoryDao;
import com.ssm.dto.ProductCategoryExecution;
import com.ssm.entity.ProductCategory;
import com.ssm.enums.ProductCatgoryStateEnum;
import com.ssm.enums.ShopStateEnum;
import com.ssm.exceptions.ProductCategoryOperationException;
import com.ssm.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    ProductCategoryDao productCategoryDao;

    @Override
    public ProductCategoryExecution getProductCategoryListById(long shopId){

        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryById(shopId);
        ProductCategoryExecution pe = new ProductCategoryExecution();
        if (productCategoryList != null){
            pe.setProductCategoryList(productCategoryList);
        }else {
            pe.setState(ShopStateEnum.INNER_ERROR.getState());
        }

        return pe;
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException {
        if (productCategoryList != null && productCategoryList.size() > 0){
            try{
                int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (effectedNum <= 0){
                    throw new ProductCategoryOperationException("店铺类别创建失败");
                }else{
                    return new ProductCategoryExecution(ProductCatgoryStateEnum.SUCCESS);
                }
            }catch (Exception e){
                throw new ProductCategoryOperationException("batchAddProductCategory error:"+e.getMessage());
            }
        }else{
            return new ProductCategoryExecution(ProductCatgoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
            throws ProductCategoryOperationException {
        //TODO 将此商品类别下的商品的类别Id置为空
        try{
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId,shopId);
            if (effectedNum <= 0){
                throw new ProductCategoryOperationException("商品类别删除失败");
            }else {
                return new ProductCategoryExecution(ProductCatgoryStateEnum.SUCCESS);
            }
        }catch (Exception e){
            throw new ProductCategoryOperationException("deleteProductCategory error:" + e.toString());
        }
    }
}
