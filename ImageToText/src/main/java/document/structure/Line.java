package document.structure;

import java.awt.image.BufferedImage;
import java.util.List;

import com.google.common.collect.Lists;

import document.analysis.LineComponentAnalyzer;

public class Line {
	private List<Word> words;
	private LineComponentAnalyzer analyzer;
	
	public Line(BufferedImage lineImage){
		words = Lists.newArrayList();
		analyzer = new LineComponentAnalyzer();
		
		analyzer.setLineImage(lineImage);
		initWords();
	}
	
	private void initWords(){
		List<BufferedImage> wordImages = analyzer.getWordSubImages();
		for(BufferedImage wordImage : wordImages) {
			words.add(new Word(wordImage));
		}
	}
	
	private List<String> getWords(){
		List<String> totalWords = Lists.newArrayList();
		for(Word wrd : words) {
			totalWords.add(wrd.getWordAsString() + " ");
		}
		return totalWords;
	}
	
	public String getConvertedLine() {
		List<String> words = getWords();
		String totalString = "";
		for(String word: words) {
			totalString += word + " ";
		}
		return totalString;
	}
}
