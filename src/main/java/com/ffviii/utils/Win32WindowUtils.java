package com.ffviii.utils;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Native;

public class Win32WindowUtils {
	
	public static int[] GetWindowCoordinates(final String winTitle){
		final List<WindowInfo> windows = new ArrayList<WindowInfo>();
		final int[] pos = new int[2];
	    final List<Integer> order = new ArrayList<Integer>();
	    int top = User32.instance.GetTopWindow(0);
	    
	    while (top!=0) {
	        order.add(top);
	        top = User32.instance.GetWindow(top, User32.GW_HWNDNEXT);
	    }
	    User32.instance.EnumWindows(new WndEnumProc()
	    {
	        public boolean callback(int hWnd, int lParam)
	        {
	        if (User32.instance.IsWindowVisible(hWnd)) {
	            RECT r = new RECT();
	            User32.instance.GetWindowRect(hWnd, r);
	            if (r.left>-32000) {     // minimized
	                byte[] buffer = new byte[1024];
	                User32.instance.GetWindowTextA(hWnd, buffer, buffer.length);
	                String title = Native.toString(buffer);
	                if(title.equals(winTitle)){
	                	windows.add(new WindowInfo(hWnd, r, title));
	                	pos[0] = r.left;
	                	pos[1] = r.top;
	                }
	            }
	        }
	        return true;
	    }
	    }, 0);

	    return pos;
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
