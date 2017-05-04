package com.guojianyiliao.eryitianshi.MyUtils.utlis;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {
	/** 关闭流 */
	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				MyLogcat.jLog().e("关闭流:"+e);
			}
		}
		return true;
	}
}
