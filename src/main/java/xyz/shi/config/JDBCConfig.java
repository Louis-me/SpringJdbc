package xyz.shi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

//表明当前类是spring的一个配置类，作用是替代spring的applicationContext.xml。
// 但其本质就是@Component注解，被此注解修饰的类，也会被存入spring的ioc容器
@Configuration
public class JDBCConfig {
    //@Bean通常出现在Spring的配置类当中，注解在方法上，表示把当前方法的返回值存入spring的ioc容器
    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPassword("123456");
        hikariConfig.setUsername("root");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/test?serverTimezone=UTC");
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.addDataSourceProperty("autoCommit", "true");
        hikariConfig.addDataSourceProperty("connectionTimeout", "5");
        hikariConfig.addDataSourceProperty("idleTimeout", "60");
        return new HikariDataSource(hikariConfig);
    }
    @Bean
    public JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}