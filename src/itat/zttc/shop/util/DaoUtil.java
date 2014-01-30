package itat.zttc.shop.util;

import itat.zttc.shop.dao.IFactoryDao;
import itat.zttc.shop.model.ShopDi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class DaoUtil {
	
	private static IFactoryDao daoFactory;
	static {
		daoFactory = createDaoFactory();
	}
	
	public static void diDao(Object obj) {
		try {
			/**
			 * 获取所有的方法
			 */
			Method[] ms = obj.getClass().getDeclaredMethods();
			for(Method m:ms) {
				/**
				 * 判断方法上面是否有ShopDi的Annotation，有这个Annotation才进行注入
				 */
				if(m.isAnnotationPresent(ShopDi.class)) {
					/*
					 * 获取method上的ShopDi对象
					 */
					ShopDi sd = m.getAnnotation(ShopDi.class);
					/**
					 * 获取这个Annotation的值
					 */
					String mn = sd.value();
					/**
					 * 判断shopDi的value是否为空，如果为空，就等于要使用setXXX这个方法名称
					 * 来完成注入
					 */
					if(mn==null||"".equals(mn.trim())) {
						mn = m.getName().substring("set".length());
						mn = mn.substring(0,1).toLowerCase()+mn.substring(1);
					}
					Object o = daoFactory.getDao(mn);
					m.invoke(obj, o);
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static IFactoryDao createDaoFactory() {
		IFactoryDao f = null;
		try {
			Properties prop = PropertiesUtil.getDaoProp();
			String fs = prop.getProperty("factory");
			Class clz = Class.forName(fs);
			String mn = "getInstance";
			Method m = clz.getMethod(mn);
			f = (IFactoryDao)m.invoke(clz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return f;
	}
}
