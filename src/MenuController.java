import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/** <p>The controller for the menu</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {
	
	private final Frame parent; //The frame, only used as parent for the Dialogs
	private final Presentation presentation; //Commands are given to the presentation
	
	private static final long serialVersionUID = 227L;
	
	protected static final String ABOUT = "About";
	protected static final String FILE = "File";
	protected static final String EXIT = "Exit";
	protected static final String GOTO = "Go to";
	protected static final String HELP = "Help";
	protected static final String NEW = "New";
	protected static final String NEXT = "Next";
	protected static final String OPEN = "Open";
	protected static final String PAGENR = "Page number?";
	protected static final String PREV = "Prev";
	protected static final String SAVE = "Save";
	protected static final String VIEW = "View";
	
	protected static final String TESTFILE = "testPresentation.xml";
	protected static final String SAVEFILE = "savedPresentation.xml";
	
	protected static final String IOEX = "IO Exception: ";
	protected static final String SAVEERR = "Save Error";

	private MenuItem menuItem;
	private final XMLAccessor xmlAccessor = new XMLAccessor();

	public MenuController(Frame frame, Presentation pres) {
		this.parent = frame;
		this.presentation = pres;

		this.addFileMenu();
		this.addViewMenu();
		this.addHelpMenu();
	}

	//Creating a menu-item
	public MenuItem mkMenuItem(String name) {
		return new MenuItem(name, new MenuShortcut(name.charAt(0)));
	}

	/**
	 * Shows the about box of the application with a message dialog.
	 */
	private void showAboutBox()
	{
		JOptionPane.showMessageDialog(parent,
				"JabberPoint is a primitive slide-show program in Java(tm). It\n" +
						"is freely copyable as long as you keep this notice and\n" +
						"the splash screen intact.\n" +
						"Copyright (c) 1995-1997 by Ian F. Darwin, ian@darwinsys.com.\n" +
						"Adapted by Gert Florijn (version 1.1) and " +
						"Sylvia Stuurman (version 1.2 and higher) for the Open" +
						"University of the Netherlands, 2002 -- now.\n" +
						"Author's version available from http://www.darwinsys.com/",
				"About JabberPoint",
				JOptionPane.INFORMATION_MESSAGE
		);
	}

	/**
	 * Adds the filemenu and its element(s).
	 */
	private void addFileMenu()
	{
		Menu fileMenu = new Menu(FILE);

		this.addOpen(fileMenu);
		this.addNew(fileMenu);
		this.addSave(fileMenu);
		fileMenu.addSeparator();
		this.addExit(fileMenu);

		add(fileMenu);
	}

	private void addOpen(Menu menu)
	{
		menu.add(this.menuItem = mkMenuItem(OPEN));
		this.menuItem.addActionListener(actionEvent -> {
			presentation.clear();

			this.xmlAccessor.loadFile(presentation, TESTFILE);
			presentation.setSlideNumber(0);

			parent.repaint();
		});
	}

	private void addNew(Menu menu)
	{
		menu.add(this.menuItem = mkMenuItem(NEW));
		this.menuItem.addActionListener(actionEvent -> {
			this.presentation.clear();
			this.parent.repaint();
		});
	}

	private void addSave(Menu menu)
	{
		menu.add(this.menuItem = mkMenuItem(SAVE));
		this.menuItem.addActionListener(e -> {
			try {
				this.xmlAccessor.saveFile(presentation, SAVEFILE);
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(parent, IOEX + exc,
						SAVEERR, JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private void addExit(Menu menu)
	{
		menu.add(this.menuItem = mkMenuItem(EXIT));
		this.menuItem.addActionListener(actionEvent -> System.exit(0));
	}

	/**
	 * Adds the viewmenu and its element(s).
	 */
	private void addViewMenu()
	{
		Menu viewMenu = new Menu(VIEW);

		this.addNext(viewMenu);
		this.addPrev(viewMenu);
		this.addGoto(viewMenu);

		add(viewMenu);
	}

	private void addNext(Menu menu)
	{
		menu.add(this.menuItem = mkMenuItem(NEXT));
		this.menuItem.addActionListener(actionEvent -> presentation.nextSlide());
	}

	private void addPrev(Menu menu)
	{
		menu.add(this.menuItem = mkMenuItem(PREV));
		this.menuItem.addActionListener(actionEvent -> presentation.prevSlide());
	}

	private void addGoto(Menu menu)
	{
		menu.add(this.menuItem = mkMenuItem(GOTO));
		this.menuItem.addActionListener(actionEvent -> {
			String pageNumberStr = JOptionPane.showInputDialog(PAGENR);
			int pageNumber = Integer.parseInt(pageNumberStr);

			if (pageNumber > presentation.getSize())
			{
				presentation.setSlideNumber(presentation.getSlideNumber());
			}
			else if (pageNumber > 0)
			{
				presentation.setSlideNumber(pageNumber - 1);
			}
		});
	}

	/**
	 * Adds the helpmenu and its element(s).
	 */
	private void addHelpMenu()
	{
		Menu helpMenu = new Menu(HELP);
		this.addAbout(helpMenu);
		setHelpMenu(helpMenu);		//Needed for portability (Motif, etc.).
	}

	private void addAbout(Menu menu)
	{
		menu.add(this.menuItem = mkMenuItem(ABOUT));
		this.menuItem.addActionListener(actionEvent -> showAboutBox());
	}
}
