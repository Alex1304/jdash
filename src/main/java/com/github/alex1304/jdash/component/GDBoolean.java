package com.github.alex1304.jdash.component;

/**
 * GD component that encapsulates a boolean
 * 
 * @author Alex1304
 */
public class GDBoolean implements GDComponent {

	private boolean isSuccess;
	
	public GDBoolean() {
	}

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

	/**
	 * Sets the isSuccess
	 *
	 * @param isSuccess - boolean
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isSuccess ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof GDBoolean))
			return false;
		GDBoolean other = (GDBoolean) obj;
		if (isSuccess != other.isSuccess)
			return false;
		return true;
	}
}
