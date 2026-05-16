package com.prasanta.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JProgressBar;

import com.prasanta.model.DownloadRequest;
import com.prasanta.model.Quality;
import com.prasanta.service.DownloadService;

public class DownloadController {

	private final DownloadService service = new DownloadService();
	private Thread thread;

	public List<Quality> fetchQuality(DownloadRequest request) {
		List<Quality> qualities = new ArrayList<>();
		
		thread = new Thread(() -> {
			List<Quality> list = service.fetchQuality(request).stream()
					.filter(q -> q != null && q.getResolution() != null && q.getExtension() != null
							&& !q.getResolution().equalsIgnoreCase("No") && !q.getExtension().equalsIgnoreCase("mhtml"))
					.toList();
			qualities.addAll(list);
		});

		thread.start();
		
		try {
			thread.join();
		} catch (Exception e) {}
		return qualities;
	}

	public void startDownload(DownloadRequest request, JProgressBar progressBar) {
		progressBar.setValue(0);
		thread = new Thread(() -> {
			service.download(request, progressBar);
		});

		thread.start();
	}

	public void shutdown() {
		service.cancelDownload();
	}
}