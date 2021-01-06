import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Vector;

/** <p>A slide. This class has drawing functionality.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Slide {
	public final static int WIDTH = 1200;
	public final static int HEIGHT = 800;
	protected String title; //The title is kept separately
	protected Vector<SlideItem> items; //The SlideItems are kept in a vector

	public Slide()
	{
		this.items = new Vector<>();
	}

	public void append(SlideItem anItem) {
		this.items.addElement(anItem);
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String newTitle) {
		this.title = newTitle;
	}

	public SlideItem getSlideItem(int number) {
		return this.items.elementAt(number);
	}

	/**
	 * Create a TextItem out of a String and add the TextItem
	 */
	public void append(int level, String message) {
		append(new TextItem(level, message));
	}

	/**
	 * @return all the SlideItems in a vector
	 */
	public Vector<SlideItem> getSlideItems() {
		return this.items;
	}

	/**
	 * @return the size of a slide
	 */
	public int getSize() {
		return this.items.size();
	}

	/**
	 * Draws one single element to the screen. Used in draw() method
	 * @return The bottom Y-axis position of the drawn element
	 */
	private int drawElements(SlideItem slideItem, int y, Graphics g, Rectangle area, ImageObserver view)
	{
		Style style = Styles.getStyle(slideItem.getLevel());
		slideItem.draw(area.x, y, getScale(area), g, style, view);

		return slideItem.getBoundingBox(g, view, getScale(area), style).height;
	}

	/**
	 * Draws the contents of the slide on the screen
	 */
	public void draw(Graphics g, Rectangle area, ImageObserver view)
	{
		int y = area.y;

		//Drawing the title
		y += drawElements(new TextItem(0, getTitle()), y, g, area, view);

		//Drawing all other elements
		for (int number = 0; number < this.getSize(); number++)
		{
			SlideItem currentItem = this.getSlideItems().elementAt(number);
			y += drawElements(currentItem, y, g, area, view);
		}
	}

	/**
	 * @param area Rectangle object
	 * @return the scale to draw a slide
	 */
	private float getScale(Rectangle area) {
		return Math.min(((float)area.width) / ((float)WIDTH), ((float)area.height) / ((float)HEIGHT));
	}
}
