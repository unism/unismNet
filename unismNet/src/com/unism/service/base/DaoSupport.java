package com.unism.service.base;

import java.util.LinkedHashMap;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.unism.bean.util.QueryResult;

@Transactional
public abstract class DaoSupport implements BaseDao {
	/**
	 * 注入实体管理器 protected 允许子类访问该对象
	 */
	@PersistenceContext
	protected EntityManager em;

	// AOP
	// EntityManager em = 实体赋值
	// 打开事务

	@Override
	public void save(Object entity) {
		em.persist(entity);

	}

	@Override
	public void update(Object entity) {
		em.merge(entity);

	}

	@Override
	public <T> void delete(Class<T> entityClass, Object entityid) {
		delete(entityClass, new Object[] { entityid });

	}

	@Override
	public <T> void delete(Class<T> entityClass, Object[] entityids) {
		for (Object entityid : entityids) {
			em.remove(em.getReference(entityClass, entityid));
		}

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public <T> T find(Class<T> entityClass, Object entityid) {
		return em.find(entityClass, entityid);

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollDate(Class<T> entityClass,
			int firstIndex, int maxResult, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderBy) {
		@SuppressWarnings("rawtypes")
		QueryResult queryResult = new QueryResult<T>();
		String entityName = getEntityName(entityClass);
		Query query = em.createQuery("select o from " + entityName + " o"
				+ (whereJpql == null ? "" : " where " + whereJpql)
				+ buildOrderBy(orderBy));
		setQueryParam(query, queryParams);
		/*
		 * query.setFirstResult(firstIndex); query.setMaxResults(maxResult);
		 */

		if (firstIndex != -1 && maxResult != -1) {
			query.setFirstResult(firstIndex).setMaxResults(maxResult);
		}
		queryResult.setResultList(query.getResultList());
		query = em.createQuery("select count(o) from " + entityName + " o"
				+ (whereJpql == null ? "" : " where " + whereJpql));
		setQueryParam(query, queryParams);
		queryResult.setTotalRecord((Long) query.getSingleResult());
		return queryResult;
	}

	/**
	 * @param query
	 * @param queryParams
	 */
	protected void setQueryParam(Query query, Object[] queryParams) {
		if (queryParams != null && queryParams.length > 0) {
			for (int i = 0; i < queryParams.length; i++) {
				query.setParameter(i + 1, queryParams[i]);
			}
		}
	}

	/**
	 * 组装Order by 语句
	 * 
	 * @param orderBy
	 * @return
	 */
	protected String buildOrderBy(LinkedHashMap<String, String> orderBy) {
		StringBuffer orderBySQL = new StringBuffer("");
		if (orderBy != null && orderBy.size() > 0) {
			orderBySQL.append(" order by ");
			// order by key1 asc ,key2 asc
			for (String key : orderBy.keySet()) {
				orderBySQL.append("o.").append(key).append(" ")
						.append(orderBy.get(key)).append(",");
			}
			orderBySQL.deleteCharAt(orderBySQL.length() - 1);
		}

		return orderBySQL.toString();
	}

	/**
	 * 获取实体名称
	 * 
	 * @param entityClass
	 * @return
	 */
	protected <T> String getEntityName(Class<T> entityClass) {
		String entityName = entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		if (entity.name() != null && !"".equals(entity.name())) {
			entityName = entity.name();
		}
		return entityName;
	}

	@Override
	public <T> QueryResult<T> getScrollDate(Class<T> entityClass,
			int firstIndex, int maxResult, LinkedHashMap<String, String> orderBy) {

		return getScrollDate(entityClass, firstIndex, maxResult, null, null,
				orderBy);

	}

	@Override
	public <T> QueryResult<T> getScrollDate(Class<T> entityClass,
			int firstIndex, int maxResult, String whereJpql, Object[] queryParam) {
		return getScrollDate(entityClass, firstIndex, maxResult, whereJpql,
				queryParam, null);
	}

	@Override
	public <T> QueryResult<T> getScrollDate(Class<T> entityClass,
			int firstIndex, int maxResult) {
		return getScrollDate(entityClass, firstIndex, maxResult, null, null,
				null);
	}

	@Override
	public <T> QueryResult<T> getScrollDate(Class<T> entityClass) {
		return getScrollDate(entityClass, -1, -1);
	}

}
