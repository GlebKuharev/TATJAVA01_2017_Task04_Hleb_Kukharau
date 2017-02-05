package catalog.controller.command.impl;

import catalog.controller.command.Command;
import catalog.controller.exception.ControllerException;

public class WrongRequest implements Command {

	@Override
	public String execute(String request) throws ControllerException {
		// TODO Auto-generated method stub
		String response = "command not supported";
		return response;
	}

}
