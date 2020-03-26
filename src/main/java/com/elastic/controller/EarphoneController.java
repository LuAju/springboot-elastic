package com.elastic.controller;

import com.elastic.entity.Earphone;
import com.elastic.service.EarphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/earphone")
public class EarphoneController {

    @Autowired
    private EarphoneService earphoneService;

    @RequestMapping("/getAll")
    public String matchAllEarphoneSorted(String fieldName,String from,String size){
        //
        List<Earphone> earphones = earphoneService.matchAllPagable(fieldName, Integer.parseInt(from), Integer.parseInt(size));
        return earphones.toString();
    }

    // 对名称字段全文查找
    @RequestMapping("queryByName")
    public String queryByName(@RequestParam(defaultValue = "0", required = false) String from ,
                                  @RequestParam(defaultValue = "2",required = false) String size,
                                  String queryString){
        List<Earphone> earphones = earphoneService.queryStringByName(queryString, Integer.parseInt(from), Integer.parseInt(size));
        return earphones.toString();
    }

    // 使用关键词查找， 根据分词结果，对于无扰通话，能识别通话，但是不能识别扰通。
    @RequestMapping("queryTermByName")
    public String queryTermByName(@RequestParam(defaultValue = "0", required = false) String from ,
                              @RequestParam(defaultValue = "2",required = false) String size,
                              String queryString){
        List<Earphone> earphones = earphoneService.queryTermByName(queryString, Integer.parseInt(from), Integer.parseInt(size));
        return earphones.toString();
    }
}
