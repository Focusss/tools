package com.framework.utils.convert;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ErrorInputStreamWathThread extends Thread{
	private Process process = null;
	private boolean over = false;
	public ErrorInputStreamWathThread(Process p) {
		process = p;
		over = false;
	}

	public void run() {
		try {
			if (process == null) {
				return;
			}


			//对出错流的处理
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			
			while (true) {
				if (process == null || over) {
					break;
				}
				String temp;
				while ((temp = br.readLine()) != null) {
//					logger.info("出错流信息:" + temp);
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
