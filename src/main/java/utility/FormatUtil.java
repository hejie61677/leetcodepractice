package utility;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>工具类:FormatUtil </p>
 * <p>Description:格式化工具类 </p>
 * @author 何杰
 * @date 2019年8月28日
 * @version 1.0
 * @since JDK 1.8
 */
public class FormatUtil {
	
	private static SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat detailDay = new SimpleDateFormat("yyyy年MM月dd日");
	private static SimpleDateFormat dateE = new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat fileName = new SimpleDateFormat("yyyyMMddHHmmss");
	private static Logger logger = LoggerFactory.getLogger(FormatUtil.class);
	
	/*
	 * 格式化日期(精确到具体时刻)
	 */
	public static String formatDateTime(Date date) {
		String timeStr = time.format(date);
		logger.debug("格式化日期成功 < " + timeStr + " > ");
		return timeStr;
	}
	
	/*
	 * 格式化日期(精确到天_1)
	 */
	public static String formatDateDay(Date date) {
		String dayStr = day.format(date);
		logger.debug("格式化日期成功 < " + dayStr + " > ");
		return dayStr;
	}
	
	/*
	 * 格式化日期(精确到天_2)
	 */
	public static String formatDateDetailDay(Date date) {
		String dayStr = detailDay.format(date);
		logger.debug("格式化日期成功 < " + dayStr + " > ");
		return dayStr;
	}
	
	/*
	 * 格式化日期(精确到天_excel)
	 */
	public static String formatDateExcel(Date date) {
		String excelStr = dateE.format(date);
		logger.debug("格式化日期成功 < " + excelStr + " > ");
		return excelStr;
	}
	
	/*
	 * 格式化日期(作为文件名)
	 */
	public static String formatDateFileName(Date date) {
		String fileStr = fileName.format(date);
		logger.debug("格式化日期成功 < " + fileStr + " > ");
		return fileStr;
	}
	
	/*
	 * 将字符转转换为日期
	 */
	public static Date formatDateString(String date){		
		Date fdate = null;
		
		try {
			fdate = day.parse(date);
			logger.debug("字符串转换为日期成功 < " + fdate + " > ");
			
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("字符串转换为日期失败，错误信息 < " + e.getMessage() + " > ");
		}
		
		return fdate;
	}
	
	/*
	 * 转为String类型(提供默认值)
	 */
	public static String convertString(Object obj, String def) {
		String value = obj != null ? String.valueOf(obj) : def;
		logger.debug("转为String类型成功 < " + value + " , 默认值:" + def + " > ");
		return value;
	}
	
	/*
	 * 转为double类型(提供默认值)
	 */
	public static double convertDouble(Object obj, double def) {
		double value = def;
		
		if (obj != null) {
			String strValue = String.valueOf(obj);
			
			if (!StringUtil.isEmpty(strValue)) {
				
				try {
					value = Double.parseDouble(strValue);
					logger.debug("转为double类型成功 < " + value + " , 默认值:" + def + " > ");
				} catch (NumberFormatException e) {
					value = def;
					e.printStackTrace();
					logger.error("转为double类型失败 < 默认值:" + def + " > ");
				}	
			}	
		}
		
		return value;	
	}
	
	/*
	 * 转为long类型(提供默认值)
	 */
	public static long convertLong(Object obj, long def) {
		long value = def;
		
		if (obj != null) {
			String strValue = String.valueOf(obj);
			
			if (!StringUtil.isEmpty(strValue)) {
				
				try {
					value = Long.parseLong(strValue);
					logger.debug("转为long类型成功 < " + value + " , 默认值:" + def + " > ");
				} catch (NumberFormatException e) {
					value = def;
					e.printStackTrace();
					logger.error("转为long类型失败 < 默认值:" + def + " > ");
				}	
			}	
		}
		
		return value;	
	}
	
	/*
	 * 转为int类型(提供默认值)
	 */
	public static int convertInt(Object obj, int def) {
		int value = def;
		
		if (obj != null) {
			String strValue = String.valueOf(obj);
			
			if (!StringUtil.isEmpty(strValue)) {
				
				try {
					value = Integer.parseInt(strValue);
					logger.debug("转为int类型成功 < " + value + " , 默认值:" + def + " > ");
				} catch (NumberFormatException e) {
					value = def;
					e.printStackTrace();
					logger.error("转为int类型失败 < 默认值:" + def + " > ");
				}	
			}	
		}
		
		return value;	
	}
	
	/*
	 * 转为boolean类型(提供默认值)
	 */
	public static boolean convertBoolean(Object obj, boolean def) {
		boolean value = def;
		
		if (obj != null) {
			String strValue = String.valueOf(obj);
			
			if (!StringUtil.isEmpty(strValue)) {
				value = Boolean.parseBoolean(strValue);
				logger.debug("转为boolean类型成功 < " + value + " , 默认值:" + def + " > ");
			}	
		}
		
		return value;	
	}
	
	/*
	 * 将url编码
	 */
	public static String encodeURL(String source) {
        String target = null;
        
        try {
        	
            target = URLEncoder.encode(source,"utf-8");
            logger.debug("编码成功 < " + target + " >");
            
        } catch (UnsupportedEncodingException e) {
        	
        	e.printStackTrace();
            logger.error("encode url failure, errorInfo:" + e.getMessage());
        }
        
        return target;
    }

    /*
     * 将URL解码
     */
    public static String dencodeURL(String source) {
        String target = null;
        
        try {
        	
            target = URLDecoder.decode(source,"utf-8");
            logger.debug("解码成功 < " + target + " >");
            
        } catch (UnsupportedEncodingException e) {
        	
        	e.printStackTrace();
            logger.error("decode url failure, errorInfo:" + e.getMessage());
        }
        
        return target;
    }
	
}
