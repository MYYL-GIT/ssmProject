package com.ssm.dao;

import com.ssm.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao {
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition")
                                         ShopCategory shopCategoryCondition);
}
