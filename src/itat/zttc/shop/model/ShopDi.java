package itat.zttc.shop.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 使用这个Annotation来标注需要进行依赖注入的方法
 * @ShopDi("userDao")就说明该方法是注入userDao对象
 * 如果@ShopDi就规定该方法使用setXX来注入，如果方法是setUserDao表示注入userDao
 * @author Administrator
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ShopDi {

	String value() default "";
}
