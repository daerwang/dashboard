package com.oceanbank.webapp.restoauth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.oceanbank.webapp.restoauth.model.W8BeneFormDirect;

public interface W8BeneFormDirectDao extends JpaRepository<W8BeneFormDirect, Long>{

	List<W8BeneFormDirect> findByW8BeneFormDirectPK(@Param("cif")String cif, @Param("name")String name);
	
	List<W8BeneFormDirect> findByCifLike(@Param("search")String cif);
	
	W8BeneFormDirect findByCif(@Param("cif")String cif);
	
	List<W8BeneFormDirect> findByCifs(@Param("cifs") List<String> cifs);
	
	public List<String> findDistinctOfficerCodes();
	
	List<W8BeneFormDirect> findByOfficer(@Param("code")String code);
	
	List<W8BeneFormDirect> findByOfficers(@Param("codes") List<String> codes);
}
