/*
 * Knowage, Open Source Business Intelligence suite
 * Copyright (C) 2016 Engineering Ingegneria Informatica S.p.A.
 *
 * Knowage is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Knowage is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.eng.spagobi.utilities.database;

import it.eng.spagobi.tools.datasource.bo.IDataSource;

/**
 * VoltDB implementation
 *
 * @author Alessandro Portosa (alessandro.portosa@eng.it)
 *
 */
import org.apache.log4j.Logger;

public class VoltDBDataBase extends AbstractDataBase {

	private static transient Logger logger = Logger.getLogger(VoltDBDataBase.class);

	public VoltDBDataBase(IDataSource dataSource) {
		super(dataSource);
	}

	@Override
	public String getDataBaseType(Class javaType) {
		String toReturn = null;
		String javaTypeName = javaType.toString();
		if (javaTypeName.contains("java.lang.String")) {
			toReturn = " VARCHAR (" + getVarcharLength() + ")";
		} else if (javaTypeName.contains("java.lang.Byte")) {
			toReturn = " INTEGER ";
		} else if (javaTypeName.contains("java.lang.Short")) {
			toReturn = " SMALLINT ";
		} else if (javaTypeName.contains("java.lang.Integer")) {
			toReturn = " INTEGER ";
		} else if (javaTypeName.contains("java.lang.Long")) {
			toReturn = " BIGINT ";
		} else if (javaTypeName.contains("java.lang.BigDecimal") || javaTypeName.contains("java.math.BigDecimal")) {
			toReturn = " DOUBLE ";
		} else if (javaTypeName.contains("java.lang.Double")) {
			toReturn = " DOUBLE ";
		} else if (javaTypeName.contains("java.lang.Float")) {
			toReturn = " DOUBLE ";
		} else if (javaTypeName.contains("java.lang.Boolean")) {
			toReturn = " BOOLEAN ";
		} else if (javaTypeName.contains("java.sql.Date")) {
			toReturn = " TIMESTAMP ";
		} else if (javaTypeName.toLowerCase().contains("timestamp")) {
			toReturn = " TIMESTAMP ";
		} else if (javaTypeName.contains("[B") || javaTypeName.contains("BLOB")) {
			toReturn = " VARBINARY ";
		} else if (javaTypeName.contains("[C") || javaTypeName.contains("CLOB")) {
			toReturn = " TEXT ";
		} else {
			logger.debug("Cannot map java type [" + javaTypeName + "] to a valid database type ");
		}

		return toReturn;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see it.eng.spagobi.utilities.database.IDataBase#getAliasDelimiter()
	 */
	@Override
	public String getAliasDelimiter() {
		return "";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see it.eng.spagobi.utilities.database.AbstractDataBase#getUsedMemorySizeQuery(java.lang.String, java.lang.String)
	 */
	@Override
	public String getUsedMemorySizeQuery(String schema, String tableNamePrefix) {
		logger.debug("Cannot find this information in VoltDB using standard query. Need to call @Statistics(Memory) stored procedure.");
		String query = "Not supported from standard SQL statement.";
		return query;
	}

	// public static void callStoredProcedure(String procedureName, String sql, IDataSource dataSource) throws Exception {
	// logger.debug("IN");
	// Connection connection = null;
	// try {
	// connection = dataSource.getConnection();
	// connection.setAutoCommit(false);
	// CallableStatement proc = connection.prepareCall("{call " + procedureName + "(?)}");
	// proc.setString(1, sql);
	// logger.debug("Executing sql " + sql + "using procedure " + procedureName);
	// proc.execute(sql);
	// connection.commit();
	// logger.debug("Sql " + sql + " using procedure " + procedureName + " executed successfully");
	// } catch (Exception e) {
	// if (connection != null) {
	// connection.rollback();
	// }
	// throw e;
	// } finally {
	// if (connection != null && !connection.isClosed()) {
	// connection.close();
	// }
	// logger.debug("OUT");
	// }
	// }
}