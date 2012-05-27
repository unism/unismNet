package com.unism.service.product.impl;

import java.util.LinkedHashMap;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unism.bean.product.Brand;
import com.unism.bean.util.QueryResult;
import com.unism.service.base.DaoSupport;
import com.unism.service.product.inter.BrandService;

/**
 * 业务Bean
 * @author pdf
 *
 */
@Service
@Transactional
//jdk / aop代理 cglib
public class BrandServiceBean extends DaoSupport implements BrandService {

	@Override
	public void save(Object entity) {

		((Brand) entity).setCode(UUID.randomUUID().toString());
		super.save(entity);
	}

}
