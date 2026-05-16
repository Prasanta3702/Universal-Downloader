package com.prasanta;

import javax.swing.SwingUtilities;

import com.prasanta.ui.MainController;
import com.prasanta.ui.MainView;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			MainView mainView = new MainView();
			new MainController(mainView);
			mainView.setVisible(true);
		});
	}
}
