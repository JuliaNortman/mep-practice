package ua.knu.ynortman.key.utils;

import lombok.extern.slf4j.Slf4j;
import ua.knu.ynortman.utils.PropertyUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

@Slf4j
public class FileUtils {
    public static void txtToCsv(String txtFilename, String csvFilename) {
        Scanner sc = null;
        try {
            ClassLoader classLoader = FileUtils.class.getClassLoader();
            sc = new Scanner(new File(classLoader.getResource(txtFilename).getFile()));
            FileWriter csvwriter = new FileWriter(csvFilename, true);
            log.info("Start writing content of file {} to file {}", txtFilename, csvFilename);
            long start = System.currentTimeMillis();
            while(sc.hasNextLine()) {
                String str = sc.nextLine();
                Scanner lineScanner = new Scanner(str);
                lineScanner.useDelimiter("\\s+");
                while (lineScanner.hasNext()) {
                    String prime = lineScanner.next();
                    csvwriter.append(prime).append("\n");
                }
                lineScanner.close();

            }
            csvwriter.close();
            log.info("End writing content of file {} to file {}", txtFilename, csvFilename);
            log.info("Operation took {} ms", System.currentTimeMillis()-start);
        } catch (IOException exp) {
            log.error(exp.getMessage());
            exp.printStackTrace();
        }finally{
            if(sc != null)
                sc.close();
        }
    }

    public static void testCsvContent(String csvFilename) {
        Scanner sc = null;
        int count = 0;
        String str = "";
        try {
            sc = new Scanner(new File(csvFilename));
            while(sc.hasNextLine()) {
                str = sc.nextLine();
                count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(sc != null) {
                sc.close();
            }
        }
        log.info("Total row num is {}, the last record is: {}", count, str);

    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            String url = PropertyUtils.getProperty("db_url");
            String user = PropertyUtils.getProperty("db_username");
            String password = PropertyUtils.getProperty("db_password");
            conn = DriverManager.getConnection(url, user, password);
            log.debug("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return conn;
    }

    public static void presetDB() throws SQLException {
        Connection conn = getConnection();
        String sql = "COPY primes FROM 'primes.csv' WITH (FORMAT csv);";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.executeUpdate();
    }
}
