package free.lib.congard.objloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CgrTools {
	
	public static float[] convertArrayListToArray(ArrayList<Float> arr) {
		float[] result = new float[arr.size()];
		for (int i = 0; i<arr.size(); i++) result[i] = arr.get(i);
		return result;
	}
	
	// [RU] Читает файл
	// [EN] Read the file
	public static byte[] rfile(File path) {
		byte[] result = null;
		try {
			FileInputStream fis = new FileInputStream(path);
			result = new byte[fis.available()];
			fis.read(result);
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	// [RU] Используется для замерения времени работы
	// [EN] Used to measure the running time
	public static class CgrTimeMeter {
		private long startTime;
		private String functName;
		
		public CgrTimeMeter(String functName) {
			this.functName = functName;
		}
		
		public void start() {
			startTime = System.currentTimeMillis();
			System.out.println(">> " + functName);
		}
		
		public void end() {
			System.out.println("Done in " + (System.currentTimeMillis() - startTime) + "ms");
			System.out.println("<< " + functName);
		}
	}
}
