package com.massisframework.sweethome3d.javafx;

import com.eteks.sweethome3d.model.CollectionEvent;
import com.eteks.sweethome3d.model.CollectionListener;
import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomeObject;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;
import com.eteks.sweethome3d.model.Wall;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FXHome {

	private Home home;

	private ObservableList<FXHomePieceOfFurniture> furniture;
	private ObservableList<FXWall> walls;
	private CollectionListener<HomePieceOfFurniture> furnitureListener;
	private CollectionListener<Wall> wallsListener;

	public FXHome(Home home)
	{
		this.home = home;
		this.furniture = FXCollections.observableArrayList();
		this.walls = FXCollections.observableArrayList();
		this.addListeners();
	}

	private void addListeners()
	{
		this.furnitureListener = createCollectionListener(this.furniture);
		this.wallsListener = createCollectionListener(this.walls);
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

	private static <HO extends HomeObject, T extends FXHomeObject<HO>> boolean homeObjectExists(
			ObservableList<T> l, HO obj)
	{
		return l.stream()
				.map(FXHomeObject::getPiece)
				.anyMatch(piece -> piece == obj);
	}

	@SuppressWarnings("unused")
	private static <HO extends HomeObject, T extends FXHomeObject<HO>> T getHomeObject(
			ObservableList<T> l, HO obj)
	{
		return l.stream()
				.filter(o -> o.getPiece() == obj)
				.findAny()
				.orElseGet(null);
	}

	private static <HO extends HomeObject, T extends FXHomeObject<HO>> boolean addHomeObject(
			ObservableList<T> l, HO obj)
	{
		boolean exists = homeObjectExists(l, obj);
		if (!exists)
		{
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
		Class<? extends HomeObject> type = obj.getClass();
		if (type == HomePieceOfFurniture.class)
		{
			return (T) new FXHomePieceOfFurniture(
					(HomePieceOfFurniture) obj);
		} else if (type == Wall.class)
		{
			return (T) new FXWall((Wall) obj);
		} else
		{
			throw new UnsupportedOperationException("Not supported yet");
		}
	}

}
