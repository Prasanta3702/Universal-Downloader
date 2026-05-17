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

		mainView.getOutputField().setText(getDownloadPath());

		mainView.getSendButton().addActionListener(e -> onSend());
		mainView.getUrlField().addActionListener(e -> onSend());
		mainView.getSelectOutputFolderBtn().addActionListener(e -> openFolderDialog());
		mainView.getDownloadButton().addActionListener(e -> onDownload());
		mainView.getCancelButton().addActionListener(e -> onCencel());
	}

	private void onSend() {
		if (!mainView.getUrlField().getText().isBlank()) {

			DownloadRequest request = new DownloadRequest();
			request.setUrl(mainView.getUrlField().getText());

			// Remove all elements from list
			qualities.clear();
			mainView.getQualityScrollPanel().removeAll();
			mainView.getQualityScrollPanel().revalidate();

			controller.fetchQuality(request, mainView.getLoadingProgress(), qualities -> {

				audioQuality = qualities.stream().filter(q -> q.getResolution().toLowerCase().contains("audio")
						&& q.getExtension().equalsIgnoreCase("m4a")).findFirst().orElse(null);

				loadQualities(qualities);
			});
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

	public void loadQualities(List<Quality> qualities) {
		int i = 1;
		for (Quality quality : qualities) {

			String text = String.format("%-15.15s | %-20.20s | %-20.20s | %-10.10s", quality.getResolution(),
					quality.getResolutionValue(), quality.getExtension(), quality.getFileSize());

			JCheckBox qualityCheckbox = new JCheckBox(text);
			qualityCheckbox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
			qualityCheckbox.setPreferredSize(new Dimension(0, 20));
			qualityCheckbox.setBorder(BorderFactory.createLineBorder(Color.red));
			if (i % 2 == 0) {
				qualityCheckbox.setBackground(new Color(220, 220, 220));
			} else {
				qualityCheckbox.setOpaque(false);
			}

			qualityCheckbox.addActionListener(e -> onQualitySelect(quality));

			qualityGroup.add(qualityCheckbox);

			mainView.getQualityScrollPanel().add(qualityCheckbox);
			i++;
		}

		mainView.getQualityScrollPanel().revalidate();
	}

	private String getDownloadPath() {
		return System.getProperty("user.home") + File.separator + "Downloads";
	}
}
