package com.eteks.sweethome3d.plugin;

import com.eteks.sweethome3d.SweetHome3D;
import com.eteks.sweethome3d.model.Home;

public class HomeReadyEvent {

	private final SweetHome3D sh3d;
	private final Home home;

	public HomeReadyEvent(SweetHome3D sh3d, Home home) {
		this.sh3d = sh3d;
		this.home = home;
	}

	public SweetHome3D getSh3d() {
		return sh3d;
	}

	public Home getHome() {
		return home;
	}

}
