package com.ffviii.bot;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

import com.ffviii.utils.ImageManager;


public class App {

	private static double TIME_PER_BLOCK = 3.333333;

	private static int WALK_CLICK_DELAY = 500;
	private static int EVENT_CLICK_DELAY = 75;

	private static int WIN_DELAY = 8000;
	private static int LOSE_DELAY = 12000;
	private static int REST_DELAY = 2000;
	private static int DROP_DELAY = 3000;
	private static int CICLE_DELAY = 100;
	
	
	private static int UNDEFINED_IN_BATTLE = 50;
	private static int TIMES_REPEATING_STATE = 500;



	private static String CHOCOBO_PROSS_NAME = "Chocobo_EN";
	private static String CHOCOBO_WINDOW_TEXT = "Chocobo World";


	private static Robot actuator;

	private static int[][] map;
	private static int player_x;
	private static int player_y;

	private static int left_ax = 550;
	private static int left_ay = 350;
	private static int right_ax = 625;
	private static int right_ay = 350;
	private static int up_ax = 580;
	private static int up_ay = 315;
	private static int down_ax = 590;
	private static int down_ay = 350;

	private static int aux_x = 1317;
	private static int aux_y = 593;

	private static int unknownImageID = 0;

	private static ImageManager imageManager;
	private static int lastState = -1;
	

	public static void main(String[] args){

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String mode = "";
		try {
			mode = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(mode.equals("C")){

			CaptureMode();

		}else if(mode.equals("T")){

			try {
				TestEvents();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else if(mode.equals("M")){

			MouseConfig();

		}else{
			try {
				InitializeNormalMode();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("Done!");
	}

	private static void InitializeNormalMode() throws InterruptedException{
		System.out.println("Initializing bot...");
		Initialize();
		System.out.println("Im Alive!");


		System.out.print("Staring in...");
		Thread.sleep(3000);
		System.out.print("1...");
		Thread.sleep(3000);
		System.out.print("2...");
		Thread.sleep(3000);
		System.out.println("3!");

		int state = -1;
		int timesIn404 = 0;
		Random generator = new Random(); 
		int timesRepeatingLastState = generator.nextInt(TIMES_REPEATING_STATE);
		boolean inEvent = false;
		
		boolean runningToEvent = true;
		

		while(true){
	    	Calendar cal = Calendar.getInstance();
	    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    	cal.getTime();
	    	System.out.println( sdf.format(cal.getTime()) );
			
			if(state == lastState){
				timesRepeatingLastState--;
				System.out.println("Same as before... " + timesRepeatingLastState);
				if(timesRepeatingLastState < 0){
					Thread.sleep(200);
					if(state == ImageManager.WALKING_DOWN || state == ImageManager.WALKING_UP){
						ClickLeft(EVENT_CLICK_DELAY);
						Thread.sleep(100);
						ClickLeft(EVENT_CLICK_DELAY);
					}else {
						ClickUp(EVENT_CLICK_DELAY);
						Thread.sleep(100);
						ClickUp(EVENT_CLICK_DELAY);
					}
				}
				
			}else {
				timesRepeatingLastState = generator.nextInt(TIMES_REPEATING_STATE);
			}
			
			lastState = state;
			
			if(!runningToEvent){
				int pcoors[] = GetPlayerPos();
				player_x = pcoors[0];
				player_y = pcoors[1];
				map = GetMapMatrix();
				
				
			}

			BufferedImage image = imageManager.GetSmallScreenImage();
			state = imageManager.GetEventFromtImage(image);

			if(inEvent){
				System.out.println("In event... " + state);
				if(state == ImageManager.UNDEFINED){
					imageManager.SaveImageToFile(image, "D:\\temp\\404event.bmp", "bmp");
				}

				if(state == ImageManager.DROP_EVENT){//test for drop event
					System.out.println("Drop event");
					while(state == ImageManager.DROP_EVENT){
						System.out.println("Getting goodies...");
						PressKey(KeyEvent.VK_CONTROL);
						Thread.sleep(DROP_DELAY);
						image = imageManager.GetSmallScreenImage();
						state = imageManager.GetEventFromtImage(image);
					}
					inEvent= false;
					continue;
				}else if(state == ImageManager.WEAPON_EVENT){//test for weapon event
					System.out.println("Wepon event");
					Thread.sleep(5000);
					image = imageManager.GetAllScreenImage();
					imageManager.SaveImageToFile(image, "D:\\temp\\last_weapon_event.bmp", "bmp");
					
					while(state > 3){
						PressKey(KeyEvent.VK_CONTROL);
						Thread.sleep(1000);
						ClickDown(EVENT_CLICK_DELAY);
						image = imageManager.GetSmallScreenImage();
						state = imageManager.GetEventFromtImage(image);
					}
					inEvent= false;
					continue;
				}else {
					System.out.println("Is it a fight event?");
					image = imageManager.GetMapImage();
					state = imageManager.GetEventFromtImage(image);
					imageManager.SaveImageToFile(image, "D:\\temp\\fightEvent.bmp", "bmp");

					if(state == ImageManager.FIGHT_EVENT){
						System.out.println("Fight event");
						timesIn404 = 0;
						while(state > 3){
							System.out.println("In combat...");

							ClickLeft(EVENT_CLICK_DELAY);
							ClickRight(EVENT_CLICK_DELAY);
							ClickLeft(EVENT_CLICK_DELAY);
							ClickRight(EVENT_CLICK_DELAY);

							image = imageManager.GetMapImage();
							state = imageManager.GetEventFromtImage(image);
							if(state == ImageManager.FIGHT_EVENT){
								continue;
							}else if(state == ImageManager.WIN_EVENT){
								System.out.println("Won event!");
								Thread.sleep(WIN_DELAY);
								break;

							}else if(state == ImageManager.LOSE_EVENT){
								System.out.println("Lost event!");
								state = ImageManager.REST_EVENT;
								break;

							}else{
								//imageManager.SaveImageToFile(image, "D:\\temp\\uk\\uk_"+ Integer.toString(unknownImageID)+ ".bmp", "bmp");
								unknownImageID++;
								timesIn404++;
								System.out.println("In combat... 404's:"  + timesIn404);
							}
						}
						inEvent= false;
						if(state == ImageManager.REST_EVENT){
							Thread.sleep(1000);
							System.out.println("Resting...");
							boolean e = false;
							BufferedImage img0;
							BufferedImage img1;
							do{
								img0 = imageManager.GetCurHPImage();
								img1 = imageManager.GetMaxHPImage();
								imageManager.SaveImageToFile(img0, "D:\\temp\\cur.bmp", "bmp");
								imageManager.SaveImageToFile(img1, "D:\\temp\\max.bmp", "bmp");
								Thread.sleep(REST_DELAY);
								e = imageManager.CompareImages(img0, img1);
								System.out.println(e);
							}while(!e);
							
							continue;
						}
					}else{
						Thread.sleep(1000);
						PressKey(KeyEvent.VK_CONTROL);
						PressKey(KeyEvent.VK_CONTROL);

						do{
							image = imageManager.GetSmallScreenImage();
							state = imageManager.GetEventFromtImage(image);}
						while(state > 3);

						inEvent= false;
						continue;
					}
				}//end fight event
			}//end inEvent
			else if (state == ImageManager.ENTERING_EVENT){
				RelaseDirectKeys();
				System.out.println("Found event");
				PressKey(KeyEvent.VK_CONTROL);
				Thread.sleep(400);
				inEvent = true;
				continue;

			}else if( state < ImageManager.WALKING_DOWN+1){
				if(state == ImageManager.WALKING_DOWN){
					System.out.println("Walking down");
					ClickDown(WALK_CLICK_DELAY);
					continue;
				}else if(state == ImageManager.WALKING_UP){
					System.out.println("Walking up");
					ClickUp(WALK_CLICK_DELAY);
					continue;
				}else if(state == ImageManager.WALKING_LEFT){
					System.out.println("Walking left");
					ClickLeft(WALK_CLICK_DELAY);
					continue;
				}
				else if(state == ImageManager.WALKING_RIGHT){
					System.out.println("Walking right");
					ClickRight(WALK_CLICK_DELAY);
					continue;
				}
			}
			if(state > 100){
				System.out.println("404 state, waiting 10s...");
				Thread.sleep(1000);
				PressKey(KeyEvent.VK_CONTROL);
				Thread.sleep(1000);

			}
			System.out.println("End of Cicle." + state);
			actuator.mouseMove(aux_x, aux_y);
			Thread.sleep(CICLE_DELAY);
		}
	}
	
	private static int[] GetPlayerPos() throws InterruptedException{
		int[] pos;
		BufferedImage[] imgs = new BufferedImage[3];
		PressKey(KeyEvent.VK_CONTROL);
		Thread.sleep(50);
		do{

		BufferedImage image0 = imageManager.GetMapImage();
		imageManager.SaveImageToFile(image0, "D:\\temp\\map_0.bmp", "bmp");
		imgs[0] = image0;

		Thread.sleep(200);

		image0 = imageManager.GetMapImage();
		imageManager.SaveImageToFile(image0, "D:\\temp\\map_1.bmp", "bmp");
		imgs[1] = image0;

		Thread.sleep(200);

		image0 = imageManager.GetMapImage();
		imageManager.SaveImageToFile(image0, "D:\\temp\\map_2.bmp", "bmp");
		imgs[2] = image0;

		pos = imageManager.GetPlayerPosition(imgs);
		}while(pos[0]>0);
		System.out.println(pos[0] + " : " + pos[1]);
		PressKey(KeyEvent.VK_CONTROL);
		Thread.sleep(50);
		return pos;
	}
	
	private static int[][] GetMapMatrix() throws InterruptedException{
		int[][] map;
		PressKey(KeyEvent.VK_CONTROL);
		Thread.sleep(50);
		map = imageManager.GetMapFromImage(imageManager.GetMapImage());
		PressKey(KeyEvent.VK_CONTROL);
		return map;
	}
	
	private static int FindClosestEvent(){
		int up, down, left, right;
		for(int range = 1; range <= 18; ++range){
			up = player_y - range;
			down = player_y + range;
			left = player_x - range;
			right = player_x + range;
			
			//TODO: need to lookup an afficient algoritm for this one
		}
		return 0;
	}
	

	private static void TestEvents() throws InterruptedException{
		System.out.println("Starting in TestEvents mode...");
		Initialize();
		int[][] map = imageManager.GetMapFromImage(imageManager.GetMapImage());
		for(int i = 0; i < map.length; ++i){
			for(int j = 0; j < map[i].length; ++j){
				
				System.out.print(map[i][j] + " ");
			}
			System.out.println(" ");
		}

	}
	
	private static void TestMapPositionCapture() throws InterruptedException{
		System.out.println("Map position capture test mode...");
		while(true){
			PressKey(KeyEvent.VK_CONTROL);
			BufferedImage[] imgs = new BufferedImage[3];
			Thread.sleep(50);

			BufferedImage image0 = imageManager.GetMapImage();
			imageManager.SaveImageToFile(image0, "D:\\temp\\map_0.bmp", "bmp");
			imgs[0] = image0;

			Thread.sleep(200);

			image0 = imageManager.GetMapImage();
			imageManager.SaveImageToFile(image0, "D:\\temp\\map_1.bmp", "bmp");
			imgs[1] = image0;

			Thread.sleep(200);

			image0 = imageManager.GetMapImage();
			imageManager.SaveImageToFile(image0, "D:\\temp\\map_2.bmp", "bmp");
			imgs[2] = image0;

			int[] pos = imageManager.GetPlayerPosition(imgs);
			System.out.println(pos[0] + " : " + pos[1]);
			PressKey(KeyEvent.VK_CONTROL);
			Thread.sleep(50);
			System.out.println("Resting 2/20 secs...");
			Thread.sleep(100);
		}

	}

	private static void CaptureMode(){
		System.out.println("Initilizalizing in Capture mode...");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		imageManager = new ImageManager();
		System.out.print("Insert Coordenates:");
		String coordenates = "";
		try {
			coordenates = br.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String parts[] = coordenates.split("X");
		imageManager.InitializeCoordenates(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

		System.out.print("Insert number of picture to take:");
		int num_pics = 0;
		try {
			num_pics = br.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("Insert a prefix for the pictures:");
		String prefix = "picure_";
		try {
			prefix = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String patch = "D:\\temp\\" + prefix;
		BufferedImage image;
		for(int t = 0; t < num_pics; t++){
			image = imageManager.GetMapImage();
			imageManager.SaveImageToFile(image, patch + Integer.toString(t) + ".bmp", "bmp");
			System.out.println(t +  "/" + num_pics);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static void MouseConfig(){
		System.out.println("Mouse config mode...");
		System.out.println("Still in dev mode. Come on man, fix me already!");
		Point p = null;
		int key = -1;
		Scanner s = new Scanner(System.in);
		
		while(key != KeyEvent.VK_U){
			System.out.println("Please, click button up " + key);
			key = s.nextInt();
		}
		p = MouseInfo.getPointerInfo().getLocation();
		up_ax = p.x;
		up_ay = p.y;
		
		/*
		while(true){
			a = MouseInfo.getPointerInfo().getLocation();
			System.out.println(a.getX() + " : " + a.getY());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}

	private static void Initialize() throws InterruptedException{
		int[] coords = ReadWindowPosition();
	

		try {
			actuator= new Robot();
		} catch (AWTException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Created Robot...");

		imageManager = new ImageManager();
		System.out.println("Created ImageManager...");

		imageManager.InitializeCoordenates(coords[0], coords[1]);
		System.out.println("Initialized Coordenates...");

		imageManager.InitializeImages();
		System.out.println("Loaded Images...");
	}
	
	private static void ClickUp(int holdTime){
		actuator.mouseMove(up_ax, up_ay);
		actuator.mousePress(InputEvent.BUTTON1_MASK);
		actuator.delay(holdTime);
		actuator.mouseRelease(InputEvent.BUTTON1_MASK);	
	}
	private static void ClickDown(int holdTime){
		actuator.mouseMove(down_ax, down_ay);
		actuator.mousePress(InputEvent.BUTTON1_MASK);
		actuator.delay(holdTime);
		actuator.mouseRelease(InputEvent.BUTTON1_MASK);	
	}
	private static void ClickLeft(int holdTime){
		actuator.mouseMove(left_ax, left_ay);
		actuator.mousePress(InputEvent.BUTTON1_MASK);
		actuator.delay(holdTime);
		actuator.mouseRelease(InputEvent.BUTTON1_MASK);	
	}
	private static void ClickRight(int holdTime){
		actuator.mouseMove(right_ax, right_ay);
		actuator.mousePress(InputEvent.BUTTON1_MASK);
		actuator.delay(holdTime);
		actuator.mouseRelease(InputEvent.BUTTON1_MASK);	
	}

	private static int[] ReadWindowPosition(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		try {
			line = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String parts[] = line.split("X");
		int[] result = new int[2];
		result[0] = Integer.parseInt(parts[0]);
		result[1] = Integer.parseInt(parts[1]);
		return result;
	}

	private static void HoldKey(int keyCode){
		try{
			actuator.keyPress(keyCode);
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void ReleaseKey(int keyCode){
		actuator.keyRelease(keyCode);
	}

	private static void RelaseDirectKeys(){
		ReleaseKey(KeyEvent.VK_LEFT);
		ReleaseKey(KeyEvent.VK_RIGHT);
		ReleaseKey(KeyEvent.VK_UP);
		ReleaseKey(KeyEvent.VK_DOWN);
	}

	private static void PressKey(int keyCode){

		try {
			actuator.keyPress(keyCode);
			Thread.sleep(100);
			actuator.keyRelease(keyCode);
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}





	/*
	HWND windowHAndle = null;
	windowHAndle = Win32WindowUtils.GetWindowHandle(CHOCOBO_WINDOW_TEXT.toUpperCase(), null);

	if(windowHAndle != null) {
		System.out.println("FOCUS!");
		System.out.println(User32.INSTANCE.ShowWindow(windowHAndle, User32.SW_SHOWDEFAULT));
		System.out.println(User32.INSTANCE.SetFocus(windowHAndle));
	}*/


}
