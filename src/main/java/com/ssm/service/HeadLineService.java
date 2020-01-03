package com.ssm.service;

import com.ssm.entity.HeadLine;

import java.util.List;

public interface HeadLineService {

    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws Exception;
}
