package com.prasanta.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import com.prasanta.model.Quality;

public class RightPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int PANEL_HEIGHT = 30;
	private static final int QUALITY_PANEL_HEIGHT = 220;
	private static final int CHECKBOX_COLUMN_WIDTH = 80;

	private static final Color PRIMARY_COLOR = new Color(33, 150, 243);

	/*
	 * Temporary dummy data
	 */
	private final List<Quality> videoQualities = List.of(new Quality("123", "mp4", "1920x1080", "100MB", "Video 1"),
			new Quality("124", "mp4", "1280x720", "70MB", "Video 2"),
			new Quality("125", "mkv", "854x480", "40MB", "Video 3"));

	private final List<Quality> audioQualities = List.of(new Quality("201", "mp3", "128kbps", "5MB", "Audio 1"),
			new Quality("202", "aac", "256kbps", "10MB", "Audio 2"),
			new Quality("203", "wav", "320kbps", "20MB", "Audio 3"));

	private final JTextField urlField = new JTextField();

	private final JButton sendButton = createButton("SEND");
	private final JButton downloadButton = createButton("Download");
	private final JButton pauseButton = createButton("Pause");
	private final JButton cancelButton = createButton("Cancel");

	private final Object[] columns = { "Select", "Quality", "Size", "Format" };

	private final DefaultTableModel videoTableModel = createTableModel();

	private final DefaultTableModel audioTableModel = createTableModel();

	private final JTable videoTable = createTable(videoTableModel);

	private final JTable audioTable = createTable(audioTableModel);

	public RightPanel() {

		initializeUI();
		registerListeners();
		loadQualities();
	}

	private void initializeUI() {

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		add(createUrlPanel());
		add(Box.createVerticalStrut(10));

		add(createQualityPanel());
		add(Box.createVerticalStrut(10));

		add(createButtonPanel());
		add(Box.createVerticalStrut(10));

		add(createDownloadPanel());
	}

	private JPanel createUrlPanel() {

		JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_HEIGHT));

		panel.setPreferredSize(new Dimension(0, PANEL_HEIGHT));

		panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		JLabel urlLabel = new JLabel("URL");

		urlLabel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

		panel.add(urlLabel);
		panel.add(urlField);
		panel.add(sendButton);

		return panel;
	}

	private JPanel createQualityPanel() {

		JPanel panel = new JPanel(new BorderLayout());

		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, QUALITY_PANEL_HEIGHT));

		panel.setPreferredSize(new Dimension(0, QUALITY_PANEL_HEIGHT));

		JTabbedPane tabbedPane = new JTabbedPane();

		tabbedPane.addTab("Video", new JScrollPane(videoTable));

		tabbedPane.addTab("Audio", new JScrollPane(audioTable));

		panel.add(tabbedPane);

		return panel;
	}

	private JPanel createButtonPanel() {

		JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_HEIGHT));

		downloadButton.addActionListener(e -> {

			Quality quality = getSelectedQuality();

			if (quality != null) {

				System.out.println(quality.getExtension());
				System.out.println(quality.getResolution());
			}
		});
		panel.add(downloadButton);
		panel.add(Box.createHorizontalStrut(5));

		panel.add(pauseButton);
		panel.add(Box.createHorizontalStrut(5));

		panel.add(cancelButton);

		return panel;
	}

	private JPanel createDownloadPanel() {

		JPanel panel = new JPanel(new BorderLayout());

		panel.setBorder(BorderFactory.createTitledBorder("Downloads"));

		return panel;
	}

	private JButton createButton(String text) {

		JButton button = new JButton(text);

		button.setBackground(PRIMARY_COLOR);
		button.setForeground(Color.WHITE);

		return button;
	}

	private JTable createTable(DefaultTableModel model) {

		JTable table = new JTable(model);

		table.setRowHeight(28);

		table.getColumnModel().getColumn(0).setMaxWidth(CHECKBOX_COLUMN_WIDTH);

		return table;
	}

	private DefaultTableModel createTableModel() {

		return new DefaultTableModel(columns, 0) {

			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {

				if (columnIndex == 0) {
					return Boolean.class;
				}

				return String.class;
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 0;
			}
		};
	}

	public Quality getSelectedQuality() {

		for (int i = 0; i < videoTableModel.getRowCount(); i++) {

			Boolean selected = (Boolean) videoTableModel.getValueAt(i, 0);

			if (Boolean.TRUE.equals(selected)) {
				return videoQualities.get(i);
			}
		}

		for (int i = 0; i < audioTableModel.getRowCount(); i++) {

			Boolean selected = (Boolean) audioTableModel.getValueAt(i, 0);

			if (Boolean.TRUE.equals(selected)) {
				return audioQualities.get(i);
			}
		}

		return null;
	}

	private void registerListeners() {

		registerSingleSelection(videoTableModel);
		registerSingleSelection(audioTableModel);
	}

	private void registerSingleSelection(DefaultTableModel model) {

		model.addTableModelListener(event -> {

			if (event.getType() != TableModelEvent.UPDATE) {
				return;
			}

			int row = event.getFirstRow();
			int column = event.getColumn();

			if (column != 0) {
				return;
			}

			Boolean checked = (Boolean) model.getValueAt(row, column);

			if (Boolean.TRUE.equals(checked)) {

				for (int i = 0; i < model.getRowCount(); i++) {

					if (i != row) {
						model.setValueAt(false, i, 0);
					}
				}
			}
		});
	}

	private void loadQualities() {

		loadVideoQualities();
		loadAudioQualities();
	}

	private void loadVideoQualities() {

		videoTableModel.setRowCount(0);

		for (Quality quality : videoQualities) {

			videoTableModel.addRow(
					new Object[] { false, quality.getResolution(), quality.getFileSize(), quality.getExtension() });
		}
	}

	private void loadAudioQualities() {

		audioTableModel.setRowCount(0);

		for (Quality quality : audioQualities) {

			audioTableModel.addRow(
					new Object[] { false, quality.getResolution(), quality.getFileSize(), quality.getExtension() });
		}
	}
}