package com.ssm.dao;

import com.ssm.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    ProductCategoryDao productCategoryDao;

    @Test
    public void testQueryProductCategory(){
        long shopId=1;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryById(shopId);
        System.out.println(productCategoryList.get(0).getProductCategoryName());
    }
    @Test
    public void testBatchInsertProductCategory(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("商品类别1");
        productCategory.setPriority(1);
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(1L);
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("商品类别2");
        productCategory2.setPriority(2);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(1L);
        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory2);
        int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        assertEquals(2,effectedNum);
    }

    @Test
    public void testDeleteProductCategory()throws Exception{
        long shopId = 1;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryById(shopId);
        for (ProductCategory pc : productCategoryList){
            if ("二手牛奶".equals(pc.getProductCategoryName())||"二手电话".equals(pc.getProductCategoryName())){
                int effectedNum = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(),shopId);
                assertEquals(1,effectedNum);

            }
        }
    }
}
