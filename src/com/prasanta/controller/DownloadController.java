package com.prasanta.controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.prasanta.model.DownloadRequest;
import com.prasanta.model.Quality;
import com.prasanta.service.DownloadService;

public class DownloadController {

	private final ExecutorService executorService = Executors.newFixedThreadPool(4);

	private final DownloadService service = new DownloadService();

	public Future<List<Quality>> fetchQuality(DownloadRequest request) {
		return executorService.submit(() -> service.fetchQuality(request));
	}

	public void startDownload(DownloadRequest request) {
		executorService.submit(() -> {
			service.download(request);
		});
	}

	public void shutdown() {
		executorService.shutdown();
	}
}