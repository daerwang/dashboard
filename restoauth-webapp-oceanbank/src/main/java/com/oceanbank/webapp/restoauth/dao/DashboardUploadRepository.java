package com.oceanbank.webapp.restoauth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.oceanbank.webapp.restoauth.model.DashboardUpload;

@Transactional(readOnly = true)
public interface DashboardUploadRepository extends JpaRepository<DashboardUpload, Long>{

	List<DashboardUpload> findByTableIdAndTableName(Integer tableId, String tableName);
	
	DashboardUpload findAmlBatchUploadById(@Param("id") Integer id);

}
