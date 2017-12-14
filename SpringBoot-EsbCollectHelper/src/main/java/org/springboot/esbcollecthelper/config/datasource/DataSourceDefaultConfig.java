package org.springboot.esbcollecthelper.config.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * Configuring the Default database
 * @author b j x
 *
 */
@Configuration
@MapperScan(basePackages = "org.springboot.esbcollecthelper.dao.def", sqlSessionTemplateRef = "DefaultSqlSessionTemplate")
public class DataSourceDefaultConfig {
	 @Bean(name = "DefaultDataSource")
	    @ConfigurationProperties(prefix = "spring.datasource.def")
	    @Primary
	    public DataSource testDataSource() {
	        return DataSourceBuilder.create().build();
	    }
	 
	 @Bean(name = "DefaultSqlSessionFactory")
	    @Primary
	    public SqlSessionFactory testSqlSessionFactory(@Qualifier("DefaultDataSource") DataSource dataSource) throws Exception {
	        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
	        bean.setDataSource(dataSource);
	        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/def/*.xml"));
	        return bean.getObject();
	    }
	 
	  @Bean(name = "DefaultTransactionManager")
	    @Primary
	    public DataSourceTransactionManager testTransactionManager(@Qualifier("DefaultDataSource") DataSource dataSource) {
	        return new DataSourceTransactionManager(dataSource);
	    }
	
	  @Bean(name = "DefaultSqlSessionTemplate")
	    @Primary
	    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("DefaultSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
	        return new SqlSessionTemplate(sqlSessionFactory);
	    }

}
