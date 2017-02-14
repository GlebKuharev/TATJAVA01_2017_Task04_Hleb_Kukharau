package catalog.controller.command.impl;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import catalog.bean.News;
import catalog.controller.command.Command;
import catalog.controller.exception.ControllerException;
import catalog.service.CatalogService;
import catalog.service.exception.ServiceException;
import catalog.service.factory.ServiceFactory;

public class SearchByName implements Command {
	
	private final static Logger log = LogManager.getRootLogger();

	@Override
	public String execute(String request) throws ControllerException {
		String response = null;
		ArrayList<News> newsList = null;
		try {
			StringTokenizer tokens = new StringTokenizer(request, " ");
			String keyWord = tokens.nextToken();
			ServiceFactory serviceFactory = ServiceFactory.getInstance(); 
			CatalogService catalogService = serviceFactory.getCatalogService();
			newsList = catalogService.searchNewsByName(keyWord); 
			response = newsList.toString();
		} catch (ServiceException | NoSuchElementException | NullPointerException e) { 
			// write log 
			log.error("Error during search_by_name procedure", e);
			throw new ControllerException(e);
		}
		if (response == null) {
			response = "nothing found";
		}
		return response;
	}

}
