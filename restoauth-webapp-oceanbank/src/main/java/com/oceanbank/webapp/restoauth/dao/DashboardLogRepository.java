package com.oceanbank.webapp.restoauth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.oceanbank.webapp.restoauth.model.DashboardLog;

@Transactional(readOnly = true)
public interface DashboardLogRepository extends JpaRepository<DashboardLog, Long>{

	List<DashboardLog> findByTableIdAndTableName(Integer tableId, String tableName);

}
