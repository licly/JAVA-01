package org.charlie.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.*;

/**
 * 简单自定义读写分离数据源
 *
 * @author Charlie
 * @date 2021/3/6
 */
@ConfigurationProperties(prefix = "simple")
public class SimpleDataSourceRegistry {


    private Map<String, List<MysqlDataSource>> dataSources;

    /**
     * 根据SQL类型返回相应数据源
     * @param sql 要执行的SQL语句
     * @return 数据源
     */
    public MysqlDataSource getDataSource(String sql) {
        sql = sql.trim();
        boolean isSelect = sql.startsWith("SELECT") || sql.startsWith("select");
        if (isSelect) {
            return loadBalance(dataSources.get("slave"));
        } else {
            return loadBalance(dataSources.get("master"));
        }
    }

    public void setDataSources(Map<String, List<MysqlDataSource>> dataSources) {
        this.dataSources = dataSources;
    }

    /**
     * 负载均衡
     */
    public MysqlDataSource loadBalance(List<MysqlDataSource> dataSources) {
        // 采用随机负载均衡策略
        Random random = new Random();
        return dataSources.get(random.nextInt(dataSources.size()));
    }
}
