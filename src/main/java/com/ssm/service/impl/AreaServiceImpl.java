package com.ssm.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssm.cache.JedisUtil;
import com.ssm.dao.AreaDao;
import com.ssm.entity.Area;
import com.ssm.exceptions.AreaOperationException;
import com.ssm.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisString;

    private static String AREALISTKEY = "arealist";
    private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Override
    public List<Area> getAreaList(){
        String key = AREALISTKEY;
        List<Area> areaList = null;
        ObjectMapper mapper = new ObjectMapper();
        if (!jedisKeys.exists(key)){
            areaList = areaDao.queryArea();
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(areaList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.toString());
                throw new AreaOperationException(e.getMessage());
            }
            jedisString.set(key,jsonString);
        }else {
            String jsonString = jedisString.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,Area.class);
            try {
                areaList = mapper.readValue(jsonString,javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.toString());
                throw new AreaOperationException(e.getMessage());
            }
        }
        return areaList;
    }
}
