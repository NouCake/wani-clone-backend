package de.abstractolotl.shadycrab.waniclone.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("#{environment.MARIA_USER}")
    private String MARIA_USER;
    @Value("#{environment.MARIA_PW}")
    private String MARIA_PW;

    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
            .driverClassName("org.mariadb.jdbc.Driver")
            .url("jdbc:mariadb://192.168.1.242:3306/crab")
            .username(MARIA_USER)
            .password(MARIA_PW)
            .build();
    }

}
