package com.prasanta.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.prasanta.model.DownloadRequest;
import com.prasanta.model.Quality;

public class DownloadService {
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

					String note = line.substring(Math.min(line.length(), 25));

					qualities.add(new Quality(formatId, ext, resolution, fileSize, note));
				}
			}

			process.waitFor();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return qualities;
	}

	public void download(DownloadRequest request) {
		try {

			ProcessBuilder pb;

			if (request.isAudioOnly()) {
				pb = new ProcessBuilder("lib/yt-dlp.exe", "-P", request.getOutputPath(), "-o", "%(title)s.%(ext)s",
						"-x", "140", "m4a", request.getUrl());
			} else {
				pb = new ProcessBuilder("lib/yt-dlp.exe", "-P", request.getOutputPath(), "-o", "%(title)s.%(ext)s",
						"-f", request.getQuality(), request.getUrl());
			}

			pb.redirectErrorStream(true);

			Process process = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;

			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			int exitCode = process.waitFor();
			System.out.println("Exit with code: " + exitCode);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}