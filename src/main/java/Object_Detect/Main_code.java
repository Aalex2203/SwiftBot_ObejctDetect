package Object_Detect;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import swiftbot.Button;
import swiftbot.ImageSize;
import swiftbot.SwiftBotAPI;


public class Main_code {
	static SwiftBotAPI swiftBot;
	//created static variable so i can use it in different methods 
	static String savedMode;

	public static void main(String[] args) {
		swiftBot = new SwiftBotAPI();
		System.out.println("\u001B[34m"); //blue colour
		System.out.println(" __        _______ _     ____ ___  __  __ _____ \r\n"
				+ " \\ \\      / / ____| |   / ___/ _ \\|  \\/  | ____|\r\n"
				+ "  \\ \\ /\\ / /|  _| | |  | |  | | | | |\\/| |  _|  \r\n"
				+ "   \\ V  V / | |___| |__| |__| |_| | |  | | |___ \r\n"
				+ "    \\_/\\_/  |_____|_____\\____\\___/|_|  |_|_____|\r\n"
				+ "");

		System.out.println("\u001B[0m"); //reset colour to default 
		System.out.println(""
				+ "Hello! This is Detect object game. \r\n"
				+ "To choose the mode - scan the QR code with SwiftBot.\r\n"
				+ "\r\n"
				+ "\u001B[34m>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\u001B[0m\r\n"
				+ "\u001B[34m> You have 3 modes:                           >\u001B[0m\r\n"
				+ ">   1. Curious                                          >\r\n"
				+ ">   2. Scaredy                                          >\r\n"
				+ ">   3. Dubious                                          >\r\n"
				+ "\u001B[34m>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\u001B[0m\r\n"
				);
		QRcode();
	}


	public static void QRcode() {
		long startTime = System.currentTimeMillis();
		long endTime = startTime + 100000; // 100 seconds

		try {
			BufferedImage img = null;
			//timer for qr code detection
			while (img == null && System.currentTimeMillis() < endTime) {
				String decodedMessage = null;
				while (decodedMessage == null || decodedMessage.trim().isEmpty()){
					img = swiftBot.getQRImage();
					Thread.sleep(500);
					decodedMessage = swiftBot.decodeQRImage(img);
					savedMode=decodedMessage;

					if (img == null || decodedMessage == null || decodedMessage.trim().isEmpty()) {
						System.out.println("No QR code was scanned. PLease try later");
					}
				}

				decodedMessage = decodedMessage.trim().toLowerCase();
				//when qr code is detected
				System.out.println("The chosen game mode is:  " + decodedMessage);

				switch (decodedMessage) {
				case "curious":
					curious();
					break;
				case "scaredy":
					scaredy();
					break;
				case "dubious":
					dubious();
					break;
					//if the wrong qr code was scanned 
				default:
					System.out.println("Sorry, you scanned the wrong QR code. Please, try again.");
					break;
				}

				System.out.println("Time elapsed: " + (System.currentTimeMillis() - startTime) / 1000.0 + " seconds"); 
			} 

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}


	static int NumOfDetect = 0; // variable for detected objects 
	public static void curious() throws InterruptedException { 

		String yellow = "\u001B[33m";
		String reset_y = "\u001B[0m";
		String curious1 = "   ____ _   _ ____  ___ ___  _   _ ____    __  __  ___  ____  _____ \r\n"
				+ "  / ___| | | |  _ \\|_ _/ _ \\| | | / ___|  |  \\/  |/ _ \\|  _ \\| ____|\r\n"
				+ " | |   | | | | |_) || | | | | | | \\___ \\  | |\\/| | | | | | | |  _|  \r\n"
				+ " | |___| |_| |  _ < | | |_| | |_| |___) | | |  | | |_| | |_| | |___ \r\n"
				+ "  \\____|\\___/|_| \\_\\___\\___/ \\___/|____/  |_|  |_|\\___/|____/|_____|\r\n"
				+ " ";
		System.out.println(yellow + curious1 + reset_y);
		System.out.println("Your mode now is: Curious mode \r\n"
				+ "\r\n"
				+ "  \u001B[33m**********************************\u001B[0m           \r\n"
				+ "\u001B[33m||\u001B[0m         Press button A          \u001B[33m ||\u001B[0m\r\n"
				+ "\u001B[33m||\u001B[0m     on the SwiftBot to start.    \u001B[33m||\u001B[0m   \r\n"
				+ " \u001B[33m **********************************\u001B[0m\r\n");

		startOfgame();

		int[] blue = {0, 0, 255}; //lights set to blue while wandering around
		swiftBot.fillUnderlights(blue);
		boolean objectDetected = false;


		// start of detection and wandering around
		while (!objectDetected) {
			int distance = (int) swiftBot.useUltrasound();
			System.out.println("Measured distance.... " + distance + " cm");
			if (distance == 30) {
				System.out.println("Object detected!");
				// lights set to green when position is completed
				int[] green = {0, 255, 0};
				swiftBot.fillUnderlights(green);
				// activate Camera
				swiftBot.takeStill(ImageSize.SQUARE_720x720);
				//save the picture

				objectDetected = true;
				swiftBot.stopMove(); //stop when object is detected
				NumOfDetect++;
				break;
			}

			// check of the distance
			swiftBot.move(50, 50, 750); //move forward
			Thread.sleep(100);

			//wandering around
			if (Math.random() > 0.5) {
				System.out.println("Turning left");
				swiftBot.move(-30, 30, 750);
			} else {
				System.out.println("Turning right");
				swiftBot.move(30, -30, 750);
			}
			Thread.sleep(100);
		}

		// object detected = stop the wandering around process 
		swiftBot.stopMove();
		swiftBot.disableUnderlights(); 
		//check of the distance 
		int measuredDistance = (int) swiftBot.useUltrasound();
		System.out.println("The measured distance:  " + measuredDistance + " cm");


		// re-check if the distance is 30cm
		while (measuredDistance != 30) {
			System.out.println("The distance is not 30cm! Adjusting the position");

			if (measuredDistance > 30) {
				System.out.println("The distance is more than 30cm! Adjusting the position");
				swiftBot.move(30, 30, 750); // move forward
			} 
			else if (measuredDistance < 30) {

				System.out.println("The distance is less than 30cm! Adjusting the position");
				swiftBot.move(-30, -30, 750); // move backward
			}

			Thread.sleep(500); 
			swiftBot.stopMove(); 

			//check again if the distance has changed 
			measuredDistance = (int) swiftBot.useUltrasound();
			System.out.println("Final distance:  " + measuredDistance + " cm");
		}


		//adjusting the position
		try {
			System.out.println("the position is adjusted. ");

			// lights set to green when position is completed
			int[] green = {0, 255, 0};
			swiftBot.fillUnderlights(green);

			// take a photo and save 
			System.out.println("Photo of detected object was taken and saved.");
			BufferedImage image = swiftBot.takeStill(ImageSize.SQUARE_720x720);

			File outputFile = new File("/data/home/pi/capturedImage.png");
			ImageIO.write(image, "png", outputFile);

			// turn off lights
			swiftBot.disableUnderlights();

		} catch (Exception e) {
			System.err.println("Sorry, something went wrong......");
			e.printStackTrace();
		}


		swiftBot.disableUnderlights();
		System.out.println("Press button X to stop the game....");
		//command to stop the game
		swiftBot.enableButton(Button.X, () -> {
			Termination2.Termination();

		});
	}


	

	public static void scaredy() throws InterruptedException {
			String green = "\u001B[32m";
			String reset_g = "\u001B[0m";

			String scaredy1 = "  ____   ____    _    ____  _____ ______   __  __  __  ___  ____  _____ \r\n"
					+ " / ___| / ___|  / \\  |  _ \\| ____|  _ \\ \\ / / |  \\/  |/ _ \\|  _ \\| ____|\r\n"
					+ " \\___ \\| |     / _ \\ | |_) |  _| | | | \\ V /  | |\\/| | | | | | | |  _|  \r\n"
					+ "  ___) | |___ / ___ \\|  _ <| |___| |_| || |   | |  | | |_| | |_| | |___ \r\n"
					+ " |____/ \\____/_/   \\_\\_| \\_\\_____|____/ |_|   |_|  |_|\\___/|____/|_____|\r\n"
					+ "  \r\n"
					+ "";


			System.out.println(green + scaredy1 + reset_g);
			System.out.println("Your mode now is: Scaredy mode \r\n"
					+ "\r\n"
					+ "  \u001B[32m**********************************\u001B[0m            \r\n"
					+ "\u001B[32m||\u001B[0m        Press button A           \u001B[32m||\u001B[0m\r\n"
					+ "\u001B[32m||\u001B[0m     on the SwiftBot to start.    \u001B[32m||\u001B[0m   \r\n"
					+ " \u001B[32m **********************************\u001B[0m\r\n");
			startOfgame();






			// lights set to blue
			int[] blue = {0, 0, 255};
			swiftBot.fillUnderlights(blue);

			boolean objectDetected = false;


			//start of wandering around and detecting object 
			while (!objectDetected) {
				long startTime = System.currentTimeMillis();
				long endTime = startTime + 5000; 

				while (System.currentTimeMillis() < endTime && !objectDetected) {

					int distance = (int) swiftBot.useUltrasound();
					System.out.println("Current distace: " + distance + " cm");

					//If distance <=50
					if (distance <= 50) {
						System.out.println("Object detected at 50 cm! Stopping wandering...");
						objectDetected = true;
						NumOfDetect++;
						// activate Camera
						swiftBot.takeStill(ImageSize.SQUARE_720x720);
						//save the pic


						// blink with red underlights 
						int[] red = {255, 0, 0};
						swiftBot.fillUnderlights(red);
						Thread.sleep(300); 

						// backup and turn in the opposite direction
						swiftBot.move(-30, -30, 1500); // move back
						Thread.sleep(1500); 
						swiftBot.stopMove(); 


						//wandering around
						if (Math.random() > 0.5) {
							System.out.println("Turning left");
							swiftBot.move(-30, 30, 1500);
						} else {
							System.out.println("Turning right");
							swiftBot.move(30, -30, 1500);
						}
						Thread.sleep(1500); 
						swiftBot.stopMove(); 

						break; //exit of the loop 
					}
					swiftBot.move(50, 50, 750); //move forward
					Thread.sleep(150); 

					// wandering around
					if (Math.random() > 0.5) {
						System.out.println("Turning left");
						swiftBot.move(-30, 30, 750);  
					} else {
						System.out.println("Turning right...");
						swiftBot.move(30, -30, 750); 
					}
					Thread.sleep(150); 
				}

				// if object was not detected in 5 seconds, moving in different direction
				if (!objectDetected) {
					System.out.println("Object was not detected in 5 seconds. Moving in different direction");

					// Stop movement
					swiftBot.stopMove();
					Thread.sleep(1000);
					DifferentDirection(); 
				}
			}
			swiftBot.disableUnderlights();
			System.out.println("Press button X to stop the game....");

			//command to stop the game
			swiftBot.enableButton(Button.X, () -> {
				Termination2.Termination();
				//			
			});
		}


	//method for different direction 
	public static void DifferentDirection() throws InterruptedException {
		System.out.println("Robot starts moving in a different direction...");
		swiftBot.move(30, 30, 3000);  // move forward
		Thread.sleep(500);
		swiftBot.move(-30, 30, 1000);  // turn to the right
		Thread.sleep(500);
		swiftBot.move(30, 30, 3000);  // move forward
		Thread.sleep(500);
		swiftBot.move(30, -30, 1000);  // turn to the right
		Thread.sleep(500);
		swiftBot.move(50, 30, 2500);  // move forward
		Thread.sleep(500);
		swiftBot.move(-30, 30, 1000);  //turn to the right
		Thread.sleep(500);
		swiftBot.move(30, 30, 2500);  // move forward
		Thread.sleep(500);
		swiftBot.stopMove();
	}

	public static void dubious() throws InterruptedException, IOException { 
		String red = "\u001B[31m";
		String reset_r = "\u001B[0m";
		String dubious1 = 
				"   ____  _   _ ____ ___ ___  _   _ ____    __  __  ___  ____  _____ \n" +
						"  |  _ \\| | | | __)_ _/ _ \\| | | / ___|   |  \\/  |/ _ \\|  _ \\| ____|\n" +
						"  | | | | | | |  _ \\| | | | | | | \\___ \\  | |\\/| | | | | | | |  _|  \n" +
						"  | |_| | |_| | |_) | | |_| | |_| |___) | | |  | | |_| | |_| | |___ \n" +
						"  |____/ \\___/|____/___\\___/ \\___/|____/  |_|  |_|\\___/|____/|_____|\n";


		System.out.println(red + dubious1 + reset_r);
		System.out.println("Your mode now is: Dubious mode \r\n"
				+ "\r\n"
				+ " \u001B[31m **********************************\u001B[0m            \r\n"
				+ "\u001B[31m||\u001B[0m         Press button A           \u001B[31m||\u001B[0m\r\n"
				+ "\u001B[31m||\u001B[0m     on the SwiftBot to start.    \u001B[31m||\u001B[0m   \r\n"
				+ "\u001B[31m  **********************************\u001B[0m\r\n");

		//random generation between 1 and 2
		Random random = new Random();
		int num = random.nextInt(2) + 1; 

		if (num == 1) {
			System.out.println("The executing Curious mode!");
			curious();
			return; //stops execution 
		} else { //if num == 2
			System.out.println("The executing Dubious mode!");
			scaredy();
			return; //stops execution 
		}
	}



	private static void startOfgame() { 
		System.out.println("To start game press Button A.");

		swiftBot.enableButton(Button.A, () -> {

			System.out.println("Button A Pressed. Start of the game....");
		});

		
	}



}
