package com.prasanta.controller;

import java.util.List;
import java.util.function.Consumer;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import com.prasanta.model.DownloadRequest;
import com.prasanta.model.Quality;
import com.prasanta.service.DownloadService;

public class DownloadController {

	private final DownloadService service = new DownloadService();

	public void fetchQuality(DownloadRequest request, JProgressBar progressBar, Consumer<List<Quality>> callback) {

		progressBar.setIndeterminate(true);

		SwingWorker<List<Quality>, Void> worker = new SwingWorker<>() {

			@Override
			protected List<Quality> doInBackground() {
				return service.fetchQuality(request).stream()
						.filter(q -> q != null && q.getResolution() != null && q.getExtension() != null
								&& !q.getResolution().equalsIgnoreCase("No")
								&& !q.getExtension().equalsIgnoreCase("mhtml"))
						.toList();
			}

			@Override
			protected void done() {
				progressBar.setIndeterminate(false);

				try {
					callback.accept(get());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		worker.execute();
	}

	public void startDownload(DownloadRequest request, JProgressBar progressBar) {

		progressBar.setValue(0);
		progressBar.setStringPainted(true);

		SwingWorker<Void, Integer> worker = new SwingWorker<>() {

			@Override
			protected Void doInBackground() {

				service.download(request, value -> {
					setProgress(value);
				});

				return null;
			}

			@Override
			protected void done() {
				progressBar.setValue(100);
				progressBar.setString("Completed");
			}
		};

		worker.addPropertyChangeListener(e -> {
			if ("progress".equals(e.getPropertyName())) {
				int val = (int) e.getNewValue();
				progressBar.setValue(val);
				progressBar.setString("Downloading: " + val + "%");
			}
		});

		worker.execute();
	}

	public void shutdown() {
		service.cancelDownload();
	}
}