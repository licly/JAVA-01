package org.charlie.sharding;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 创建表
 *
 * @author Charlie
 * @date 2021/3/12
 */
public class TableCreation {

    static final String DB1_URL = "jdbc:mysql://localhost:3306/java0?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
    static final String DB2_URL = "jdbc:mysql://localhost:3306/java1?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
    static final String USER = "root";
    static final String PWD = "root";

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection db1Connection = DriverManager.getConnection(DB1_URL, USER, PWD);
        Connection db2Connection = DriverManager.getConnection(DB2_URL, USER, PWD);

        Statement db1Statement = db1Connection.createStatement();
        Statement db2Statement = db2Connection.createStatement();

        List<String> list = Files.readAllLines(Paths.get(new ClassPathResource("order_ddl.sql").getURI()));
        String sql = String.join(" ", list);

        for (int i = 0; i < 16; i++) {
            db1Statement.execute(String.format(sql, i));
            db2Statement.execute(String.format(sql, i));
        }

        db1Connection.close();
        db2Connection.close();
    }
}
