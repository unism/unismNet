package com.unism.bean.test;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unism.bean.scene.Scene;
import com.unism.bean.util.QueryResult;
import com.unism.service.scene.inter.SceneService;

public class SceneTest {

	private static ApplicationContext cxt;
	private static SceneService sceneService;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
			sceneService = (SceneService) cxt.getBean("sceneServiceBean");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSave() {

		for (int i = 0; i < 10; i++) {
			Scene scene = new Scene();
			scene.setSceneAddr("上海"+i);
			scene.setSceneDesc("场景 上海 "+i);
			scene.setSceneName("场景 "+i);
			scene.setSceneVisible(true);
			sceneService.save(scene);
		}
	}

	@Test
	public void testFind() {

		// 注意导入的文件为 javax.sql.DataSource
		// DataSource dataSource = (DataSource) cxt.getBean("dataSource");
		// System.out.println(dataSource);
		
		Scene scene = sceneService.find(Scene.class, "691618a7-00f1-496d-9b08-da7294af9741");
		Assert.assertNotNull("获取不到ID为UUID的记录", scene);
	}
	
	@Test
	public void testUpdate() {
		Scene scene = sceneService.find(Scene.class, "e8d6161a-1d61-4592-95f3-dc9ae83b11fd");
		scene.setSceneName("场景修改了2");
		scene.setSceneDesc("说的啥啊2");
		sceneService.update(scene);
	}
	
	@Test
	public void testDelete() {
		sceneService.delete(Scene.class, "e8d6161a-1d61-4592-95f3-dc9ae83b11fd");
	}
	
	@Test
	public void testgetScrollDate() {

		LinkedHashMap <String ,String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("sceneId", "desc");
		orderBy.put("sceneVisible", "desc");
		QueryResult<Scene> queryResult = sceneService.getScrollDate(Scene.class, 0, 5, orderBy);//.getScrollDate(Scene.class);
		for (Scene scene : queryResult.getResultList()) {
			System.out.println(scene.getSceneName());
		}
	}
}
