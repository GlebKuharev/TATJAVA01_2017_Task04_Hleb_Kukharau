package catalog.controller;

import catalog.controller.command.Command;
import catalog.controller.exception.ControllerException;

public class Controller {

	private final CommandProvider provider = new CommandProvider(); 
	private final char paramDelimeter = ' '; 

	public String executeTask(String request) { 
		String commandName; 
		Command executionCommand;

		commandName = request.substring(0, request.indexOf(paramDelimeter)); 
		executionCommand = provider.getCommand(commandName);
		
		String response = null; 
		try {
			response = executionCommand.execute(request);
		} catch (ControllerException e) {
			response = e.getMessage();
		}

		return response;

	}
}