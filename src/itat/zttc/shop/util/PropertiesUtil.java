package itat.zttc.shop.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	private static Properties daoProp;
	static {
		try {
				daoProp = new Properties();
				daoProp.load(PropertiesUtil.class.getClassLoader()
						.getResourceAsStream("dao.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static Properties getDaoProp() {
		return daoProp;
	}
}
