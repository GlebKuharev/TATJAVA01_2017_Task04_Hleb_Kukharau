package catalog.service.impl;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import catalog.bean.News;
import catalog.dao.NewsDAO;
import catalog.dao.connection.ConnectionPool;
import catalog.dao.connection.ConnectionPoolException;
import catalog.dao.exception.DAOException;
import catalog.dao.impl.DAOFactory;
import catalog.service.CatalogService;
import catalog.service.exception.ServiceException;

public class CatalogServiceImpl implements CatalogService {

	private final static Logger log = LogManager.getRootLogger();

	@Override
	public String addNews(News news) throws ServiceException {
		
		String response;

		if (news == null) {
			response = "news is empty, please enter valid news";
			return null;
		}


		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance(); 
			NewsDAO newsDAO = daoObjectFactory.getNewsDAO(); 
			response = newsDAO.addNews(news);
		} catch (DAOException e) {
			log.error("error in addNews CatalogService, item not added");
			throw new ServiceException(e);
		}
		return response;
	}


	@Override
	public ArrayList<News> searchNewsByName(String keyword) throws ServiceException {
		
		if (keyword == null || keyword.isEmpty()) {
			return null;
		}
		
		ArrayList<News> newsList = null;
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance(); 
			NewsDAO newsDAO = daoObjectFactory.getNewsDAO(); 
			newsList = newsDAO.searchNewsByName(keyword);
		} catch (DAOException e) {
			log.error("error in searchNewsByName CatalogService");
			throw new ServiceException(e);
		}
		return newsList;
	}

	@Override
	public ArrayList<News> searchNewsByCategory(String keyword) throws ServiceException {
		
		if (keyword == null || keyword.isEmpty()) {
			return null;
		}
		
		ArrayList<News> newsList = null;
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance(); 
			NewsDAO newsDAO = daoObjectFactory.getNewsDAO(); 
			newsList = newsDAO.searchNewsByCategory(keyword);
		} catch (DAOException e) {
			log.error("error in searchNewsByCategory CatalogService");
			throw new ServiceException(e);
		}
		return newsList;
	}

	@Override
	public ArrayList<News> searchNewsByYear(int year) throws ServiceException {
		
		if (year<=0) {
			return null;
		}
		
		ArrayList<News> newsList = null;
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance(); 
			NewsDAO newsDAO = daoObjectFactory.getNewsDAO(); 
			newsList = newsDAO.searchNewsByYear(year);
		} catch (DAOException e) {
			log.error("error in searchNewsByYear CatalogService");
			throw new ServiceException(e);
		}
		return newsList;
	}


	public static void initConnectionPool() throws ServiceException {

		try {
		ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			log.error("InitConnectionPool exception in service class");
			throw new ServiceException(e);
		}
	}


	public static void destroyConnectionPool() throws ServiceException {
		try {
		ConnectionPool.getInstance().dispose();
		} catch (ConnectionPoolException e) {
			log.error("destroyConnectionPool exception in service class");
			throw new ServiceException(e);
		}
	}

}
