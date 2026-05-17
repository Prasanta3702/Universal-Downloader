package com.prasanta.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.prasanta.ui.MainView;

public class AppMenuBar extends JMenuBar implements ActionListener {
	private static final long serialVersionUID = 1L;

	JMenu fileMenu = new JMenu("File");
	JMenu editMenu = new JMenu("Edit");
	JMenu helpMenu = new JMenu("Help");
	
	JMenuItem aboutMenuItem = new JMenuItem("About");
	
	private MainView mainView;

	public AppMenuBar(MainView mainView) {
		this.mainView = mainView;
		
		aboutMenuItem.addActionListener(this);
		
		helpMenu.add(aboutMenuItem);
		
		this.add(fileMenu);
		this.add(editMenu);
		this.add(helpMenu);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == aboutMenuItem) {
			AboutDialog aboutDialog = new AboutDialog(mainView);
			aboutDialog.setVisible(true);
		}
	}

}
