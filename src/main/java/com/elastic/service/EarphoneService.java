package com.elastic.service;

import com.elastic.dao.EarphoneDao;
import com.elastic.entity.Earphone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EarphoneService {
    @Autowired
    private EarphoneDao earphoneDao;

    public List<Earphone> matchAllPagable(String fieldName, Integer from ,Integer size){
        return earphoneDao.matchAllForPage(fieldName,"earphone","earphones",from,size);
    }

    public List<Earphone> queryStringByName(String fieldName, Integer from ,Integer to) {
        return earphoneDao.queryStringByName(fieldName, from, to);
    }

    public List<Earphone> queryTermByName(String fieldName,Integer from, Integer to) {
        return earphoneDao.queryTermByName(fieldName, from, to);
    }

}
