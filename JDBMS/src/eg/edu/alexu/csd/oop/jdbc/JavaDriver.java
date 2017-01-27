package eg.edu.alexu.csd.oop.jdbc;

import java.io.File;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Properties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JavaDriver implements java.sql.Driver {

    private ArrayList<Connection> currentConections;
    private ArrayList<String> urls;
    private String formatType;
    private  Logger history =  LogManager.getLogger();

    private enum Format {
        XML, JSON
    };

    public JavaDriver() {
        // TODO Auto-generated constructor stub
        currentConections = new ArrayList<>();
        urls = new ArrayList<>();
        history.info("A JDBC has been created");

    }

    @Override
    public boolean acceptsURL(final String url) throws SQLException { // ========
        Pattern check = Pattern.compile("\\s*jdbc:\\s*((?:xmldb|altdb))\\s*://localhost");
        Matcher mould = check.matcher(url);
        if (mould.find()) {
            if (mould.group(1).equals("xmldb")) {
                formatType = Format.XML.toString();
            } else {
                formatType = Format.JSON.toString();
            }
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Connection connect(final String url, final Properties info) throws SQLException { // ========
        // TODO Auto-generated method stub
        urls.add(url);
        File f = (File) info.get("path");
        if (f.exists()){
            history.error("This name is taken by another file");
            throw new SQLException("file Exits");
        }
        acceptsURL(url);
        f.mkdir();
        Connection conncection = new JavaConnection(f.getAbsolutePath(),formatType,history);
        currentConections.add(conncection);
        return conncection;
    }

    @Override
    public int getMajorVersion() {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");
    }

    @Override
    public int getMinorVersion() {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");
    }

    @Override
    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(final String url, final Properties info) throws SQLException { // ========
        // TODO Auto-generated method stub
        DriverPropertyInfo[] driverInfo = new DriverPropertyInfo[info.size()];
        if (!urls.isEmpty()) {
            if (!urls.get(urls.size() - 1).equals(url)) {
                urls.add(url);
            }
        } else {
            urls.add(url);
        }
        int x = 0;
        for (Object item : info.keySet()) {
            driverInfo[x].name = item.toString();
            driverInfo[x].value = info.get(item).toString();
            x++;
        }
        return driverInfo;
    }

    @Override
    public boolean jdbcCompliant() {
        throw new java.lang.UnsupportedOperationException("Unsupported Operation attempted");
    }

}