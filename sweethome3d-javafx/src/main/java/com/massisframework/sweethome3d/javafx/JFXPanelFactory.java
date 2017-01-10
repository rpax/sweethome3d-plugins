package com.massisframework.sweethome3d.javafx;

import java.awt.GridLayout;
import java.io.IOException;
import java.net.URL;
import java.util.function.BiConsumer;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public final class JFXPanelFactory {

	public static <T extends JFXController> void wrapInFXPanel(
			final URL location, BiConsumer<JFXPanel, T> action)
	{
		JFXPanel fxPanel = new JFXPanel();
		JPanel parent = new JPanel();
		parent.setLayout(new GridLayout(1, 0));
		parent.add(fxPanel);
		Platform.runLater(() -> {
			try
			{
				T controller = (T) loadController(location);
				fxPanel.setScene(new Scene(controller.getRoot()));
				SwingUtilities.invokeLater(() -> {
					action.accept(fxPanel, controller);
				});
			} catch (Exception e)
			{
				e.printStackTrace();
				action.accept(null, null);

			}
		});

	}

	@SuppressWarnings("unchecked")
	public static <T extends AbstractJFXController> T loadController(
			final URL location) throws IOException
	{
		T c = null;
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader();
			Parent root = fxmlLoader.load(location.openStream());
			c = (T) fxmlLoader.getController();
			c.setRoot(root);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return c;

	}
}
