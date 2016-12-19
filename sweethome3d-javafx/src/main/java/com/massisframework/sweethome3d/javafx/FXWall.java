package com.massisframework.sweethome3d.javafx;

import java.beans.PropertyChangeEvent;

import com.eteks.sweethome3d.model.Baseboard;
import com.eteks.sweethome3d.model.HomeTexture;
import com.eteks.sweethome3d.model.Level;
import com.eteks.sweethome3d.model.TextureImage;
import com.eteks.sweethome3d.model.Wall;
import com.eteks.sweethome3d.model.Wall.Property;

import javafx.beans.property.ReadOnlyFloatWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class FXWall extends FXHomeObject<Wall> {

	private final ReadOnlyFloatWrapper xStart = new ReadOnlyFloatWrapper();
	private final ReadOnlyFloatWrapper yStart = new ReadOnlyFloatWrapper();
	private final ReadOnlyFloatWrapper xEnd = new ReadOnlyFloatWrapper();
	private final ReadOnlyFloatWrapper yEnd = new ReadOnlyFloatWrapper();
	private final ReadOnlyFloatWrapper arcExtent = new ReadOnlyFloatWrapper();
	// private final ReadOnlyObjectWrapper<Wall> wallAtStart;
	// private final ReadOnlyObjectWrapper<Wall> wallAtEnd;
	private final ReadOnlyFloatWrapper thickness = new ReadOnlyFloatWrapper();
	private final ReadOnlyFloatWrapper height = new ReadOnlyFloatWrapper();
	private final ReadOnlyFloatWrapper heightAtEnd = new ReadOnlyFloatWrapper();
	private final ReadOnlyIntegerWrapper leftSideColor = new ReadOnlyIntegerWrapper();
	private final ReadOnlyObjectWrapper<HomeTexture> leftSideTexture = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyFloatWrapper leftSideShininess = new ReadOnlyFloatWrapper();
	private final ReadOnlyObjectWrapper<Baseboard> leftSideBaseboard = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyIntegerWrapper rightSideColor = new ReadOnlyIntegerWrapper();
	private final ReadOnlyObjectWrapper<HomeTexture> rightSideTexture = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyFloatWrapper rightSideShininess = new ReadOnlyFloatWrapper();
	private final ReadOnlyObjectWrapper<Baseboard> rightSideBaseboard = new ReadOnlyObjectWrapper<>();
	// private final ReadOnlyBooleanWrapper symmetric = new
	// ReadOnlyBooleanWrapper(true);
	private final ReadOnlyObjectWrapper<TextureImage> pattern = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyIntegerWrapper topColor = new ReadOnlyIntegerWrapper();
	private final ReadOnlyObjectWrapper<Level> level = new ReadOnlyObjectWrapper<>();

	public FXWall(Wall wall)
	{
		super(wall);
		for (Property prop : Wall.Property.values())
		{
			updateProperty(prop);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		updateProperty(Wall.Property.valueOf(evt.getPropertyName()));
	}

	private void updateProperty(Property type)
	{
		switch (type)
		{
		case ARC_EXTENT:
			this.arcExtent.set(this.piece.getArcExtent());
			break;
		case HEIGHT:
			this.height.set(this.piece.getHeight());
			break;
		case HEIGHT_AT_END:
			if (piece.getHeightAtEnd() == null)
			{
				this.heightAtEnd.bind(this.height);
			} else
			{
				this.heightAtEnd.unbind();
				this.heightAtEnd.set(piece.getHeightAtEnd());
			}
			break;
		case LEFT_SIDE_BASEBOARD:
			this.leftSideBaseboard.set(piece.getLeftSideBaseboard());
			break;
		case LEFT_SIDE_COLOR:
			this.leftSideColor.set(piece.getLeftSideColor());
			break;
		case LEFT_SIDE_SHININESS:
			this.leftSideShininess.set(piece.getLeftSideShininess());
			break;
		case LEFT_SIDE_TEXTURE:
			this.leftSideTexture.set(piece.getLeftSideTexture());
			break;
		case LEVEL:
			this.level.set(piece.getLevel());
			break;
		case PATTERN:
			this.pattern.set(piece.getPattern());
			break;
		case RIGHT_SIDE_BASEBOARD:
			this.rightSideBaseboard.set(piece.getRightSideBaseboard());
			break;
		case RIGHT_SIDE_COLOR:
			this.rightSideColor.set(piece.getRightSideColor());
			break;
		case RIGHT_SIDE_SHININESS:
			this.rightSideShininess.set(piece.getRightSideShininess());
			break;
		case RIGHT_SIDE_TEXTURE:
			this.rightSideTexture.set(piece.getRightSideTexture());
			break;
		case THICKNESS:
			this.thickness.set(piece.getThickness());
			break;
		case TOP_COLOR:
			this.topColor.set(piece.getTopColor());
			break;
		case WALL_AT_END:
			// TODO
			// this.wallAtEnd.set(piece.getWallAtEnd());
			break;
		case WALL_AT_START:
			// TODO
			// this.wallAtStart.set(piece.getWallAtStart());
			break;
		case X_END:
			this.xEnd.set(piece.getXEnd());
			break;
		case X_START:
			this.xStart.set(piece.getXStart());
			break;
		case Y_END:
			this.yEnd.set(piece.getXEnd());
			break;
		case Y_START:
			this.yStart.set(piece.getYStart());
			break;
		default:
			break;

		}
	}

}
