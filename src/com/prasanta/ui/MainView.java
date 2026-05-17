package com.prasanta.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.prasanta.ui.components.AppMenuBar;

public class MainView extends JFrame {
	private static final long serialVersionUID = 1L;
	private final Color PRIMARY_COLOR = new Color(33, 150, 243);

	private final String TITLE = "Universal Downloader";

	private final JPanel mainPanel = new JPanel();
	private final JPanel qualityScrollPanel = new JPanel();

	private final JProgressBar loadingProgress = new JProgressBar();
	private final JProgressBar progressBar = new JProgressBar();

	private final JTextField urlField = new JTextField();
	private final JTextField outputField = new JTextField();

	private final JButton selectOutputFolderBtn = new JButton("...");
	private final JButton sendButton = new JButton("SEND");
	private final JButton downloadButton = new JButton("Download");
	private final JButton cancelButton = new JButton("Cancel");

	private AppMenuBar appMenuBar = new AppMenuBar(this);

	public MainView() {
		this.setTitle(TITLE);
		this.setSize(new Dimension(500, 360));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(appMenuBar);
		this.setResizable(false);
		this.setIconImage(
		        new ImageIcon(
		                getClass().getResource("/assets/download-icon.png")
		        ).getImage()
		);

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		init();
	}

	private void init() {

		JPanel urlPanel = createUrlPanel();
		JPanel loadingPanel = createLoadingPanel();
		JPanel qualityPanel = createQualityPanel();
		JPanel outputPanel = createOutputPanel();
		JPanel actionPanel = createActionPanel();

		mainPanel.add(urlPanel);
		mainPanel.add(loadingPanel);
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(qualityPanel);
		mainPanel.add(outputPanel);
		mainPanel.add(actionPanel);

		this.add(mainPanel);
	}

	private JPanel createUrlPanel() {
		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		panel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		JLabel urlLabel = new JLabel("URL");
		urlLabel.setHorizontalAlignment(SwingConstants.CENTER);
		urlLabel.setMaximumSize(new Dimension(50, 30));
		urlLabel.setPreferredSize(new Dimension(50, 30));
		urlLabel.setOpaque(false);
		urlLabel.setBackground(Color.gray);

		urlField.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));

		sendButton.setBorder(BorderFactory.createEmptyBorder());
		sendButton.setMaximumSize(new Dimension(50, 30));
		sendButton.setPreferredSize(new Dimension(50, 30));
		sendButton.setBackground(PRIMARY_COLOR);
		sendButton.setForeground(Color.white);

		panel.add(urlLabel);
		panel.add(urlField);
		panel.add(sendButton);

		return panel;
	}

	private JPanel createLoadingPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 5));
		panel.setPreferredSize(new Dimension(0, 5));

		loadingProgress.setForeground(PRIMARY_COLOR);
		loadingProgress.setBorder(BorderFactory.createEmptyBorder());

		panel.add(loadingProgress);

		return panel;
	}

	private JPanel createQualityPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
		panel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 150));
		panel.setBorder(BorderFactory.createTitledBorder("All Formats"));

		qualityScrollPanel.setLayout(new BoxLayout(qualityScrollPanel, BoxLayout.Y_AXIS));
		qualityScrollPanel.setBackground(Color.white);
		JScrollPane jsp = new JScrollPane(qualityScrollPanel);
		jsp.setBorder(BorderFactory.createEmptyBorder());
		jsp.getVerticalScrollBar().setUnitIncrement(20);
		panel.add(jsp);

		return panel;
	}

	private JPanel createOutputPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(BorderFactory.createTitledBorder("Output"));

		outputField.setMaximumSize(new Dimension(1000, 25));
		outputField.setPreferredSize(new Dimension(1000, 25));
		outputField.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));

		selectOutputFolderBtn.setMaximumSize(new Dimension(60, 25));
		selectOutputFolderBtn.setPreferredSize(new Dimension(60, 25));
		selectOutputFolderBtn.setBackground(PRIMARY_COLOR);
		selectOutputFolderBtn.setForeground(Color.white);
		selectOutputFolderBtn.setBorder(BorderFactory.createEmptyBorder());

		panel.add(outputField);
		panel.add(selectOutputFolderBtn);

		return panel;
	}

	private JPanel createActionPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		panel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
		panel.setBorder(BorderFactory.createTitledBorder("Action"));

		downloadButton.setBackground(PRIMARY_COLOR);
		downloadButton.setForeground(Color.white);

		cancelButton.setBackground(Color.lightGray);

		progressBar.setValue(0);
		progressBar.setForeground(PRIMARY_COLOR);
		progressBar.setStringPainted(true);
		progressBar.setString("Status");
		progressBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		progressBar.setBorder(BorderFactory.createEmptyBorder());
		progressBar.setBackground(Color.lightGray);

		panel.add(downloadButton);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(cancelButton);
		panel.add(Box.createHorizontalStrut(10));
		panel.add(progressBar);

		return panel;
	}

	public void createAndShow() {
		this.setVisible(true);
	}

	/*
	 * Getter
	 */
	public JProgressBar getLoadingProgress() {
		return loadingProgress;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public JTextField getUrlField() {
		return urlField;
	}

	public JPanel getQualityScrollPanel() {
		return qualityScrollPanel;
	}

	public JTextField getOutputField() {
		return outputField;
	}

	public JButton getSelectOutputFolderBtn() {
		return selectOutputFolderBtn;
	}

	public JButton getSendButton() {
		return sendButton;
	}

	public JButton getDownloadButton() {
		return downloadButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public Color getPrimaryColor() {
		return PRIMARY_COLOR;
	}

}
