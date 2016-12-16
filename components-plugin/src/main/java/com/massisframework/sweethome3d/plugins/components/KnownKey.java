package com.massisframework.sweethome3d.plugins.components;

public enum KnownKey {
	METADATA("__MASSIS_METADATA"), UID("__MASSIS_UID");
	private String keyName;

	KnownKey(String keyName) {
		this.keyName = keyName;
	}
	@Override
	public String toString() {
		return this.getKeyName();
	}
	public String getKeyName() {
		return this.keyName;
	}
}
