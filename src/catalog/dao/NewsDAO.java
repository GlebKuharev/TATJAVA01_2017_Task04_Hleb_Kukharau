package catalog.dao;

import java.util.ArrayList;

import catalog.bean.News;
import catalog.dao.exception.DAOException;

public interface NewsDAO {
	
	String addNews(News news) throws DAOException;
	ArrayList<News> searchNewsByName(String name) throws DAOException;
	ArrayList<News> searchNewsByCategory(String category) throws DAOException;
	ArrayList<News> searchNewsByYear(int year) throws DAOException;

}
