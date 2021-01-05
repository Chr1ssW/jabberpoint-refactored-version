import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/** <p>A text item.</p>
 * <p>A text item has drawing capabilities.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class TextItem extends SlideItem {
	private final String text;
	
	private static final String EMPTYTEXT = "No Text Given";

	//A textitem of int level with text string
	public TextItem(int level, String string) {
		super(level);
		this.text = string;
	}

	//An empty textitem
	public TextItem() {
		this(0, EMPTYTEXT);
	}

	//Returns the text
	public String getText() {
		return this.text == null ? "" : this.text;
	}

	//Returns the AttributedString for the Item
	public AttributedString getAttributedString(Style style, float scale)
	{
		AttributedString attrStr = new AttributedString(this.getText());
		attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, this.text.length());
		return attrStr;
	}

	/**
	 * Returns the bounding box of an Item
	 */
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle)
	{
		int xsize = 0;
		int ysize = (int) (myStyle.getLeading() * scale);

		List<TextLayout> layouts = getLayouts(g, myStyle, scale);
		for (TextLayout layout : layouts)
		{
			Rectangle2D bounds = layout.getBounds();
			if (bounds.getWidth() > xsize) {
				xsize = (int) bounds.getWidth();
			}
			if (bounds.getHeight() > 0) {
				ysize += bounds.getHeight();
			}

			ysize += layout.getLeading() + layout.getDescent();
		}
		return new Rectangle((int)(myStyle.getIndent() * scale), 0, xsize, ysize );
	}

	/**
	 * Draws the textItem
	 */
	public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver o)
	{
		if (this.text != null && this.text.length() != 0)
		{
			//Creating a pointer with the required coordinates
			int indent = (int) (myStyle.getIndent() * scale);
			int leading = (int) (myStyle.getLeading() * scale);
			Point pen = new Point(x + indent, y + leading);

			//Creating 2D graphics object
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(myStyle.getColor());

			//Looping through the layouts and drawing them
			List<TextLayout> layouts = getLayouts(g, myStyle, scale);
			for (TextLayout layout : layouts) {
				pen.y += layout.getAscent();
				layout.draw(g2d, pen.x, pen.y);
				pen.y += layout.getDescent();
			}
		}
	}

	/**
	 * Returns the linebreaks of textItems
	 */
	private LineBreakMeasurer getLineBreaks(Graphics g, Style s, float scale)
	{
		AttributedString attrStr = getAttributedString(s, scale);
		Graphics2D g2d = (Graphics2D) g;
		FontRenderContext frc = g2d.getFontRenderContext();

		return new LineBreakMeasurer(attrStr.getIterator(), frc);
	}

	/**
	 * Returns the layouts of the textItems
	 */
	private List<TextLayout> getLayouts(Graphics g, Style s, float scale)
	{
		List<TextLayout> layouts = new ArrayList<>();

		LineBreakMeasurer measurer = getLineBreaks(g, s, scale);

    	float wrappingWidth = (Slide.WIDTH - s.getIndent()) * scale;
    	while (measurer.getPosition() < getText().length())
    	{
    		TextLayout layout = measurer.nextLayout(wrappingWidth);
    		layouts.add(layout);
    	}
    	return layouts;
	}

	public String toString() {
		return "TextItem[" + getLevel()+","+getText()+"]";
	}
}
