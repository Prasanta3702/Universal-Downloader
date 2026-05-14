package com.prasanta.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.prasanta.ui.components.AppMenuBar;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private final String TITLE = "Universal Downloader";
	
	private JSplitPane splitPane = new JSplitPane();
	private JPanel leftPanel = new JPanel();
	private RightPanel rightPanel = new RightPanel();
	
	private AppMenuBar appMenuBar = new AppMenuBar();
	
	public MainFrame() {
		this.setTitle(TITLE);
		this.setSize(800, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setJMenuBar(appMenuBar);
		
		init();
	}
		
	private void init() {
		leftPanel.setMinimumSize(new Dimension(200, 0));
		leftPanel.setPreferredSize(new Dimension(200, 0));

		splitPane.setLeftComponent(leftPanel);
		splitPane.setRightComponent(rightPanel);
		this.add(splitPane);
	}
	
	public void createAndShow() {
		this.setVisible(true);
	}

}
