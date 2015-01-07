package document.structure;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import utils.ImageUtils;
import utils.Point;

public class LetterSet {
	private List<Point> on;
	private List<List<Point>> sets;

	private int width;
	private int height;
	private BufferedImage img;

	public LetterSet(int width, int height, BufferedImage image){
		this.width = width;
		this.height = height;
		this.img = ImageUtils.copyImage(image);

		ImageUtils.threshold(img, 0.8);
		this.on = new ArrayList<Point>();
		this.sets = createSets();
	}

	public void getOn(){
		int black = Color.BLACK.getRGB();

		for(int x = 0; x < img.getWidth(); x++){
			for(int y = 0; y < img.getHeight(); y++){
				if(img.getRGB(x, y) == black){
					on.add(new Point(x, y)); 
				}
			}
		}
	}

	public List<Point> centerImage(List<Point> image){
		List<Point> image1 = Lists.newArrayList(image);
		List<Point> offsetImage = Lists.newArrayList();

		Point firstPoint = image1.get(0);
		int bottom = firstPoint.Y();
		int top = firstPoint.Y();
		int right = firstPoint.X(); 
		int left = firstPoint.X();

		for(Point t : image1){

			int x = t.X();
			int y = t.Y();
			if(x < left)	{ 	left = x; 	}
			if(x > right)	{	right = x;	}
			if(y < top)		{	top = y;	}
			if(y > bottom)	{	bottom = y;	}
		}

		int imageWidth = Math.abs(right - left);
		int imageHeight = Math.abs(bottom - top);
		int xOffset = (this.width - imageWidth)/2;
		int yOffset = (this.height - imageHeight)/2;

		for(Point t : image1){
			int x = t.X();
			int y  = t.Y();
			int xAdd = x - left;
			int yAdd = y - top;
			int xSubtract = t.X() - xOffset;
			int ySubtract = t.Y() - yOffset;
			Point z = new Point(x - xSubtract + xAdd, y - ySubtract + yAdd);
			offsetImage.add(z);
		}

		return offsetImage;
	}

	public BufferedImage getImage(int index){
		BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		if(this.getPointSet(index) == null){
			System.err.println("Error: index out of bounds");
			return null;
		}

		for(Point t : this.getPointSet(index)){
			image.setRGB(t.X(), t.Y(), Color.BLACK.getRGB());
		}
		return image;
	}

	public List<BufferedImage> getImageSet(){
		List<BufferedImage> imageSet = new ArrayList<BufferedImage>();
		for(int i = 0; i < this.sets.size(); i++){
			imageSet.add(getImage(i));
		}
		return imageSet;
	}

	public List<Point> getPointSet(int index){
		return sets.get(index);
	}

	public List<List<Point>> createSets(){
		List<List<Point>> sets = Lists.newArrayList();
		getOn();
		while(!on.isEmpty()){
			List<Point> curImage = this.getAllNextTo(on.get(0));
			if(curImage.size() == 0) { break; }
			List<Point> set = this.centerImage(curImage);
			sets.add(set);
		}
		return sets;
	}

	private List<Point> getAllNextTo(Point current){
		List<Point> next = Lists.newArrayList();
		List<Point> possible = ImageUtils.getNeighborCoordinates(current, 1, true);
		for(Point other : possible){
			if(on.contains(other)){
				next.add(other);
				on.remove(other);
				next.addAll(this.getAllNextTo(other));
			}
		}
		return next;
	}
}
