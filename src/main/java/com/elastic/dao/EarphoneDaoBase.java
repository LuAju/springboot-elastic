package com.elastic.dao;

import com.elastic.entity.Earphone;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import javax.swing.text.Highlighter;
import javax.validation.constraints.NotNull;
import java.util.*;

// dao的基本类，使用桥接器模式，使用合成而不是继承。
@Repository
@Scope("prototype")
public class EarphoneDaoBase {

    @Value("earphone")
    private String indexName;

    @Value("earphones")
    private String typeName;

    // 查询语句
    @NotNull
    private QueryBuilder queryBuilder;

    // 查询结果的起始页数
    @Value("0")
    private Integer from;

    // 每页的数量
    @Value("2")
    private Integer size;

    // 排序语句
    private SortBuilder sortBuilder;

    // 高亮语句
    private HighlightBuilder.Field highlightBuilder;


    // 过滤语句
    private QueryBuilder filter;

    public void setFilter(QueryBuilder filter) {
        this.filter = filter;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setQueryBuilder(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public void setSize(Integer size) {
        this.size = size;
    }




    public void setSortBuilder(SortBuilder sortBuilder) {
        this.sortBuilder = sortBuilder;
    }

    public void setHighlightBuilder(HighlightBuilder.Field highlightBuilder) {
        this.highlightBuilder = highlightBuilder;
    }

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public List<Earphone> operateEarphoneDao(){
        List<Earphone> earphones = new ArrayList<>();

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
                .withIndices(this.indexName) // 设置索引
                .withTypes(this.typeName)  // 设置类
                .withQuery(queryBuilder) //设置查找语句
                .withPageable(PageRequest.of(from,size));

        if (sortBuilder !=null) {
            nativeSearchQueryBuilder = nativeSearchQueryBuilder.withSort(sortBuilder);
        }
        if (filter != null) {
            nativeSearchQueryBuilder = nativeSearchQueryBuilder.withFilter(filter);
        }
        if (highlightBuilder != null) {
            nativeSearchQueryBuilder = nativeSearchQueryBuilder.withHighlightFields(highlightBuilder);
        }

        SearchQuery searchQuery = nativeSearchQueryBuilder.build();

        AggregatedPage<Earphone> earphonespages = elasticsearchTemplate.queryForPage(searchQuery, Earphone.class,
                new SearchResultMapper() {
                    @Override
                    public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                        SearchHits searchHits = searchResponse.getHits();
                        SearchHit[] hits = searchHits.getHits();
                        List<Earphone> earphones1 = new ArrayList<>();
                        for (SearchHit hit : hits) {
                            Earphone earphone = new Earphone();
                            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                            earphone.setBand(sourceAsMap.get("band").toString());
                            if (highlightFields.containsKey("band")) {
                                earphone.setName(highlightFields.get("band").fragments()[0].toString());
                            }
                            earphone.setCategory(sourceAsMap.get("category").toString());
                            earphone.setFunction(sourceAsMap.get("function").toString());
                            earphone.setName(sourceAsMap.get("name").toString());
                            if (highlightFields.containsKey("name")) {
                                earphone.setName(highlightFields.get("name").fragments()[0].toString());
                            }
                            earphone.setId(sourceAsMap.get("id").toString());
                            earphone.setDate(new Date(Long.valueOf(sourceAsMap.get("date").toString())));
                            earphone.setPrice(Double.valueOf(sourceAsMap.get("price").toString()));
                            earphones1.add(earphone);
                        }
                        return new AggregatedPageImpl<T>((List <T>)  earphones1);

                    }
                });
        Iterator<Earphone> iterator = earphonespages.iterator();
        while(iterator.hasNext()){
            earphones.add(iterator.next());
        }
        return earphones;
    }
}
