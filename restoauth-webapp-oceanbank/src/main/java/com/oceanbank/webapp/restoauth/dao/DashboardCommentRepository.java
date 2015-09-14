package com.oceanbank.webapp.restoauth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.oceanbank.webapp.restoauth.model.DashboardComment;

@Transactional(readOnly = true)
public interface DashboardCommentRepository extends JpaRepository<DashboardComment, Long>{

	List<DashboardComment> findByTableIdAndTableName(Integer tableId, String tableName);
	
	DashboardComment findDashboardCommentById(@Param("id") Integer id);

}
