package com.unism.service.product.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unism.service.base.DaoSupport;
import com.unism.service.product.inter.ProductTypeService;

@Service
@Transactional
public class ProductTypeServiceBean extends DaoSupport implements
		ProductTypeService {

	/*
	 * (non-Javadoc) 重写delete方法，不真正删除数据库中的数据，只是修改显示标志为false
	 * 
	 * @see com.unism.service.base.DaoSupport#delete(java.lang.Class,
	 * java.lang.Object[])
	 */
	@Override
	public <T> void delete(Class<T> entityClass, Object[] entityids) {
		if (entityids != null && entityids.length > 0) {
			StringBuffer jpql = new StringBuffer();
			for (int i = 0; i < entityids.length; i++) {
				jpql.append("?").append(i + 2).append(",");
			}
			jpql.deleteCharAt(jpql.length() - 1);
			// ?2,?3,?4
			Query query = em.createQuery(
					"update ProductType pt set pt.visible=?1 where pt.typeid in ("
							+ jpql.toString() + ")").setParameter(1, false);
			for (int i = 0; i < entityids.length; i++) {
				query.setParameter(i + 2, entityids[i]);

			}
			query.executeUpdate();
		}

	}

}
