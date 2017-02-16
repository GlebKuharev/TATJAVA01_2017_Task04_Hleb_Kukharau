package catalog.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import catalog.bean.News;
import catalog.dao.NewsDAO;
import catalog.dao.connection.ConnectionPool;
import catalog.dao.connection.ConnectionPoolException;
import catalog.dao.exception.DAOException;

public class SQLNewsDAO implements NewsDAO {
	
	private final static Logger log = LogManager.getRootLogger();

	@Override
	public String addNews(News news) throws DAOException {
		
		Connection con = null;
		PreparedStatement ps = null;
		String res = null;
		try{
			con = ConnectionPool.getInstance().takeConnection();
			String sql = "INSERT INTO catalog.news (category, name, releaseDate, description) VALUES(?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, news.getCategory());
			ps.setString(2, news.getName());
			ps.setString(3, news.getReleaseDate());
			ps.setString(4, news.getDescription());
			ps.executeUpdate();
			res = "adding ok";
		}
		catch(ConnectionPoolException e){
			log.error("ConnectionPoolException: method addNews, class SQLNewsDao");
			throw new DAOException("Adding new record failure", e);
		}
		catch(SQLException e){
			log.error("SQLException: method addNews, class SQLNewsDao");
			throw new DAOException("Adding new record failure", e);
		}
		finally{
			if (ps != null){
				ConnectionPool.close(ps);
			}
			if (con != null){
				try{
					con.close();
				}
				catch(SQLException e){
					log.error("Closing connection failure");
					throw new DAOException("Closing connection failure", e);
				}
			}
		}
		return res;
	}
		
	
	@Override
	public ArrayList<News> searchNewsByName(String name) throws DAOException{
		ArrayList<News> newsList = new ArrayList<News>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			con = ConnectionPool.getInstance().takeConnection();
			String sql = "SELECT * FROM catalog.news WHERE name=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();
			while(rs.next()){
				newsList.add(new News(rs.getString("category"),rs.getString("releaseDate"), rs.getString("name"), rs.getString("description")));
			}
		}
		catch(ConnectionPoolException e){
			log.error("ConnectionPoolException: method getNewsByName, class SQLUserDao");
			throw new DAOException("Getting news by name failure", e);
		}
		catch(SQLException e){
			log.error("SQLException: method getNewsByName, class SQLUserDao");
			throw new DAOException("Getting news by name failure", e);
		}
		finally{
			if (ps != null){
				ConnectionPool.close(ps);
			}
			if (rs != null){
				ConnectionPool.close(rs);
			}
			if (con != null){
				try{
					con.close();
				}
				catch(SQLException e){
					log.error("Closing connection failure");
					throw new DAOException("Closing connection failure", e);
				}
			}
		}
		return newsList;
	}
	
	
	@Override
	public ArrayList<News> searchNewsByCategory(String category) throws DAOException{
		ArrayList<News> newsList = new ArrayList<News>();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			con = ConnectionPool.getInstance().takeConnection();
			String sql = "SELECT * FROM catalog.news WHERE category=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, category);
			rs = ps.executeQuery();
			while(rs.next()){
				newsList.add(new News(rs.getString("category"),rs.getString("releaseDate"), rs.getString("name"), rs.getString("description")));
			}
		}
		catch(ConnectionPoolException e){
			log.error("ConnectionPoolException: method getNewsList, class SQLUserDao");
			throw new DAOException("Getting newsList failure", e);
		}
		catch(SQLException e){
			log.error("SQLException: method getNewsList, class SQLUserDao");
			throw new DAOException("Getting newsList failure", e);
		}
		finally{
			if (ps != null){
				ConnectionPool.close(ps);
			}
			if (rs != null){
				ConnectionPool.close(rs);
			}
			if (con != null){
				try{
					con.close();
				}
				catch(SQLException e){
					log.error("Closing connection failure");
					throw new DAOException("Closing connection failure", e);
				}
			}
		}
		return newsList;
	}
	
	@Override
	public ArrayList<News> searchNewsByYear(int year) throws DAOException{
		ArrayList<News> newsList = new ArrayList<News>();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			con = ConnectionPool.getInstance().takeConnection();
			String sql = "SELECT * FROM catalog.news WHERE releaseDate=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, year);
			rs = ps.executeQuery();
			while(rs.next()){
				newsList.add(new News(rs.getString("category"),rs.getString("releaseDate"), rs.getString("name"), rs.getString("description")));
			}
		}
		catch(ConnectionPoolException e){
			log.error("ConnectionPoolException: method getNewsList, class SQLUserDao");
			throw new DAOException("Getting newsList failure", e);
		}
		catch(SQLException e){
			log.error("SQLException: method getNewsList, class SQLUserDao");
			throw new DAOException("Getting newsList failure", e);
		}
		finally{
			if (ps != null){
				ConnectionPool.close(ps);
			}
			if (rs != null){
				ConnectionPool.close(rs);
			}
			if (con != null){
				try{
					con.close();
				}
				catch(SQLException e){
					log.error("Closing connection failure");
					throw new DAOException("Closing connection failure", e);
				}
			}
		}
		return newsList;
	}

}

