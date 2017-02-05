package catalog.service;

import catalog.bean.News;
import catalog.service.exception.ServiceException;

public interface CatalogService {
	
	String addNews(News news) throws ServiceException;
	String searchNews(String keyword) throws ServiceException;

}
