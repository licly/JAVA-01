package org.charlie.config;


import javax.sql.DataSource;
import java.util.Map;

/**
 * TODO 使用ShardingJDBC实现读写分离，待完善，暂时没弄明白
 *
 * @author Charlie
 * @date 2021/3/6
 */

// @ConfigurationProperties(prefix = "sharding")
public class ShardingJdbcDataSourceRegistry {

    private Map<String, DataSource> dataSourceMap;

    // public DataSource getDataSource() throws SQLException {
        // ShardingAutoTableRuleConfiguration orderTableRuleConfig = new ShardingAutoTableRuleConfiguration("order", "{master, slave}.order");
        //
        // ReplicaQueryDataSourceRuleConfiguration replicaQueryDataSourceRuleConfiguration = new ReplicaQueryDataSourceRuleConfiguration(null, "master", Collections.singletonList("master"), "RANDOM");

        // ReplicaQueryRuleConfiguration ruleConfig = new ReplicaQueryRuleConfiguration(Collections.singletonList(replicaQueryDataSourceRuleConfiguration), new ShardingSphereAlgorithmConfiguration("INLINE", null));

        // DataSource dataSource = ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, Collections.singleton(ruleConfig), null);

    //     return dataSource;
    // }

    public void setDataSourceMap(Map<String, DataSource> dataSourceMap) {
        this.dataSourceMap = dataSourceMap;
    }

}
