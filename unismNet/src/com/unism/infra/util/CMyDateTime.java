package com.unism.infra.util;

import java.io.PrintStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @Title: CMyDateTime.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:12:24
 * @version CMS V1.0 
 */
public class CMyDateTime implements Cloneable, Serializable {
	//
	private java.util.Date m_dtDate = null;
 
	//
	private SimpleDateFormat m_dtFormater = null;
	// 日时字符串格式：默认格式
	public static final int FORMAT_DEFAULT = 0;
	// 日时字符串格式：长格式（如：年份用4位表示）
	public static final int FORMAT_LONG = 1;
	// 日时字符串格式：短格式（如：年份用2位表示）
	public static final int FORMAT_SHORT = 2;
	// 默认日期字符串格式 "yyyy-MM-dd" （用于java程序）
	public static final String DEF_DATE_FORMAT_PRG = "yyyy-MM-dd";
	// 默认时间字符串格式 "HH:mm:ss" （用于java程序）
	public static final String DEF_TIME_FORMAT_PRG = "HH:mm:ss";
	// 默认日时字符串格式 "yyyy-MM-dd HH:mm:ss" （用于java程序）
	public static final String DEF_DATETIME_FORMAT_PRG = "yyyy-MM-dd HH:mm:ss";
	// 默认日时字符串格式 "YYYY-MM-DD HH24:MI:SS" （用于Oracle DB）
	public static final String DEF_DATETIME_FORMAT_DB = "YYYY-MM-DD HH24:MI:SS";
	// 日期/时间的各个部分标识：年（1）
	public static final int YEAR = 1;
	// 日期/时间的各个部分标识：月（2）
	public static final int MONTH = 2;
	// 日期/时间的各个部分标识：日（3）
	public static final int DAY = 3;
	// 日期/时间的各个部分标识：时（4）
	public static final int HOUR = 4;
	// 日期/时间的各个部分标识：分（5）
	public static final int MINUTE = 5;
	// 日期/时间的各个部分标识：秒（6）
	public static final int SECOND = 6;
	// 日期/时间的各个部分标识：一刻钟（11）
	public static final int QUATER = 11;
	// 日期/时间的各个部分标识：一周（12）
	public static final int WEEK = 12;
	// 当月天数（13）
	public static final int DAY_OF_MONTH = 13;
	// 当月周数（14）
	public static final int WEEK_OF_MONTH = 14;
	// 当年天数（15）
	public static final int DAY_OF_YEAR = 15;
	// 当月周数（14）
	public static final int WEEK_OF_YEAR = 16;
	// 1 天的时间数（毫秒数）
	public static final long ADAY_MILLIS = 86400000L;

	/**
	 * 默认构造函数
	 */
	public CMyDateTime() {
	}

	/**
	 * 使用长整型的数构造日期对象
	 * 
	 * @param p_lDate
	 */
	public CMyDateTime(long p_lDate) {
		this.m_dtDate = new java.util.Date(p_lDate);
	}

	/**
	 * 获取值为系统当前时间的CMyDateTime对象
	 * 
	 * @return
	 */
	public static CMyDateTime now() {
		CMyDateTime mydtNow = new CMyDateTime();
		mydtNow.setDateTimeWithCurrentTime();
		return mydtNow;
	}

	/**
	 * 判断时间对象是否为空
	 * 
	 * @return
	 */
	public boolean isNull() {
		return this.m_dtDate == null;
	}

	/**
	 * 获取时间值（毫秒数）
	 * 
	 * @return
	 */
	public long getTimeInMillis() {
		return this.m_dtDate == null ? 0L : this.m_dtDate.getTime();
	}

	/**
	 * 取当前时区差值(单位：毫秒)
	 * <p>
	 * 如：东8区，getTimeZoneRawOffset()=1000*60*60*8=28800000
	 * 
	 * @return
	 */
	public static int getTimeZoneRawOffset() {
		TimeZone timeZone = TimeZone.getDefault();
		int nOffset = timeZone.getRawOffset();
		return nOffset;
	}

	/**
	 * 时间比较
	 * 
	 * @param p_dtAnother
	 *            - 待比较的时间对象：java.util.Date对象
	 * @return 两者之间的时间差（毫秒数）。
	 */
	public long compareTo(java.util.Date p_dtAnother) {
		long lMyTime = this.m_dtDate == null ? 0L : this.m_dtDate.getTime();
		long lAnotherTime = p_dtAnother == null ? 0L : p_dtAnother.getTime();
		return lMyTime - lAnotherTime;
	}

	/**
	 * 时间比较
	 * 
	 * @param p_mydtAnother
	 *            - 待比较的时间对象：CMyDateTime对象
	 * @return 两者之间的时间差（毫秒数）。
	 */
	public long compareTo(CMyDateTime p_mydtAnother) {
		return compareTo(p_mydtAnother.getDateTime());
	}

	/**
	 * 取与指定时间之间的（年/月/日/小时/分/秒/季度/周）差
	 * 
	 * @param p_nPart
	 *            - 定义返回值的类型（CMyDateTime.YEAR等）
	 * @param p_mydtAnother
	 *            - 指定的时间对象：CMyDateTime
	 * @return
	 * @throws CMyException
	 */
	public long dateDiff(int p_nPart, CMyDateTime p_mydtAnother)
			throws CMyException {
		if (p_mydtAnother == null) {
			throw new CMyException(10,
					"无效的日期时间对象参数(CMyDateTime.dateDiff(CMyDateTime))");
		}
		return dateDiff(p_nPart, p_mydtAnother.getDateTime());
	}

	/**
	 * 取与指定时间之间的（年/月/日/小时/分/秒/季度/周）差
	 * 
	 * @param p_nPart
	 *            - 定义返回值的类型（CMyDateTime.YEAR等）
	 * @param p_dtAnother
	 *            - 指定的时间对象：java.util.Date
	 * 
	 * @return
	 * @throws CMyException
	 */
	public long dateDiff(int p_nPart, java.util.Date p_dtAnother)
			throws CMyException {
		if (p_dtAnother == null)
			throw new CMyException(10,
					"无效的日期时间参数（CMyDateTime.dateDiff(int,java.util.Date)）");
		if (isNull()) {
			throw new CMyException(10,
					"日期时间为空（CMyDateTime.dateDiff(int,java.util.Date)）");
		}

		if (p_nPart == 1)
			return dateDiff_year(p_dtAnother);
		if (p_nPart == 2) {
			return dateDiff_month(p_dtAnother);
		}

		long lMyTime = this.m_dtDate == null ? 0L : this.m_dtDate.getTime();
		long lAnotherTime = p_dtAnother == null ? 0L : p_dtAnother.getTime();
		long lDiffTime = (lMyTime - lAnotherTime) / 1000L;

		switch (p_nPart) {
		case 3:
			return lDiffTime / 86400L;
		case 4:
			return lDiffTime / 3600L;
		case 5:
			return lDiffTime / 60L;
		case 6:
			return lDiffTime;
		case 11:
			return lDiffTime / 86400L / 91L;
		case 12:
			return lDiffTime / 86400L / 7L;
		case 7:
		case 8:
		case 9:
		case 10:
		}
		throw new CMyException(10,
				"参数无效(CMyDateTime.dateDiff(int,java.util.Date))");
	}

	/**
	 * 
	 * @param p_dtAnother
	 * @return
	 */
	private long dateDiff_year(java.util.Date p_dtAnother) {
		Calendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getDefault());

		cal.setTime(this.m_dtDate);
		int nYear1 = cal.get(1);
		int nMonth1 = cal.get(2);

		cal.setTime(p_dtAnother);
		int nYear2 = cal.get(1);
		int nMonth2 = cal.get(2);

		if (nYear1 == nYear2)
			return 0L;
		if (nYear1 > nYear2) {
			return nYear1 - nYear2 + (nMonth1 >= nMonth2 ? 0 : -1);
		}
		return nYear1 - nYear2 + (nMonth1 > nMonth2 ? 1 : 0);
	}

	/**
	 * 计算当前时间对象与指定时间的【月】差
	 * 
	 * @param p_dtAnother
	 *            - 指定的时间对象
	 * @return
	 */
	public long dateDiff_month(java.util.Date p_dtAnother) {
		Calendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getDefault());

		cal.setTime(this.m_dtDate);
		int nMonths1 = cal.get(1) * 12 + cal.get(2);
		int nDay1 = cal.get(5);

		cal.setTime(p_dtAnother);
		int nMonths2 = cal.get(1) * 12 + cal.get(2);
		int nDay2 = cal.get(5);

		if (nMonths1 == nMonths2)
			return 0L;
		if (nMonths1 > nMonths2) {
			return nMonths1 - nMonths2 + (nDay1 < nDay2 ? -1 : 0);
		}
		return nMonths1 - nMonths2 + (nDay1 > nDay2 ? 1 : 0);
	}

	/**
	 * 日期/时间的分解
	 * 
	 * @param p_nField
	 *            - 指定域（年/月/日/小时/分/秒/星期）编号（定义在CMyDateTime.YEAR）
	 * @return
	 * @throws CMyException
	 */
	public int get(int p_nField) throws CMyException {
		if (this.m_dtDate == null) {
			throw new CMyException(20, "日期时间为空（CMyDateTime.get）");
		}

		Calendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getDefault());
		cal.setTime(this.m_dtDate);
		switch (p_nField) {
		case 1:
			return cal.get(1);
		case 2:
			return cal.get(2) + 1;
		case 3:
			return cal.get(5);
		case 4:
			return cal.get(11);
		case 5:
			return cal.get(12);
		case 6:
			return cal.get(13);
		case 12:
			return cal.get(7);
		case 13:
			return ((GregorianCalendar) cal).getActualMaximum(5);
		case 14:
			return getWeekCountsOfMonth(true);
		case 15:
			return ((GregorianCalendar) cal).getActualMaximum(6);
		case 16:
			return ((GregorianCalendar) cal).getActualMaximum(3);
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		}
		throw new CMyException(10, "无效的日期时间域参数（CMyDateTime.get）");
	}

	/**
	 * 获取当前对象中的【年】的部分
	 * 
	 * @return
	 * @throws CMyException
	 */
	public int getYear() throws CMyException {
		return get(1);
	}

	/**
	 * 获取当前对象中的【月】的部分
	 * 
	 * @return
	 * @throws CMyException
	 */
	public int getMonth() throws CMyException {
		return get(2);
	}

	/**
	 * 获取当前对象中的【日】的部分
	 * 
	 * @return
	 * @throws CMyException
	 */
	public int getDay() throws CMyException {
		return get(3);
	}

	/**
	 * 获取当前对象中的【小时】的部分
	 * 
	 * @return
	 * @throws CMyException
	 */
	public int getHour() throws CMyException {
		return get(4);
	}

	/**
	 * 获取当前对象中的【分】的部分
	 * 
	 * @return
	 * @throws CMyException
	 */
	public int getMinute() throws CMyException {
		return get(5);
	}

	/**
	 * 获取当前对象中的【秒】的部分
	 * 
	 * @return
	 * @throws CMyException
	 */
	public int getSecond() throws CMyException {
		return get(6);
	}

	/**
	 * 取当前日期是所在week的第几天
	 * <p>
	 * 说明：一个礼拜的第一天为Monday(0)，最后一天为Sunday(6)
	 * 
	 * @return
	 * @throws CMyException
	 */
	public int getDayOfWeek() throws CMyException {
		return get(12);
	}

	/**
	 * 日期时间增/减函数
	 * 
	 * @param p_nField
	 *            - 指定域（例如CMyDateTime.YEAR等）
	 * @param p_nAdd
	 *            - 增加数目（负值为减）
	 * @return 返回当前对象本身
	 * @throws CMyException
	 */
	public CMyDateTime dateAdd(int p_nField, int p_nAdd) throws CMyException {
		if (this.m_dtDate == null) {
			throw new CMyException(20, "日期时间为空（CMyDateTime.dateAdd）");
		}

		int nCalField = 0;
		switch (p_nField) {
		case 1:
			nCalField = 1;
			break;
		case 2:
			nCalField = 2;
			break;
		case 12:
			nCalField = 5;
			p_nAdd *= 7;
			break;
		case 3:
			nCalField = 5;
			break;
		case 4:
			nCalField = 10;
			break;
		case 5:
			nCalField = 12;
			break;
		case 6:
			nCalField = 13;
			break;
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		default:
			throw new CMyException(10, "无效的日期时间域参数（CMyDateTime.dateAdd）");
		}

		Calendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getDefault());
		cal.setTime(this.m_dtDate);
		cal.set(nCalField, cal.get(nCalField) + p_nAdd);
		this.m_dtDate = cal.getTime();
		return this;
	}

	/**
	 * 对象克隆
	 */
	public synchronized Object clone() {
		CMyDateTime newMyDateTime = new CMyDateTime();
		newMyDateTime.m_dtDate = (this.m_dtDate == null ? null
				: (java.util.Date) this.m_dtDate.clone());
		newMyDateTime.m_dtFormater = (this.m_dtFormater == null ? null
				: (SimpleDateFormat) this.m_dtFormater.clone());
		return newMyDateTime;
	}

	/**
	 * 将当前对象转换为java.util.Date对象
	 * 
	 * @return
	 */
	public java.util.Date getDateTime() {
		return this.m_dtDate;
	}

	/**
	 * 
	 */
	public String toString() {
		return toString("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将日期数据转化为指定格式的字符串
	 * 
	 * @param p_sFormat
	 *            - 指定的格式（例如"yyyy-MM-dd HH:mm:ss"）可省略，默认为"yyyy-MM-dd HH:mm:ss"
	 * @return
	 * @see toString(String p_sFormat)
	 */
	public String toString(String p_sFormat) {
		if (this.m_dtDate == null)
			return null;
		try {
			return getDateTimeAsString(p_sFormat);
		} catch (CMyException ex) {
		}
		return null;
	}

	/**
	 * 
	 * @param sFormat
	 * @param sLocaleId
	 * @param sTimezoneId
	 * @return
	 */
	public String toString(String sFormat, String sLocaleId, String sTimezoneId) {
		if (this.m_dtDate == null) {
			return null;
		}
		boolean bWithLocale = !CMyString.isEmpty(sLocaleId);
		boolean bWithTimezone = !CMyString.isEmpty(sTimezoneId);
		if ((!bWithLocale) && (!bWithTimezone))
			return toString(sFormat);
		try {
			Locale locale = bWithLocale ? new Locale(sLocaleId) : Locale
					.getDefault();
			TimeZone timeZone = bWithTimezone ? TimeZone
					.getTimeZone(sTimezoneId) : TimeZone.getDefault();
			DateFormat df = new SimpleDateFormat(sFormat, locale);
			df.setTimeZone(timeZone);
			return df.format(this.m_dtDate);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 转化为java.sq.Date型数据
	 * 
	 * @return
	 */
	public java.sql.Date toDate() {
		if (this.m_dtDate == null)
			return null;
		return new java.sql.Date(this.m_dtDate.getTime());
	}

	/**
	 * 转化为java.sql.Time型数据
	 * 
	 * @return
	 */
	public Time toTime() {
		if (this.m_dtDate == null)
			return null;
		return new Time(this.m_dtDate.getTime());
	}

	/**
	 * 转化为java.sql.Timestamp型数据
	 * 
	 * @return
	 */
	public Timestamp toTimestamp() {
		if (this.m_dtDate == null)
			return null;
		return new Timestamp(this.m_dtDate.getTime());
	}

	/**
	 * 使用 java.util.Date 对象设置日期和时间
	 * 
	 * @param p_dtDate
	 *            - java.util.Date 对象
	 */
	public void setDateTime(java.util.Date p_dtDate) {
		this.m_dtDate = p_dtDate;
	}

	/**
	 * 使用字符串设置日期和时 间
	 * 
	 * @param p_sValue
	 *            - 日期和时间字符串
	 * @param p_sFormat
	 *            - 日期和时间字符串格式
	 * @return
	 * @throws CMyException
	 */
	public boolean setDateTimeWithString(String p_sValue, String p_sFormat)
			throws CMyException {
		try {
			SimpleDateFormat dtFormat = new SimpleDateFormat(p_sFormat);
			this.m_dtDate = dtFormat.parse(p_sValue);
			return true;
		} catch (Exception ex) {
			throw new CMyException(10,
					"日期时间字符串值和格式无效（CMyDateTime.setDateTimeWithString）", ex);
		}

	}

	/**
	 * 设置日期和时间为系统当前日期时间
	 */
	public void setDateTimeWithCurrentTime() {
		if (this.m_dtDate == null)
			this.m_dtDate = new java.util.Date(System.currentTimeMillis());
		else
			this.m_dtDate.setTime(System.currentTimeMillis());
	}

	/**
	 * 使用Timestamp对象设置日期和时间
	 * 
	 * @param p_tsDateTime
	 *            - java.sql.Timestamp对象
	 * @throws CMyException
	 */
	public void setDateTimeWithTimestamp(Timestamp p_tsDateTime)
			throws CMyException {
		try {
			if (p_tsDateTime == null) {
				this.m_dtDate = null;
			} else {
				if (this.m_dtDate == null) {
					this.m_dtDate = new java.util.Date();
				}
				this.m_dtDate.setTime(p_tsDateTime.getTime());
			}
		} catch (Exception ex) {
			throw new CMyException(
					0,
					"使用Timestamp对象设置日期和时间出错：CMyDateTime.setDateTimeWithTimestamp()",
					ex);
		}
	}

	/**
	 * 从记录集当前行读取指定日期/时间字段的值
	 * 
	 * @param p_rsData
	 *            - java.sql.ResultSet对象
	 * @param p_nFieldIndex
	 *            - 指定Date字段的位置
	 * @throws CMyException
	 */
	public void setDateTimeWithRs(ResultSet p_rsData, int p_nFieldIndex)
			throws CMyException {
		try {
			Timestamp tsDateTime = p_rsData.getTimestamp(p_nFieldIndex);
			setDateTimeWithTimestamp(tsDateTime);
		} catch (SQLException ex) {
			throw new CMyException(40,
					"从记录集中读取时间字段时出错：CMyDateTime.setDateTimeWithRs()", ex);
		}
	}

	/**
	 * 从记录集当前行读取指定日期/时间字段的值
	 * 
	 * @param p_rsData
	 *            - java.sql.ResultSet对象
	 * @param p_sFieldName
	 *            - 指定Date字段名称
	 * @throws CMyException
	 */
	public void setDateTimeWithRs(ResultSet p_rsData, String p_sFieldName)
			throws CMyException {
		try {
			Timestamp tsDateTime = p_rsData.getTimestamp(p_sFieldName);
			setDateTimeWithTimestamp(tsDateTime);
		} catch (SQLException ex) {
			throw new CMyException(40,
					"从记录集中读取时间字段时出错：CMyDateTime.setDateTimeWithRs()", ex);
		}
	}

	/**
	 * 设置日期值
	 * <p>
	 * 说明：若当前日期时间非空，则仅改变日期值，时间值保持不变
	 * 
	 * @param p_dDate
	 * @return
	 * @throws CMyException
	 */
	public boolean setDate(java.sql.Date p_dDate) throws CMyException {
		if (p_dDate == null)
			return false;
		return setDateWithString(p_dDate.toString(), 0);
	}

	/**
	 * 设置时间值
	 * <p>
	 * 说明：若当前日期时间非空，则仅改变时间值，日期值保持不变
	 * 
	 * @param p_tTime
	 * @return
	 * @throws CMyException
	 */
	public boolean setTime(Time p_tTime) throws CMyException {
		if (p_tTime == null)
			return false;
		return setTimeWithString(p_tTime.toString(), 0);
	}

	/**
	 * 使用字符串设置日期值
	 * 
	 * @param p_sDateValue
	 *            - 日期值字符串
	 * 
	 * @param p_nFormatType
	 *            - 日期格式类型
	 * @return
	 * @throws CMyException
	 *             若格式不正确，则抛出异常
	 */
	public boolean setDateWithString(String p_sDateValue, int p_nFormatType)
			throws CMyException {
		boolean blHasSepChar = false;
		int nLen = p_sDateValue.length();

		if (nLen < 6)
			throw new CMyException(10, "日期字符串无效（CMyDateTime.setDateWithString）");
		try {
			String sDateValue;
			switch (p_nFormatType) {
			case 1:
				blHasSepChar = nLen >= 10;
				sDateValue = p_sDateValue.substring(0, 4)
						+ "-"
						+ p_sDateValue.substring(blHasSepChar ? 5 : 4,
								blHasSepChar ? 7 : 6)
						+ "-"
						+ p_sDateValue.substring(blHasSepChar ? 8 : 6,
								blHasSepChar ? 10 : 8);
				break;
			case 2:
				sDateValue = p_sDateValue.charAt(0) < '5' ? "20" : "19";
				blHasSepChar = nLen >= 8;
				sDateValue = sDateValue
						+ p_sDateValue.substring(0, 2)
						+ "-"
						+ p_sDateValue.substring(blHasSepChar ? 3 : 2,
								blHasSepChar ? 5 : 4)
						+ "-"
						+ p_sDateValue.substring(blHasSepChar ? 6 : 4,
								blHasSepChar ? 8 : 6);
				break;
			default:
				sDateValue = p_sDateValue;
			}

			if (this.m_dtDate == null) {
				return setDateTimeWithString(sDateValue, "yyyy-MM-dd");
			}

			String sTimeValue = getDateTimeAsString("HH:mm:ss");
			return setDateTimeWithString(sDateValue + " " + sTimeValue,
					"yyyy-MM-dd HH:mm:ss");
		} catch (Exception ex) {
			throw new CMyException(10,
					"无效的日期字符串（CMyException.setDateWithString）", ex);
		}

	}

	/**
	 * 使用字符串设置时间值
	 * 
	 * @param p_sTimeValue
	 *            - 时间值字符串
	 * @param p_nFormatType
	 *            - 时间格式类型（例如CMyDateTime.FORMAT_LONG等）
	 * @return
	 * @throws CMyException
	 *             若格式不正确，则抛出异常
	 */
	public boolean setTimeWithString(String p_sTimeValue, int p_nFormatType)
			throws CMyException {
		boolean blHasSepChar = false;
		int nLen = p_sTimeValue.length();

		if (nLen < 4)
			throw new CMyException(10, "时间字符串格式无效（）");
		try {
			String sTimeValue;
			switch (p_nFormatType) {
			case 1:
				blHasSepChar = nLen >= 8;
				sTimeValue = p_sTimeValue.substring(0, 2)
						+ ":"
						+ p_sTimeValue.substring(blHasSepChar ? 3 : 2,
								blHasSepChar ? 5 : 4)
						+ ":"
						+ p_sTimeValue.substring(blHasSepChar ? 6 : 4,
								blHasSepChar ? 8 : 6);
				break;
			case 2:
				blHasSepChar = nLen >= 5;
				sTimeValue = p_sTimeValue.substring(0, 2)
						+ ":"
						+ p_sTimeValue.substring(blHasSepChar ? 3 : 2,
								blHasSepChar ? 5 : 4) + ":00";
				break;
			default:
				sTimeValue = p_sTimeValue;
			}

			if (this.m_dtDate == null) {
				return setDateTimeWithString(sTimeValue, "HH:mm:ss");
			}

			String sDateValue = getDateTimeAsString("yyyy-MM-dd");
			return setDateTimeWithString(sDateValue + " " + sTimeValue,
					"yyyy-MM-dd HH:mm:ss");
		} catch (Exception ex) {
			throw new CMyException(10,
					"无效的时间字符串（CMyException.setTimeWithString）", ex);
		}

	}

	/**
	 * 设置日期时间字符串格式
	 * 
	 * @param p_sFormat
	 *            - 指定格式（例如："yyyy-MM-dd HH:mm:ss"）
	 */
	public void setDateTimeFormat(String p_sFormat) {
		if (this.m_dtFormater == null)
			this.m_dtFormater = new SimpleDateFormat(p_sFormat);
		else
			this.m_dtFormater.applyPattern(p_sFormat);
	}

	/**
	 * 获取格式化的日期时间字符串
	 * 
	 * @param p_sFormat
	 *            - 指定日期时间字符串格式（例如："yyyy-MM-dd HH:mm:ss"）
	 * @return
	 * @throws CMyException
	 */
	public String getDateTimeAsString(String p_sFormat) throws CMyException {
		if (this.m_dtDate == null)
			return null;
		try {
			setDateTimeFormat(p_sFormat);
			return this.m_dtFormater.format(this.m_dtDate);
		} catch (Exception ex) {
			throw new CMyException(10,
					"指定的日期时间格式有错（CMyDateTime.getDateTimeAsString）", ex);
		}

	}

	/**
	 * 输出格式化的日期时间字符串 格式：由setDateTimeFormat指定
	 * 
	 * @return 若日期时间为空，或者格式化对象m_dtFormater为空，返回null
	 * @throws CMyException
	 */
	public String getDateTimeAsString() throws CMyException {
		if ((this.m_dtDate == null) || (this.m_dtFormater == null))
			return null;
		try {
			return this.m_dtFormater.format(this.m_dtDate);
		} catch (Exception ex) {
			throw new CMyException(0,
					"格式化日期时间字符串出错（CMyDateTime.getDateTimeAsString()）", ex);
		}

	}

	/**
	 * 提取 日期时间 字符串数据的格式
	 * 
	 * @param _sValue
	 *            - 指定的日期时间字符串
	 * @return 若正确解析，则返回日时字符串的格式字符串；否则返回null
	 */
	public static String extractDateTimeFormat(String _sValue) {
		char[] FORMAT_CHAR = { 'y', 'M', 'd', 'H', 'm', 's' };

		return extractFormat(_sValue, FORMAT_CHAR);
	}

	/**
	 * 提取 日期 字符串数据的格式
	 * 
	 * @param _sValue
	 *            - 指定的日期字符串
	 * 
	 * @return 若正确解析，则返回日期字符串的格式字符串；否则返回null。
	 */
	public static String extractDateFormat(String _sValue) {
		char[] FORMAT_CHAR = { 'y', 'M', 'd' };

		return extractFormat(_sValue, FORMAT_CHAR);
	}

	/**
	 * 提取 时间 字符串数据的格式
	 * 
	 * @param _sValue
	 *            - 指定的时间字符串
	 * @return 若正确解析，则返回时间字符串的格式字符串；否则返回null
	 */
	public static String extractTimeFormat(String _sValue) {
		char[] FORMAT_CHAR = { 'H', 'm', 's' };

		return extractFormat(_sValue, FORMAT_CHAR);
	}

	/**
	 * 
	 * @param _sValue
	 * @param _formatChar
	 * @return
	 */
	private static String extractFormat(String _sValue, char[] _formatChar) {
		if (_sValue == null) {
			return null;
		}
		char[] buffValue = _sValue.trim().toCharArray();
		if (buffValue.length == 0) {
			return null;
		}

		StringBuffer buffFormat = new StringBuffer(19);

		int nAt = 0;
		int nAtField = 0;

		while (nAt < buffValue.length) {
			char aChar = buffValue[(nAt++)];
			if (Character.isDigit(aChar)) {
				buffFormat.append(_formatChar[nAtField]);
			} else {
				buffFormat.append(aChar);
				nAtField++;
				if (nAtField >= _formatChar.length) {
					break;
				}
			}
		}
		return buffFormat.toString();
	}

	/**
	 * 使用未知格式的字符串值设置日期时间
	 * 
	 * @param _sValue
	 *            - 指定的日期时间值（字符串）
	 * @return 若设置成功，则返回true；否则返回false
	 * @throws CMyException
	 *             解析日期时间值（字符串）可能抛出异常
	 */
	public boolean setDateTimeWithString(String _sValue) throws CMyException {
		String sFormat = extractDateTimeFormat(_sValue);
		if (_sValue == null) {
			return false;
		}

		return setDateTimeWithString(_sValue, sFormat);
	}

	/**
	 * 把以毫秒计算的使用时间格式化为“xxx分xx秒”的格式
	 * 
	 * @param iMillis
	 * @return 格式化的时间
	 */
	public static final String formatTimeUsed(long iMillis) {
		if (iMillis <= 0L) {
			return "";
		}

		int iSecond = 0;
		int iMinute = 0;
		StringBuffer sb = new StringBuffer(16);
		iSecond = (int) (iMillis / 1000L);
		iMillis %= 1000L;
		if (iSecond > 0) {
			iMinute = iSecond / 60;
			iSecond %= 60;
		}
		if (iMinute > 0) {
			sb.append(iMinute).append('分');
			if (iSecond < 10) {
				sb.append('0');
			}
			sb.append(iSecond);
		} else {
			sb.append(iSecond).append('.');
			if (iMillis < 10L)
				sb.append('0').append('0');
			else if (iMillis < 100L) {
				sb.append('0');
			}
			sb.append(iMillis);
		}
		sb.append('秒');

		return sb.toString();
	}

	/**
	 * 
	 * @param _dtTime
	 * @param _sFormat
	 * @return
	 */
	public static String getStr(Object _dtTime, String _sFormat) {
		if ((_dtTime instanceof CMyDateTime)) {
			return ((CMyDateTime) _dtTime).toString(_sFormat);
		}

		return CMyString.showObjNull(_dtTime);
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CMyDateTime myDateTime = new CMyDateTime();
		try {
			CMyDateTime now = now();
			System.out.println("now:" + now.toString("yyyy-MM-dd HH:mm:ss"));
			CMyDateTime nowClone = (CMyDateTime) now.clone();

			CMyDateTime execStartTime = nowClone.dateAdd(3, -30);
			System.out.println("now-30:"
					+ execStartTime.toString("yyyy-MM-dd HH:mm:ss"));
			System.out.println("now:" + now.toString("yyyy-MM-dd HH:mm:ss"));
			System.out.println("nowClone:"
					+ nowClone.toString("yyyy-MM-dd HH:mm:ss"));

			now = now();
			execStartTime = now.dateAdd(3, -3);
			System.out.println("now-3:"
					+ execStartTime.toString("yyyy-MM-dd HH:mm:ss"));

			now = now();
			execStartTime = now.dateAdd(4, -3);
			System.out.println("now-3:"
					+ execStartTime.toString("yyyy-MM-dd HH:mm:ss"));

			execStartTime.setDateTimeWithString("2002.1.1 00:00:00",
					"yyyy.MM.dd HH:mm:ss");
			System.out.println("time:"
					+ execStartTime.toString("yyyy-MM-dd HH:mm:ss"));

			execStartTime.setDateTimeWithString(execStartTime
					.toString("yyyy-MM-dd")
					+ " 23:00:00", "yyyy-MM-dd HH:mm:ss");

			CMyDateTime execEndTime = now();
			execEndTime.setDateTimeWithString(execEndTime
					.toString("yyyy-MM-dd")
					+ " 24:00:00", "yyyy-MM-dd HH:mm:ss");

			System.out.println("now:" + now.toString());
			System.out.println("execStartTime:" + execStartTime.toString());
			System.out.println("now.compareTo(execStartTime):"
					+ now.compareTo(execStartTime));

			System.out.println("TimeZone = " + getTimeZoneRawOffset());

			myDateTime.setDateTimeWithCurrentTime();
			System.out.println("Start:"
					+ myDateTime.getDateTimeAsString("yyyy/MM/dd HH:mm:ss"));

			long nTime = myDateTime.getTimeInMillis() % 3600000L;
			System.out.print("\nTime=" + nTime);
			Time tempTime = new Time(nTime);
			System.out.print("  " + tempTime.toString());
			System.out.print("\n");

			myDateTime.setDateWithString("2001-04-15", 0);
			System.out.println(myDateTime.getDateTimeAsString("yyyy.MM.dd"));

			myDateTime.setDateWithString("000505", 2);
			System.out.println(myDateTime.getDateTimeAsString("yyyy.MM.dd"));

			myDateTime.setTimeWithString("12:01:02", 0);
			System.out.println(myDateTime.getDateTimeAsString("HH:mm:ss"));

			myDateTime.setTimeWithString("00:25", 2);
			System.out.println(myDateTime
					.getDateTimeAsString("yyyy-MM-dd HH:mm:ss"));

			java.sql.Date dDate = new java.sql.Date(0L);
			Time tTime = new Time(0L);
			dDate = java.sql.Date.valueOf("1978-02-04");
			tTime = Time.valueOf("12:00:20");
			System.out.println(myDateTime
					.getDateTimeAsString("yyyy/MM/dd HH:mm:ss"));
			myDateTime.setDate(dDate);
			System.out.println(myDateTime
					.getDateTimeAsString("yyyy/MM/dd HH:mm:ss"));
			myDateTime.setTime(tTime);
			System.out.println(myDateTime
					.getDateTimeAsString("yyyy/MM/dd HH:mm:ss"));

			myDateTime.setDateTimeWithCurrentTime();
			System.out.println("End:"
					+ myDateTime.getDateTimeAsString("yyyy/MM/dd HH:mm:ss"));

			CMyDateTime myDateTime2 = new CMyDateTime();
			int[] nFields = { 1, 2, 3, 4, 5, 6, 11, 12 };
			myDateTime2.setDateTimeWithString("2001-02-07 14:34:00",
					"yyyy-MM-dd HH:mm:ss");
			myDateTime.setDateTimeWithString("2001-03-07 15:35:01",
					"yyyy-MM-dd HH:mm:ss");

			for (int i = 0; i < 8; i++) {
				long lDateDiff = myDateTime.dateDiff(nFields[i], myDateTime2
						.getDateTime());
				System.out.println("DateDiff(" + nFields[i] + ")=" + lDateDiff);
			}

			for (int i = 0; i < 6; i++) {
				System.out.println("get(" + nFields[i] + ")="
						+ myDateTime.get(nFields[i]));
			}
			System.out.println("getWeek=" + myDateTime.get(12));

			System.out.println("Test for dateAdd()");
			System.out.println("oldDateTime = " + myDateTime.toString());
			myDateTime.dateAdd(1, 12);
			System.out.println("dateAdd(YEAR,12) = " + myDateTime.toString());
			myDateTime.dateAdd(1, -12);
			System.out.println("dateAdd(YEAR,-12) = " + myDateTime.toString());
			myDateTime.dateAdd(2, -3);
			System.out.println("dateAdd(MONTH,-3) = " + myDateTime.toString());
			myDateTime.dateAdd(3, 10);
			System.out.println("dateAdd(DAY,10) = " + myDateTime.toString());

			myDateTime.setDateTimeWithCurrentTime();
			int nWeek = myDateTime.getDayOfWeek();
			myDateTime.dateAdd(3, -nWeek);
			System.out.println("Monday of this week is:"
					+ myDateTime.toString("yyyy-MM-dd"));
			for (int j = 1; j < 7; j++) {
				myDateTime.dateAdd(3, 1);
				System.out.println(j + 1 + " of this week is:"
						+ myDateTime.toString("yyyy-MM-dd"));
			}

			System.out.println("\n\n===== test for CMyDateTime.set() ====== ");
			String[] sValues = { "2002.06.13 12:00:12", "1900.2.4 3:4:5",
					"1901-03-15 23:05:10", "1978-2-4 5:6:7",
					"2001/12/31 21:08:22", "1988/2/5 9:1:2", "1986.12.24",
					"0019.2.8", "2002-12-20", "1999-8-1", "2001/12/21",
					"2000/1/5", "78.02.04", "89.2.6", "99-12-31", "22-3-6",
					"01/02/04", "02/5/8" };
			for (int j = 0; j < sValues.length; j++) {
				myDateTime.setDateTimeWithString(sValues[j]);
				System.out.println("[" + j + "]"
						+ extractDateTimeFormat(sValues[j]) + "  "
						+ myDateTime.toString());
			}
		} catch (CMyException ex) {
			ex.printStackTrace(System.out);
		}
	}

	/**
	 * 
	 * @return
	 * @throws CMyException
	 */
	public boolean isLeapYear() throws CMyException {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(this.m_dtDate);
		return calendar.isLeapYear(getYear());
	}

	/**
	 * 
	 * @return
	 */
	public boolean isToday() {
		CMyDateTime today = now();
		return toString("yyyy-MM-dd").equals(today.toString("yyyy-MM-dd"));
	}

	/**
	 * 
	 * @param _bSundayStart
	 * @return
	 * @throws CMyException
	 */
	public int getWeekCountsOfMonth(boolean _bSundayStart) throws CMyException {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(this.m_dtDate);
		int nWeekCounts = calendar.getActualMaximum(4);

		if (_bSundayStart) {
			return nWeekCounts;
		}

		CMyDateTime firstDay = new CMyDateTime();
		firstDay.setDateTime(this.m_dtDate);
		firstDay.setDateTimeWithString(firstDay.getYear() + "-"
				+ firstDay.getMonth() + "-1");

		if (firstDay.getDayOfWeek() == 6) {
			nWeekCounts++;
		}

		return nWeekCounts;
	}

	/**
	 * Returns true if this datetime equals with the specified one
	 */
	public boolean equals(Object _another) {
		return (_another != null)
				&& ((_another instanceof CMyDateTime))
				&& (((CMyDateTime) _another).getTimeInMillis() == getTimeInMillis());
	}
}