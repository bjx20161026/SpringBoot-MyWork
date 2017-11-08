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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * Configuring the RM database
 * @author bjx
 *
 */
@Configuration
@MapperScan(basePackages = "org.springboot.workorder.dao.rm", sqlSessionTemplateRef = "RmSqlSessionTemplate")
public class DataSourceRmConfig {
	 @Bean(name = "RmDataSource")
	    @ConfigurationProperties(prefix = "spring.datasource.rm")
	    public DataSource testDataSource() {
	        return DataSourceBuilder.create().build();
	    }
	 
	 @Bean(name = "RmSqlSessionFactory")
	    public SqlSessionFactory testSqlSessionFactory(@Qualifier("RmDataSource") DataSource dataSource) throws Exception {
	        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
	        bean.setDataSource(dataSource);
	        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/rm/*.xml"));
	        return bean.getObject();
	    }
	 
	  @Bean(name = "RmTransactionManager")
	    public DataSourceTransactionManager testTransactionManager(@Qualifier("RmDataSource") DataSource dataSource) {
	        return new DataSourceTransactionManager(dataSource);
	    }
	
	  @Bean(name = "RmSqlSessionTemplate")
	    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("RmSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
	        return new SqlSessionTemplate(sqlSessionFactory);
	    }

}
