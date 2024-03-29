package com.starofdream.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	private static final String DRIVERNAME = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/test?&useSSL=false&serverTimezone=UTC";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "3429211993mysqlW";

	public static PreparedStatement pstmt = null;
	public static ResultSet rs = null;
	public static Connection connection = null;
	/**
	 * 建立数据库连接
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVERNAME);
		Connection c =  DriverManager.getConnection(URL, USERNAME, PASSWORD);
		
		return c;
	}
	/**
	 * 创建sql语句的通用方法
	 * @param sql
	 * @param params
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static PreparedStatement createPreparedStatement(String sql, Object[] params) throws ClassNotFoundException, SQLException {
		connection = getConnection();
		pstmt = connection.prepareStatement(sql);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
		}
		
		return pstmt;
	}
	/**
	 * 关闭结果集、查询语句、数据库连接的通用方法
	 * @param rs
	 * @param stmt
	 * @param connection
	 */
	public static void closeAll(ResultSet rs, Statement stmt, Connection connection) {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取总数据条数
	 * @param sql
	 * @return
	 */
	public static int getTotalCount(String sql) {
		int count = -1;
		try {
			pstmt = createPreparedStatement(sql, null);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, connection);
		}
		return count;
	}
	/**
	 * 通用的增删改方法
	 * @param sql
	 * @param params
	 * @return
	 */
	public static boolean executeUpdate(String sql, Object[] params) {
		try {
			pstmt = createPreparedStatement(sql, params);
			int count = pstmt.executeUpdate();
			if (count > 0) {
				return true;
			} else {
				return false;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeAll(null, pstmt, connection);
		}
	}
	/**
	 * 通用的查询方法
	 * @param sql
	 * @param params
	 * @return
	 */
	public static ResultSet executeQuery(String sql, Object[] params) {
		try {
			pstmt = createPreparedStatement(sql, params);
			rs = pstmt.executeQuery();
			
			return rs;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
