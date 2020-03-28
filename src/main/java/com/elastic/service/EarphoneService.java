package com.elastic.service;

import com.elastic.dao.EarphoneDaoBase;
import com.elastic.entity.Earphone;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EarphoneService {
    @Autowired
    private EarphoneDaoBase earphoneDaoBase;

    public List<Earphone> matchAllPagable(String fieldName, Integer from ,Integer size){
        earphoneDaoBase.setSize(size);
        earphoneDaoBase.setFrom(from);
        earphoneDaoBase.setSortBuilder(new FieldSortBuilder(fieldName).order(SortOrder.DESC));
        earphoneDaoBase.setQueryBuilder(QueryBuilders.matchAllQuery());
        return earphoneDaoBase.operateEarphoneDao();
    }

    public List<Earphone> queryStringByName(String queryString, Integer from ,Integer size) {
        earphoneDaoBase.setSize(size);
        earphoneDaoBase.setFrom(from);
        earphoneDaoBase.setQueryBuilder( QueryBuilders.queryStringQuery(queryString)
                .analyzer("ik_max_word")
                .field("name"));
        return earphoneDaoBase.operateEarphoneDao();
    }

    public List<Earphone> queryTermByName(String queryString,Integer from, Integer size) {
        earphoneDaoBase.setSize(size);
        earphoneDaoBase.setFrom(from);
        earphoneDaoBase.setQueryBuilder(QueryBuilders.termQuery("name",queryString));
        HighlightBuilder.Field nameField = new HighlightBuilder.Field("*")
                .preTags("<span style='color:red'>")
                .postTags(" </span>").requireFieldMatch(false);
        earphoneDaoBase.setHighlightBuilder(nameField);
        return earphoneDaoBase.operateEarphoneDao();
    }

    public List<Earphone> queryTermByNameFilterSales(String queryString,Integer from, Integer size) {
        earphoneDaoBase.setSize(size);
        earphoneDaoBase.setFrom(from);
        earphoneDaoBase.setQueryBuilder(QueryBuilders.termQuery("name",queryString));
        earphoneDaoBase.setSortBuilder(new FieldSortBuilder("sales").order(SortOrder.DESC));
        earphoneDaoBase.setFilter(QueryBuilders.termQuery("band","米"));
        return earphoneDaoBase.operateEarphoneDao();
    }


    // 前缀查询
    public List<Earphone> prefixQuery(String queryString,String fieldName, Integer from, Integer size){
        earphoneDaoBase.setSize(size);
        earphoneDaoBase.setFrom(from);
        earphoneDaoBase.setQueryBuilder(QueryBuilders.prefixQuery(fieldName,queryString));
        return earphoneDaoBase.operateEarphoneDao();
    }

    //通配符查询
    public List<Earphone> wildQuery(String queryString,String fieldName, Integer from, Integer size){
        earphoneDaoBase.setSize(size);
        earphoneDaoBase.setFrom(from);
        earphoneDaoBase.setQueryBuilder(QueryBuilders.wildcardQuery(fieldName,queryString));
        return earphoneDaoBase.operateEarphoneDao();
    }

    // id查询
    public List<Earphone> idsQuery(){
        earphoneDaoBase.setQueryBuilder(QueryBuilders.idsQuery().addIds("1","3"));
        return earphoneDaoBase.operateEarphoneDao();
    }


    // fuzzy查询
    public List<Earphone> fuzzyQuery(String queryString,String fieldName, Integer from, Integer size){
        earphoneDaoBase.setSize(size);
        earphoneDaoBase.setFrom(from);
        earphoneDaoBase.setQueryBuilder(QueryBuilders.fuzzyQuery(fieldName,queryString));
        return earphoneDaoBase.operateEarphoneDao();
    }

    // bool 查询

    // 多字段


    // 高亮查询
//    public List<Earphone> highlightQuery(String queryString,String fieldName, Integer from, Integer size){
//        earphoneDaoBase.setSize(size);
//        earphoneDaoBase.setFrom(from);
//        HighlightBuilder.Field field = new HighlightBuilder.Field("name")
//                .preTags("<span style='color.red'").postTags("</span>");
//        earphoneDaoBase.setQueryBuilder(QueryBuilders.wildcardQuery(fieldName,queryString));
//        earphoneDaoBase.setHighlightField(field);
//        return earphoneDaoBase.operateEarphoneDao();
//    }
}