package com.elastic.dao;

import com.elastic.entity.Earphone;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class EarphoneDao {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public List<Earphone> matchAllForPage(String fieldName, String indexName,String typeName, int from, int size){
        List<Earphone> earphones = new ArrayList<>();

        // 排序条件
        SortBuilder sortBuilder = new FieldSortBuilder(fieldName).order(SortOrder.DESC);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("earphone") // 设置索引
                .withTypes("earphones")  // 设置类
                .withQuery(QueryBuilders.matchAllQuery()) //设置查找语句
                .withSort(sortBuilder) // 设置排序条件
                .withPageable(PageRequest.of(from,size))
                .build();

        AggregatedPage<Earphone> earphones1 = elasticsearchTemplate.queryForPage(searchQuery, Earphone.class);
        Iterator<Earphone> iterator = earphones1.iterator();
        while(iterator.hasNext()){
            earphones.add(iterator.next());
        }
        return earphones;
    }

    // 全文查找
    public List<Earphone> queryStringByName(String queryString,
                                            Integer from,
                                             Integer size ) {
        List<Earphone> earphones = new ArrayList<>();
        //  全文查找 builder
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(queryString)
                .analyzer("ik_max_word")
                .field("name");

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("earphone") // 设置索引
                .withTypes("earphones")  // 设置类
                .withQuery(queryStringQueryBuilder) //设置查找语句
                .withPageable(PageRequest.of(from,size))
                .build();

        AggregatedPage<Earphone> earphones1 = elasticsearchTemplate.queryForPage(searchQuery, Earphone.class);
        Iterator<Earphone> iterator = earphones1.iterator();
        while(iterator.hasNext()){
            earphones.add(iterator.next());
        }
        return earphones;
    }

    // 关键字查找
    public List<Earphone> queryTermByName(String queryString,Integer from,Integer size ) {
        List<Earphone> earphones = new ArrayList<>();
        // 关键字查找 builder
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("name",queryString);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("earphone") // 设置索引
                .withTypes("earphones")  // 设置类
                .withQuery(queryBuilder) //设置查找语句
                .withPageable(PageRequest.of(from,size))
                .build();
        AggregatedPage<Earphone> earphones1 = elasticsearchTemplate.queryForPage(searchQuery, Earphone.class);
        Iterator<Earphone> iterator = earphones1.iterator();
        while(iterator.hasNext()){
            earphones.add(iterator.next());
        }
        return earphones;
    }


}
