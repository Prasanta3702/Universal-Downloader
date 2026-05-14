package com.prasanta.ui.components;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class AppMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	JMenu fileMenu = new JMenu("File");
	JMenu editMenu = new JMenu("Edit");
	JMenu optionMenu = new JMenu("Options");
	JMenu helpMenu = new JMenu("Help");

	public AppMenuBar() {
		this.add(fileMenu);
		this.add(editMenu);
		this.add(optionMenu);
		this.add(helpMenu);
	}

}
