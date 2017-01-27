package eg.edu.alexu.csd.oop.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import java.util.Properties;
import java.util.concurrent.Executor;


public class JavaConnection implements java.sql.Connection {

    private boolean closed;
    private String path;
    private String formatType;
    private  Logger history ;

    public JavaConnection(final String path, String formatType, Logger history) {
        // TODO Auto-generated constructor stub
        closed = false;
        this.path = path;
        this.formatType = formatType;
        this.history = history;
        history.info("Connection created successfully");
    }

    @Override
    public boolean isWrapperFor(final Class<?> arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public <T> T unwrap(final Class<T> arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void abort(final Executor arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void clearWarnings() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void close() throws SQLException { // ========

        closed = true;
        history.info("Connection closed sucessfully");

    }

    @Override
    public void commit() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public Array createArrayOf(final String arg0, final Object[] arg1) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public Blob createBlob() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public Clob createClob() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public NClob createNClob() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public JavaStatement createStatement() throws SQLException { // ========

        if (closed){
             history.error("No statement allowed because the connection is closed");
             throw new SQLException();
        }
        return new JavaStatement(this , path, formatType,history);
    }

    @Override
    public Statement createStatement(final int arg0, final int arg1) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public Statement createStatement(final int arg0, final int arg1, final int arg2) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");


    }

    @Override
    public Struct createStruct(final String arg0, final Object[] arg1) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public boolean getAutoCommit() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public String getCatalog() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public Properties getClientInfo() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public String getClientInfo(final String arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public int getHoldability() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public int getNetworkTimeout() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public String getSchema() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public int getTransactionIsolation() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public boolean isClosed() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public boolean isReadOnly() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public boolean isValid(final int arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public String nativeSQL(final String arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public CallableStatement prepareCall(final String arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public CallableStatement prepareCall(final String arg0, final int arg1, final int arg2) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public CallableStatement prepareCall(final String arg0, final int arg1, final int arg2, final int arg3)
            throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public PreparedStatement prepareStatement(final String arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public PreparedStatement prepareStatement(final String arg0, final int arg1) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public PreparedStatement prepareStatement(final String arg0, final int[] arg1) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public PreparedStatement prepareStatement(final String arg0, final String[] arg1) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public PreparedStatement prepareStatement(final String arg0, final int arg1, final int arg2) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public PreparedStatement prepareStatement(final String arg0, final int arg1, final int arg2, final int arg3)
            throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void releaseSavepoint(final Savepoint arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void rollback() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void rollback(final Savepoint arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void setAutoCommit(final boolean arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void setCatalog(final String arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void setClientInfo(final Properties arg0) throws SQLClientInfoException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void setClientInfo(final String arg0, final String arg1) throws SQLClientInfoException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void setHoldability(final int arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void setNetworkTimeout(final Executor arg0, final int arg1) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void setReadOnly(final boolean arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public Savepoint setSavepoint(final String arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void setSchema(final String arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void setTransactionIsolation(final int arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void setTypeMap(final Map<String, Class<?>> arg0) throws SQLException {
       throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

}
