package com.massisframework.sweethome3d.plugins.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.eteks.sweethome3d.model.HomeObject;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;

public class Location {

	private HomeObject ho;
	static Map<String, List<Object>> components = new ConcurrentHashMap<>();

	public static Location getLocationOf(HomeObject ho) {
		if (ho.getProperty("MASSIS_ID") == null) {
			ho.setProperty("MASSIS_ID", UUID.randomUUID().toString());
		}
		String uid = ho.getProperty("MASSIS_ID");
		if (components.get(uid) == null)
			components.put(uid, new ArrayList<>());
		for (Object c : components.get(uid)) {
			if (c instanceof Location)
				return (Location) c;
		}
		Location l = new Location(ho);
		components.get(uid).add(l);
		return l;
	}

	public Location(HomeObject ho) {
		this.ho = ho;
	}

	private float x, y, z;

	public float getX() {
		if (this.ho instanceof HomePieceOfFurniture) {
			return (this.x = ((HomePieceOfFurniture) this.ho).getX());
		}
		return this.x;
	}

	public float getY() {
		if (this.ho instanceof HomePieceOfFurniture) {
			return (this.y = ((HomePieceOfFurniture) this.ho).getY());
		}
		return this.y;
	}

	public float getZ() {
		return z;
	}

	public void setX(float x) {
		this.x = x;
		if (this.ho instanceof HomePieceOfFurniture) {
			((HomePieceOfFurniture) this.ho).setX(x);
		}
	}

	public void setY(float y) {
		this.y = y;
		if (this.ho instanceof HomePieceOfFurniture) {
			((HomePieceOfFurniture) this.ho).setY(y);
		}
	}

	public void setZ(float z) {
		this.z = z;
		// uh
	}

}
