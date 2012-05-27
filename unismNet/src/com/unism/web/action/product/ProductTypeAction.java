package com.unism.web.action.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.unism.bean.product.ProductType;
import com.unism.bean.util.QueryResult;
import com.unism.service.product.inter.ProductTypeService;
import com.unism.web.formbean.product.ProductTypeVO;
import com.unism.web.util.PageView;

@Controller
public class ProductTypeAction extends ActionSupport implements
		ModelDriven<ProductTypeVO> {

	@Resource(name = "productTypeServiceBean")
	private ProductTypeService productTypeService;
	private ProductTypeVO productTypeVO = new ProductTypeVO();
	/**
	 * 
	 */
	private static final long serialVersionUID = -8223000769614998L;

	@Override
	public ProductTypeVO getModel() {
		return productTypeVO;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return super.execute();
	}

	public String list() {
		int maxResult = 20;
		if (productTypeVO.getRows() != null) {
			maxResult = Integer.valueOf(productTypeVO.getRows().get(0)
					.toString());
		}
		int firstIndex = ((productTypeVO.getPage() - 1) * maxResult);
		StringBuffer whereJpql = new StringBuffer("o.visible=?1");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		if (productTypeVO.getParentid()!=null && productTypeVO.getParentid()>0){
			whereJpql.append(" and o.parent.typeid=?2");
			params.add(productTypeVO.getParentid());
		}else {
			whereJpql.append(" and o.parent is null");
		}
		
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("name", "desc");
		QueryResult<ProductType> qr = productTypeService.getScrollDate(
				ProductType.class, firstIndex, maxResult,whereJpql.toString(),params.toArray(), orderBy);
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		
		PageView<ProductType> pageView = new PageView<ProductType>(20,
				productTypeVO.getPage());
		pageView.setQueryResult(qr);
		productTypeVO.setTotal((int)qr.getTotalRecord());
		jsonMap.put("rows", qr.getResultList());
		jsonMap.put("total", productTypeVO.getTotal());
		productTypeVO.setResult(JSONObject.fromObject(jsonMap));

		return SUCCESS;
	}

	public String del() {
		productTypeService.delete(ProductType.class, Integer.valueOf(productTypeVO.getTypeid()));
		return SUCCESS;
	}

	public String add() {
		ProductType type = new ProductType();
		
		type.setName(productTypeVO.getName());
		type.setNote(productTypeVO.getNote());
		System.out.println(productTypeVO.getParentid());
		if (productTypeVO.getParentid() != null && productTypeVO.getParentid()> 0) {
			type.setParent(new ProductType(productTypeVO.getParentid()));
			System.out.println("true");
		}
		productTypeService.save(type);
		return SUCCESS;
	}
	
	public String edit() {
		ProductType type = productTypeService.find(ProductType.class,
				Integer.valueOf(productTypeVO.getTypeid()));

		type.setName(productTypeVO.getName());
		type.setNote(productTypeVO.getNote());
		productTypeService.update(type);
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		if (productTypeVO.getTypeid() != null) {
			jsonMap.put("typeid", productTypeVO.getTypeid());
		}
		jsonMap.put("name", type.getName());
		jsonMap.put("note", type.getNote());
		productTypeVO.setData(JSONObject.fromObject(jsonMap));
		return SUCCESS;
	}

	public String getproductbyid() {
		ProductType type = productTypeService.find(ProductType.class,
				Integer.valueOf(productTypeVO.getTypeid()));
		// productTypeVO.setJson(JSONArray.fromObject(type));
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("name", type.getName());
		jsonMap.put("note", type.getNote());
		productTypeVO.setData(JSONObject.fromObject(jsonMap));
		return SUCCESS;
	}
}
