package com.oceanbank.webapp.restoauth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.oceanbank.webapp.restoauth.model.W8BeneForm;

public interface W8BeneFormDao extends JpaRepository<W8BeneForm, Integer>{

	List<W8BeneForm> findByDatatableSearch(@Param("search") String search);
	
	List<W8BeneForm> findByEntityIds(@Param("search") List<Integer> ids);
}
