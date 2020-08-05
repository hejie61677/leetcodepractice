package utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>工具类:StringUtil </p>
 * <p>Description:字符串相关工具 </p>
 * @author 何杰
 * @date 2019年8月28日
 * @version 1.0
 * @since JDK 1.8
 */
public class StringUtil {
	
	private static Logger logger = LoggerFactory.getLogger(StringUtil.class);
		
	/*
	 * 判断字符串是否为空
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0; 
	}
	
	/*
	 * 返回 唯一字符串编号(可作为文件名)
	 */
	public static String getUniqueNo() throws SQLException {
		String retStr = null;
		Date date = new Date();
		String a = FormatUtil.formatDateFileName(date);
		String b = "";
		Connection con = ConnDbUtil.getConnection();
		PreparedStatement pst = con.prepareStatement("select nextval('seqFileNo')");
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			b = rs.getString(1);
		}
		
		ConnDbUtil.close(rs, pst, con);
		
		StringBuffer c = new StringBuffer("");
		
		while (c.length() < 6 - b.length()) {
			c.append("0");
		}
		
		retStr = a + c.append(b);
		
		logger.debug("获取唯一编号成功 < " + retStr + " >");
		
		return retStr;
	}
}
