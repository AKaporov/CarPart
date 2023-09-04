package ru.hw.demo.config;

import com.jolbox.bonecp.BoneCPDataSource;
import com.querydsl.sql.H2Templates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import com.querydsl.sql.types.DateTimeType;
import com.querydsl.sql.types.LocalDateType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
//@PropertySource({"classpath:jdbc.properties"})
public class JdbcConfiguration {

    @Inject
    Environment env;

    @Bean
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();

        dataSource.setDriverClass(env.getRequiredProperty("spring.datasource.driver"));
        dataSource.setJdbcUrl(env.getRequiredProperty("spring.datasource.url"));
        dataSource.setUsername(env.getRequiredProperty("spring.datasource.username"));
        dataSource.setPassword(env.getRequiredProperty("spring.datasource.password"));

        return dataSource;
    }

    @Bean
    public com.querydsl.sql.Configuration querydslConfiguration() {
        SQLTemplates templates = H2Templates.builder().build();
        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
        configuration.setExceptionTranslator(new SpringExceptionTranslator());
        configuration.register(new DateTimeType());
        configuration.register(new LocalDateType());
        return configuration;
    }

    @Bean
    public SQLQueryFactory queryFactory() {
        SpringConnectionProvider provider = new SpringConnectionProvider(dataSource());
        return new SQLQueryFactory(querydslConfiguration(), provider);
    }

}
