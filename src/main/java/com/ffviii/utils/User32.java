package com.ffviii.utils;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

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
	
	
    final User32 instance = (User32) Native.loadLibrary ("user32", User32.class);
    
    boolean EnumWindows (WndEnumProc wndenumproc, int lParam);
    
    boolean IsWindowVisible(int hWnd);
    
    int GetWindowRect(int hWnd, RECT r);
    
    void GetWindowTextA(int hWnd, byte[] buffer, int buflen);
    
    int GetTopWindow(int hWnd);
    
    int GetWindow(int hWnd, int flag);
    
    final int GW_HWNDNEXT = 2;
}
