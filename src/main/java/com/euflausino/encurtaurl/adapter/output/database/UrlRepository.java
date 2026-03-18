package com.euflausino.encurtaurl.adapter.output.database;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UrlRepository extends CassandraRepository<DBUrlEntity, String> {



}
