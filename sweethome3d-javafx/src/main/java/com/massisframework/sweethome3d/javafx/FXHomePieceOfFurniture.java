package com.massisframework.sweethome3d.javafx;

import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;

import com.eteks.sweethome3d.model.Content;
import com.eteks.sweethome3d.model.HomeMaterial;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;
import com.eteks.sweethome3d.model.HomePieceOfFurniture.Property;
import com.eteks.sweethome3d.model.HomeTexture;
import com.eteks.sweethome3d.model.Level;
import com.eteks.sweethome3d.model.TextStyle;

import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyFloatWrapper;
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
	private final ReadOnlyObjectWrapper<Integer> color;
	private final ReadOnlyObjectWrapper<HomeTexture> texture;
	private final ReadOnlyObjectWrapper<Float> shininess;
	private final ReadOnlyObjectWrapper<float[][]> modelRotation;
	private final ReadOnlyStringWrapper staircaseCutOutShape;
	private final ReadOnlyStringWrapper creator;
	private final ReadOnlyBooleanWrapper backFaceShown;
	private final ReadOnlyBooleanWrapper resizable;
	private final ReadOnlyBooleanWrapper deformable;
	private final ReadOnlyBooleanWrapper texturable;
	private final ReadOnlyObjectWrapper<BigDecimal> price;
	private final ReadOnlyObjectWrapper<BigDecimal> valueAddedTaxPercentage;
	private final ReadOnlyObjectWrapper<String> currency;
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
		this.color = new ReadOnlyObjectWrapper<>(piece.getColor());
		this.modelRotation = new ReadOnlyObjectWrapper<>(piece.getModelRotation());
		this.staircaseCutOutShape = new ReadOnlyStringWrapper(
				piece.getStaircaseCutOutShape());
		this.creator = new ReadOnlyStringWrapper(piece.getCreator());
		this.backFaceShown = new ReadOnlyBooleanWrapper(piece.isBackFaceShown());
		this.resizable = new ReadOnlyBooleanWrapper(piece.isResizable());
		this.deformable = new ReadOnlyBooleanWrapper(piece.isDeformable());
		this.texturable = new ReadOnlyBooleanWrapper(piece.isTexturable());
		this.price = new ReadOnlyObjectWrapper<>(piece.getPrice());
		this.valueAddedTaxPercentage = new ReadOnlyObjectWrapper<>(piece.getValueAddedTaxPercentage());
		this.currency = new ReadOnlyObjectWrapper<String>(piece.getCurrency());

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
		this.shininess = new ReadOnlyObjectWrapper<>(piece.getShininess());
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
			this.angle.set(this.homeObject.getAngle());
			break;
		case COLOR:
			this.color.set(this.homeObject.getColor());
			break;
		case DEPTH:
			this.depth.set(this.homeObject.getDepth());
			break;
		case DESCRIPTION:
			this.description.set(this.homeObject.getDescription());
			break;
		case ELEVATION:
			this.elevation.set(this.homeObject.getElevation());
			break;
		case HEIGHT:
			this.height.set(this.homeObject.getHeight());
			break;
		case LEVEL:
			this.level.set(this.homeObject.getLevel());
			break;
		case MODEL_MATERIALS:
			this.modelMaterials.set(this.homeObject.getModelMaterials());
			break;
		case MODEL_MIRRORED:
			this.modelMirrored.set(this.homeObject.isModelMirrored());
			break;
		case MOVABLE:
			this.movable.set(this.homeObject.isMovable());
			break;
		case NAME:
			this.name.set(this.homeObject.getName());
			break;
		case NAME_ANGLE:
			this.nameAngle.set(this.homeObject.getNameAngle());
			break;
		case NAME_STYLE:
			this.nameStyle.setValue(this.homeObject.getNameStyle());
			break;
		case NAME_VISIBLE:
			this.nameVisible.setValue(this.homeObject.isNameVisible());
			break;
		case NAME_X_OFFSET:
			this.nameXOffset.set(this.homeObject.getNameXOffset());
			break;
		case NAME_Y_OFFSET:
			this.nameYOffset.set(this.homeObject.getNameYOffset());
			break;
		case PRICE:
			this.price.set(this.homeObject.getPrice());
			break;
		case SHININESS:
			this.shininess.set(this.homeObject.getShininess());
			break;
		case TEXTURE:
			this.texture.set(this.homeObject.getTexture());
			break;
		case VISIBLE:
			this.visible.set(this.homeObject.isVisible());
			break;
		case WIDTH:
			this.width.set(this.homeObject.getWidth());
			break;
		case X:
			this.x.set(this.homeObject.getX());
			break;
		case Y:
			this.y.set(this.homeObject.getY());
			break;
		default:
			break;

		}
	}

}
