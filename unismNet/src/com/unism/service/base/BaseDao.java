package com.unism.service.base;

import java.util.LinkedHashMap;

import com.unism.bean.util.QueryResult;

public interface BaseDao {
	/**
	 * 保存实体
	 * 
	 * @param entity
	 */
	public void save(Object entity);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	public void update(Object entity);

	/**
	 * 删除实体
	 * 
	 * @param entityid
	 */
	public <T> void delete(Class<T> entityClass, Object entityid);

	/**
	 * 删除实体
	 * 
	 * @param entityid
	 */
	public <T> void delete(Class<T> entityClass, Object[] entityids);

	/**
	 * 获取实体
	 * 
	 * @param entityClass
	 * @param entityid
	 * @return
	 */
	public <T> T find(Class<T> entityClass, Object entityid);

	/**
	 * 获取分页数据
	 * 
	 * @param entityClass
	 *            需要分页的实体Bean
	 * @param firstIndex
	 *            开始索引
	 * @param maxResult
	 *            最大结果集
	 * 
	 * @param orderBy
	 * @return
	 */
	// key 存放实体属性，ASC/DESC,使用linkedhashmap 保证存放的数据按照添加顺序排序，不会重排序（hashmap）
	// Order by key1 desc,key2 asc
	public <T> QueryResult<T> getScrollDate(Class<T> entityClass,
			int firstIndex, int maxResult, String whereJpql,
			Object[] queryParam, LinkedHashMap<String, String> orderBy);

	/**
	 * @param entityClass
	 * @param firstIndex
	 * @param maxResult
	 * @param orderBy
	 * @return
	 */
	public <T> QueryResult<T> getScrollDate(Class<T> entityClass,
			int firstIndex, int maxResult, LinkedHashMap<String, String> orderBy);

	/**
	 * @param entityClass
	 * @param firstIndex
	 * @param maxResult
	 * @param whereJpql
	 * @param queryParam
	 * @return
	 */
	public <T> QueryResult<T> getScrollDate(Class<T> entityClass,
			int firstIndex, int maxResult, String whereJpql, Object[] queryParam);

	/**
	 * @param entityClass
	 * @param firstIndex
	 * @param maxResult
	 * @return
	 */
	public <T> QueryResult<T> getScrollDate(Class<T> entityClass,
			int firstIndex, int maxResult);

	/**
	 * @param entityClass
	 * @return
	 */
	public <T> QueryResult<T> getScrollDate(Class<T> entityClass);

}
