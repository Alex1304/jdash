package com.alex1304dev.jdash.component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A list of GD components
 * 
 * @author Alex1304
 */
public class GDComponentList<E extends GDComponent> extends ArrayList<E> implements GDComponent {

	private static final long serialVersionUID = -8181621256805798654L;

	public GDComponentList() {
		super();
	}

	public GDComponentList(Collection<? extends E> arg0) {
		super(arg0);
	}

	public GDComponentList(int arg0) {
		super(arg0);
	}
}
