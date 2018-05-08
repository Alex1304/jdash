package com.alex1304dev.jdash.api;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.alex1304dev.jdash.api.request.GDLevelHttpRequest;
import com.alex1304dev.jdash.api.request.GDLevelSearchHttpRequest;
import com.alex1304dev.jdash.api.request.GDMessageListHttpRequest;
import com.alex1304dev.jdash.api.request.GDMessageReadHttpRequest;
import com.alex1304dev.jdash.api.request.GDMessageSendHttpRequest;
import com.alex1304dev.jdash.api.request.GDUserHttpRequest;
import com.alex1304dev.jdash.api.request.GDUserSearchHttpRequest;
import com.alex1304dev.jdash.component.GDBoolean;
import com.alex1304dev.jdash.component.GDComponentList;
import com.alex1304dev.jdash.component.GDLevel;
import com.alex1304dev.jdash.component.GDLevelPreview;
import com.alex1304dev.jdash.component.GDMessage;
import com.alex1304dev.jdash.component.GDUser;
import com.alex1304dev.jdash.component.GDUserPreview;

public class GDHttpClientTest {
	
	private GDHttpClient client, clientAuth;
	private static long accountID;
	private static String password;
	
	@BeforeClass
	public static void setupBeforeClass() throws Exception {
		Scanner sc = new Scanner(System.in);
		
		do {
			try {
				System.out.print("Geometry Dash account ID: ");
				accountID = Long.parseLong(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input, try again.");
			}
		} while (accountID < 0);
		
		
		System.out.print("Geometry Dash password: ");
		password = sc.nextLine();
		sc.close();
	}

	@Before
	public void setUp() throws Exception {
		this.client = new GDHttpClient();
		this.clientAuth = new GDHttpClient(accountID, password);
		System.out.println("\n------------------------------\n");
	}

	@Test
	public void test_level_fetch() throws Exception {
		GDLevelHttpRequest levelReq = new GDLevelHttpRequest(38693063);
		GDLevel level = client.fetch(levelReq);
		assertNotNull(level);
		System.out.println(level);
	}
	
	public void test_level_caseLevelNotFound_fetch() throws Exception {
		GDLevelHttpRequest levelReq = new GDLevelHttpRequest(0);
		assertNull(client.fetch(levelReq));
	}
	
	@Test
	public void test_levelSearch_fetch() throws Exception {
		GDLevelSearchHttpRequest lvlSearchReq = new GDLevelSearchHttpRequest("sonic wave", 0);
		GDComponentList<GDLevelPreview> lvlList = client.fetch(lvlSearchReq);
		assertNotNull(lvlList);
		
		System.out.println(lvlList);
	}

	@Test
	public void test_user_fetch() throws Exception {
		GDUserHttpRequest req = new GDUserHttpRequest(2795);
		GDUser user = client.fetch(req);
		assertNotNull(user);
		System.out.println(user);
	}
	
	@Test
	public void test_userSearch_fetch() throws Exception {
		GDUserSearchHttpRequest userSearchReq = new GDUserSearchHttpRequest("viprin", 0);
		GDComponentList<GDUserPreview> userList = client.fetch(userSearchReq);
		assertNotNull(userList);
		
		System.out.println(userList);
	}
	
	@Test
	public void test_messageList_fetch() throws Exception {
		GDMessageListHttpRequest messageListReq = new GDMessageListHttpRequest(0);
		GDComponentList<GDMessage> messageList = clientAuth.fetch(messageListReq);
		assertNotNull(messageList);
		
		System.out.println(messageList);
	}
	
	@Test
	public void test_messageRead_fetch() throws Exception {
		GDMessageReadHttpRequest messageReadReq = new GDMessageReadHttpRequest(35557342);
		GDMessage message = clientAuth.fetch(messageReadReq);
		assertNotNull(message);
		
		System.out.println(message);
	}
	
	@Test
	public void test_messageSend_fetch() throws Exception {
		GDMessageSendHttpRequest req = new GDMessageSendHttpRequest(98006, "Test", "This is a test message.");
		GDBoolean bool = clientAuth.fetch(req);
		assertNotNull(bool);
		assertTrue(bool.isSuccess());
	}

}
