package com.elastic.controller;

import com.elastic.entity.Earphone;
import com.elastic.service.EarphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
