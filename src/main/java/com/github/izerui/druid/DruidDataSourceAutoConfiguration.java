package com.github.izerui.druid;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by serv on 16/6/23.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@ConditionalOnClass(DataSource.class)
@EnableConfigurationProperties(DruidProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@Import(DruidWebAutoConfiguration.class)
public class DruidDataSourceAutoConfiguration {


    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Primary
    @Bean(name = "druidDataSource", destroyMethod = "close", initMethod = "init")
    public DruidDataSource dataSource(DruidProperties properties) throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource(false);
        if (StringUtils.isNotEmpty(properties.getId())) {
            druidDataSource.setName(properties.getId());
        }
        druidDataSource.setUsername(properties.getUsername());
        druidDataSource.setUrl(properties.getUrl());
        druidDataSource.setPassword(properties.getPassword());
        druidDataSource.setFilters(properties.getFilters());
//        druidDataSource.getProxyFilters().add(new TenantFilter(properties, applicationContext));
        druidDataSource.setMaxActive(properties.getMaxActive());
        druidDataSource.setInitialSize(properties.getInitialSize());
        druidDataSource.setMaxWait(properties.getMaxWait());
        druidDataSource.setMinIdle(properties.getMinIdle());
        druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(properties.getValidationQuery());
        druidDataSource.setTestWhileIdle(properties.isTestWhileIdle());
        druidDataSource.setTestOnBorrow(properties.isTestOnBorrow());
        druidDataSource.setTestOnReturn(properties.isTestOnReturn());
        druidDataSource.setPoolPreparedStatements(properties.isPoolPreparedStatements());
        druidDataSource.setMaxOpenPreparedStatements(properties.getMaxOpenPreparedStatements());
        return druidDataSource;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean(name = "stat-filter")
    public StatFilter statFilter(DruidProperties properties) {
        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(properties.getSlowSqlMillis());
        statFilter.setLogSlowSql(properties.isLogSlowSql());
        return statFilter;
    }


}
