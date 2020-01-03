package com.ssm.service.impl;

import com.ssm.dao.ShopCategoryDao;
import com.ssm.entity.ShopCategory;
import com.ssm.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryConition){
        return shopCategoryDao.queryShopCategory(shopCategoryConition);
    }

}
