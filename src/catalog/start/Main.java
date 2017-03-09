package catalog.start;

import catalog.controller.Controller;

public class Main {

	public static void main(String[] args) {
		

		String command = "add book 1957 Atlas_Shrugged byAynRand";

		Controller controller = new Controller();
		
		System.out.println(controller.initConnectionPool());

		
		String response = controller.executeTask(command);
		System.out.println(response);
		
		command = "search_by_year 1957";
		response = controller.executeTask(command);
		System.out.println(response);
		
		command = "search_by_name st_anger";
		response = controller.executeTask(command);
		System.out.println(response);
		
		command = "search_by_category music";
		response = controller.executeTask(command);
		System.out.println(response);
		
		command = "search_by_category book";
		response = controller.executeTask(command);
		System.out.println(response);
		
		command = "search_by_year 2010";
		response = controller.executeTask(command);
		System.out.println(response);
		
		System.out.println(controller.destroyConnectionPool());
	}

}
