package com.xfkj.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtil {

	private static  SqlSessionFactory factory;
	
	static{
			try {
				InputStream in=Resources.getResourceAsStream("mybatis-config-test.xml");
				factory=new SqlSessionFactoryBuilder().build(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	public static SqlSession createSession(){
		
		return factory.openSession();
	}
	public static void closeSession(SqlSession session){
		
		session.close();
	}
}
