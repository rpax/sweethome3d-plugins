package com.massisframework.sweethome3d.javafx;

import java.awt.Container;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public final class JFXPanelFactory {

	private JFXPanelFactory()
	{
	}

	private static JFXPanelFactory INSTANCE = null;

	public static JFXPanelFactory getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new JFXPanelFactory();
		}
		return INSTANCE;
	}

	public Future<JComponent> loadFromURL(final URL fxmlLocation)
	{

		final CompletableFuture<JComponent> cF = new CompletableFuture<>();
		JFXPanel fxPanel = new JFXPanel();
		JPanel parent = new JPanel();
		parent.setLayout(new GridLayout(1, 0));
		parent.add(fxPanel);
		Platform.runLater(() -> {
			try
			{
				fxPanel.setScene(new Scene(FXMLLoader.load(fxmlLocation)));
				cF.complete(parent);
			} catch (IOException e)
			{
				e.printStackTrace();
				cF.completeExceptionally(e);
			}

		});

		return cF;
	}
}
