package thirdpartylibrary;

public class Server {

	Server() {
		System.out.println("Classloader on Server start: " + ExecEnvironment.class.getClassLoader().getName());
		if (!isExecEnvironmentTrue("arg1")) {
			throw new RuntimeException("ExecEnvironment.value still has the orignal value false!");
		}
		System.out.println("~~ > ~~ > ~~ > ~~ > ~~ > ~~ . ~~ < ~~ < ~~ < ~~ < ~~ < ~~ < ");
		System.out.println("                  IT FINALLY WORKED!!!!");
		System.out.println("~~ > ~~ > ~~ > ~~ > ~~ > ~~ . ~~ < ~~ < ~~ < ~~ < ~~ < ~~ < ");
	}

	private boolean isExecEnvironmentTrue(String test) {
		return ExecEnvironment.value;
	}
	
	public static void main(String[] args) {
		new Server();
	}

}
