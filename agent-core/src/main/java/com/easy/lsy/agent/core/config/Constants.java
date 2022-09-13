package com.easy.lsy.agent.core.config;

import java.io.File;

/**
 * 通用相关常量
 */
public interface Constants {

	/**
	 * yaml存储路径
	 */
	public final String RESULT_PATH = System.getProperty("user.dir") + File.separator +"result" + File.separator + "callinfo.yaml";

	/**
	 * 实例方法调用
	 */
	public final String SELF_CALL = "selfCall";
	/**
	 * 实例方法返回
	 */
	public final String SELF_RETURN = "selfReturn";
	/**
	 * 实例方法异常
	 */
	public final String SELF_THROWING = "selfThrowing";
	/**
	 * 静态方法调用
	 */
	public final String GLOBAL_CALL = "globalCall";
	/**
	 * 静态方法返回
	 */
	public final String GLOBAL_RETURN = "globalReturn";
	/**
	 * 静态方法异常
	 */
	public final String GLOBAL_THROWING = "globalThrowing";

}
