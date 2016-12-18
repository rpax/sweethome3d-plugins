package com.massisframework.sweethome3d.javafx;

import java.beans.PropertyChangeEvent;

import com.eteks.sweethome3d.model.Content;
import com.eteks.sweethome3d.model.HomeMaterial;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;
import com.eteks.sweethome3d.model.HomePieceOfFurniture.Property;
import com.eteks.sweethome3d.model.HomeTexture;
import com.eteks.sweethome3d.model.Level;
import com.eteks.sweethome3d.model.TextStyle;

import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyFloatWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;

public class FXHomePieceOfFurniture
		extends FXHomeObject<HomePieceOfFurniture> {

	private final ReadOnlyStringWrapper catalogId;
	private final ReadOnlyStringWrapper name;
	private final ReadOnlyBooleanWrapper nameVisible;
	private final ReadOnlyFloatWrapper nameXOffset;
	private final ReadOnlyFloatWrapper nameYOffset;
	private final javafx.beans.property.Property<TextStyle> nameStyle;
	private final ReadOnlyFloatWrapper nameAngle;
	private final ReadOnlyStringWrapper description;
	private final ReadOnlyStringWrapper information;
	private final ReadOnlyObjectWrapper<Content> icon;
	private final ReadOnlyObjectWrapper<Content> planIcon;
	private final ReadOnlyObjectWrapper<Content> model;
	private final ReadOnlyFloatWrapper width;
	private final ReadOnlyFloatWrapper depth;
	private final ReadOnlyFloatWrapper height;
	private final ReadOnlyFloatWrapper elevation;
	private final ReadOnlyFloatWrapper dropOnTopElevation;
	private final ReadOnlyBooleanWrapper movable;
	private final ReadOnlyBooleanWrapper doorOrWindow;
	private final ReadOnlyObjectWrapper<HomeMaterial[]> modelMaterials;
	private final ReadOnlyIntegerWrapper color;
	private final ReadOnlyObjectWrapper<HomeTexture> texture;
	private final ReadOnlyFloatWrapper shininess;
	private final ReadOnlyObjectWrapper<float[][]> modelRotation;
	private final ReadOnlyStringWrapper staircaseCutOutShape;
	private final ReadOnlyStringWrapper creator;
	private final ReadOnlyBooleanWrapper backFaceShown;
	private final ReadOnlyBooleanWrapper resizable;
	private final ReadOnlyBooleanWrapper deformable;
	private final ReadOnlyBooleanWrapper texturable;
	private final ReadOnlyDoubleWrapper price;
	private final ReadOnlyDoubleWrapper valueAddedTaxPercentage;
	private final ReadOnlyStringWrapper currency;
	private final ReadOnlyBooleanWrapper visible;
	private final ReadOnlyFloatWrapper x;
	private final ReadOnlyFloatWrapper y;
	private final ReadOnlyFloatWrapper angle;
	private final ReadOnlyBooleanWrapper modelMirrored;
	private final ReadOnlyObjectWrapper<Level> level;

	public FXHomePieceOfFurniture(HomePieceOfFurniture piece)
	{
		super(piece);

		this.name = new ReadOnlyStringWrapper(piece,
				HomePieceOfFurniture.Property.NAME.name(), piece.getName());

		this.description = new ReadOnlyStringWrapper(piece.getDescription());
		this.information = new ReadOnlyStringWrapper(piece.getInformation());
		this.icon = new ReadOnlyObjectWrapper<>(piece.getIcon());
		this.planIcon = new ReadOnlyObjectWrapper<>(piece.getPlanIcon());
		this.model = new ReadOnlyObjectWrapper<>(piece.getModel());
		this.width = new ReadOnlyFloatWrapper(piece.getWidth());
		this.depth = new ReadOnlyFloatWrapper(piece.getDepth());
		this.height = new ReadOnlyFloatWrapper(piece.getHeight());
		this.elevation = new ReadOnlyFloatWrapper(piece.getElevation());
		this.dropOnTopElevation = new ReadOnlyFloatWrapper(
				piece.getDropOnTopElevation());
		this.movable = new ReadOnlyBooleanWrapper(piece.isMovable());
		this.doorOrWindow = new ReadOnlyBooleanWrapper(piece.isDoorOrWindow());
		this.color = new ReadOnlyIntegerWrapper(piece.getColor());
		this.modelRotation = new ReadOnlyObjectWrapper<>(
				piece.getModelRotation());
		this.staircaseCutOutShape = new ReadOnlyStringWrapper(
				piece.getStaircaseCutOutShape());
		this.creator = new ReadOnlyStringWrapper(piece.getCreator());
		this.backFaceShown = new ReadOnlyBooleanWrapper(
				piece.isBackFaceShown());
		this.resizable = new ReadOnlyBooleanWrapper(piece.isResizable());
		this.deformable = new ReadOnlyBooleanWrapper(piece.isDeformable());
		this.texturable = new ReadOnlyBooleanWrapper(piece.isTexturable());
		this.price = new ReadOnlyDoubleWrapper(piece.getPrice().doubleValue());
		this.valueAddedTaxPercentage = new ReadOnlyDoubleWrapper(
				piece.getValueAddedTaxPercentage().doubleValue());
		this.currency = new ReadOnlyStringWrapper(piece.getCurrency());

		this.catalogId = new ReadOnlyStringWrapper(piece.getCatalogId());
		this.nameVisible = new ReadOnlyBooleanWrapper(piece.isNameVisible());
		this.nameXOffset = new ReadOnlyFloatWrapper(piece.getNameXOffset());
		this.nameYOffset = new ReadOnlyFloatWrapper(piece.getNameYOffset());
		this.nameAngle = new ReadOnlyFloatWrapper(piece.getNameAngle());
		this.nameStyle = new ReadOnlyObjectWrapper<>(piece.getNameStyle());
		this.visible = new ReadOnlyBooleanWrapper(piece.isVisible());
		this.angle = new ReadOnlyFloatWrapper(piece.getAngle());
		this.x = new ReadOnlyFloatWrapper(piece.getX());
		this.y = new ReadOnlyFloatWrapper(piece.getY());
		this.modelMirrored = new ReadOnlyBooleanWrapper(
				piece.isModelMirrored());
		this.texture = new ReadOnlyObjectWrapper<>(piece.getTexture());
		this.shininess = new ReadOnlyFloatWrapper(piece.getShininess());
		this.modelMaterials = new ReadOnlyObjectWrapper<>(
				piece.getModelMaterials());
		this.level = new ReadOnlyObjectWrapper<>(piece,
				HomePieceOfFurniture.Property.LEVEL.name(), piece.getLevel());

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		Property prop = HomePieceOfFurniture.Property
				.valueOf(evt.getPropertyName());
		this.propertyChange(prop);
	}

	private void propertyChange(HomePieceOfFurniture.Property prop)
	{

		switch (prop)
		{
		case ANGLE:
			this.angle.set(this.piece.getAngle());
			break;
		case COLOR:
			this.color.set(this.piece.getColor());
			break;
		case DEPTH:
			this.depth.set(this.piece.getDepth());
			break;
		case DESCRIPTION:
			this.description.set(this.piece.getDescription());
			break;
		case ELEVATION:
			this.elevation.set(this.piece.getElevation());
			break;
		case HEIGHT:
			this.height.set(this.piece.getHeight());
			break;
		case LEVEL:
			this.level.set(this.piece.getLevel());
			break;
		case MODEL_MATERIALS:
			this.modelMaterials.set(this.piece.getModelMaterials());
			break;
		case MODEL_MIRRORED:
			this.modelMirrored.set(this.piece.isModelMirrored());
			break;
		case MOVABLE:
			this.movable.set(this.piece.isMovable());
			break;
		case NAME:
			this.name.set(this.piece.getName());
			break;
		case NAME_ANGLE:
			this.nameAngle.set(this.piece.getNameAngle());
			break;
		case NAME_STYLE:
			this.nameStyle.setValue(this.piece.getNameStyle());
			break;
		case NAME_VISIBLE:
			this.nameVisible.setValue(this.piece.isNameVisible());
			break;
		case NAME_X_OFFSET:
			this.nameXOffset.set(this.piece.getNameXOffset());
			break;
		case NAME_Y_OFFSET:
			this.nameYOffset.set(this.piece.getNameYOffset());
			break;
		case PRICE:
			this.price.set(this.piece.getPrice().doubleValue());
			break;
		case SHININESS:
			this.shininess.set(this.piece.getShininess());
			break;
		case TEXTURE:
			this.texture.set(this.piece.getTexture());
			break;
		case VISIBLE:
			this.visible.set(this.piece.isVisible());
			break;
		case WIDTH:
			this.width.set(this.piece.getWidth());
			break;
		case X:
			this.x.set(this.piece.getX());
			break;
		case Y:
			this.y.set(this.piece.getY());
			break;
		default:
			break;

		}
	}
	
	protected void forceUpdate()
	{
		super.forceUpdate();
		for (Property prop : HomePieceOfFurniture.Property.values())
		{
			this.propertyChange(prop);
		}
	}

}
