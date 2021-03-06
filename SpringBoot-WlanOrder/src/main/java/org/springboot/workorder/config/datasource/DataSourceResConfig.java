package org.springboot.workorder.config.datasource;

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
 * Configuring the RES database
 * @author bjx
 *
 */
@Configuration
@MapperScan(basePackages = "org.springboot.workorder.dao.res", sqlSessionTemplateRef = "ResSqlSessionTemplate")
public class DataSourceResConfig {
	 @Bean(name = "ResDataSource")
	    @ConfigurationProperties(prefix = "spring.datasource.res")
	    @Primary
	    public DataSource testDataSource() {
	        return DataSourceBuilder.create().build();
	    }
	 
	 @Bean(name = "ResSqlSessionFactory")
	    @Primary
	    public SqlSessionFactory testSqlSessionFactory(@Qualifier("ResDataSource") DataSource dataSource) throws Exception {
	        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
	        bean.setDataSource(dataSource);
	        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/res/*.xml"));
	        return bean.getObject();
	    }
	 
	  @Bean(name = "ResTransactionManager")
	    @Primary
	    public DataSourceTransactionManager testTransactionManager(@Qualifier("ResDataSource") DataSource dataSource) {
	        return new DataSourceTransactionManager(dataSource);
	    }
	
	  @Bean(name = "ResSqlSessionTemplate")
	    @Primary
	    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("ResSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
	        return new SqlSessionTemplate(sqlSessionFactory);
	    }

}
