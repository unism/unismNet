package com.unism.bean.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unism.bean.product.Brand;
import com.unism.service.product.inter.BrandService;

public class BrandTest {
	private static ApplicationContext cxt;
	private static BrandService brandService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			/**
			 *  
			 */
			cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
			brandService = (BrandService) cxt.getBean("brandServiceBean");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSave() {
		for (int i = 0; i < 20; i++) {
			brandService.save(new Brand("计算机" + i,
					"/images/brand/2012/04/25/21/test2.jpg"));
		}
	}

}
