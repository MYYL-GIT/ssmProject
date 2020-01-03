package com.ssm.dao;

import com.ssm.entity.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest{
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testABatchInsertProductImg() throws Exception{
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(1L);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setImgDesc("测试");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(1L);
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2,effectedNum);
    }

    @Test
    public void testBatchDeleteProductImg() throws Exception{
        long productId = 1;
        productImgDao.batchDeleteProductImg(1);
    }

    @Test
    public void QueryProductImgById() throws Exception{
        long productId = 7;
        List<ProductImg> productImgList = productImgDao.queryProductImgById(productId);
        System.out.println(productImgList.get(0).getImgAddr());
    }
}
