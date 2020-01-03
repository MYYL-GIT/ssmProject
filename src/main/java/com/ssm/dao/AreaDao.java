package com.ssm.dao;

import com.ssm.entity.Area;

import java.util.List;

public interface AreaDao {
    /**
     * 列出区域列表
     * @returnareaList
     */
    List<Area> queryArea();
}
