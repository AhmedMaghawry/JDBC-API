package eg.edu.alexu.csd.oop.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;

import classes.SqlParser.sqlFactoryImp;

public class JavaStatement implements java.sql.Statement {

    private ArrayList<String> commands;
    private sqlFactoryImp statment;
    private boolean isClosed;
    private JavaResultset result;
    private int lastResult;
    private JavaConnection father;
    private ArrayList<ArrayList<String>> receiver;
    private String path;
    private Logger history;

    public JavaStatement(final JavaConnection father, String path, String formatType, Logger history) {
        // TODO Auto-generated constructor stub
        commands = new ArrayList<>();
        statment = new sqlFactoryImp(path, formatType);
        isClosed = false;
        this.father = father;
        lastResult = -1;
        this.path = path;
        this.history = history;
    }

    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public void addBatch(final String arg0) throws SQLException { // ========
        // TODO Auto-generated method stub

        if (!isClosed) {
            if (keyFound(arg0, "insert") || keyFound(arg0, "update")) {
                commands.add(arg0.toLowerCase());
                history.info("Query added successfully");
                return;
            }
        }
        history.error("Not supported query for this function");
        throw new SQLException("Statement channel is closed");

    }

    @Override
    public void cancel() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");

    }

    @Override
    public void clearBatch() throws SQLException { // ========
        // TODO Auto-generated method stub
        if (!isClosed) {
            commands.clear();
            history.info("Clear batch is done successfully");
        } else {
            history.error("Clear batch can't be done successfully");
            throw new SQLException("Statement channel is closed");
        }

    }

    @Override
    public void clearWarnings() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");

    }

    @Override
    public void close() throws SQLException { // ========
        // TODO Auto-generated method stub
        commands = null;
        statment = null;
        isClosed = true;
        lastResult = -1;
        history.info("Statement is closed");

    }

    @Override
    public void closeOnCompletion() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");

    }

    @Override
    // any thing
    public boolean execute(final String arg0) throws SQLException { // ========
        // TODO Auto-generated method stub
        lastResult = -1;
        if (!isClosed) {
            try {
                statment.execute(arg0.toLowerCase());
            } catch (Exception e) {
                history.error("Execution isn't done");
                throw new SQLException("Herrrrrrrrrrrrrre");
            }
            if (keyFound(arg0, "select")) {
                try {
                    if (statment.getTable().getFromTable(0) != null) {
                        result = new JavaResultset(statment.getTable(), this, history);
                        history.info("Execution is done");
                        return true;
                    }
                } catch (Exception e) {
                    // remove
                    return false;
                }
            }
            history.info("Execute only support Select");
            return false;
        }
        history.error("Action denied statemnt is closed");
        throw new SQLException();
    }

    @Override
    public boolean execute(final String arg0, final int arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public boolean execute(final String arg0, final int[] arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public boolean execute(final String arg0, final String[] arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public int[] executeBatch() throws SQLException { // ========
        // TODO Auto-generated method stub
        if (!isClosed) {
            int[] changes = new int[commands.size()];
            for (int i = 0; i < changes.length; i++) {
                changes[i] = executeUpdate(commands.get(i));
            }
            lastResult = -1;
            history.info("Batches are done");
            return changes;
        } else {
            history.error("Statement is Closed");
            throw new SQLException("Statement channel is closed");
        }
    }

    @Override
    // Select only
    public JavaResultset executeQuery(final String arg0) throws SQLException { // ========
        // TODO Auto-generated method stub
        if (!isClosed) {
            if (keyFound(arg0, "select")) {
                try {
                    receiver = statment.execute(arg0.toLowerCase());
                } catch (Exception e) {
                    history.error("Error in the input of execution");
                    throw new SQLException("error in execution query");
                }
                if (receiver != null) {
                    lastResult = -1;
                    try {
                        result = new JavaResultset(statment.getTable(), this, history);
                    } catch (Exception e) {
                        history.error("Error in Fetching results ");
                        throw new SQLException("error in getting result from execute query");
                    }
                    return result;
                }
            }
        }
        throw new SQLException();
    }

    @Override
    // Not select
    public int executeUpdate(final String arg0) throws SQLException { // ========
        // TODO Auto-generated method stub
        if (!isClosed) {
            if (keyFound(arg0, "update") || keyFound(arg0, "delete") || keyFound(arg0, "insert")) {
                try {
                    statment.execute(arg0.toLowerCase());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    history.error("Error in execution");
                    throw new SQLException(e.getMessage());
                }
                try {
                    lastResult = statment.getChangedSize();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    history.error("Error in calculating result");
                    throw new SQLException("Error in getting result in execute update");
                }
                history.info("Update is done successfully");
                return lastResult;
            }
        }
        history.error("Statment is Closed");
        throw new SQLException("Statement channel is closed");
    }

    @Override
    public int executeUpdate(final String arg0, final int arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public int executeUpdate(final String arg0, final int[] arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public int executeUpdate(final String arg0, final String[] arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public JavaConnection getConnection() throws SQLException { // ========
        // TODO Auto-generated method stub
        return father;
    }

    @Override
    public int getFetchDirection() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public int getFetchSize() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public int getMaxRows() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public boolean getMoreResults(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public int getQueryTimeout() throws SQLException { // ========
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return result;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public int getResultSetType() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return lastResult;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public boolean isClosed() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public boolean isPoolable() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");

    }

    @Override
    public void setCursorName(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");

    }

    @Override
    public void setEscapeProcessing(final boolean arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");

    }

    @Override
    public void setFetchDirection(final int arg0) throws SQLException {
        // TODO Auto-generated method stub
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");

    }

    @Override
    public void setFetchSize(final int arg0) throws SQLException {
        // TODO Auto-generated method stub
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");

    }

    @Override
    public void setMaxFieldSize(final int arg0) throws SQLException {
        // TODO Auto-generated method stub
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");

    }

    @Override
    public void setMaxRows(final int arg0) throws SQLException {
        // TODO Auto-generated method stub
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");

    }

    @Override
    public void setPoolable(final boolean arg0) throws SQLException {
        // TODO Auto-generated method stub
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");

    }

    @Override
    public void setQueryTimeout(final int arg0) throws SQLException { // ========
        // TODO Auto-generated method stub
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");

    }

    private boolean keyFound(String input, String key) {
        Pattern sele = Pattern.compile("(?i)(?:" + key + ")");
        Matcher matcher = sele.matcher(input.trim().replaceAll(" +", " "));
        if (matcher.find()) {
            return true;
        }
        return false;
    }

}