package com.github.alex1304.jdash.entity;

/**
 * Represents anything on Geometry Dash that has a unique ID (a user, a level, a private message...)
 * (���� ID(�����, ����, ���� �޽���...)�� ���� ������Ʈ�� ��ÿ��� ��� �׸� ǥ��)
 */
public interface GDEntity {
	/**
	 * The unique identifier for the entity.
	 * (����ü�� ���� ���� �ĺ���.)
	 * 
	 * @return an ID
	 * (ID�� ��ȯ�Ѵ�.)
	 */
	long getId();
}
