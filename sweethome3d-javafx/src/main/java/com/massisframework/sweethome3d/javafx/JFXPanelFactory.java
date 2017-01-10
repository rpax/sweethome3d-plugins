package com.massisframework.sweethome3d.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.function.BiConsumer;

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
		try
		{
			T controller = (T) loadController(location);
			fxPanel.setScene(new Scene(controller.getRoot()));
			action.accept(fxPanel, controller);

		} catch (Exception e)
		{
			e.printStackTrace();
			action.accept(null, null);

		}

	}

	public static void runInFX(Runnable r)
	{
		if (Platform.isFxApplicationThread())
		{
			r.run();
		} else
		{
			Platform.runLater(r);
		}
	}

	public static void runInSwing(Runnable r)
	{
		if (SwingUtilities.isEventDispatchThread())
		{
			r.run();
		} else
		{
			SwingUtilities.invokeLater(r);
		}
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
