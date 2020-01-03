package com.ssm.service;

import com.ssm.dao.BaseTest;
import com.ssm.entity.Area;
import com.ssm.entity.PersonInfo;
import com.ssm.entity.Shop;
import com.ssm.entity.ShopCategory;
import com.ssm.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Date;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testAddShop(){


        Shop shop= new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺1");
        shop.setShopDesc("test1");
        shop.setShopAddr("test1");
        shop.setPhone("test1");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("C:\\Users\\A21570\\Desktop\\SAP_TEST\\111.png");
       // ShopExecution se = shopService.addShop(shop,shopImg);
     //   assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
    }

}
