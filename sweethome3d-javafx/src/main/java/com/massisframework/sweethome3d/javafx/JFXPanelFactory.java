package com.massisframework.sweethome3d.javafx;

import java.awt.GridLayout;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.function.BiConsumer;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sun.javafx.scene.control.skin.ScrollPaneSkin;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Skin;
import javafx.scene.layout.StackPane;

public final class JFXPanelFactory {

	private static JFXPanel newJFXPanel()
	{
		// final CompletableFuture<JFXPanel> cF = new CompletableFuture<>();
		// SwingUtilities.invokeLater(() -> {
		// cF.complete(new JFXPanel());
		// });
		// try
		// {
		// return cF.get();
		// } catch (InterruptedException | ExecutionException e)
		// {
		// throw new RuntimeException(e);
		// }
		return new JFXPanel();
	}

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
