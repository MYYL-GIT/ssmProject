package com.ssm.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssm.cache.JedisUtil;
import com.ssm.dao.ShopCategoryDao;
import com.ssm.entity.ShopCategory;
import com.ssm.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    private static String SCLISTKEY = "shopcategorylist";

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryConition){
        String key = SCLISTKEY;
        List<ShopCategory> shopCategoryList = null;
        ObjectMapper mapper = new ObjectMapper();
        if (!jedisKeys.exists(key)) {
            ShopCategory shopCategoryCondition = new ShopCategory();
            // 当shopCategoryId不为空的时候，查询的条件会变为 where parent_id is null
            shopCategoryCondition.setShopCategoryId(-1L);
            shopCategoryList = shopCategoryDao
                    .queryShopCategory(shopCategoryCondition);
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(shopCategoryList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            jedisStrings.set(key, jsonString);
        } else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory()
                    .constructParametricType(ArrayList.class,
                            ShopCategory.class);
            try {
                shopCategoryList = mapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return shopCategoryList;
    }

}
