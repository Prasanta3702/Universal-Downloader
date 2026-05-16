package com.prasanta.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JProgressBar;

import com.prasanta.model.DownloadRequest;
import com.prasanta.model.Quality;

public class DownloadService {
	private Process process;

	public List<Quality> fetchQuality(DownloadRequest request) {

		List<Quality> qualities = new ArrayList<>();

		try {

			ProcessBuilder pb = new ProcessBuilder("lib/yt-dlp.exe", "-F", request.getUrl());

			pb.redirectErrorStream(true);

			Process process = pb.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;

			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				// Skip headers
				if (line.startsWith("[") || line.startsWith("ID") || line.trim().isEmpty()) {
					continue;
				}
				String[] parts = line.trim().split("\\s+");

				if (parts.length >= 5) {
					String formatId = parts[0];
					String ext = parts[1];
					String resolution = parts[2];
					String fileSize = parts[5];
					String resolutionValue = "None";
					try {
						resolutionValue = parts[13];
					} catch (Exception ignored) {
					}

					String note = line.substring(Math.min(line.length(), 25));

					qualities.add(new Quality(formatId, ext, resolution, resolutionValue, fileSize, note));
				}
			}

			process.waitFor();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return qualities;
	}

	public void download(DownloadRequest request, JProgressBar progressBar) {

		try {
			ProcessBuilder pb = new ProcessBuilder("lib/yt-dlp.exe", "-P", request.getOutputPath(), "-f",
					request.getQuality(), request.getUrl());
			pb.redirectErrorStream(true);

			process = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;

			while ((line = reader.readLine()) != null) {
				System.out.println(line);

				if (line.contains("%") && line.contains("of")) {
					try {
						String percentPart = line.substring(line.indexOf("]") + 1);
						String percent = percentPart.trim().split("%")[0].trim();
						double value = Double.parseDouble(percent);
						int progressValue = (int) Math.round(value);
						progressBar.setValue(progressValue);
						progressBar.setString("Downloading: " + progressValue+"%");
					} catch (Exception ignored) {
					}
				}
			}

			int exitCode = process.waitFor();
			System.out.println("Exit with code: " + exitCode);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public void cancelDownload() {
		if (process != null && process.isAlive()) {
			long pid = process.pid();

			try {
				new ProcessBuilder("taskkill", "/PID", String.valueOf(pid), "/T", "/F").start().waitFor();
			} catch (Exception e) {
				e.printStackTrace();
			}

			process.destroyForcibly();
		}
	}
}