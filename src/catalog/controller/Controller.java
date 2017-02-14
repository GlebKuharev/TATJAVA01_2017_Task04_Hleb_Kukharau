package catalog.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import catalog.controller.command.Command;
import catalog.controller.exception.ControllerException;

public class Controller {
	
	private final static Logger log = LogManager.getRootLogger();

	private final CommandProvider provider = new CommandProvider(); 
	private final char paramDelimiter = ' '; 

	public String executeTask(String request) { 
		String commandName; 
		Command executionCommand;

		commandName = request.substring(0, request.indexOf(paramDelimiter)); 
		executionCommand = provider.getCommand(commandName);
		
		String response = null; 
		try {
			response = executionCommand.execute(request.substring(request.indexOf(paramDelimiter)+1));
		} catch (ControllerException e) {
			log.error("Controller executeTask exception", e);
			response = e.getMessage();
		}

		return response;

	}
}