package main;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import eg.edu.alexu.csd.oop.jdbc.JavaDriver;

public class JDBCMain {

    public static void main(String[] args) throws SQLException {
        // TODO Auto-generated method stub

        // load Configurations

        Scanner input = new Scanner(System.in);
        Driver driver = new JavaDriver();
        while (true) {
            System.out.println("Enter URL :");
            String url = input.nextLine();
            //String url ="jdbc:" + "xmldb" + "://localhost";
            if (driver.acceptsURL(url)) {
                System.out.println("Enter Path:");
                String path = input.nextLine();
                //String path = "C:\\Users\\Ahmed Maghawry\\AppData\\Local\\Temp\\Dodo";
                Properties info = new Properties();
                File dbDir = new File(path);
                info.put("path", dbDir.getAbsoluteFile());
                Connection connection = driver.connect(url, info);
                Statement statment = connection.createStatement();
                while (true) {
                    try {
                        System.out.print("sql>");
                        String tmp = input.nextLine();
                        statment.execute(tmp);
                    } catch (Exception e) {
                        System.out.println("Error");
                    }
                }

            }
        }
    }
}
