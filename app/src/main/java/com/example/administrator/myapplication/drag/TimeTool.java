package com.example.administrator.myapplication.drag;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public class TimeTool {

	public static boolean isInTrade() {
		SimpleDateFormat format = new SimpleDateFormat("HH-mm");
		String currentTime = format
				.format(new Date(System.currentTimeMillis()));
		

		int startH = 9;
		int startM = 30;
		int endH = 15;
		int endM = 30;

		String[] hm = currentTime.split("-");

		int curH = Integer.valueOf(hm[0]);
		int curM = Integer.valueOf(hm[1]);

		if (curH < startH) {
			return false;
		} else if (curH == startH) {
			if (curM < startM) {
				return false;
			}
		}

		if (curH > endH) {
			return false;
		} else if (curH == endH) {
			if (curM > endM) {
				return false;
			}
		}

		return true;

	}
}
