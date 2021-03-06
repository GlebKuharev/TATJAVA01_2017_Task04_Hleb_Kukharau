package catalog.controller.command.impl;

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

public class Add implements Command {
	
	private final static Logger Log = LogManager.getRootLogger();

	public String execute (String request) throws ControllerException {

		String response = null;

		try {
			StringTokenizer tokens = new StringTokenizer(request, " ");
			String category = tokens.nextToken();
			String releaseDate = tokens.nextToken();
			String name = tokens.nextToken();
			String description = tokens.nextToken();
			
			News news = new News(category, releaseDate, name, description);
			ServiceFactory serviceFactory = ServiceFactory.getInstance(); 
			CatalogService catalogService = serviceFactory.getCatalogService();
			response = catalogService.addNews(news); 
		} catch (ServiceException | NoSuchElementException e) { 
			Log.error("Controller exception, error during adding procedure: method execute, class Add", e);
			throw new ControllerException(e);
		}
		return response;
	}

}
