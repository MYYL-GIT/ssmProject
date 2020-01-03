package com.ssm.service.impl;

import com.ssm.dao.AreaDao;
import com.ssm.entity.Area;
import com.ssm.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Override
    public List<Area> getAreaList(){
        return areaDao.queryArea();
    }
}
