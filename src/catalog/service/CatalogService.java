package catalog.service;

import java.util.ArrayList;

import catalog.bean.News;
import catalog.service.exception.ServiceException;

public interface CatalogService {
	
	String addNews(News news) throws ServiceException;
	ArrayList<News> searchNewsByName(String keyWord) throws ServiceException;
	ArrayList<News> searchNewsByCategory(String keyWord) throws ServiceException;
	ArrayList<News> searchNewsByYear(int year) throws ServiceException;

}
