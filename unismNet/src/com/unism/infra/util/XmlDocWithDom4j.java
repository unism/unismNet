package com.unism.infra.util;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlDocWithDom4j {
	/**
	 * 
	 */
	private static final String DEFAUTL_ENCODING = "UTF-8";

	/**
	 * @param _sXmlFile
	 * @return
	 * @throws DocumentException
	 */
	public static Document parse(String _sXmlFile) throws DocumentException {
		SAXReader reader = new SAXReader();
		reader.setValidation(false);
		Document result = reader.read(_sXmlFile);
		return result;
	}

	/**
	 * @param _sXmlFile
	 * @param _sXslFile
	 * @return
	 * @throws DocumentException
	 * @throws TransformerException
	 */
	public static Document parse(String _sXmlFile, String _sXslFile)
			throws DocumentException, TransformerException {
		Document result = parse(_sXmlFile);
		return styleDocument(result, _sXslFile);
	}

	/**
	 * @param _document
	 * @param _sTargetFile
	 * @throws FileNotFoundException
	 */
	public static void write(Document _document, String _sTargetFile)
			throws FileNotFoundException {
		try {
			write(_document, "UTF-8", _sTargetFile);
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
		}
	}

	/**
	 * @param paramDocument
	 * @param paramString1
	 * @param paramString2
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	/**
	 * @param paramDocument
	 * @param paramString1
	 * @param paramString2
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public static void write(Document paramDocument, String paramString1,
			String paramString2) throws UnsupportedEncodingException,
			FileNotFoundException {
		BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(
				new FileOutputStream(paramString2));

		OutputFormat localOutputFormat = OutputFormat.createPrettyPrint();
		if (!isEmpty(paramString1)) {
			localOutputFormat.setEncoding(paramString1);
		}

		XMLWriter localXMLWriter = null;
		try {
			localXMLWriter = new XMLWriter(localBufferedOutputStream,
					localOutputFormat);
			localXMLWriter.write(paramDocument);
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
			throw localUnsupportedEncodingException;
		} catch (IOException localIOException1) {
		} finally {
			try {
				localBufferedOutputStream.close();
				if (localXMLWriter != null)
					localXMLWriter.close();
			} catch (IOException localIOException2) {
			}
		}
	}

	/**
	 * @param _document
	 * @param _xslFile
	 * @param _encoding
	 * @param _result
	 * @throws TransformerException
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public static void write(Document _document, String _xslFile,
			String _encoding, String _result) throws TransformerException,
			UnsupportedEncodingException, FileNotFoundException {
		Document resultdoc = styleDocument(_document, _xslFile);
		write(resultdoc, _encoding, _result);
	}

	/**
	 * @param _document
	 * @param _stylesheet
	 * @return
	 * @throws TransformerException
	 */
	public static Document styleDocument(Document _document, String _stylesheet)
			throws TransformerException {
		if (isEmpty(_stylesheet)) {
			return _document;
		}

		StreamSource style = new StreamSource(_stylesheet);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(style);

		DocumentSource source = new DocumentSource(_document);
		DocumentResult result = new DocumentResult();
		transformer.transform(source, result);

		Document transformedDoc = result.getDocument();
		return transformedDoc;
	}

	/**
	 * @param _sSourceXml
	 * @param _sSourceXsl
	 * @param _sTarget
	 * @throws TransformerException
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public static void transform(String _sSourceXml, String _sSourceXsl,
			String _sTarget) throws TransformerException,
			FileNotFoundException, DocumentException {
		try {
			transform(_sSourceXml, _sSourceXsl, "UTF-8", _sTarget);
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
		}
	}

	/**
	 * @param _sSourceXml
	 * @param _sSourceXsl
	 * @param _encoding
	 * @param _sTarget
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @throws TransformerException
	 * @throws UnsupportedEncodingException
	 */
	public static void transform(String _sSourceXml, String _sSourceXsl,
			String _encoding, String _sTarget) throws FileNotFoundException,
			DocumentException, TransformerException,
			UnsupportedEncodingException {
		Document source = parse(_sSourceXml);
		Document result = styleDocument(source, _sSourceXsl);
		write(result, _encoding, _sTarget);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String fn = "e:\\dom4j.xml";
			Document doc = parse(fn);
			System.out.println(doc.nodeCount());
			System.out.println(doc.getRootElement().getName());
			List list = doc.getRootElement().element("properties").elements();
			for (int i = 0; i < list.size(); i++) {
				Element ele = (Element) list.get(i);
				if (ele != null)
					System.out.println(ele.getName() + ":" + ele.getText()
							+ " hascon:" + ele.hasContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param s
	 * @return
	 */
	private static boolean isEmpty(String s) {
		return (s == null) || (s.trim().length() == 0);
	}
}