package com.massisframework.sweethome3d.javafx;

import java.util.Optional;

import com.eteks.sweethome3d.model.CollectionEvent;
import com.eteks.sweethome3d.model.CollectionListener;
import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomeObject;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;
import com.eteks.sweethome3d.model.SelectionListener;
import com.eteks.sweethome3d.model.Wall;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FXHome {

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
		this.home.getFurniture().forEach(this::addFurniture);
		this.home.getWalls().forEach(this::addWall);
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

	private SelectionListener createSelectionListener()
	{
		return evt -> {

			System.out.println("Selection evt!: " + evt.getSelectedItems());
			// TODO measure performance & GUI binding
			this.selectedItems.clear();
			this.home.getSelectedItems()
					.stream()
					.filter(o -> (o instanceof HomeObject))
					.map(HomeObject.class::cast)
					.map(this::getHomeObject)
					// TODO temporary: we dont have all types
					.filter(fx -> fx != null)
					.filter(Optional::isPresent)
					.map(Optional::get)
					.forEach(this.selectedItems::add);
			// TODOS los que tengamos aqui que no tengamos antes
		};
	}

	public void removeListeners()
	{
		this.home.removeFurnitureListener(this.furnitureListener);
		this.home.removeWallsListener(this.wallsListener);
	}

	/*
	 * Utility methods
	 */
	private static <HO extends HomeObject, T extends FXHomeObject<HO>> CollectionListener<HO> createCollectionListener(
			final ObservableList<T> l)
	{
		return new CollectionListener<HO>() {

			@Override
			public void collectionChanged(CollectionEvent<HO> ev)
			{
				switch (ev.getType())
				{
				case ADD:
					addHomeObject(l, ev.getItem());
					break;
				case DELETE:
					removeHomeObject(l, ev.getItem());
					break;
				default:
					break;
				}
			}
		};
	}

	private boolean addFurniture(HomePieceOfFurniture hpof)
	{
		return addHomeObject(this.furniture, hpof);
	}

	private boolean addWall(Wall w)
	{
		return addHomeObject(this.walls, w);
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
				.map(FXHomeObject::getPiece)
				.anyMatch(piece -> piece == obj);
	}

	private static <HO extends HomeObject, T extends FXHomeObject<HO>> Optional<T> getHomeObject(
			ObservableList<T> l, HO obj)
	{
		return l.stream()
				.filter(o -> o.getPiece() == obj)
				.findAny();
	}

	private static <HO extends HomeObject, T extends FXHomeObject<HO>> boolean addHomeObject(
			ObservableList<T> l, HO obj)
	{
		boolean exists = homeObjectExists(l, obj);
		if (!exists)
		{
			System.out.println("Adding " + obj);
			l.add(createFXHomeObject(obj));
			return true;
		} else
		{
			return false;
		}
	}

	private static <HO extends HomeObject, T extends FXHomeObject<HO>> boolean removeHomeObject(
			ObservableList<T> l, HO obj)
	{
		return l.removeIf(o -> o.getPiece() == obj);
	}

	@SuppressWarnings("unchecked")
	private static <HO extends HomeObject, T extends FXHomeObject<HO>> T createFXHomeObject(
			HO obj)
	{
		if (obj instanceof HomePieceOfFurniture)
		{
			return (T) new FXHomePieceOfFurniture(
					(HomePieceOfFurniture) obj);
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
}
