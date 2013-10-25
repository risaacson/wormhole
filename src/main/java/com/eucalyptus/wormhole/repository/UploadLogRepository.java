package com.eucalyptus.wormhole.repository;

import com.eucalyptus.wormhole.model.UploadLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 */
public interface UploadLogRepository extends JpaRepository<UploadLog, Integer> {
  //TODO this should implement pagination.
  // http://www.petrikainulainen.net/spring-data-jpa-tutorial/
  @Query("SELECT x FROM UploadLog x")
  public List<UploadLog> findRecentTwenty();
}
