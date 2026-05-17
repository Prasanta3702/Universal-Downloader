package com.prasanta.ui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.prasanta.ui.MainView;

public class AboutDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public AboutDialog(MainView mainView) {
		super(mainView, "About", true);

		JPanel root = new JPanel();
		root.setLayout(new BoxLayout(root, BoxLayout.X_AXIS));
		root.setAlignmentY(Component.TOP_ALIGNMENT);

		// LEFT IMAGE
		JLabel imageLabel = new JLabel();
		ImageIcon icon = new ImageIcon(getClass().getResource("/assets/download-icon.png"));

		Image img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		imageLabel.setIcon(new ImageIcon(img));

		imageLabel.setAlignmentY(Component.TOP_ALIGNMENT);

		// RIGHT PANEL
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setAlignmentY(Component.TOP_ALIGNMENT);

		JLabel title = new JLabel("Universal Downloader");
		title.setFont(new Font("Arial", Font.BOLD, 16));
		title.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel version = new JLabel("v1.0.0, Built on May 2026");
		version.setFont(new Font(null, Font.PLAIN, 12));
		version.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel author = new JLabel("<html>Author: <font color='blue'>Prasanta Roy</font></html>");
		author.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel desc = new JLabel("<html>Fast and simple video downloader<br>Built for all type of downloader.</html>");
		desc.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JButton okButton = new JButton("OK");
		okButton.setBackground(new Color(33, 150, 243));
		okButton.setForeground(Color.white);
		okButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		okButton.addActionListener(e -> dispose());

		rightPanel.add(title);
		rightPanel.add(version);
		rightPanel.add(author);
		rightPanel.add(Box.createVerticalStrut(10));
		rightPanel.add(desc);
		rightPanel.add(Box.createVerticalStrut(20));
		rightPanel.add(okButton);

		root.add(Box.createHorizontalStrut(15));
		root.add(imageLabel);
		root.add(Box.createHorizontalStrut(15));
		root.add(rightPanel);

		add(root);

		setSize(350, 200);
		setLocationRelativeTo(mainView);
	}
}