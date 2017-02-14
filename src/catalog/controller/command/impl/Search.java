package catalog.controller.command.impl;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import catalog.controller.command.Command;
import catalog.controller.exception.ControllerException;
import catalog.service.CatalogService;
import catalog.service.exception.ServiceException;
import catalog.service.factory.ServiceFactory;

public class Search implements Command {
	
	private final static Logger log = LogManager.getRootLogger();

	public String execute (String request) throws ControllerException {

		// get parameters from request and initialize the variables
		String response = null;
		try {
			StringTokenizer tokens = new StringTokenizer(request, " ");
			tokens.nextToken();
			String keyWord = tokens.nextToken();
			ServiceFactory serviceFactory = ServiceFactory.getInstance(); 
			CatalogService catalogService = serviceFactory.getCatalogService();
			response = catalogService.searchNews(keyWord); 
		} catch (ServiceException | NoSuchElementException e) { 
			// write log 
			log.error("Error during search procedure", e);
			throw new ControllerException(e);
		}
		if (response == null) {
			response = "nothing found";
		}
		return response;
	}

}
