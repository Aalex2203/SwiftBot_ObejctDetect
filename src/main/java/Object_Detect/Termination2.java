package Object_Detect;

import java.time.Duration;
import java.time.Instant;

import swiftbot.Button;

public class Termination2 {
	
	public static void Termination() {
		Main_code.swiftBot.disableButton(Button.X);

		String blue = "\u001B[34m";
		String reset_b = "\u001B[0m";
		String termination1 = "  _____                   _             _   _             \r\n"
				+ " |_   _|__ _ __ _ __ ___ (_)_ __   __ _| |_(_) ___  _ __  \r\n"
				+ "   | |/ _ \\ '__| '_ ` _ \\| | '_ \\ / _` | __| |/ _ \\| '_ \\ \r\n"
				+ "   | |  __/ |  | | | | | | | | | | (_| | |_| | (_) | | | |\r\n"
				+ "   |_|\\___|_|  |_| |_| |_|_|_| |_|\\__,_|\\__|_|\\___/|_| |_|\r\n"
				+ "                                                          ";



		System.out.println(blue + termination1 + reset_b);
		System.out.println("\u001B[34m>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\u001B[0m\r\n"
				+ ">> Do you want to see the execution log? >>\r\n"
				+ "\u001B[34m>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\u001B[0m\r\n"
				+ ">> Press the [Y] button on the SwiftBot for Yes. >>\r\n"
				+ ">> Press the [X] button on the SwiftBot for No. >>\r\n"
				+ "\u001B[34m>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\u001B[0m\r\n"
				+ "");

		long startTime = System.currentTimeMillis();
		long executionDuration = (System.currentTimeMillis() - startTime) / 1000; 

		Main_code.swiftBot.enableButton(Button.Y, () -> { 
			System.out.println("Execution Log:");
			displayExecutionLog();
		});
		Main_code.swiftBot.enableButton(Button.X, () -> { 
			System.out.println("Game Over. Terminating...");
			System.out.println("\u001B[34m...........................................................\u001B[0m\r\n"
					+ "\u001B[34m.\u001B[0m                 Here is your execution log:             \u001B[34m.\u001B[0m\r\n"
					+ "\u001B[34m...........................................................\u001B[0m\r\n"
					+ ".   File path for log file:"                    + logFile  + "      \u001B[34m.\u001B[0m \r\n" 

			+ "");
			System.exit(0);
		});

	}


	static String photoPath = "/data/home/pi/capturedImage.png"; // Path to photos
	static String logFile = "/data/home/pi/Logfile.txt"; //path to the log file




	private static void displayExecutionLog() {
		Instant startTime = Instant.now();
		Instant endTime = Instant.now();
		Duration executionDuration = Duration.between(startTime, endTime);

		System.out.println("\u001B[34m..............................................................\u001B[0m\r\n"
				+ "\u001B[34m.\u001B[0m                   Here is your execution log:              \u001B[34m.\u001B[0m\r\n"
				+ "\u001B[34m..............................................................\u001B[0m\r\n"
				+ "\u001B[34m.  The mode:"                    +                  Main_code.savedMode + "\u001B[34m.\u001B[0m\r\n"
				+ "\u001B[34m.   Duration of execution:"       +    executionDuration.toMillis() + " ms\n" +"\u001B[34m.\u001B[0m\r\n"
				+ "\u001B[34m.   Encounters with objects:"      +          Main_code.NumOfDetect + "\u001B[34m.\u001B[0m\r\n"
				+ "\u001B[34m.   File path for image:"       +              photoPath +"\u001B[34m.\u001B[0m\r\n"
				+ "\u001B[34m.   File path for log file:"                        + logFile + "\u001B[34m.\u001B[0m\r\n"
				+ "\u001B[34m..............................................................\u001B[0m\r\n"
				+ "");


	}

}
