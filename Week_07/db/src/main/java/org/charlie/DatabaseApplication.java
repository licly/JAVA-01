package org.charlie;

import org.charlie.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/3/6
 */

@SpringBootApplication
@ConfigurationPropertiesScan
public class DatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseApplication.class, args);
    }

    /**
     * 测试自定义读写分离
     * @param dbService
     */
    @Autowired
    public void testSimpleReadWriteSplit(DbService dbService) {
        try {
            dbService.execute("SELECT * FROM `order` LIMIT 10").forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
