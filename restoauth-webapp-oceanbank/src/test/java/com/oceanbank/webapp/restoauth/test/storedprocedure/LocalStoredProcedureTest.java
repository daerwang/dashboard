package com.oceanbank.webapp.restoauth.test.storedprocedure;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.util.StringUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.ComparisonChain;
import com.oceanbank.webapp.common.model.AmlBatchCifResponse;
import com.oceanbank.webapp.common.model.AmlBatchRequestResponse;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.RestWebServiceUrl;
import com.oceanbank.webapp.restoauth.model.AmlBatchTransactionType;
import com.oceanbank.webapp.restoauth.model.DashboardUser;
import com.oceanbank.webapp.restoauth.model.RestOauthAccessToken;

/**
 * Sample Oauth2 request and refresh token	
 * 
 * request a token
 * http://localhost:8085/restoauth-webapp-oceanbank/oauth/token?grant_type=password&client_id=devaccess&client_secret=oceanbank&username=oceandev&password=ocean@123
 * 
 * refresh a token
 * http://localhost:8085/restoauth-webapp-oceanbank/oauth/token?grant_type=refresh_token&client_id=devaccess&client_secret=oceanbank&refresh_token=7ac7940a-d29d-4a4c-9a47-25a2167c8c49
 * 
 */

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"file:src/main/resources/springcontext/test-context-spring.xml", "file:src/main/resources/springcontext/local-as400-sp-spring.xml"})
public class LocalStoredProcedureTest {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	public static final String TEST_REST_URI = "http://localhost:8080/restoauth-webapp-oceanbank";
	
	@PersistenceContext
	private EntityManager entityManagerFactory;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void test_as400_cursors(){
		String sql = "ALLEN.test_as400_cursors";
		
		StoredProcedureQuery proc = entityManagerFactory.createStoredProcedureQuery(sql);
		//proc.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		proc.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);

		//proc.setParameter(1, "mmedina");
		proc.execute();
		
		LOGGER.info("It is " + proc.getOutputParameterValue(2));
		
	}
	
	@Test
	public void test_execute_aml_approval_bank_700(){
		String sql = "ALLEN.execute_aml_approval_bank_700";
		
		StoredProcedureQuery proc = entityManagerFactory.createStoredProcedureQuery(sql);
		proc.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		proc.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);

		proc.setParameter(1, "mmedina");
		proc.execute();
		
		LOGGER.info("It is " + proc.getOutputParameterValue(2));
		
	}
	
	@Test
	public void test_count_of_table(){
		StoredProcedureQuery proc = entityManagerFactory.createStoredProcedureQuery("allen.plus1inout");
		proc.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		proc.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);

		proc.setParameter(1, 2);
		proc.execute();
		
		LOGGER.info("It is " + proc.getOutputParameterValue(2));
		
		assertThat(proc.getOutputParameterValue(2), is((Object) 2));
	}
	
	@Test
	public void test_jdbc_template_stored_procedure(){
		String sql = "CALL ALLEN.test_local_get_one_resultset()";
		List<DashboardUser> customers = new ArrayList<DashboardUser>();
		 
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		
		for (Map<String, Object> row : rows) {
			DashboardUser customer = new DashboardUser();
			customer.setUsername((String)row.get("USERNAME"));
			
			LOGGER.info(customer.getUsername());
			
			customers.add(customer);
		}
	}
	
	protected static final class UserMapper implements RowMapper<DashboardUser>{

		@Override
		public DashboardUser mapRow(ResultSet rs, int rowNum)throws SQLException {
			DashboardUser u = new DashboardUser();
			u.setUsername(StringUtils.trimToNull(rs.getString("USERNAME")));
			return u;
		}
		
	}
	
	@Test
	public void test_jdbc_template(){
		String sql = "SELECT * FROM ALLEN.USERS";
		List<DashboardUser> customers = new ArrayList<DashboardUser>();
		 
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		
		for (Map<String, Object> row : rows) {
			DashboardUser customer = new DashboardUser();
			customer.setUsername((String)row.get("USERNAME"));
			
			LOGGER.info(customer.getUsername());
			
			customers.add(customer);
		}
	}
	
	@Test
	public void test_sp_output_parameter(){
		StoredProcedureQuery proc = entityManagerFactory.createStoredProcedureQuery("ALLEN.test_out_parameter");
		//proc.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		proc.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);

		//proc.setParameter(1, 2);
		proc.execute();
		
		LOGGER.info("It is " + proc.getOutputParameterValue(2));
		
		//assertThat(proc.getOutputParameterValue(2), is((Object) 2));
	}
	
	@Test
	public void test_stored_procedure_from_entity_manager(){
		StoredProcedureQuery proc = entityManagerFactory.createStoredProcedureQuery("allen.plus1inout");
		proc.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		proc.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);

		proc.setParameter(1, 2);
		proc.execute();
		
		LOGGER.info("It is " + proc.getOutputParameterValue(2));
		
		assertThat(proc.getOutputParameterValue(2), is((Object) 2));
	}
		
}
