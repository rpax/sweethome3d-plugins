package com.massisframework.sweethome3d.javafx;

import javafx.scene.Node;
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
//	protected void setCacheFalse()
//	{
//		setCacheFalse(this.getRoot());
//	}
//
//	protected static void setCacheFalse(Node n)
//	{
//		n.setCache(false);
//		if (n instanceof Parent)
//		{
//			((Parent) n).getChildrenUnmodifiable()
//					.forEach(c -> c.setCache(false));
//		}
//	}
	
}
