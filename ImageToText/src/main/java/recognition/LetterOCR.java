package recognition;

import java.awt.image.BufferedImage;
import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class LetterOCR {
	private static Tesseract tesseract;
	private static String sep = File.separator;
	
	public LetterOCR(){
		initTesseract();
	}
	
	public static void initTesseract() {
		String jnaPath = getJnaPath();
    	System.setProperty("jna.library.path", jnaPath);
		
    	tesseract = Tesseract.getInstance();
    	tesseract.setDatapath(jnaPath+"tessdata");
    	tesseract.setLanguage("eng");
    	tesseract.setOcrEngineMode(8);
	}
	
	private static String getJnaPath(){
    	String homeDir = System.getProperty("user.dir");
    	String jnaPath = homeDir + sep + "src" + sep + "main" + sep + "resources" + sep + "tesseract" + sep;
        return jnaPath;
	}
	
	public static String recognize(BufferedImage image) {
		try {
			return tesseract.doOCR(image);
		} 
		catch (TesseractException e) {
			System.err.println("Could not recognize letter because of: " + e.getMessage());
		}
		return null;
	}
}