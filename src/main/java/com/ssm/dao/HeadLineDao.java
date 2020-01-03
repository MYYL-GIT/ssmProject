package com.ssm.dao;

import com.ssm.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineDao {

    /**
     * 根据传入的查询条件（根据头条名查询头条）
     *
     * @param headLineCondition
     * @return
     */
    List<HeadLine>queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
