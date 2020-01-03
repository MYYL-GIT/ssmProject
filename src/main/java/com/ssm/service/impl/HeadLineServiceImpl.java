package com.ssm.service.impl;

import com.ssm.dao.HeadLineDao;
import com.ssm.entity.HeadLine;
import com.ssm.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;

    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws Exception{
        return headLineDao.queryHeadLine(headLineCondition);
    }
}
