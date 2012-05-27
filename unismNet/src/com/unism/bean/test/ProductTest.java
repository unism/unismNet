package com.unism.bean.test;

import java.util.LinkedHashMap;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unism.bean.product.ProductType;
import com.unism.bean.util.QueryResult;
import com.unism.service.product.inter.ProductTypeService;

public class ProductTest {
	private static ApplicationContext cxt;
	private static ProductTypeService productService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
			productService = (ProductTypeService) cxt
					.getBean("productTypeServiceBean");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@Test
	public void test() {

		// 注意导入的文件为 javax.sql.DataSource
		// DataSource dataSource = (DataSource) cxt.getBean("dataSource");
		// System.out.println(dataSource);

		ProductType productType = new ProductType();
		productType.setName("足球");
		productType.setNote("很好使用的足球啊");
		productService.save(productType);

	}
	
	@Test
	public void testSave() {

		for (int i = 0; i < 20; i++) {
			ProductType productType = new ProductType();
			productType.setName("篮球"+i);
			productType.setNote("很好使用的篮球啊");
			productService.save(productType);
		}

	}
	
	@Test
	public void testFind() {

		// 注意导入的文件为 javax.sql.DataSource
		// DataSource dataSource = (DataSource) cxt.getBean("dataSource");
		// System.out.println(dataSource);

		ProductType productType = productService.find(ProductType.class, 1);
		Assert.assertNotNull("获取不到ID为1的记录", productType);

	}
	
	@Test
	public void testUpdate() {

		ProductType productType = productService.find(ProductType.class, 1);
		productType.setName("足球2");
		productType.setNote("很好使用的足球啊3");
		productService.update(productType);

	}
	
	@Test
	public void testDelete() {

		productService.delete(ProductType.class, 2);
	}
	
	@Test
	public void testgetScrollDate() {

		LinkedHashMap <String ,String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("typeid", "asc");
		orderBy.put("visible", "asc");
		QueryResult<ProductType> queryResult = productService.getScrollDate(
				ProductType.class);
		for (ProductType productType : queryResult.getResultList()) {
			System.out.println(productType.getName());
		}
	}
}
