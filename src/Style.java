import java.awt.Color;
import java.awt.Font;

/** <p>Style stands for Indent, Color, Font and Leading.</p>
 * @author Chris Takacs
 */

public class Style {
    private static final String FONTNAME = "Helvetica";
    private int indent;
    private Color color;
    private Font font;
    private int fontSize;
    private int leading;

    public Style(int indent, Color color, int points, int leading) {
        this.indent = indent;
        this.color = color;
        this.font = new Font(FONTNAME, Font.BOLD, fontSize=points);
        this.leading = leading;
    }

    public static String getFONTNAME() {
        return FONTNAME;
    }

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Font getFont(float scale) {
        return font.deriveFont(fontSize * scale);
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getLeading() {
        return leading;
    }

    public void setLeading(int leading) {
        this.leading = leading;
    }

    public String toString() {
        return "["+ this.indent + "," + this.color + "; " + this.fontSize + " on " + this.leading +"]";
    }
}
