package com.massisframework.sweethome3d.javafx;

import java.awt.GridLayout;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Pair;

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

	public static <T extends JFXController> Future<Pair<JComponent, T>> wrapInFXPanel(
			final URL location)
	{
		final CompletableFuture<Pair<JComponent, T>> cF = new CompletableFuture<>();
		JFXPanel fxPanel = new JFXPanel();
		JPanel parent = new JPanel();
		parent.setLayout(new GridLayout(1, 0));
		parent.add(fxPanel);
		Platform.runLater(() -> {
			try
			{
				T controller = (T) loadController(location);
				fxPanel.setScene(new Scene(controller.getRoot()));
				cF.complete(new Pair<JComponent, T>(parent, controller));
			} catch (Exception e)
			{
				e.printStackTrace();
				cF.completeExceptionally(e);
			}
		});

		return cF;
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
