package com.ffviii.utils;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.sun.jna.platform.win32.WinDef.HWND;

public class Win32WindowUtils {

	private static final int WIN_TITLE_MAX_SIZE = 512;
	private static final int NAME_PART = 0;

	public static HWND GetWindowHandle(String strSearch, String strClass) {
		char[] lpString = new char[WIN_TITLE_MAX_SIZE];
		String strTitle;
		int iFind = -1;
		HWND hWnd = User32.INSTANCE.FindWindow(strClass, null);
		while(hWnd != null) {
			User32.INSTANCE.GetWindowText(hWnd, lpString, WIN_TITLE_MAX_SIZE);
			strTitle = new String(lpString);
			strTitle = strTitle.toUpperCase();
			iFind = strTitle.indexOf(strSearch);
			if(iFind != -1) {
				return hWnd;
			}
			hWnd = User32.INSTANCE.FindWindowEx(null, hWnd, strClass, null);
		}
		return hWnd;
	}
	
	
	public static List<String> GetActiveProcessName(){
		List<String> result = new ArrayList<String>();
		int n = 2;
		try {
			String line;
			String parts[];
			Process p = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				if(n < 0){
					parts = line.split(" ");
					//System.out.println(line);
					result.add(parts[NAME_PART]);
				}else{
					n--;
				}
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}

		return result;
	}
	
	
	public static BufferedImage TakePrintScreen(int x, int y, int width, int height){
		Robot r = null;
		BufferedImage image = null;
		Rectangle area = new Rectangle(x, y, width, height);
		
		try {
			r = new Robot();
			image = r.createScreenCapture(area);			
		} catch (AWTException e) {
			System.out.println("Error while taking printScreen: " + e.getMessage());
		}
		return image;
	}


}
