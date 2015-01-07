package document.structure;

import java.awt.image.BufferedImage;
import java.util.List;

import recognition.LetterOCR;

public class Word {
	private LetterSet letterSet;
	BufferedImage wordImage;
	
	public Word(BufferedImage wordImage) {
		this.letterSet = new LetterSet(40, 40, wordImage);
		this.wordImage = wordImage;
	}
	
	public String getWordAsString(){
		String converted = "";
//		List<BufferedImage> letterImgs = letterSet.getImageSet();
		
//		for(BufferedImage letterImg : letterImgs) {
			converted += LetterOCR.recognize(wordImage);
//		}
		return converted;
	}
	
	public List<BufferedImage> getLetterImages() {
		return letterSet.getImageSet();
	}
}
