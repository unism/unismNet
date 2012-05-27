package com.unism.infra.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @Title: CImage.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:08:46
 * @version CMS V1.0 
 */
public class CImage {
	/**
	 * @param p_PicFile
	 * @return
	 * @throws IOException
	 */
	public Image loadImage(String p_PicFile) throws IOException {
		Frame frame = new Frame();
		frame.addNotify();

		Toolkit toolkit = frame.getToolkit();
		Image i = toolkit.getImage(p_PicFile);

		MediaTracker mt = new MediaTracker(frame);
		mt.addImage(i, 0);
		try {
			mt.waitForID(0);
		} catch (InterruptedException localInterruptedException) {
		}
		if (mt.isErrorID(0)) {
			throw new IOException("Error loading image: " + p_PicFile);
		}
		return i;
	}

	/**
	 * @param p_Image
	 * @return
	 */
	public int getWidth(Image p_Image) {
		return p_Image.getWidth(null);
	}

	/**
	 * @param p_Image
	 * @return
	 */
	public int getHeight(Image p_Image) {
		return p_Image.getHeight(null);
	}

	/**
	 * @param p_Image
	 * @param p_Color
	 * @param p_sWatermark
	 * @param p_sDestFileName
	 * @throws IOException
	 */
	public void drawWatermark(Image p_Image, Color p_Color,
			String p_sWatermark, String p_sDestFileName) throws IOException {
		drawWatermark(p_Image, p_Color, p_sWatermark, p_sDestFileName, "宋体", 30);
	}

	/**
	 * 给图片加上水印
	 * @param p_Image  - 图片对象

	 * @param p_Color - 颜色
	 * @param p_sWatermark - 水印
	 * @param p_sDestFileName - 目标文件
	 * @param _sFont - 字体（可省略，默认为宋体）

	 * @param _nFontSize - 字大小（可省略，默认为30）
	 * @throws IOException
	 */
	public void drawWatermark(Image p_Image, Color p_Color,
			String p_sWatermark, String p_sDestFileName, String _sFont,
			int _nFontSize) throws IOException {
		int nPicWidth = getWidth(p_Image);
		int nPicHeight = getHeight(p_Image);
		BufferedImage buffImg = new BufferedImage(nPicWidth, nPicHeight, 1);
		Graphics2D g = buffImg.createGraphics();
		g.drawImage(p_Image, 0, 0, null);

		g.setColor(p_Color);

		Font tmpFont = new Font(_sFont, 1, _nFontSize);
		g.setFont(tmpFont);

		g.drawString(p_sWatermark, nPicWidth / 2, nPicHeight / 2);

		FileOutputStream fileOut = new FileOutputStream(p_sDestFileName);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fileOut);
		encoder.encode(buffImg);
	}

	/**
	 * @param p_sSrcPicName
	 * @param p_sDestPicName
	 * @param p_nSmallPicWidth
	 * @throws IOException
	 */
	public void createSmallImage(String p_sSrcPicName, String p_sDestPicName,
			int p_nSmallPicWidth) throws IOException {
		Frame frame = new Frame();
		frame.addNotify();

		Toolkit toolkit = frame.getToolkit();

		Image i = toolkit.getImage(p_sSrcPicName);

		MediaTracker mt = new MediaTracker(frame);
		mt.addImage(i, 0);
		try {
			mt.waitForID(0);
		} catch (InterruptedException localInterruptedException) {
		}
		if (mt.isErrorID(0)) {
			throw new IOException("Error loading image: " + p_sSrcPicName);
		}

		int nPicWidth = i.getWidth(null);
		int nPicHeight = i.getHeight(null);

		if (p_nSmallPicWidth < 108)
			p_nSmallPicWidth = 108;
		int nPicMax;
		if (nPicWidth >= nPicHeight)
			nPicMax = nPicWidth;
		else {
			nPicMax = nPicHeight;
		}
		double lnPicRate = p_nSmallPicWidth / nPicMax;
		int nBuffHeight;
		int nBuffWidth;
		if (nPicHeight < nPicMax) {
			nBuffWidth = p_nSmallPicWidth;
			nBuffHeight = (int) (lnPicRate * nPicHeight);
		} else {
			nBuffHeight = p_nSmallPicWidth;
			nBuffWidth = (int) (lnPicRate * nPicWidth);
		}

		FileOutputStream fileOut = null;

		File File_SmallPic = new File(p_sDestPicName);
		if (File_SmallPic.exists()) {
			File_SmallPic.delete();
		}

		if (nPicMax < p_nSmallPicWidth) {
			BufferedImage image = new BufferedImage(nBuffWidth, nBuffHeight, 1);
			Graphics2D g = image.createGraphics();
			AffineTransform at = AffineTransform.getTranslateInstance(0.0D,
					0.0D);
			at.setToScale(lnPicRate, lnPicRate);
			g.transform(at);
			g.drawImage(i, 0, 0, null);

			fileOut = new FileOutputStream(p_sDestPicName);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fileOut);
			encoder.encode(image);
			fileOut.close();
		} else {
			BufferedImage image = new BufferedImage(nPicWidth, nPicHeight, 1);
			Graphics2D g = image.createGraphics();
			AffineTransform at = AffineTransform.getTranslateInstance(0.0D,
					0.0D);
			at.setToScale(lnPicRate, lnPicRate);
			g.transform(at);
			g.drawImage(i, 0, 0, null);

			BufferedImage image_sub = image.getSubimage(0, 0, nBuffWidth,
					nBuffHeight);
			fileOut = new FileOutputStream(p_sDestPicName);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fileOut);
			encoder.encode(image_sub);
			fileOut.close();
		}
		toolkit.sync();
	}

	/**
	 * @param p_srcFileName
	 * @param p_destFileName
	 */
	public void moveFile(String p_srcFileName, String p_destFileName) {
		try {
			FileInputStream in = new FileInputStream(p_srcFileName);
			FileOutputStream out = new FileOutputStream(p_destFileName);
			int c;
			while ((c = in.read()) != -1) {
				out.write(c);
			}
			in.close();
			out.close();
			File inputFile = new File(p_srcFileName);
			inputFile.delete();
		} catch (IOException ex) {
			ex.printStackTrace(System.out);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sTmpPicName_Normal = "d:/temp/20090510084456962.jpg";
		String sTmpPicName_Small = "d:/temp/20090510084456962.jpg";

		CImage m_Image = new CImage();
		try {
			m_Image
					.createSmallImage(sTmpPicName_Normal, sTmpPicName_Small,
							200);
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
		try {
			Image i = m_Image.loadImage(sTmpPicName_Normal);
			m_Image.drawWatermark(i, Color.red, "我是徐娟",
					"d:/temp/small.jpg");
			int nPicWidth = m_Image.getWidth(i);
			int nPicHeight = m_Image.getHeight(i);
			System.out.println("------the normal pic------xujuan-------------");
			System.out.println(nPicWidth);
			System.out.println(nPicHeight);
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
	}
}