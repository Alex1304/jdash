package com.alex1304dev.jdash.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.alex1304dev.jdash.api.request.GDLevelHttpRequest;
import com.alex1304dev.jdash.api.request.GDLevelSearchHttpRequest;
import com.alex1304dev.jdash.api.request.GDUserHttpRequest;
import com.alex1304dev.jdash.component.GDComponentList;
import com.alex1304dev.jdash.component.GDLevel;
import com.alex1304dev.jdash.component.GDLevelPreview;
import com.alex1304dev.jdash.component.GDUser;

public class GDHttpClientTest {
	
	private GDHttpClient client;

	@Before
	public void setUp() throws Exception {
		this.client = new GDHttpClient();
	}

	@Test
	public void test_level_fetch() throws Exception {
		GDLevelHttpRequest levelReq = new GDLevelHttpRequest(38693063);
		GDHttpResponse<GDLevel> result = client.fetch(levelReq);
		
		System.out.println(result.getStatusCode());
		assertEquals(200, result.getStatusCode());
		
		GDLevel level = result.getResponse();
		assertNotNull(level);
		System.out.println(level);
	}
	
	@Test
	public void test_level_caseLevelNotFound_fetch() throws Exception {
		GDLevelHttpRequest levelReq = new GDLevelHttpRequest(0);
		GDHttpResponse<GDLevel> result = client.fetch(levelReq);
		
		// GD servers always return status code 200 even if level not found.
		System.out.println(result.getStatusCode());
		assertEquals(200, result.getStatusCode());
		
		GDLevel level = result.getResponse();
		assertNull(level);
		System.out.println(level);
	}
	
	@Test
	public void test_levelSearch_fetch() throws Exception {
		GDLevelSearchHttpRequest lvlSearchReq = new GDLevelSearchHttpRequest("sonic wave");
		GDHttpResponse<GDComponentList<GDLevelPreview>> result = client.fetch(lvlSearchReq);
		
		System.out.println(result.getStatusCode());
		assertEquals(200, result.getStatusCode());
		
		GDComponentList<GDLevelPreview> lvlList = result.getResponse();
		assertNotNull(lvlList);
		
		System.out.println(lvlList);
	}

	@Test
	public void test_user_fetch() throws Exception {
		GDUserHttpRequest req = new GDUserHttpRequest(2795);
		GDHttpResponse<GDUser> result = client.fetch(req);
		
		System.out.println(result.getStatusCode());
		assertEquals(200, result.getStatusCode());
		
		GDUser user = result.getResponse();
		assertNotNull(user);
		System.out.println(user);
	}

}
