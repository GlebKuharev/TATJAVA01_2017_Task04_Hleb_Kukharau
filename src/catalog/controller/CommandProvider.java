package catalog.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import catalog.controller.command.Command;
import catalog.controller.command.CommandName;
import catalog.controller.command.impl.Add;
import catalog.controller.command.impl.SearchByCategory;
import catalog.controller.command.impl.SearchByName;
import catalog.controller.command.impl.SearchByYear;
import catalog.controller.command.impl.WrongRequest;

final class CommandProvider {

	private final static Logger log = LogManager.getRootLogger();

	private final Map<CommandName, Command> repository = new HashMap<>();

	CommandProvider() { 
		repository.put(CommandName.ADD, new Add()); 
		repository.put(CommandName.WRONG_REQUEST, new WrongRequest());
		repository.put(CommandName.SEARCH_BY_NAME, new SearchByName());
		repository.put(CommandName.SEARCH_BY_CATEGORY, new SearchByCategory());
		repository.put(CommandName.SEARCH_BY_YEAR, new SearchByYear());
		//...
	}

	Command getCommand(String name) { 
		CommandName commandName = null; 
		Command command = null;
		try{
			commandName = CommandName.valueOf(name.toUpperCase()); 
			command = repository.get(commandName); 
		}catch(IllegalArgumentException | NullPointerException e){ 
			log.error("CommandProvider getCommand exception", e);
			command = repository.get(CommandName.WRONG_REQUEST);
		}
		return command;
	}

}
