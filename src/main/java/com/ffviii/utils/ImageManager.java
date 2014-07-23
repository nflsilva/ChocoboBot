package com.ffviii.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManager {
	
    private static final int MAP_POINT_HEIGHT = 4;
    private static final int MAP_POINT_WIDTH = 4;
    private static final int MAP_X_OFFSET = 8;
    private static final int MAP_Y_OFFSET = 4;
    private static final int MAP_HEIGHT = 72;
    private static final int MAP_WIDTH = 72;
    private static final int WINDOW_X_OFFSET = 4;
    private static final int WINDOW_Y_OFFSET = 23;
    private static final int WINDOW_HEIGHT = 128;
    private static final int WINDOW_SMALL_HEIGHT = 108;
    private static final int WINDOW_WIDTH = 128;
    
    private static final int CUR_HP_X_OFFSET = 42;
    private static final int CUR_HP_Y_OFFSET = 3;
    
    private static final int MAX_HP_X_OFFSET = 90;
    private static final int MAX_HP_Y_OFFSET = 3;
    
    private static final int HP_WINDOW_HEIGHT = 22;
    private static final int HP_WINDOW_WIDTH = 36;
    
    private static final int WHITE_VALUE = 0;
    private static final int BLACK_VALUE = 1;
	
    private static final String IMAGE_EXTENCION = ".bmp";
    
    private static final int NUM_R_FRAMES = 4;			//moving right
    private static final int NUM_L_FRAMES = 4;			//moving left
    private static final int NUM_U_FRAMES = 6;			//moving up
    private static final int NUM_D_FRAMES = 6;			//moving down
    private static final int NUM_EE_FRAMES = 1;			//entering event
    private static final int NUM_FE_FRAMES = 20;		//fight event
    private static final int NUM_DE_FRAMES = 3;			//drop event
    private static final int NUM_WE_FRAMES = 4;			//weapon event
    private static final int NUM_RE_FRAMES = 3;			//rest event
    private static final int NUM_LE_FRAMES = 1;			//lose event
    private static final int NUM_WI_FRAMES = 7;			//win event
    
    private static final String IMAGES_MASTER_PATH = "images\\";
    
    private static final String R_FILE_PATH = IMAGES_MASTER_PATH + "right\\right_";
    private BufferedImage r_imgs[];
    private static final String L_FILE_PATH = IMAGES_MASTER_PATH + "left\\left_";
    private BufferedImage l_imgs[];
    private static final String U_FILE_PATH = IMAGES_MASTER_PATH + "up\\up_";
    private BufferedImage u_imgs[];
    private static final String D_FILE_PATH = IMAGES_MASTER_PATH + "down\\down_";
    private BufferedImage d_imgs[];
    
    private static final String E_FILE_PATH = IMAGES_MASTER_PATH + "event\\event_";
    private BufferedImage ee_imgs[];
    
    private static final String EF_FILE_PATH = IMAGES_MASTER_PATH + "event\\fight\\fight_";
    private BufferedImage fe_imgs[];
    
    private static final String ED_FILE_PATH = IMAGES_MASTER_PATH + "event\\drop\\drop_";
    private BufferedImage de_imgs[];
    
    private static final String WE_FILE_PATH = IMAGES_MASTER_PATH + "event\\weapon\\weapon_";
    private BufferedImage we_imgs[];
    
    private static final String RE_FILE_PATH = IMAGES_MASTER_PATH + "rest\\rest_";
    private BufferedImage re_imgs[];
    
    private static final String LE_FILE_PATH = IMAGES_MASTER_PATH + "event\\lose\\lose_";
    private BufferedImage le_imgs[];
    
    private static final String WI_FILE_PATH = IMAGES_MASTER_PATH + "event\\win\\win_";
    private BufferedImage wi_imgs[];
    
    public static final int UNDEFINED = 404;
    public static final int WALKING_UP = 0;
    public static final int WALKING_LEFT = 1;
    public static final int WALKING_RIGHT = 2;
    public static final int WALKING_DOWN = 3;
    public static final int ENTERING_EVENT = 4;
    public static final int FIGHT_EVENT = 5;
    public static final int WEAPON_EVENT = 6;
    public static final int DROP_EVENT = 7;
    public static final int REST_EVENT = 8;
    public static final int LOSE_EVENT = 9;
    public static final int WIN_EVENT = 10;
   
  
    public static final int X_POS = 0;
    public static final int Y_POS = 1;
    
    private int initialX;
    private int initialY;
    
    public void InitializeImages(){
    	InitializeDownImages();
    	InitializeLeftImages();
    	InitializeRightImages();
    	InitializeUpImages();
    	InitializeEventImages();
    	InitializeRestImages();
    	InitializeLoseImages();
    	InitializeWinImages();
    	InitializeWeaponImages();
    }
    private void InitializeRightImages(){
    	r_imgs = new BufferedImage[NUM_R_FRAMES];
    	for(int i = 0; i < NUM_R_FRAMES; i++){
    		r_imgs[i] = LoadImageFromFile(R_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
    	}
    }
    private void InitializeLeftImages(){
    	l_imgs = new BufferedImage[NUM_L_FRAMES];
    	for(int i = 0; i < NUM_L_FRAMES; i++){
    		l_imgs[i] = LoadImageFromFile(L_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
    	}
    }
    private void InitializeUpImages(){
    	u_imgs = new BufferedImage[NUM_U_FRAMES];
    	for(int i = 0; i < NUM_U_FRAMES; i++){
    		u_imgs[i] = LoadImageFromFile(U_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
    	}
    }    
    private void InitializeDownImages(){
    	d_imgs = new BufferedImage[NUM_D_FRAMES];
    	for(int i = 0; i < NUM_D_FRAMES; i++){
    		d_imgs[i] = LoadImageFromFile(D_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
    	}
    }    
    private void InitializeEventImages(){
    	ee_imgs = new BufferedImage[NUM_EE_FRAMES];
    	for(int i = 0; i < NUM_EE_FRAMES; i++){
    		ee_imgs[i] = LoadImageFromFile(E_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
			System.out.println(i + E_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
    	}
    	de_imgs = new BufferedImage[NUM_DE_FRAMES];
    	for(int i = 0; i < NUM_DE_FRAMES; i++){
    		de_imgs[i] = LoadImageFromFile(ED_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
			System.out.println(i + ED_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
    	}
    	fe_imgs = new BufferedImage[NUM_FE_FRAMES];
    	for(int i = 0; i < NUM_FE_FRAMES; i++){
    		fe_imgs[i] = LoadImageFromFile(EF_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
			System.out.println(i + EF_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
    	}
    }    
    private void InitializeRestImages(){
    	re_imgs = new BufferedImage[NUM_RE_FRAMES];
    	for(int i = 0; i < NUM_RE_FRAMES; i++){
    		re_imgs[i] = LoadImageFromFile(RE_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
			System.out.println(i + RE_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
    	}
    }
    private void InitializeLoseImages(){
    	le_imgs = new BufferedImage[NUM_LE_FRAMES];
    	for(int i = 0; i < NUM_LE_FRAMES; i++){
    		le_imgs[i] = LoadImageFromFile(LE_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
			System.out.println(i + LE_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
    	}
    }
    private void InitializeWinImages(){
    	wi_imgs = new BufferedImage[NUM_WI_FRAMES];
    	for(int i = 0; i < NUM_WI_FRAMES; i++){
    		wi_imgs[i] = LoadImageFromFile(WI_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
			System.out.println(i + WI_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
    	}
    }
    private void InitializeWeaponImages(){
    	we_imgs = new BufferedImage[NUM_WE_FRAMES];
    	for(int i = 0; i < NUM_WE_FRAMES; i++){
    		we_imgs[i] = LoadImageFromFile(WE_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
			System.out.println(i + WE_FILE_PATH + Integer.toString(i) + IMAGE_EXTENCION);
    	}
    }

    public int GetRed(int pixel){
        return (pixel & 0x00FF0000) >>> 16;
    }
    public int GetGreen(int pixel){
        return (pixel & 0x0000FF00) >>> 8;
    }
    public int GetBlue(int pixel){
        return (pixel & 0x000000FF);
    }
    
    public void InitializeCoordenates(int initialX, int initialY){
    	this.initialX = initialX;
    	this.initialY = initialY;
    }

	public BufferedImage GetMapImage(){
		BufferedImage image = null;
		image = Win32WindowUtils.TakePrintScreen(initialX+WINDOW_X_OFFSET+MAP_X_OFFSET, initialY+WINDOW_Y_OFFSET+MAP_Y_OFFSET, MAP_WIDTH, MAP_HEIGHT);
		return image;
	}
	public BufferedImage GetSmallScreenImage(){
		BufferedImage image = null;
		image = Win32WindowUtils.TakePrintScreen(initialX+WINDOW_X_OFFSET,  initialY+WINDOW_Y_OFFSET, WINDOW_WIDTH, WINDOW_SMALL_HEIGHT);
		return image;
	}
	public BufferedImage GetAllScreenImage(){
		BufferedImage image = null;
		image = Win32WindowUtils.TakePrintScreen(initialX+WINDOW_X_OFFSET, initialY+WINDOW_Y_OFFSET, WINDOW_WIDTH, WINDOW_HEIGHT);
		return image;
	}
	public BufferedImage GetCurHPImage(){
		BufferedImage image = null;
		image = Win32WindowUtils.TakePrintScreen(initialX+WINDOW_X_OFFSET+CUR_HP_X_OFFSET, initialY+WINDOW_Y_OFFSET+CUR_HP_Y_OFFSET, HP_WINDOW_WIDTH, HP_WINDOW_HEIGHT);
		return image;
	}
	
	public BufferedImage GetMaxHPImage(){
		BufferedImage image = null;
		image = Win32WindowUtils.TakePrintScreen(initialX+WINDOW_X_OFFSET+MAX_HP_X_OFFSET, initialY+WINDOW_Y_OFFSET+MAX_HP_Y_OFFSET, HP_WINDOW_WIDTH, HP_WINDOW_HEIGHT);
		return image;
	}
	
	
	public void SaveImageToFile(BufferedImage image, String name, String format){
		File output = new File(name);
		try {
			ImageIO.write(image, format, output);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	public BufferedImage LoadImageFromFile(String fileName){
		BufferedImage image = null;
		File file = new File(fileName);
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return image;
	}
	public boolean CompareImages(BufferedImage img0, BufferedImage img1){
		
		if(img0.getHeight() != img1.getHeight() || img0.getWidth() != img1.getWidth()){
			return false;
		}
		
		boolean areEqual = true;
		for(int i = 0; i < img0.getWidth(); i++){
			for(int j = 0; j < img0.getHeight(); j++){
				if(img0.getRGB(i, j) != img1.getRGB(i, j)){
					areEqual = false;
					break;
				}
				if(!areEqual){
					break;
				}
			}
		}
		return areEqual;
	}

    public int GetEventFromtImage(BufferedImage img0){
    	
    	for(BufferedImage img1 : r_imgs){
    		if(CompareImages(img0, img1)){
    			return WALKING_RIGHT;
    		}
    	}
    	
    	for(BufferedImage img1 : l_imgs){
    		if(CompareImages(img0, img1)){
    			return WALKING_LEFT;
    		}
    	}
    	
    	for(BufferedImage img1 : d_imgs){
    		if(CompareImages(img0, img1)){
    			return WALKING_DOWN;
    		}
    	}
    	
    	for(BufferedImage img1 : u_imgs){
    		if(CompareImages(img0, img1)){
    			return WALKING_UP;
    		}
    	}
    	
    	for(BufferedImage img1 : ee_imgs){
    		if(CompareImages(img0, img1)){
    			return ENTERING_EVENT;
    		}
    	}
    	
    	for(BufferedImage img1 : de_imgs){
    		if(CompareImages(img0, img1)){
    			return DROP_EVENT;
    		}
    	}
    	
    	for(BufferedImage img1 : fe_imgs){
    		if(CompareImages(img0, img1)){
    			return FIGHT_EVENT;
    		}
    	}
    	
    	for(BufferedImage img1 : re_imgs){
    		if(CompareImages(img0, img1)){
    			return REST_EVENT;
    		}
    	}
    	
    	for(BufferedImage img1 : le_imgs){
    		if(CompareImages(img0, img1)){
    			return LOSE_EVENT;
    		}
    	}
    	
    	for(BufferedImage img1 : we_imgs){
    		if(CompareImages(img0, img1)){
    			return WEAPON_EVENT;
    		}
    	}
    	
    	for(BufferedImage img1 : wi_imgs){
    		if(CompareImages(img0, img1)){
    			return WIN_EVENT;
    		}
    	}
    	
    	return UNDEFINED;
    }
	
    public int[][] GetMapFromImage(BufferedImage image){
    	int h = MAP_HEIGHT/MAP_POINT_HEIGHT;
    	int w = MAP_WIDTH/MAP_POINT_WIDTH;
    	int[][] map = new int[h][w];
    	int r, g,b;
		for(int i = 0, x = 0; i < image.getHeight(); i+= MAP_POINT_HEIGHT, x++){
			
			for(int j = 0, y = 0; j < image.getWidth(); j+= MAP_POINT_WIDTH, y++){
				
				r = GetRed(image.getRGB(i, j));
				g = GetGreen(image.getRGB(i, j));
				b = GetBlue(image.getRGB(i, j));
				System.out.println(x + "," + y + " RGB " + r + ":" + g + ":" + b);
				if(r > 250 && g > 250 && b > 250){
					map[y][x] = WHITE_VALUE;
				}else {
					map[y][x] = BLACK_VALUE;
				}
			}
		}
		return map;
    }
    public int[] GetPlayerPosition(BufferedImage[] images){
    	
    	int[] res = new int[]{-1, -1};
    	boolean found = false;
		for(int i = 0; i < images[0].getWidth(); i+= MAP_POINT_WIDTH){
			
			for(int j = 0; j < images[0].getHeight(); j+= MAP_POINT_HEIGHT){
				
				for(int p = 1; p < images.length; p++){
					if(images[0].getRGB(i, j) != images[p].getRGB(i, j)){
						res[0] = i/MAP_POINT_WIDTH;
						res[1] = j/MAP_POINT_HEIGHT;
						found = true;
						break;
					}
				}
				if(found){
					break;
				}
			}
			if(found){
				break;
			}
		}
    	return res;
    }
 
    
}
