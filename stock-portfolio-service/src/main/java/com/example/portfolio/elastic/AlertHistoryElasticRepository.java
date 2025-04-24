package com.example.portfolio.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AlertHistoryElasticRepository extends ElasticsearchRepository<AlertHistoryDocument, String> {
}
