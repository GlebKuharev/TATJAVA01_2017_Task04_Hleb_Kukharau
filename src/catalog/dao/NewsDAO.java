package catalog.dao;

import catalog.bean.News;
import catalog.dao.exception.DAOException;

public interface NewsDAO {
	
	String addNews(News news) throws DAOException;
	String searchNews(String keyword) throws DAOException;

}
