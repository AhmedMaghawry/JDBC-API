package eg.edu.alexu.csd.oop.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import classes.TabelImp;
import interfaces.IsDate;
import interfaces.IsFloat;
import interfaces.IsInteger;
import interfaces.IsString;

public class JavaResultset implements java.sql.ResultSet {

    private JavaResultsetMetadata meta;
    private TabelImp resultSet;
    private int rowIndex;
    private boolean isClosed;
    private JavaStatement father;
    private Logger history;

    public JavaResultset(final TabelImp result, JavaStatement father,Logger history) {
        // TODO Auto-generated constructor stub
        rowIndex = 0;
        isClosed = false;
        this.resultSet = result;
        this.father = father;
        meta = new JavaResultsetMetadata(result);
        this.history= history;

    }

    private void Checker() throws SQLException {
        if (isClosed) {
            throw new SQLException("ResultSet channel is closed");
        }
    }

    private boolean isValidRow() {
        if (rowIndex < resultSet.getNumberOfRows() && rowIndex > -1) {
            return true;
        } else {
            return false;
        }
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
    public boolean absolute(final int row) throws SQLException { // ========
        this.Checker();
        int tmp = -1;
        if (row > 0 && row <= resultSet.getNumberOfRows()) {
            tmp = row - 1;

        } else if (row < 0 && (Math.abs(row)) <= resultSet.getNumberOfRows()) {
            tmp = resultSet.getNumberOfRows() + row;
        }
        if(tmp >= 0 && tmp < resultSet.getNumberOfRows()){
            rowIndex = tmp;
            return true;
        }
        return false;
    }

    @Override
    public void afterLast() throws SQLException { // ========
        this.Checker();
        rowIndex = resultSet.getNumberOfRows();

    }

    @Override
    public void beforeFirst() throws SQLException { // ========
        this.Checker();
        if (resultSet.getNumberOfRows() != 0) {
            rowIndex = -1;
        }
    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");

    }

    @Override
    public void clearWarnings() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");

    }

    @Override
    public void close() throws SQLException { // ========
        rowIndex = 0;
        resultSet = null;
        isClosed = true;
        history.info("Result set is closed");

    }

    @Override
    public void deleteRow() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");

    }

    @Override
    public int findColumn(final String columnLabel) throws SQLException { // ========
        int columnIndex = 0;
        this.Checker();
        if (!resultSet.isIn(columnLabel)) {
        	history.info("Entered name doesn`t match" + "any of the columns");
            throw new SQLException("Entered name doesn`t match" + "any of the columns");
        }
        columnIndex = resultSet.getIndex(columnLabel);
        return columnIndex;
    }

    @Override
    public boolean first() throws SQLException { // ========
        this.Checker();
        if (resultSet.getNumberOfRows() != 0) {
            rowIndex = 0;
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Array getArray(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Array getArray(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public InputStream getAsciiStream(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public InputStream getAsciiStream(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public BigDecimal getBigDecimal(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public BigDecimal getBigDecimal(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public BigDecimal getBigDecimal(final int arg0, final int arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public BigDecimal getBigDecimal(final String arg0, final int arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public InputStream getBinaryStream(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public InputStream getBinaryStream(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Blob getBlob(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Blob getBlob(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public boolean getBoolean(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public boolean getBoolean(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public byte getByte(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public byte getByte(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public byte[] getBytes(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public byte[] getBytes(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Reader getCharacterStream(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Reader getCharacterStream(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Clob getClob(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Clob getClob(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public int getConcurrency() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public String getCursorName() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Date getDate(final int columnIndex) throws SQLException { // ========
        this.Checker();
        if (columnIndex-1 < resultSet.GetTableSize() && this.isValidRow()
                && (resultSet.getFromTable(columnIndex-1) instanceof IsDate
                || (resultSet.getFromTable(columnIndex-1).getColumn().get(0)).equals("null"))) {
            Object temp = resultSet.getFromTable(columnIndex-1).getColumn().get(rowIndex);
            if (temp.equals("null")) {
                return null;
            } else {
                return (Date) temp;
            }
        } else {
        	history.error("The Date attempted is invalid");
            throw new SQLException("The Date attempted is invalid");
        }

    }

    @Override
    public Date getDate(final String columnLabel) throws SQLException { // ========
        this.Checker();
        int columnIndex;
        if (resultSet.isIn(columnLabel)) {
            columnIndex = resultSet.getIndex(columnLabel);
        } else {
        	history.error("The table has no such column Label\t (Date)");
            throw new SQLException("The table has no such column Label\t (Date)");
        }
        return this.getDate(columnIndex+1);

    }

    @Override
    public Date getDate(final int arg0, final Calendar arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Date getDate(final String arg0, final Calendar arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public double getDouble(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public double getDouble(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
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
    public float getFloat(final int columnIndex) throws SQLException { // ========
        this.Checker();
        if (columnIndex-1 < resultSet.GetTableSize() && this.isValidRow()
                && resultSet.getFromTable(columnIndex-1) instanceof IsFloat) {
            Object temp = resultSet.getFromTable(columnIndex-1).getColumn().get(rowIndex);
            if (temp == null) {
                return 0;
            } else {
                return (Float) temp;
            }
        } else {
            throw new SQLException("The Float attempted is invalid");
        }

    }

    @Override
    public float getFloat(final String columnLabel) throws SQLException { // ========
        this.Checker();
        int columnIndex;
        if (resultSet.isIn(columnLabel)) {
            columnIndex = resultSet.getIndex(columnLabel);
        } else {
        	history.error("The Float attempted is invalid");
            throw new SQLException("The table has no such column Label\t (Float)");
        }
        return this.getFloat(columnIndex+1);

    }

    @Override
    public int getHoldability() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public int getInt(final int columnIndex) throws SQLException { // ========
        this.Checker();
        if (columnIndex-1 < resultSet.GetTableSize() && this.isValidRow()
                && resultSet.getFromTable(columnIndex-1) instanceof IsInteger) {
            Object temp = resultSet.getFromTable(columnIndex-1).getColumn().get(rowIndex);
            if (temp == null) {
                return 0;
            } else {
                return (Integer) temp;
            }
        } else {
        	history.error("The table has no such column Label\t (Integer)");
            throw new SQLException("The Integer attempted is invalid");
        }

    }

    @Override
    public int getInt(final String columnLabel) throws SQLException { // ========
        this.Checker();
        int columnIndex;
        if (resultSet.isIn(columnLabel)) {
            columnIndex = resultSet.getIndex(columnLabel);
        } else {
            throw new SQLException("The table has no such column Label\t (Integer)");
        }
        return this.getInt(columnIndex+1);

    }

    @Override
    public long getLong(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public long getLong(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException { // ========
        this.Checker();
        return meta;
    }

    @Override
    public Reader getNCharacterStream(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Reader getNCharacterStream(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public NClob getNClob(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public NClob getNClob(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public String getNString(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public String getNString(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Object getObject(final int columnIndex) throws SQLException { // ========
        this.Checker();
        if (columnIndex-1 < resultSet.GetTableSize() && this.isValidRow()) {
            Object temp = resultSet.getFromTable(columnIndex-1).getColumn().get(rowIndex);
            if (temp == null) {
                return null;
            } else {
                return temp;
            }
        } else {
        	history.error("The Object attempted is invalid");
            throw new SQLException("The Object attempted is invalid");
        }

    }

    @Override
    public Object getObject(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Object getObject(final int arg0, final Map<String, Class<?>> arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Object getObject(final String arg0, final Map<String, Class<?>> arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public <T> T getObject(final int arg0, final Class<T> arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public <T> T getObject(final String arg0, final Class<T> arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Ref getRef(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Ref getRef(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public int getRow() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public RowId getRowId(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public RowId getRowId(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public SQLXML getSQLXML(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public SQLXML getSQLXML(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public short getShort(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public short getShort(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Statement getStatement() throws SQLException { // ========
        this.Checker();
        return father;

    }

    @Override
    public String getString(final int columnIndex) throws SQLException { // ========
        this.Checker();
        if (columnIndex-1 < resultSet.GetTableSize() && this.isValidRow()
                && resultSet.getFromTable(columnIndex-1) instanceof IsString) {
            Object temp = resultSet.getFromTable(columnIndex-1).getColumn().get(rowIndex);
            if (temp == null) {
                return null;
            } else {
                return (String) temp;
            }
        } else {
        	history.error("The String attempted is invalid");
            throw new SQLException("The String attempted is invalid");
        }

    }

    @Override
    public String getString(final String columnLabel) throws SQLException { // ========
        this.Checker();
        int columnIndex;
        if (resultSet.isIn(columnLabel)) {
            columnIndex = resultSet.getIndex(columnLabel);
        } else {
        	history.error("The table has no such column Label\t (String)");
            throw new SQLException("The table has no such column Label\t (String)");
        }
        return this.getString(columnIndex+1);

    }

    @Override
    public Time getTime(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Time getTime(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Time getTime(final int arg0, final Calendar arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Time getTime(final String arg0, final Calendar arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Timestamp getTimestamp(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Timestamp getTimestamp(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Timestamp getTimestamp(final int arg0, final Calendar arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public Timestamp getTimestamp(final String arg0, final Calendar arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public int getType() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public URL getURL(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public URL getURL(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public InputStream getUnicodeStream(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported" + " Operation attempted");
    }

    @Override
    public InputStream getUnicodeStream(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void insertRow() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public boolean isAfterLast() throws SQLException { // ========
        this.Checker();
        if (resultSet.getNumberOfRows() != 0 && rowIndex == resultSet.getNumberOfRows()) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean isBeforeFirst() throws SQLException { // ========
        this.Checker();
        if (resultSet.getNumberOfRows() != 0 && rowIndex == -1) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean isClosed() throws SQLException { // ========

        return isClosed;

    }

    @Override
    public boolean isFirst() throws SQLException { // ========
        this.Checker();
        if (resultSet.getNumberOfRows() != 0 && rowIndex == 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean isLast() throws SQLException { // ========
        this.Checker();
        if (resultSet.getNumberOfRows() != 0 && rowIndex == resultSet.getNumberOfRows() - 1) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean last() throws SQLException { // ========
        this.Checker();
        if (resultSet.getNumberOfRows() != 0) {
            rowIndex = resultSet.getNumberOfRows() - 1;
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void moveToCurrentRow() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void moveToInsertRow() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public boolean next() throws SQLException { // ========
        this.Checker();
        if (resultSet.getNumberOfRows() > rowIndex) {
            rowIndex++;
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean previous() throws SQLException { // ========
        this.Checker();
        if (0 <= rowIndex && resultSet.getNumberOfRows() != 0/* && this.isValidRow()*/) {
            rowIndex--;
            return true;
        }
        return false;

    }

    @Override
    public void refreshRow() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public boolean relative(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public boolean rowDeleted() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public boolean rowInserted() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public boolean rowUpdated() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void setFetchDirection(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void setFetchSize(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateArray(final int arg0, final Array arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateArray(final String arg0, final Array arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateAsciiStream(final int arg0, final InputStream arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateAsciiStream(final String arg0, final InputStream arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateAsciiStream(final int arg0, final InputStream arg1, final int arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateAsciiStream(final String arg0, final InputStream arg1, final int arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateAsciiStream(final int arg0, final InputStream arg1, final long arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateAsciiStream(final String arg0, final InputStream arg1, final long arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBigDecimal(final int arg0, final BigDecimal arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBigDecimal(final String arg0, final BigDecimal arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBinaryStream(final int arg0, final InputStream arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBinaryStream(final String arg0, final InputStream arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBinaryStream(final int arg0, final InputStream arg1, final int arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBinaryStream(final String arg0, final InputStream arg1, final int arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBinaryStream(final int arg0, final InputStream arg1, final long arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBinaryStream(final String arg0, final InputStream arg1, final long arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBlob(final int arg0, final Blob arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBlob(final String arg0, final Blob arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBlob(final int arg0, final InputStream arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBlob(final String arg0, final InputStream arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBlob(final int arg0, final InputStream arg1, final long arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBlob(final String arg0, final InputStream arg1, final long arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBoolean(final int arg0, final boolean arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBoolean(final String arg0, final boolean arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateByte(final int arg0, final byte arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateByte(final String arg0, final byte arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBytes(final int arg0, final byte[] arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateBytes(final String arg0, final byte[] arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateCharacterStream(final int arg0, final Reader arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateCharacterStream(final String arg0, final Reader arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateCharacterStream(final int arg0, final Reader arg1, final int arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateCharacterStream(final String arg0, final Reader arg1, final int arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateCharacterStream(final int arg0, final Reader arg1, final long arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateCharacterStream(final String arg0, final Reader arg1, final long arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateClob(final int arg0, final Clob arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateClob(final String arg0, final Clob arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateClob(final int arg0, final Reader arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateClob(final String arg0, final Reader arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateClob(final int arg0, final Reader arg1, final long arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateClob(final String arg0, final Reader arg1, final long arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateDate(final int arg0, final Date arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateDate(final String arg0, final Date arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateDouble(final int arg0, final double arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateDouble(final String arg0, final double arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateFloat(final int arg0, final float arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateFloat(final String arg0, final float arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateInt(final int arg0, final int arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateInt(final String arg0, final int arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateLong(final int arg0, final long arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateLong(final String arg0, final long arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateNCharacterStream(final int arg0, final Reader arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateNCharacterStream(final String arg0, final Reader arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateNCharacterStream(final int arg0, final Reader arg1, final long arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateNCharacterStream(final String arg0, final Reader arg1, final long arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateNClob(final int arg0, final NClob arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateNClob(final String arg0, final NClob arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateNClob(final int arg0, final Reader arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateNClob(final String arg0, final Reader arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateNClob(final int arg0, final Reader arg1, final long arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateNClob(final String arg0, final Reader arg1, final long arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateNString(final int arg0, final String arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateNString(final String arg0, final String arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateNull(final int arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateNull(final String arg0) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateObject(final int arg0, final Object arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateObject(final String arg0, final Object arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateObject(final int arg0, final Object arg1, final int arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateObject(final String arg0, final Object arg1, final int arg2) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateRef(final int arg0, final Ref arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateRef(final String arg0, final Ref arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateRow() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateRowId(final int arg0, final RowId arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateRowId(final String arg0, final RowId arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateSQLXML(final int arg0, final SQLXML arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateSQLXML(final String arg0, final SQLXML arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateShort(final int arg0, final short arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateShort(final String arg0, final short arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateString(final int arg0, final String arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateString(final String arg0, final String arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateTime(final int arg0, final Time arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateTime(final String arg0, final Time arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateTimestamp(final int arg0, final Timestamp arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public void updateTimestamp(final String arg0, final Timestamp arg1) throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

    @Override
    public boolean wasNull() throws SQLException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");

    }

}