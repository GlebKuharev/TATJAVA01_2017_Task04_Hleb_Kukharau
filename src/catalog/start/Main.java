package catalog.start;

import catalog.controller.Controller;

public class Main {

	public static void main(String[] args) {
		
		String command = "add book 1957 Atlas_Shrugged byAynRand";
		
		Controller controller = new Controller();
		String response = controller.executeTask(command);
		System.out.println(response);
	}

}
