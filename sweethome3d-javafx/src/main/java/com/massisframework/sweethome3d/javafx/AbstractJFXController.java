package com.massisframework.sweethome3d.javafx;

import javafx.scene.Parent;

public class AbstractJFXController implements JFXController {

	private Parent root;

	void setRoot(Parent root)
	{
		this.root = root;
		this.root.setCache(false);
	}

	@Override
	public Parent getRoot()
	{
		return this.root;
	}
	
}
