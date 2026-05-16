package com.prasanta.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import com.prasanta.controller.DownloadController;
import com.prasanta.model.DownloadRequest;
import com.prasanta.model.Quality;

public class MainController {
	private ButtonGroup qualityGroup = new ButtonGroup();
	private List<Quality> qualities = new ArrayList<>();
	private DownloadController controller = new DownloadController();
	private Quality selectedQuality;
	private Quality audioQuality;

	private MainView mainView;

	public MainController(MainView mainView) {
		this.mainView = mainView;

		qualities.add(new Quality("123", "mp4", "1920x1080", "720P", "100MB", "Video 1"));
		qualities.add(new Quality("124", "mp4", "1280x720", "1080P", "70MB", "Video 2"));
		qualities.add(new Quality("125", "mp4", "1280x720", "1080P", "70MB", "Video 2"));
		qualities.add(new Quality("126", "mp4", "1280x720", "1080P", "70MB", "Video 2"));
		qualities.add(new Quality("127", "mp4", "1280x720", "1080P", "70MB", "Video 2"));
		qualities.add(new Quality("128", "mp4", "1280x720", "1080P", "70MB", "Video 2"));
		qualities.add(new Quality("129", "mp4", "1280x720", "1080P", "70MB", "Video 2"));
		qualities.add(new Quality("130", "mp4", "1280x720", "1080P", "70MB", "Video 2"));
		qualities.add(new Quality("131", "mp4", "1280x720", "1080P", "70MB", "Video 2"));

		mainView.getOutputField().setText(getDownloadPath());

		mainView.getSendButton().addActionListener(e -> onSend());
		mainView.getUrlField().addActionListener(e -> onSend());
		mainView.getSelectOutputFolderBtn().addActionListener(e -> openFolderDialog());
		mainView.getDownloadButton().addActionListener(e -> onDownload());
		mainView.getCancelButton().addActionListener(e -> onCencel());
	}

	private void onSend() {
		if (!mainView.getUrlField().getText().isBlank()) {
			mainView.getLoadingProgress().setIndeterminate(true);
			mainView.getLoadingProgress().revalidate();

			DownloadRequest request = new DownloadRequest();
			request.setUrl(mainView.getUrlField().getText());

			try {
				qualities.clear();
				List<Quality> fetchQualities = controller.fetchQuality(request);
				qualities.addAll(fetchQualities);
				audioQuality = qualities.stream().filter(q -> q.getResolution().toLowerCase().contains("audio")
						&& q.getExtension().equalsIgnoreCase("m4a")).findFirst().orElse(null);

				loadQualities(mainView.getQualityScrollPanel());

			} catch (Exception ignored) {
			} finally {
				mainView.getLoadingProgress().setIndeterminate(false);
				mainView.getLoadingProgress().revalidate();
			}
		}
	}

	private void onDownload() {
		if (!mainView.getUrlField().getText().isBlank() && selectedQuality != null
				&& !mainView.getOutputField().getText().isBlank()) {

			mainView.getProgressBar().setForeground(mainView.getPrimaryColor());
			mainView.getProgressBar().setString("Downloading...");

			DownloadRequest downloadRequest = new DownloadRequest();
			downloadRequest.setUrl(mainView.getUrlField().getText());
			downloadRequest.setOutputPath(mainView.getOutputField().getText());

			if (selectedQuality != null) {
				if (selectedQuality.getResolution().toLowerCase().contains("audio")) {
					downloadRequest.setAudioOnly(true);
					downloadRequest.setQuality(selectedQuality.getFormatId());
				} else {
					downloadRequest.setAudioOnly(false);
					downloadRequest.setQuality(selectedQuality.getFormatId() + "+" + audioQuality.getFormatId());
				}

				controller.startDownload(downloadRequest, mainView.getProgressBar());
			}
		}
	}

	private void onCencel() {
		controller.shutdown();
		mainView.getProgressBar().setValue(0);
		mainView.getProgressBar().setForeground(Color.RED);
		mainView.getProgressBar().setString("Cancelled");
		mainView.getProgressBar().revalidate();
	}

	private void openFolderDialog() {
		JFileChooser folderChooser = new JFileChooser();

		folderChooser.setDialogTitle("Select Folder");
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int result = folderChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFolder = folderChooser.getSelectedFile();
			mainView.getOutputField().setText(selectedFolder.getAbsolutePath());
		}
	}

	private void onQualitySelect(Quality quality) {
		this.selectedQuality = quality;
	}

	public void loadQualities(JPanel qualityScrollPanel) {
		for (Quality quality : qualities) {
			String text = String.format("%-20s | %-20s | %-20s | %-10s", quality.getResolution(),
					quality.getResolutionValue(), quality.getExtension(), quality.getFileSize());
			JCheckBox qualityCheckbox = getQualityCheckbox(text);
			qualityCheckbox.addActionListener(e -> onQualitySelect(quality));
			qualityGroup.add(qualityCheckbox);
			qualityScrollPanel.add(qualityCheckbox);
		}

		qualityScrollPanel.revalidate();
	}

	private JCheckBox getQualityCheckbox(String title) {
		JCheckBox checkbox = new JCheckBox(title);
		checkbox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		checkbox.setPreferredSize(new Dimension(100, 20));
		checkbox.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		checkbox.setOpaque(false);
		return checkbox;
	}

	private String getDownloadPath() {
		return System.getProperty("user.home") + File.separator + "Downloads";
	}
}
