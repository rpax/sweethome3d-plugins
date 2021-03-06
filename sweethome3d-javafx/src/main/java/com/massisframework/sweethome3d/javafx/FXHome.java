package com.massisframework.sweethome3d.javafx;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.eteks.sweethome3d.model.CollectionEvent;
import com.eteks.sweethome3d.model.CollectionEvent.Type;
import com.eteks.sweethome3d.model.CollectionListener;
import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomeObject;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;
import com.eteks.sweethome3d.model.SelectionListener;
import com.eteks.sweethome3d.model.Wall;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FXHome {

	public static final long VERSION = 1000;
	private Home home;

	private ObservableList<FXHomePieceOfFurniture> furniture;
	private ObservableList<FXWall> walls;
	private CollectionListener<HomePieceOfFurniture> furnitureListener;
	private CollectionListener<Wall> wallsListener;
	private SelectionListener selectionListener;

	private ObservableList<FXHomeObject<?>> selectedItems;

	public FXHome(Home home)
	{
		this.home = home;
		this.selectedItems = FXCollections.observableArrayList();
		this.furniture = FXCollections.observableArrayList();
		this.walls = FXCollections.observableArrayList();
		this.addListeners();
		this.home.getFurniture()
				.forEach(f -> addHomeObject(this.furniture, f, true));
		this.home.getWalls().forEach(w -> addHomeObject(this.walls, w, true));
	}

	private void addListeners()
	{
		this.furnitureListener = createCollectionListener(this.furniture);
		this.wallsListener = createCollectionListener(this.walls);
		this.selectionListener = createSelectionListener();
		this.home.addSelectionListener(this.selectionListener);
		this.home.addFurnitureListener(this.furnitureListener);
		this.home.addWallsListener(this.wallsListener);
	}

	public void removeListeners()
	{
		this.home.removeFurnitureListener(this.furnitureListener);
		this.home.removeWallsListener(this.wallsListener);
	}

	/*
	 * Utility methods
	 */
	private SelectionListener createSelectionListener()
	{
		return evt -> {
			final List<? extends Object> evtSelectedItems = new ArrayList<>(
					evt.getSelectedItems());
			try
			{
				Platform.runLater(() -> {
					this.selectedItems.setAll(
							evtSelectedItems
									.stream()
									.filter(o -> (o instanceof HomeObject))
									.map(HomeObject.class::cast)
									.map(this::getHomeObject)
									.filter(fx -> fx != null)
									.filter(Optional::isPresent)
									.map(Optional::get)
									.collect(Collectors.toList()));
				});
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			// TODOS los que tengamos aqui que no tengamos antes
		};

	}

	private static <HO extends HomeObject, T extends FXHomeObject<HO>> CollectionListener<HO> createCollectionListener(
			final ObservableList<T> l)
	{
		return new CollectionListener<HO>() {

			@Override
			public void collectionChanged(CollectionEvent<HO> ev)
			{

				HO item = ev.getItem();
				Type type = ev.getType();
				Platform.runLater(() -> {
					switch (type)
					{
					case ADD:
						addHomeObject(l, item, false);
						break;
					case DELETE:
						removeHomeObject(l, item);
						break;
					default:
						break;
					}
				});
			}
		};
	}

	@SuppressWarnings("unchecked")
	private <HO extends HomeObject, T extends FXHomeObject<HO>> Optional<T> getHomeObject(
			HO ho)
	{
		Optional<T> item;
		if (ho instanceof Wall)
		{
			item = (Optional<T>) getHomeObject(this.walls, (Wall) ho);
		} else if (ho instanceof HomePieceOfFurniture)
		{
			item = (Optional<T>) getHomeObject(this.furniture,
					(HomePieceOfFurniture) ho);
		} else
		{
			item = Optional.empty();
		}
		return item;
	}

	private static <HO extends HomeObject, T extends FXHomeObject<HO>> boolean homeObjectExists(
			ObservableList<T> l, HO obj)
	{
		return l.stream()
				.map(FXHomeObject::getHomeObject)
				.anyMatch(piece -> piece == obj);
	}

	private static <HO extends HomeObject, T extends FXHomeObject<HO>> Optional<T> getHomeObject(
			ObservableList<T> l, HO obj)
	{
		return l.stream()
				.filter(o -> o.getHomeObject() == obj)
				.findAny();
	}

	private static <HO extends HomeObject, T extends FXHomeObject<HO>> boolean addHomeObject(
			ObservableList<T> l, HO obj, boolean loaded)
	{
		boolean exists = homeObjectExists(l, obj);

		if (!exists)
		{
			T fxobj = createFXHomeObject(obj);
			if (!loaded)
			{
				fxobj.getMassisProperties().objectIdProperty()
						.set(UUID.randomUUID().toString());
			}
			l.add(fxobj);

			return true;
		} else
		{
			return false;
		}
	}

	private static <HO extends HomeObject, T extends FXHomeObject<HO>> boolean removeHomeObject(
			ObservableList<T> l, HO obj)
	{
		return l.removeIf(o -> o.getHomeObject() == obj);
	}

	@SuppressWarnings("unchecked")
	private static <HO extends HomeObject, T extends FXHomeObject<HO>> T createFXHomeObject(
			HO obj)
	{
		if (obj instanceof HomePieceOfFurniture)
		{
			return (T) new FXHomePieceOfFurniture((HomePieceOfFurniture) obj);
		} else if (obj instanceof Wall)
		{
			return (T) new FXWall((Wall) obj);
		} else
		{
			throw new UnsupportedOperationException("Not supported yet");
		}
	}

	// ----------

	public ObservableList<FXHomeObject<?>> selectedItemsProperty()
	{
		return this.selectedItems;
	}

	public ObservableList<FXHomePieceOfFurniture> furnitureProperty()
	{
		return this.furniture;
	}
}
