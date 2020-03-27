package com.elastic.dao;

import com.elastic.entity.Earphone;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import javax.swing.text.Highlighter;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// dao的基本类，使用桥接器模式，使用合成而不是继承。
@Repository
@Scope("prototype")
public class EarphoneDaoBase {

    @Value("earphone")
    private String indexName;
    @Value("earphones")
    private String typeName;
    @NotNull
    private QueryBuilder queryBuilder;

    @Value("0")
    private Integer from;

    @Value("2")
    private Integer size;

    private FieldSortBuilder fieldSortBuilder;

    private SortOrder sortOrder;

    private HighlightField highlightField;

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

    public void setFieldSortBuilder(FieldSortBuilder fieldSortBuilder) {
        this.fieldSortBuilder = fieldSortBuilder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setHighlightField(HighlightField highlightField) {
        this.highlightField = highlightField;
    }

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public List<Earphone> operateEarphobeDao(){
        List<Earphone> earphones = new ArrayList<>();

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices(this.indexName) // 设置索引
                .withTypes(this.typeName)  // 设置类
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
