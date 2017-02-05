package catalog.controller.command.impl;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import catalog.bean.News;
import catalog.controller.command.Command;
import catalog.controller.exception.ControllerException;
import catalog.service.CatalogService;
import catalog.service.exception.ServiceException;
import catalog.service.factory.ServiceFactory;

public class Add implements Command {

	public String execute (String request) throws ControllerException {

		String response = null;

		// get parameters from request and initialize the variables
		
		try {
			StringTokenizer tokens = new StringTokenizer(request, " ");
			tokens.nextToken();
			String category = tokens.nextToken();
			String releaseDate = tokens.nextToken();
			String name = tokens.nextToken();
			String description = tokens.nextToken();
			
			News news = new News(category, releaseDate, name, description);

			ServiceFactory serviceFactory = ServiceFactory.getInstance(); 
			CatalogService catalogService = serviceFactory.getCatalogService();
			response = catalogService.addNews(news); 
		} catch (ServiceException | NoSuchElementException e) { 
			// write log 
			response = "Error during adding procedure";
			throw new ControllerException(e);
		}
		return response;
	}

}
