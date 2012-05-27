package com.unism.infra.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.unism.infra.common.WCMException;

/**
 * Document對象轉換為dom4j對象工具類
 * @Title: CMyDOM4jHelper.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:07:03
 * @version CMS V1.0 
 */
public class CMyDOM4jHelper {
	/** 
	 * @param _sXmlFile
	 * @param _sXslFile
	 * @return
	 * @throws WCMException
	 */
	public static Element buildXmlFile(String _sXmlFile, String _sXslFile)
			throws WCMException {
		Document document = null;
		SAXReader reader = new SAXReader();
		try {
			InputStream checkedIns = new XmlCharCheckedInputStream(
					new FileInputStream(_sXmlFile));
			document = reader.read(checkedIns);
		} catch (Exception e) {
			throw new WCMException("读取XML文件[" + _sXmlFile + "]失败！", e);
		}
		if ((_sXslFile == null) || (_sXslFile.length() == 0)) {
			return document.getRootElement();
		}
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(
					_sXslFile));

			DocumentSource source = new DocumentSource(document);
			DocumentResult result = new DocumentResult();
			transformer.transform(source, result);

			document = result.getDocument();
		} catch (Exception e) {
			throw new WCMException("对XML文件[" + _sXmlFile + "]进行转换[XSLT:"
					+ _sXslFile + "]失败！");
		}
		return document.getRootElement();
	}

	/**
	 * @param paramElement
	 * @param paramString1
	 * @param paramString2
	 * @param paramString3
	 * @throws WCMException
	 */
	public static void toXMLFile(Element paramElement, String paramString1,
			String paramString2, String paramString3) throws WCMException {
		Document localDocument = makeXMLDocument(paramElement, paramString3);

		OutputStreamWriter localOutputStreamWriter = null;
		FileOutputStream localFileOutputStream = null;
		try {
			localFileOutputStream = new FileOutputStream(paramString2);
			localOutputStreamWriter = new OutputStreamWriter(
					localFileOutputStream, paramString1);

			OutputFormat localOutputFormat = OutputFormat.createPrettyPrint();
			XMLWriter localXMLWriter = new XMLWriter(localOutputStreamWriter,
					localOutputFormat);
			localXMLWriter.write(localDocument);
		} catch (Exception localException1) {
			throw new WCMException(1100, "生成XML文件时失败!", localException1);
		} finally {
			if (localOutputStreamWriter != null) {
				try {
					localOutputStreamWriter.close();
				} catch (Exception localException2) {
					localException2.printStackTrace();
				}
			}
			if (localFileOutputStream != null)
				try {
					localFileOutputStream.close();
				} catch (Exception localException3) {
					localException3.printStackTrace();
				}
		}
	}

	/**
	 * @param _element
	 * @param _XSLFileName
	 * @return
	 * @throws TransformerFactoryConfigurationError
	 * @throws WCMException
	 */
	public static Document makeXMLDocument(Element _element, String _XSLFileName)
			throws TransformerFactoryConfigurationError, WCMException {
		Document document = DocumentHelper.createDocument();
		document.add(_element);

		if ((_XSLFileName == null) || (_XSLFileName.length() == 0)) {
			return document;
		}
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(
					_XSLFileName));

			DocumentSource source = new DocumentSource(document);
			DocumentResult result = new DocumentResult();
			transformer.transform(source, result);

			document = result.getDocument();
		} catch (Exception e) {
			throw new WCMException("使用XSLT转换失败!");
		}
		return document;
	}
}