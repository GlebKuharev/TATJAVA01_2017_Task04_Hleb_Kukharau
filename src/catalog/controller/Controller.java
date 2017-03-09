package catalog.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import catalog.controller.command.Command;
import catalog.controller.exception.ControllerException;
import catalog.service.exception.ServiceException;
import catalog.service.impl.CatalogServiceImpl;

public class Controller {

	private final static Logger log = LogManager.getRootLogger();

	private final CommandProvider provider = new CommandProvider(); 
	private final char paramDelimiter = ' '; 

	public String executeTask(String request) { 

		String commandName = null; 
		Command executionCommand;
		String response = null; 

		try {
			commandName = request.substring(0, request.indexOf(paramDelimiter)); 
		} catch (IndexOutOfBoundsException e) {
			log.error("Controller executeTask exception", e);
		}

		executionCommand = provider.getCommand(commandName);

		try {
			response = executionCommand.execute(request.substring(request.indexOf(paramDelimiter)+1));
		} catch (ControllerException e) {
			log.error("Controller executeTask exception", e);
		}
		
		return response;

	}

	public String initConnectionPool() {
		try {
			CatalogServiceImpl.initConnectionPool();
		} catch (ServiceException e) {
			log.error("Controller initConnectionPool exception", e);
			return "error initializing connection pool";
		}
		return "Pool initialized";
	}

	public String destroyConnectionPool() {
		try {
			CatalogServiceImpl.destroyConnectionPool();
		} catch (ServiceException e) {
			log.error("Controller initConnectionPool exception", e);
			return "error destroying connection pool";
		}
		return "Pool destroyed";
	}
}