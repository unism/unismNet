package com.unism.web.action.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.unism.bean.scene.Scene;
import com.unism.bean.util.QueryResult;
import com.unism.service.scene.inter.SceneService;
import com.unism.web.formbean.scene.SceneVO;

@Controller
public class SceneAction implements ModelDriven<SceneVO> {
	@Resource(name = "sceneServiceBean")
	private SceneService sceneService;
	Scene scene = new Scene();
	private Logger logger = null;

	private SceneVO sceneVO = new SceneVO();
	
	public String list() {
		int maxResult = 5;
		if (sceneVO.getRows() != null) {
			maxResult = Integer.valueOf(sceneVO.getRows().get(0).toString());
		}
		int firstIndex = ((sceneVO.getPage() - 1) * maxResult);
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("sceneName", "desc");
		QueryResult<Scene> qr = sceneService.getScrollDate(Scene.class,
				firstIndex, maxResult, orderBy);

		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		sceneVO.setTotal(25);
		sceneVO.setResult(JSONArray.fromObject(qr.getResultList())); //
		JSONObject.fromObject(jsonMap);
		return "SUCCESS";
	}

	@Override
	public SceneVO getModel() {
		return sceneVO;
	}
}
