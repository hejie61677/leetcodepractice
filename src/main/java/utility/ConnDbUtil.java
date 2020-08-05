package utility;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pojo.PublicEntity;

/**
 * <p>工具类:ConnDbUtil </p>
 * <p>数据库连接工具类 </p>
 * @author 何杰
 * @date 2020-8-4
 * @version 1.0
 * @since JDK 1.8
 */
public class ConnDbUtil {
	
	//public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	//public static final String URL = "jdbc:mysql://localhost:3306/mydatabase?characterEncoding=UTF-8";
	public static final String URL = "jdbc:mysql://localhost:3306/mydatabase?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT&useSSL=false";
	public static final String USERNAME = "test";
	public static final String PASSWORD = "test";
	private static Logger logger = LoggerFactory.getLogger(ConnDbUtil.class);
	
	/*
	 * 得到连接
	 */
	public static Connection getConnection() {
		Connection con = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			logger.error("加载驱动失败，错误信息 < " + e.getMessage() + " > ");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			logger.error("获取连接失败，错误信息 < " + e.getMessage() + " > ");
		}
		
		logger.debug("获取连接成功");
		return con;
	}
	
	/*
	 * 关闭连接
	 */
	public static void closeConnection(Connection connection) {
		
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("关闭连接失败，错误信息 < " + e.getMessage() + " > ");
			}
		}
		
		logger.debug("关闭连接成功");
	}
	
	/*
	 * 关闭PreparedStatement
	 */
	public static void closePreparedStatement(PreparedStatement preparedStatement) {
		
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("关闭预编译处理对象失败，错误信息 < " + e.getMessage() + " > ");
			}
		}
		
		logger.debug("关闭预编译处理对象成功");
	}
	
	/*
	 * 关闭Statement
	 */
	public static void closeStatement(Statement statement) {
		
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("关闭Statement对象失败，错误信息 < " + e.getMessage() + " > ");
			}
		}
		
		logger.debug("关闭Statement对象成功");
	}
	
	/*
	 * 关闭结果集
	 */
	public static void closeResultSet(ResultSet resultSet) {
		
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("关闭ResultSet对象失败，错误信息 < " + e.getMessage() + " > ");
			}
		}
		
		logger.debug("关闭ResultSet对象成功");
	}
	
	/*
	 * 依次关闭
	 */
	public static void close(ResultSet resultSet, PreparedStatement preparedStatement, Connection conn) {
		closeResultSet(resultSet);
		closePreparedStatement(preparedStatement);
		closeConnection(conn);
	}
	
	/*
	 * param: sql(String) 传入需执行SQL
	 * param: type(String) 传入操作类型
	 * param: params(String[])
	 * return: result(HashMap) 返回结果(returnCode返回码 dataSet 返回数据)
	 */
	public static HashMap<String, Object> executeSql(String sql, ArrayList<String> params, String type, ArrayList<String> columns) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		ArrayList<PublicEntity> publics = new ArrayList<PublicEntity>();
		PublicEntity publicEntity;
		Connection conn = ConnDbUtil.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			//动态传入SQL参数
			if (!params.isEmpty()) {
				for (int i = 0; i < params.size(); i++) {
					pst.setString(i + 1, params.get(i));
				}
			}
			//判断SQL类型
			if (type.equals("select")) {
				rs = pst.executeQuery();
			} else {
				pst.executeUpdate();
			}
			
			//获取查询结果
			while (rs.next()) {
				publicEntity = new PublicEntity();
				//动态填充数据
				for (int i = 0; i < columns.size(); i++) {
					if ("Score".equals(columns.get(i))) {
						publicEntity
						.getClass()
						.getMethod("setData" + Integer.toString(i + 1), Class.forName("java.lang.String"))
						.invoke(publicEntity, new BigDecimal(rs.getString(columns.get(i))).setScale(2).toString());
					} else {
						publicEntity
						.getClass()
						.getMethod("setData" + Integer.toString(i + 1), Class.forName("java.lang.String"))
						.invoke(publicEntity, rs.getString(columns.get(i)));
					}
					
				}
				publics.add(publicEntity);
			}
			result.put("returnCode", "0000");
			result.put("dataSet", publics);
		} catch (SQLException e) {
			result.put("returnCode", "2222");
			e.printStackTrace();
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			close(rs, pst, conn);
		}
	
		return result;
		
	}
	
}
