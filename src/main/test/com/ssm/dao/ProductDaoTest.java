package com.ssm.dao;

import com.ssm.entity.Product;
import com.ssm.entity.ProductCategory;
import com.ssm.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest{
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testAinsertProduct() throws Exception{
        Shop shop1 = new Shop();
        shop1.setShopId(1L);
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setProductCategoryId(9L);
        //初始化两个商品实例并添加进shopId为1的店铺里，同时商品类别Id也为1
        Product product1 = new Product();
        product1.setProductName("测试1");
        product1.setProductDesc("测试DESC");
        product1.setImgAddr("TEST1");
        product1.setPriority(1);
        product1.setEnableStatus(2);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(productCategory1);
        Product product2 = new Product();
        product2.setProductName("测试2");
        product2.setProductDesc("测试DESC2");
        product2.setImgAddr("TEST2");
        product2.setPriority(2);
        product2.setEnableStatus(2);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setShop(shop1);
        product2.setProductCategory(productCategory1);
        int effectedNum = productDao.insertProduct(product1);
        assertEquals(1,effectedNum);
        effectedNum = productDao.insertProduct(product2);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testQueryProductById() throws Exception{
        Product product = productDao.queryProductById(1);
        System.out.println(product.getProductCategory().getProductCategoryId());
        System.out.println(product.getProductCategory().getProductCategoryName());
    }

    @Test
    public void testUpdateProduct() throws Exception{
        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("牛奶");
        product.setProductDesc("牛奶");
        product.setImgAddr("test");
        product.setNormalPrice("1");
        product.setPromotionPrice("1");
        product.setPriority(1);
        product.setLastEditTime(new Date());
        product.setEnableStatus(1);
        productDao.updateProduct(product);
    }

    @Test
    public void testQueryProductList() throws Exception{
        Product productCondition = new Product();
        productCondition.setProductName("牛奶");
        List<Product> list = productDao.queryProductList(productCondition,1,1);
        int effectedNum = productDao.queryProductCount(productCondition);
        System.out.println(list.get(0).getProductName());
        System.out.println(effectedNum);
    }

    @Test
    public void testUpadateProductCategoryToNull() throws Exception{
        int effectedNum = productDao.updateProductCategoryToNull(9);
        assertEquals(1,effectedNum);
    }
}
