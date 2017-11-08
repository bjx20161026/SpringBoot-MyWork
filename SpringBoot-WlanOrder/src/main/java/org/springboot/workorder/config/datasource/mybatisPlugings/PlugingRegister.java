package org.springboot.workorder.config.datasource.mybatisPlugings;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlugingRegister implements InitializingBean{
	
    @Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	public void init() {
		sqlSessionFactory.getConfiguration().addInterceptor(new RstInterceptor());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		init();
	}
}
