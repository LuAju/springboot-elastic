package com.elastic.dao;

import com.elastic.entity.Earphone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EarphoneRepository extends ElasticsearchRepository<Earphone,String> {

}
