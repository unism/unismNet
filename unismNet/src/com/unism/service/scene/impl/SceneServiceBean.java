package com.unism.service.scene.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unism.bean.scene.Scene;
import com.unism.service.base.BaseDao;
import com.unism.service.base.DaoSupport;
import com.unism.service.scene.inter.SceneService;

@Service
@Transactional
public class SceneServiceBean extends DaoSupport implements SceneService {

	@Override
	public void save(Object entity) {
		// TODO Auto-generated method stub
		((Scene) entity).setSceneId(UUID.randomUUID().toString());
		super.save(entity);
	}
}
