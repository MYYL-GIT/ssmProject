package com.ssm.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssm.cache.JedisUtil;
import com.ssm.dao.HeadLineDao;
import com.ssm.entity.HeadLine;
import com.ssm.service.HeadLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    private static String HLLISTKEY = "headlinelist";
    private static Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);

    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws Exception{
        //定义redis的key前缀
        String key = HLLISTKEY;
        //定义接收对象
        List<HeadLine> headLineList = null;
        //定义jackson数据转换操作类
        ObjectMapper mapper = new ObjectMapper();
        //拼接出redis的key
        if (headLineCondition!=null&&headLineCondition.getEnableStatus()!=null){
            key = key+"_"+headLineCondition.getEnableStatus();
        }
        //判断key是否存在
        if (!jedisKeys.exists(key)){
            //若不存在，则从数据库里面取出相应数据
            headLineList = headLineDao.queryHeadLine(headLineCondition);
            //将相关的实体类集合转换成String,存入redis里面对应的key中
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(headLineList);
            }catch (JsonProcessingException e){
                e.printStackTrace();;
                logger.error(e.toString());
                throw new RuntimeException(e.getMessage());
            }
            jedisStrings.set(key,jsonString);
        }else{
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory()
                    .constructParametricType(ArrayList.class, HeadLine.class);
            headLineList = mapper.readValue(jsonString, javaType);
        }
        return headLineList;
    }
}
