package com.ffviii.utils;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface User32 extends StdCallLibrary {
	
	static int SW_FORCEMINIMIZE = 11;
	static int SW_HIDE = 0;
	static int SW_MAXIMIZE = 3;
	static int SW_SHOW = 5;
	static int SW_MINIMIZE = 6;
	static int SW_RESTORE = 9;
	static int SW_SHOWDEFAULT = 10;
	static int SW_SHOWMAXIMIZED = 3;
	static int SW_SHOWMINIMIZED = 2;
	static int SW_SHOWMINNOACTIVE = 7;
	static int SW_SHOWNA = 8;
	static int SW_SHOWNOACTIVE = 4;
	static int SW_SHOWNORMAL = 1;
	
	
	User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);
	
	HWND GetParent(HWND hWnd);
	
	HWND FindWindow(String lpClassName, String lpWindowName);
	
	HWND SetFocus(HWND hWnd);
	
	boolean ShowWindow(HWND hWnd, int nCmdShow);
	
	HWND FindWindowEx(HWND hwndParent, HWND hwndChildAfter, String lpszClass, String lpszWindow);
	
	int GetWindowText(HWND hWnd, char[] lpString, int nMaxCount);
}
