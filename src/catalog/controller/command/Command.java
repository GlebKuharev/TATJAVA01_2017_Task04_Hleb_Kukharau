package catalog.controller.command;

import catalog.controller.exception.ControllerException;

public interface Command {
	public String execute (String request) throws ControllerException;

}
