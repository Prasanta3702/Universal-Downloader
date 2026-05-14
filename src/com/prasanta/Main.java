package com.prasanta;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.swing.SwingUtilities;

import com.prasanta.controller.DownloadController;
import com.prasanta.model.DownloadRequest;
import com.prasanta.model.Quality;
import com.prasanta.ui.MainFrame;

public class Main {
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> new MainFrame().createAndShow());
		
//		DownloadRequest request = new DownloadRequest();
//		request.setUrl("https://youtu.be/enQuoRciccM?si=p0l_f-hTWiNi_1kf");
//		DownloadController controller = new DownloadController();
//		Future<List<Quality>> fetchQuality = controller.fetchQuality(request);
//		try {
//			List<Quality> list = fetchQuality.get();
//			list.forEach(System.out::println);
//		} catch (InterruptedException | ExecutionException e) {
//			e.printStackTrace();
//		}
	}
}
