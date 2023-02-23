package com.notice.project.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.notice.project.user.Role;

public class UserTypeHandler extends BaseTypeHandler<Role>{

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Role parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, parameter.name());
		
	}

	@Override
	public Role getNullableResult(ResultSet rs, String columnName) throws SQLException {
		
		return Role.valueOf(rs.getString(columnName));
	}

	@Override
	public Role getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		
		return Role.valueOf(rs.getString(columnIndex));
	}

	@Override
	public Role getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		
		return Role.valueOf(cs.getString(columnIndex));
	};
	
	@Bean
	public SqlSessionFactory sessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
			SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
	        sqlSessionFactoryBean.setDataSource(dataSource);
	        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
	        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("/mappers/*.xml"));
	        sqlSessionFactoryBean.setTypeHandlers(new UserTypeHandler()); //등록

	        return sqlSessionFactoryBean.getObject();
	}

}
