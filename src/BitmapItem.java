import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.imageio.ImageIO;

import java.io.IOException;


/** <p>The class for a Bitmap item</p>
 * <p>Bitmap items are responsible for drawing themselves.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public class BitmapItem extends SlideItem {
  private BufferedImage bufferedImage;
  private final String imageName;
  
  protected static final String FILE = "File ";
  protected static final String NOTFOUND = " not found";


  	//level indicates the item-level; name indicates the name of the file with the image
	public BitmapItem(int level, String name) {
		super(level);
		imageName = name;
		try {
			bufferedImage = ImageIO.read(new File(imageName));
		}
		catch (IOException e) {
			System.err.println(FILE + imageName + NOTFOUND) ;
		}
	}

	//An empty bitmap item
	public BitmapItem() {
		this(0, null);
	}

	//Returns the filename of the image
	public String getName() {
		return imageName;
	}

	/**
	 * Returns the bounding box of the image
	 */
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle)
	{
		int indent = (int) (myStyle.getIndent() * scale);
		int width = (int) (this.bufferedImage.getWidth(observer) * scale);
		int height = (int) (myStyle.getLeading() * scale) + (int) (this.bufferedImage.getHeight(observer) * scale);

		return new Rectangle(indent, 0, width, height);
	}

	/**
	 * Draws the image
	 */
	public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer)
	{
		int width = x + (int) (myStyle.getIndent() * scale);
		int height = y + (int) (myStyle.getLeading() * scale);
		int imageWidth = (int) (this.bufferedImage.getWidth(observer)*scale);
		int imageHeight = (int) (bufferedImage.getHeight(observer)*scale);

		g.drawImage(this.bufferedImage, width, height, imageWidth, imageHeight, observer);
	}

	public String toString() {
		return "BitmapItem[" + getLevel() + "," + imageName + "]";
	}
}
