package catalog.service.impl;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import catalog.bean.News;
import catalog.dao.NewsDAO;
import catalog.dao.exception.DAOException;
import catalog.dao.impl.DAOFactory;
import catalog.service.CatalogService;
import catalog.service.exception.ServiceException;

public class CatalogServiceImpl implements CatalogService {
	
	private final static Logger log = LogManager.getRootLogger();

	@Override
	public String addNews(News news) throws ServiceException {
		String response = null;
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
	public String searchNews(String keyword) throws ServiceException {
		String response = null;
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance(); 
			NewsDAO newsDAO = daoObjectFactory.getNewsDAO(); 
			response = newsDAO.searchNews(keyword);
		} catch (DAOException e) {
			log.error("error in searchNews CatalogService");
			throw new ServiceException(e);
		}
		return response;
	}

	@Override
	public ArrayList<News> searchNewsByName(String keyWord) throws ServiceException {
		ArrayList<News> newsList = null;
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance(); 
			NewsDAO newsDAO = daoObjectFactory.getNewsDAO(); 
			newsList = newsDAO.searchNewsByName(keyWord);
		} catch (DAOException e) {
			log.error("error in searchNewsByName CatalogService");
			throw new ServiceException(e);
		}
		return newsList;
	}

	@Override
	public ArrayList<News> searchNewsByCategory(String keyWord) throws ServiceException {
		ArrayList<News> newsList = null;
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance(); 
			NewsDAO newsDAO = daoObjectFactory.getNewsDAO(); 
			newsList = newsDAO.searchNewsByCategory(keyWord);
		} catch (DAOException e) {
			log.error("error in searchNewsByCategory CatalogService");
			throw new ServiceException(e);
		}
		return newsList;
	}

	@Override
	public ArrayList<News> searchNewsByYear(int year) throws ServiceException {
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

}
