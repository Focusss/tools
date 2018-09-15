package com.framework.utils.convert;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InputStreamWathThread extends Thread{
	private Process process = null;
	private boolean over = false;
	public InputStreamWathThread(Process p) {
		process = p;
		over = false;
	}

	public void run() {
		try {
			if (process == null) {
				return;
			}

			//对输入流，可能是一个回车之类的输入
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while (true) {
				if (process == null || over) {
					break;
				}
				String temp;
				while ((temp = br.readLine()) != null) {
//					logger.info("输入流信息:" + temp);//如这些信息:NOTICE  processing PDF page 10 (595x842:0:0) (move:0:0)等等的打印时提示信息
					;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setOver(boolean over) {
		this.over = over;
	}
}
