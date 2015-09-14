/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.util.DefaultJdbcListFactory;
import org.springframework.security.oauth2.common.util.JdbcListFactory;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;


/**
 * The Class As400JdbcClientDetailsService copied from Spring oAuth2 JdbcClientDetailsService.
 */
public class As400JdbcClientDetailsService implements ClientDetailsService,
ClientRegistrationService{
	
	/** The Constant logger. */
	private static final Log logger = LogFactory.getLog(As400JdbcClientDetailsService.class);

	/** The Constant schemaName. */
	private static final String schemaName = "IBMOB700.";
	
	/** The mapper. */
	private JsonMapper mapper = createJsonMapper();

	/** The Constant CLIENT_FIELDS_FOR_UPDATE. */
	private static final String CLIENT_FIELDS_FOR_UPDATE = "resource_ids, scope, "
			+ "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
			+ "refresh_token_validity, additional_information, autoapprove";

	/** The Constant CLIENT_FIELDS. */
	private static final String CLIENT_FIELDS = "client_secret, " + CLIENT_FIELDS_FOR_UPDATE;

	/** The Constant BASE_FIND_STATEMENT. */
	private static final String BASE_FIND_STATEMENT = "select client_id, " + CLIENT_FIELDS
			+ " from " + schemaName + "oauth_client_details";

	/** The Constant DEFAULT_FIND_STATEMENT. */
	private static final String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

	/** The Constant DEFAULT_SELECT_STATEMENT. */
	private static final String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

	/** The Constant DEFAULT_INSERT_STATEMENT. */
	private static final String DEFAULT_INSERT_STATEMENT = "insert into " + schemaName + "oauth_client_details (" + CLIENT_FIELDS
			+ ", client_id) values (?,?,?,?,?,?,?,?,?,?,?)";

	/** The Constant DEFAULT_UPDATE_STATEMENT. */
	private static final String DEFAULT_UPDATE_STATEMENT = "update " + schemaName + "oauth_client_details " + "set "
			+ CLIENT_FIELDS_FOR_UPDATE.replaceAll(", ", "=?, ") + "=? where client_id = ?";

	/** The Constant DEFAULT_UPDATE_SECRET_STATEMENT. */
	private static final String DEFAULT_UPDATE_SECRET_STATEMENT = "update " + schemaName + "oauth_client_details "
			+ "set client_secret = ? where client_id = ?";

	/** The Constant DEFAULT_DELETE_STATEMENT. */
	private static final String DEFAULT_DELETE_STATEMENT = "delete from " + schemaName + "oauth_client_details where client_id = ?";

	/** The row mapper. */
	private RowMapper<ClientDetails> rowMapper = new ClientDetailsRowMapper();

	/** The delete client details sql. */
	private String deleteClientDetailsSql = DEFAULT_DELETE_STATEMENT;

	/** The find client details sql. */
	private String findClientDetailsSql = DEFAULT_FIND_STATEMENT;

	/** The update client details sql. */
	private String updateClientDetailsSql = DEFAULT_UPDATE_STATEMENT;

	/** The update client secret sql. */
	private String updateClientSecretSql = DEFAULT_UPDATE_SECRET_STATEMENT;

	/** The insert client details sql. */
	private String insertClientDetailsSql = DEFAULT_INSERT_STATEMENT;

	/** The select client details sql. */
	private String selectClientDetailsSql = DEFAULT_SELECT_STATEMENT;

	/** The password encoder. */
	private PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();

	/** The jdbc template. */
	private final JdbcTemplate jdbcTemplate;

	/** The list factory. */
	private JdbcListFactory listFactory;

	/**
	 * Instantiates a new as400 jdbc client details service.
	 *
	 * @param dataSource the data source
	 */
	public As400JdbcClientDetailsService(DataSource dataSource) {
		Assert.notNull(dataSource, "DataSource required");
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.listFactory = new DefaultJdbcListFactory(new NamedParameterJdbcTemplate(jdbcTemplate));
	}

	/**
	 * Sets the password encoder.
	 *
	 * @param passwordEncoder the new password encoder
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientDetailsService#loadClientByClientId(java.lang.String)
	 */
	public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
		ClientDetails details;
		try {
			details = jdbcTemplate.queryForObject(selectClientDetailsSql, new ClientDetailsRowMapper(), clientId);
		}
		catch (EmptyResultDataAccessException e) {
			throw new NoSuchClientException("No client with requested id: " + clientId);
		}

		return details;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientRegistrationService#addClientDetails(org.springframework.security.oauth2.provider.ClientDetails)
	 */
	public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
		try {
			jdbcTemplate.update(insertClientDetailsSql, getFields(clientDetails));
		}
		catch (DuplicateKeyException e) {
			throw new ClientAlreadyExistsException("Client already exists: " + clientDetails.getClientId(), e);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientRegistrationService#updateClientDetails(org.springframework.security.oauth2.provider.ClientDetails)
	 */
	public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
		int count = jdbcTemplate.update(updateClientDetailsSql, getFieldsForUpdate(clientDetails));
		if (count != 1) {
			throw new NoSuchClientException("No client found with id = " + clientDetails.getClientId());
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientRegistrationService#updateClientSecret(java.lang.String, java.lang.String)
	 */
	public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
		int count = jdbcTemplate.update(updateClientSecretSql, passwordEncoder.encode(secret), clientId);
		if (count != 1) {
			throw new NoSuchClientException("No client found with id = " + clientId);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientRegistrationService#removeClientDetails(java.lang.String)
	 */
	public void removeClientDetails(String clientId) throws NoSuchClientException {
		int count = jdbcTemplate.update(deleteClientDetailsSql, clientId);
		if (count != 1) {
			throw new NoSuchClientException("No client found with id = " + clientId);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientRegistrationService#listClientDetails()
	 */
	public List<ClientDetails> listClientDetails() {
		return listFactory.getList(findClientDetailsSql, Collections.<String, Object> emptyMap(), rowMapper);
	}

	/**
	 * Gets the fields.
	 *
	 * @param clientDetails the client details
	 * @return the fields
	 */
	private Object[] getFields(ClientDetails clientDetails) {
		Object[] fieldsForUpdate = getFieldsForUpdate(clientDetails);
		Object[] fields = new Object[fieldsForUpdate.length + 1];
		System.arraycopy(fieldsForUpdate, 0, fields, 1, fieldsForUpdate.length);
		fields[0] = clientDetails.getClientSecret() != null ? passwordEncoder.encode(clientDetails.getClientSecret())
				: null;
		return fields;
	}

	/**
	 * Gets the fields for update.
	 *
	 * @param clientDetails the client details
	 * @return the fields for update
	 */
	private Object[] getFieldsForUpdate(ClientDetails clientDetails) {
		String json = null;
		try {
			json = mapper.write(clientDetails.getAdditionalInformation());
		}
		catch (Exception e) {
			logger.warn("Could not serialize additional information: " + clientDetails, e);
		}
		return new Object[] {
				clientDetails.getResourceIds() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails
						.getResourceIds()) : null,
				clientDetails.getScope() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails
						.getScope()) : null,
				clientDetails.getAuthorizedGrantTypes() != null ? StringUtils
						.collectionToCommaDelimitedString(clientDetails.getAuthorizedGrantTypes()) : null,
				clientDetails.getRegisteredRedirectUri() != null ? StringUtils
						.collectionToCommaDelimitedString(clientDetails.getRegisteredRedirectUri()) : null,
				clientDetails.getAuthorities() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails
						.getAuthorities()) : null, clientDetails.getAccessTokenValiditySeconds(),
				clientDetails.getRefreshTokenValiditySeconds(), json, getAutoApproveScopes(clientDetails),
				clientDetails.getClientId() };
	}

	/**
	 * Gets the auto approve scopes.
	 *
	 * @param clientDetails the client details
	 * @return the auto approve scopes
	 */
	private String getAutoApproveScopes(ClientDetails clientDetails) {
		if (clientDetails.isAutoApprove("true")) {
			return "true"; // all scopes autoapproved
		}
		Set<String> scopes = new HashSet<String>();
		for (String scope : clientDetails.getScope()) {
			if (clientDetails.isAutoApprove(scope)) {
				scopes.add(scope);
			}
		}
		return StringUtils.collectionToCommaDelimitedString(scopes);
	}

	/**
	 * Sets the select client details sql.
	 *
	 * @param selectClientDetailsSql the new select client details sql
	 */
	public void setSelectClientDetailsSql(String selectClientDetailsSql) {
		this.selectClientDetailsSql = selectClientDetailsSql;
	}

	/**
	 * Sets the delete client details sql.
	 *
	 * @param deleteClientDetailsSql the new delete client details sql
	 */
	public void setDeleteClientDetailsSql(String deleteClientDetailsSql) {
		this.deleteClientDetailsSql = deleteClientDetailsSql;
	}

	/**
	 * Sets the update client details sql.
	 *
	 * @param updateClientDetailsSql the new update client details sql
	 */
	public void setUpdateClientDetailsSql(String updateClientDetailsSql) {
		this.updateClientDetailsSql = updateClientDetailsSql;
	}

	/**
	 * Sets the update client secret sql.
	 *
	 * @param updateClientSecretSql the new update client secret sql
	 */
	public void setUpdateClientSecretSql(String updateClientSecretSql) {
		this.updateClientSecretSql = updateClientSecretSql;
	}

	/**
	 * Sets the insert client details sql.
	 *
	 * @param insertClientDetailsSql the new insert client details sql
	 */
	public void setInsertClientDetailsSql(String insertClientDetailsSql) {
		this.insertClientDetailsSql = insertClientDetailsSql;
	}

	/**
	 * Sets the find client details sql.
	 *
	 * @param findClientDetailsSql the new find client details sql
	 */
	public void setFindClientDetailsSql(String findClientDetailsSql) {
		this.findClientDetailsSql = findClientDetailsSql;
	}

	/**
	 * Sets the list factory.
	 *
	 * @param listFactory the new list factory
	 */
	public void setListFactory(JdbcListFactory listFactory) {
		this.listFactory = listFactory;
	}

	/**
	 * Sets the row mapper.
	 *
	 * @param rowMapper the new row mapper
	 */
	public void setRowMapper(RowMapper<ClientDetails> rowMapper) {
		this.rowMapper = rowMapper;
	}

	/**
	 * Creates the json mapper.
	 *
	 * @return the json mapper
	 */
	private static JsonMapper createJsonMapper() {
		if (ClassUtils.isPresent("org.codehaus.jackson.map.ObjectMapper", null)) {
			return new JacksonMapper();
		}
		else if (ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", null)) {
			return new Jackson2Mapper();
		}
		return new NotSupportedJsonMapper();
	}
	
	/**
	 * The Class ClientDetailsRowMapper.
	 */
	private static class ClientDetailsRowMapper implements RowMapper<ClientDetails> {
		
		/** The mapper. */
		private JsonMapper mapper = createJsonMapper();

		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 */
		public ClientDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			BaseClientDetails details = new BaseClientDetails(rs.getString(1), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(7), rs.getString(6));
			details.setClientSecret(rs.getString(2));
			if (rs.getObject(8) != null) {
				details.setAccessTokenValiditySeconds(rs.getInt(8));
			}
			if (rs.getObject(9) != null) {
				details.setRefreshTokenValiditySeconds(rs.getInt(9));
			}
			String json = rs.getString(10);
			if (json != null) {
				try {
					@SuppressWarnings("unchecked")
					Map<String, Object> additionalInformation = mapper.read(json, Map.class);
					details.setAdditionalInformation(additionalInformation);
				}
				catch (Exception e) {
					logger.warn("Could not decode JSON for additional information: " + details, e);
				}
			}
			String scopes = rs.getString(11);
			if (scopes != null) {
				details.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(scopes));
			}
			return details;
		}
	}

	/**
	 * The Interface JsonMapper.
	 */
	interface JsonMapper {
		
		/**
		 * Write.
		 *
		 * @param input the input
		 * @return the string
		 * @throws Exception the exception
		 */
		String write(Object input) throws Exception;

		/**
		 * Read.
		 *
		 * @param <T> the generic type
		 * @param input the input
		 * @param type the type
		 * @return the t
		 * @throws Exception the exception
		 */
		<T> T read(String input, Class<T> type) throws Exception;
	}

	

	/**
	 * The Class JacksonMapper.
	 */
	private static class JacksonMapper implements JsonMapper {
		
		/** The mapper. */
		private org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();

		/* (non-Javadoc)
		 * @see com.oceanbank.webapp.restoauth.service.As400JdbcClientDetailsService.JsonMapper#write(java.lang.Object)
		 */
		@Override
		public String write(Object input) throws Exception {
			return mapper.writeValueAsString(input);
		}

		/* (non-Javadoc)
		 * @see com.oceanbank.webapp.restoauth.service.As400JdbcClientDetailsService.JsonMapper#read(java.lang.String, java.lang.Class)
		 */
		@Override
		public <T> T read(String input, Class<T> type) throws Exception {
			return mapper.readValue(input, type);
		}
	}

	/**
	 * The Class Jackson2Mapper.
	 */
	private static class Jackson2Mapper implements JsonMapper {
		
		/** The mapper. */
		private com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

		/* (non-Javadoc)
		 * @see com.oceanbank.webapp.restoauth.service.As400JdbcClientDetailsService.JsonMapper#write(java.lang.Object)
		 */
		@Override
		public String write(Object input) throws Exception {
			return mapper.writeValueAsString(input);
		}

		/* (non-Javadoc)
		 * @see com.oceanbank.webapp.restoauth.service.As400JdbcClientDetailsService.JsonMapper#read(java.lang.String, java.lang.Class)
		 */
		@Override
		public <T> T read(String input, Class<T> type) throws Exception {
			return mapper.readValue(input, type);
		}
	}

	/**
	 * The Class NotSupportedJsonMapper.
	 */
	private static class NotSupportedJsonMapper implements JsonMapper {
		
		/* (non-Javadoc)
		 * @see com.oceanbank.webapp.restoauth.service.As400JdbcClientDetailsService.JsonMapper#write(java.lang.Object)
		 */
		@Override
		public String write(Object input) throws Exception {
			throw new UnsupportedOperationException(
					"Neither Jackson 1 nor 2 is available so JSON conversion cannot be done");
		}

		/* (non-Javadoc)
		 * @see com.oceanbank.webapp.restoauth.service.As400JdbcClientDetailsService.JsonMapper#read(java.lang.String, java.lang.Class)
		 */
		@Override
		public <T> T read(String input, Class<T> type) throws Exception {
			throw new UnsupportedOperationException(
					"Neither Jackson 1 nor 2 is available so JSON conversion cannot be done");
		}
	}
}
