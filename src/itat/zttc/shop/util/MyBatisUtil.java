package itat.zttc.shop.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
	private static SqlSessionFactory factory;
	private static ThreadLocal<SqlSession> localSession = new ThreadLocal<>();
	
	static {
		try {
			InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
			factory = new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SqlSession getSession() {
		SqlSession result = localSession.get();
		if (result == null) {
			result = factory.openSession();
			localSession.set(result);
		}
		return result;
	}
	
	public static void closeSession(SqlSession session) {
		if(session!=null) session.close();
		localSession.remove();
	}
}
