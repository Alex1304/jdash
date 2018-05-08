package com.alex1304dev.jdash.component;

/**
 * GD component that encapsulates a boolean
 * 
 * @author Alex1304
 */
public class GDBoolean implements GDComponent {

	private boolean isSuccess;

	/**
	 * @param isSuccess
	 *            - whether it's a success
	 */
	public GDBoolean(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * Gets whether it's a success
	 * 
	 * @return boolean
	 */
	public boolean isSuccess() {
		return isSuccess;
	}
}
