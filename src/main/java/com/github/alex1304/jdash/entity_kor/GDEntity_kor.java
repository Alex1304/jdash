package com.github.alex1304.jdash.entity;

/**
 * Represents anything on Geometry Dash that has a unique ID (a user, a level, a private message...)
 * (고유 ID(사용자, 수준, 개인 메시지...)를 가진 지오메트리 대시에서 모든 항목 표시)
 */
public interface GDEntity {
	/**
	 * The unique identifier for the entity.
	 * (독립체에 대한 고유 식별자.)
	 * 
	 * @return an ID
	 * (ID로 반환한다.)
	 */
	long getId();
}
