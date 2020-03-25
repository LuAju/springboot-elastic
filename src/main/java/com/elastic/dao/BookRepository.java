package com.elastic.dao;

import com.elastic.entity.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

// 泛型1：对象的类型 ； 泛型2： 逐渐的类型
public interface BookRepository extends ElasticsearchRepository<Book,String> {
    List<Book> findBookByName(String name);

    List<Book> findBookByContent(String content);


    List<Book> findBookByNameAndContent(String name, String content);
}
