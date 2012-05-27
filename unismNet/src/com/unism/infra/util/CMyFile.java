package com.unism.infra.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.log4j.Logger;

import com.unism.infra.common.WCMException;

/**
 * @Title: CMyFile.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:20:58
 * @version CMS V1.0
 */
public class CMyFile {
	/**
	 *  
	 */
	private static Logger m_oLogger = Logger.getLogger(CMyFile.class);

	/**
	 * @param _sPathFileName
	 * @return
	 */
	public static boolean fileExists(String _sPathFileName) {
		File file = new File(_sPathFileName);
		return file.exists();
	}

	/**
	 * @param _sPathFileName
	 * @return
	 */
	public static boolean pathExists(String _sPathFileName) {
		String sPath = extractFilePath(_sPathFileName);
		return fileExists(sPath);
	}

	/**
	 * 从文件的完整路径名（路径+文件名）中提取文件名(包含扩展名)<br/>
	 * 如：d:\path\file.ext --> file.ext
	 * 
	 * @param _sFilePathName
	 * @return
	 */
	public static String extractFileName(String _sFilePathName) {
		return extractFileName(_sFilePathName, File.separator);
	}

	/**
	 * 从文件的完整路径名（路径+文件名）中提取文件名(包含扩展名) <br/>
	 * 如：d:\path\file.ext --> file.ext
	 * 
	 * @param _sFilePathName
	 *            - 全文件路径名
	 * @param _sFileSeparator
	 *            - 文件分隔符
	 * @return
	 */
	public static String extractFileName(String _sFilePathName,
			String _sFileSeparator) {
		int nPos = -1;
		if (_sFileSeparator == null) {
			nPos = _sFilePathName.lastIndexOf(File.separatorChar);
			if (nPos < 0)
				nPos = _sFilePathName.lastIndexOf(File.separatorChar == '/'
						? 92
						: '/');
		} else {
			nPos = _sFilePathName.lastIndexOf(_sFileSeparator);
		}

		if (nPos < 0) {
			return _sFilePathName;
		}

		return _sFilePathName.substring(nPos + 1);
	}

	/**
	 * 从EB路径地址中提取: 文件名
	 * 
	 * @param _sFilePathName
	 * @return
	 */
	public static String extractHttpFileName(String _sFilePathName) {
		int nPos = _sFilePathName.lastIndexOf("/");
		return _sFilePathName.substring(nPos + 1);
	}

	/**
	 * 从文件的完整路径名（路径+文件名）中提取:主文件名（不包括路径和扩展名）
	 * 
	 * @param _sFilePathName
	 * @return
	 */
	public static String extractMainFileName(String _sFilePathName) {
		String sFileMame = extractFileName(_sFilePathName);
		int nPos = sFileMame.lastIndexOf('.');
		if (nPos > 0) {
			return sFileMame.substring(0, nPos);
		}
		return sFileMame;
	}

	/**
	 * 排除文件的扩展名,只保留路径(如果存在)和主文件名
	 * 
	 * @param sFileMame
	 * @return
	 */
	public static String excludeFileExt(String sFileMame) {
		int nPos = sFileMame.lastIndexOf('.');
		if (nPos > 0) {
			return sFileMame.substring(0, nPos);
		}
		return sFileMame;
	}

	/**
	 * 从文件的完整路径名（路径+文件名）中提取: 文件扩展名
	 * 
	 * @param _sFilePathName
	 * @return
	 */
	public static String extractFileExt(String _sFilePathName) {
		int nPos = _sFilePathName.lastIndexOf('.');
		return nPos >= 0 ? _sFilePathName.substring(nPos + 1) : "";
	}

	/**
	 * 从文件的完整路径名（路径+文件名）中提取 路径（包括：Drive+Directroy )
	 * 
	 * @param _sFilePathName
	 * @return
	 */
	public static String extractFilePath(String _sFilePathName) {
		int nPos = _sFilePathName.lastIndexOf('/');
		if (nPos < 0) {
			nPos = _sFilePathName.lastIndexOf('\\');
		}
		return nPos >= 0 ? _sFilePathName.substring(0, nPos + 1) : "";
	}

	/**
	 * 将文件/路径名称转化为绝对路径名
	 * 
	 * @param _sFilePathName
	 * @return
	 */
	public static String toAbsolutePathName(String _sFilePathName) {
		File file = new File(_sFilePathName);
		return file.getAbsolutePath();
	}

	/**
	 * 从文件的完整路径名（路径+文件名）中提取文件所在驱动器 注意：区分两种类型的文件名表示 <>[1] d:\path\filename.ext
	 * --> return "d:" [2] \\host\shareDrive\shareDir\filename.ext --> return
	 * "\\host\shareDrive"
	 * 
	 * @param _sFilePathName
	 * @return
	 */
	public static String extractFileDrive(String _sFilePathName) {
		int nLen = _sFilePathName.length();

		if ((nLen > 2) && (_sFilePathName.charAt(1) == ':')) {
			return _sFilePathName.substring(0, 2);
		}

		if ((nLen > 2) && (_sFilePathName.charAt(0) == File.separatorChar)
				&& (_sFilePathName.charAt(1) == File.separatorChar)) {
			int nPos = _sFilePathName.indexOf(File.separatorChar, 2);
			if (nPos >= 0)
				nPos = _sFilePathName.indexOf(File.separatorChar, nPos + 1);
			return nPos >= 0
					? _sFilePathName.substring(0, nPos)
					: _sFilePathName;
		}

		return "";
	}

	/**
	 * 删除指定的文件
	 * 
	 * @param _sFilePathName
	 * @return
	 */
	public static boolean deleteFile(String _sFilePathName) {
		File file = new File(_sFilePathName);
		return file.exists() ? file.delete() : false;
	}

	/**
	 * 创建目录
	 * 
	 * @param _sDir
	 * @param _bCreateParentDir
	 * @return
	 */
	public static boolean makeDir(String _sDir, boolean _bCreateParentDir) {
		boolean zResult = false;
		File file = new File(_sDir);
		if (_bCreateParentDir)
			zResult = file.mkdirs();
		else
			zResult = file.mkdir();
		if (!zResult)
			zResult = file.exists();
		return zResult;
	}

	/**
	 * 删除指定的目录下所有的文件 注意：若文件或目录正在使用，删除操作将失败。
	 * 
	 * @param _sDir
	 *            - 目录名
	 * 
	 * @param _bDeleteChildren
	 *            - 是否删除其子目录或子文件（可省略，默认不删除）
	 * @return - true if the directory exists and has been deleted successfully.
	 */
	public static boolean deleteDir(String _sDir, boolean _bDeleteChildren) {
		File file = new File(_sDir);
		if (!file.exists()) {
			return false;
		}
		if (_bDeleteChildren) {
			File[] files = file.listFiles();

			for (int i = 0; i < files.length; i++) {
				File aFile = files[i];
				if (aFile.isDirectory())
					deleteDir(aFile);
				else {
					aFile.delete();
				}
			}
		}
		return file.delete();
	}

	/**
	 * Deletes a file path, and all the files and subdirectories in this path
	 * will also be deleted.
	 * 
	 * @param _sPath
	 *            - the specified path.
	 * @return - true if the path exists and has been deleted successfully;
	 *         false othewise.
	 */
	public static boolean deleteDir(String _sPath) {
		File path = new File(_sPath);
		return deleteDir(path);
	}

	/**
	 * Deletes a file path, and all the files and subdirectories in this path
	 * will also be deleted.
	 * 
	 * @param _path
	 *            - the specified path.
	 * 
	 * @return - true if the path exists and has been deleted successfully;
	 *         false othewise.
	 */
	public static boolean deleteDir(File _path) {
		if (!_path.exists()) {
			return false;
		}

		if (_path.isDirectory()) {
			File[] files = _path.listFiles();

			for (int i = 0; i < files.length; i++) {
				File aFile = files[i];
				if (aFile.isDirectory())
					deleteDir(aFile);
				else {
					aFile.delete();
				}
			}

		}

		return _path.delete();
	}

	/**
	 * 获取某一路径下的特殊文件
	 * 
	 * @param _dir
	 * @param _extendName
	 * @return
	 */
	public static File[] listFiles(String _dir, String _extendName) {
		File fDir = new File(_dir);
		if (_extendName.charAt(0) != '.')
			_extendName = "." + _extendName;
		File[] Files = fDir.listFiles(new CMyFilenameFilter(_extendName));
		return Files;
	}

	/**
	 * 获取某一路径下的子文件夹
	 * 
	 * @param _dir
	 *            - 路径名称
	 * @return 子文件夹对象数组
	 */
	public static File[] listSubDirectories(String _dir) {
		File fDir = new File(_dir);
		FileFilter fileFilter = new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();
			}
		};
		File[] files = fDir.listFiles(fileFilter);
		return files;
	}

	/**
	 * 读取文件的内容，返回字符串类型的文件内容
	 * 
	 * @param _sFileName
	 *            - 文件名
	 * 
	 * @return
	 * @throws CMyException
	 */
	public static String readFile(String _sFileName) throws CMyException {
		return readFile(_sFileName, CMyString.FILE_WRITING_ENCODING);
	}

	/**
	 * 读取文件的内容，返回字符串类型的文件内容
	 * 
	 * @param _sFileName
	 *            - 文件名
	 * 
	 * @param _sEncoding
	 *            - 以指定的字符编码读取文件内容,默认为"UTF-8"
	 * @return
	 * @throws CMyException
	 */
	public static String readFile(String _sFileName, String _sEncoding)
			throws CMyException {
		FileReader fileReader = null;

		StringBuffer buffContent = null;

		FileInputStream fis = null;
		BufferedReader buffReader = null;
		if (_sEncoding == null) {
			_sEncoding = "UTF-8";
		}

		try {
			fis = new FileInputStream(_sFileName);
			buffReader = new BufferedReader(new InputStreamReader(fis,
					_sEncoding));
			String sLine;
			while ((sLine = buffReader.readLine()) != null) {
				if (buffContent == null)
					buffContent = new StringBuffer();
				else {
					buffContent.append("\n");
				}
				buffContent.append(sLine);
			}
			// 修改
			String str1 = buffContent == null ? "" : buffContent.toString();

			return str1;
		} catch (FileNotFoundException ex) {

			throw new CMyException(55, "要读取得文件没有找到(CMyFile.readFile)", ex);
		} catch (IOException ex) {
			throw new CMyException(53, "读文件时错误(CMyFile.readFile)", ex);
		} finally {
			try {
				if (fileReader != null)
					fileReader.close();
				if (buffReader != null)
					buffReader.close();
				if (fis != null)
					fis.close();
			} catch (Exception localException) {
			}
		}
	}

	/**
	 * @param _sFileName
	 * @return
	 * @throws CMyException
	 */
	public static byte[] readBytesFromFile(String _sFileName)
			throws CMyException {
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;

		byte[] arrayOfByte1;
		try {
			fis = new FileInputStream(_sFileName);
			byte[] buffer = new byte[1024];
			bos = new ByteArrayOutputStream(2048);
			int nLen = 0;
			while ((nLen = fis.read(buffer)) > 0) {
				bos.write(buffer, 0, nLen);
			}
			arrayOfByte1 = bos.toByteArray();

			return arrayOfByte1;
		} catch (Exception e) {
			throw new CMyException("读取文件[" + _sFileName + "]失败！");
		} finally {
			if (bos != null)
				try {
					bos.close();
				} catch (Exception localException1) {
				}
			if (fis != null)
				try {
					fis.close();
				} catch (Exception localException2) {
				}
		}
	}

	/**
	 * 以指定内容_sFileContent生成新的文件_sFileName
	 * 
	 * @param _sFileName
	 *            - 文件名
	 * @param _sFileContent
	 *            - 指定的内容
	 * @return
	 * @throws CMyException
	 */
	public static boolean writeFile(String _sFileName, String _sFileContent)
			throws CMyException {
		return writeFile(_sFileName, _sFileContent,
				CMyString.FILE_WRITING_ENCODING);
	}

	/**
	 * 以指定内容_sFileContent生成新的文件_sFileName
	 * 
	 * @param _sFileName
	 *            - 文件名
	 * @param _sFileContent
	 *            - 指定的内容
	 * @param _encoding
	 * @return
	 * @throws CMyException
	 */
	public static boolean writeFile(String _sFileName, String _sFileContent,
			String _encoding) throws CMyException {
		return writeFile(_sFileName, _sFileContent, _encoding, false);
	}

	/**
	 * @param paramString1
	 * @param paramString2
	 * @param paramString3
	 * @param paramBoolean
	 * @return
	 * @throws CMyException
	 */
	public static boolean writeFile(String paramString1, String paramString2,
			String paramString3, boolean paramBoolean) throws CMyException {
		String str1 = extractFilePath(paramString1);
		if (!pathExists(str1)) {
			makeDir(str1, true);
		}
		String str2 = CMyString.showNull(paramString3,
				CMyString.FILE_WRITING_ENCODING);

		int i = 0;

		FileOutputStream localFileOutputStream = null;
		OutputStreamWriter localOutputStreamWriter = null;
		try {
			localFileOutputStream = new FileOutputStream(paramString1);
			localOutputStreamWriter = new OutputStreamWriter(
					localFileOutputStream, str2);
			if (paramBoolean)
				localOutputStreamWriter.write(65279);
			localOutputStreamWriter.write(paramString2);

			i = 1;
		} catch (Exception localException1) {
			m_oLogger.error("写文件[" + paramString1 + "]发生异常", localException1);

			throw new CMyException(54, "写文件错误(CMyFile.writeFile)",
					localException1);
		} finally {
			try {
				if (localOutputStreamWriter != null) {
					localOutputStreamWriter.flush();
					localOutputStreamWriter.close();
				}
				if (localFileOutputStream != null)
					localFileOutputStream.close();
			} catch (Exception localException2) {
			}
		}
		/**
		 * 修改
		 */
		return i == 1 ? true : false;
	}

	/**
	 * 把指定的内容_sAddContent追加到文件_sFileName中
	 * 
	 * @param paramString1
	 *            - 文件名
	 * 
	 * @param paramString2
	 *            - 追加的内容
	 * @return
	 * @throws CMyException
	 */
	public static boolean appendFile(String paramString1, String paramString2)
			throws CMyException {
		int i = 0;

		RandomAccessFile localRandomAccessFile = null;
		try {
			localRandomAccessFile = new RandomAccessFile(paramString1, "rw");
			localRandomAccessFile.seek(localRandomAccessFile.length());
			localRandomAccessFile.writeBytes(paramString2);
			i = 1;
		} catch (Exception localException1) {
			throw new CMyException(50, "向文件追加内容时发生异常(CMyFile.appendFile)",
					localException1);
		} finally {
			try {
				if (localRandomAccessFile != null)
					localRandomAccessFile.close();
			} catch (Exception localException2) {
			}
		}
		return i == 1 ? true : false;
	}

	/**
	 * 移动文件
	 * 
	 * @param _sSrcFile
	 *            - 待移动的文件
	 * 
	 * @param _sDstFile
	 *            - 目标文件
	 * @return
	 * @throws CMyException
	 */
	public static boolean moveFile(String _sSrcFile, String _sDstFile)
			throws CMyException {
		return moveFile(_sSrcFile, _sDstFile, true);
	}

	/**
	 * 移动文件
	 * 
	 * @param _sSrcFile
	 *            - 待移动的文件
	 * @param _sDstFile
	 *            - 目标文件
	 * @param _bMakeDirIfNotExists
	 *            - 若目标路径不存在，是否创建;可缺省,默认值为true
	 * @return
	 * @throws CMyException
	 */
	public static boolean moveFile(String _sSrcFile, String _sDstFile,
			boolean _bMakeDirIfNotExists) throws CMyException {
		copyFile(_sSrcFile, _sDstFile, _bMakeDirIfNotExists);

		deleteFile(_sSrcFile);
		return false;
	}

	/**
	 * 复制文件
	 * 
	 * @param paramString1
	 *            - 源文件（必须包含路径）
	 * 
	 * @param paramString2
	 *            - 目标文件（必须包含路径）
	 * @return -若文件复制成功，则返回true；否则，返回false
	 * @throws CMyException
	 *             - 源文件不存在,或目标文件所在目录不存在,或文件复制失败,会抛出异常.
	 */
	public static boolean copyFile(String paramString1, String paramString2)
			throws CMyException {
		return copyFile(paramString1, paramString2, true);
	}

	/**
	 * @param paramString1
	 * @param paramString2
	 * @param paramBoolean
	 * @return
	 * @throws CMyException
	 */
	public static boolean copyFile(String paramString1, String paramString2,
			boolean paramBoolean) throws CMyException {
		return copyFile(paramString1, paramString2, paramBoolean, false);
	}

	/**
	 * @param paramString1
	 * @param paramString2
	 * @param paramBoolean1
	 * @param paramBoolean2
	 * @return
	 * @throws CMyException
	 */
	public static boolean copyFile(String paramString1, String paramString2,
			boolean paramBoolean1, boolean paramBoolean2) throws CMyException {
		FileInputStream localFileInputStream = null;
		FileOutputStream localFileOutputStream = null;
		try {
			localFileInputStream = new FileInputStream(paramString1);
			try {
				localFileOutputStream = new FileOutputStream(paramString2);
			} catch (FileNotFoundException localFileNotFoundException1) {
				if (paramBoolean1) {
					if (!makeDir(extractFilePath(paramString2), true)) {
						throw new CMyException(50, "为目标文件[" + paramString2
								+ "]创建目录失败！");
					}

					localFileOutputStream = new FileOutputStream(paramString2);
				} else {
					throw new CMyException(50, "指定目标文件[" + paramString2
							+ "]所在目录不存在！", localFileNotFoundException1);
				}

			}

			byte[] arrayOfByte = new byte[4096];
			int i;
			while ((i = localFileInputStream.read(arrayOfByte, 0, 4096)) > 0)
				localFileOutputStream.write(arrayOfByte, 0, i);
		} catch (FileNotFoundException localFileNotFoundException2) {
			throw new CMyException(55, "要复制的原文件没有发现(CMyFile.copyFile)",
					localFileNotFoundException2);
		} catch (IOException localIOException) {
			throw new CMyException(50, "复制文件时发生异常(CMyFile.copyFile)",
					localIOException);
		} finally {
			if (localFileOutputStream != null)
				try {
					localFileOutputStream.close();
				} catch (Exception localException1) {
				}
			if (localFileInputStream != null)
				try {
					localFileInputStream.close();
				} catch (Exception localException2) {
				}
		}
		if (paramBoolean2) {
			new File(paramString2).setLastModified(new File(paramString1)
					.lastModified());
		}

		return true;
	}

	/**
	 * map the resource related path to full real path
	 * 
	 * @param _resource
	 *            - related path of resource
	 * 
	 * @return - the full real path
	 * @throws WCMException
	 *             - if encounter errors
	 */
	public static String mapResouceFullPath(String _resource)
			throws WCMException {
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource(_resource);
		if (url == null) {
			throw new WCMException(55, "文件[" + _resource + "]没有找到！");
		}

		String sPath = null;
		try {
			sPath = url.getFile();
			if (sPath.indexOf('%') >= 0) {
				String enc = System.getProperty("file.encoding", "GBK");
				sPath = URLDecoder.decode(url.getFile(), enc);
			}
		} catch (Exception ex) {
			throw new WCMException(55, "文件[" + url.getFile() + "]转换失败！", ex);
		}
		return sPath;
	}

	/**
	 * @param _resource
	 * @param _currClass
	 * @return
	 * @throws WCMException
	 */
	public static String mapResouceFullPath(String _resource, Class _currClass)
			throws WCMException {
		URL url = _currClass.getResource(_resource);
		if (url == null) {
			throw new WCMException(55, "文件[" + _resource + "]没有找到！");
		}

		String sPath = null;
		try {
			sPath = url.getFile();
			if (sPath.indexOf('%') >= 0) {
				String enc = System.getProperty("file.encoding", "GBK");
				sPath = URLDecoder.decode(url.getFile(), enc);
			}
		} catch (Exception ex) {
			throw new WCMException(55, "文件[" + url.getFile() + "]转换失败！", ex);
		}
		return sPath;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			writeFile("c:\\test.txt", "中国人[]test", "GBK", true);

			String sSrcFile = "";

			sSrcFile = "d:\\temp\\InfoRadar.pdf";

			long lStartTime = System.currentTimeMillis();

			long lEndTime = System.currentTimeMillis();
			System.out.println("==============所用时间：" + (lEndTime - lStartTime)
					+ "ms ==============");

			sSrcFile = "d:\\write_test.html";
			String sContent = readFile(sSrcFile);
			lStartTime = System.currentTimeMillis();
			writeFile(sSrcFile + ".new", sContent);
			lEndTime = System.currentTimeMillis();
			System.out.println("==============所用时间：" + (lEndTime - lStartTime)
					+ "ms ==============");
		} catch (CMyException ex) {
			ex.printStackTrace(System.out);
		}
	}
}