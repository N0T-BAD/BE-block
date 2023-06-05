package com.blockpage.blockservice.adaptor.infrastructure.mysql.configuration;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class DataSourceConfig {

    private static final String MASTER_DATASOURCE = "masterDataSource";
    private static final String SLAVE_DATASOURCE = "slaveDataSource";

    @Bean(MASTER_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .build();
    }

    @Bean(SLAVE_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .build();
    }

    @Bean
    @DependsOn({MASTER_DATASOURCE, SLAVE_DATASOURCE})
    public DataSource routingDataSource(
        @Qualifier(MASTER_DATASOURCE) DataSource masterDataSource,
        @Qualifier(SLAVE_DATASOURCE) DataSource slaveDataSource) {

        RoutingDatasource routingDatasource = new RoutingDatasource();

        Map<Object, Object> dataSourceMap = new HashMap<Object, Object>() {
            {
                put("master", masterDataSource);
                put("slave", slaveDataSource);
            }
        };
        routingDatasource.setTargetDataSources(dataSourceMap);
        routingDatasource.setDefaultTargetDataSource(masterDataSource);

        return routingDatasource;
    }

    @Bean
    @Primary
    @DependsOn("routingDataSource")
    public LazyConnectionDataSourceProxy dataSource(DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    // DataSource 에서 Transaction 관리를 위한 Manager 클래스 등록
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
