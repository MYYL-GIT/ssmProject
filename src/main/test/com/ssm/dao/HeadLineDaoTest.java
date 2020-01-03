package com.ssm.dao;

import com.ssm.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HeadLineDaoTest extends BaseTest {
    @Autowired
    HeadLineDao headLineDao;

    @Test
    public void testQueryHeadLine() throws Exception{
        List<HeadLine> headLineList = headLineDao.queryHeadLine(new HeadLine());
        assertEquals(4,headLineList.size());
    }
}
