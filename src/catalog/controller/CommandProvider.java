package catalog.controller;

import java.util.HashMap;
import java.util.Map;

import catalog.controller.command.Command;
import catalog.controller.command.CommandName;
import catalog.controller.command.impl.Add;
import catalog.controller.command.impl.Search;
import catalog.controller.command.impl.WrongRequest;

public class CommandProvider {

	private final Map<CommandName, Command> repository = new HashMap<>();
	
	CommandProvider() { 
		repository.put(CommandName.ADD, new Add()); 
		repository.put(CommandName.SEARCH, new Search()); 
		repository.put(CommandName.WRONG_REQUEST, new WrongRequest());
		//...
	}

	Command getCommand(String name){ 
		CommandName commandName = null; 
		Command command = null;
		try{
			commandName = CommandName.valueOf(name.toUpperCase()); 
			command = repository.get(commandName); 
		}catch(IllegalArgumentException | NullPointerException e){ 
			//write log 
			command = repository.get(CommandName.WRONG_REQUEST);
		}
		return command;
	}

}
