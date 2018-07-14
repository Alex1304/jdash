package com.github.alex1304.jdash.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.alex1304.jdash.api.request.GDLevelHttpRequest;
import com.github.alex1304.jdash.api.request.GDLevelSearchHttpRequest;
import com.github.alex1304.jdash.api.request.GDMessageListHttpRequest;
import com.github.alex1304.jdash.api.request.GDMessageReadHttpRequest;
import com.github.alex1304.jdash.api.request.GDMessageSendHttpRequest;
import com.github.alex1304.jdash.api.request.GDSongInfoHttpRequest;
import com.github.alex1304.jdash.api.request.GDTimelyLevelHttpRequest;
import com.github.alex1304.jdash.api.request.GDUserHttpRequest;
import com.github.alex1304.jdash.api.request.GDUserSearchHttpRequest;
import com.github.alex1304.jdash.component.GDBoolean;
import com.github.alex1304.jdash.component.GDComponentList;
import com.github.alex1304.jdash.component.GDLevel;
import com.github.alex1304.jdash.component.GDLevelPreview;
import com.github.alex1304.jdash.component.GDMessage;
import com.github.alex1304.jdash.component.GDSong;
import com.github.alex1304.jdash.component.GDTimelyLevel;
import com.github.alex1304.jdash.component.GDUser;
import com.github.alex1304.jdash.component.GDUserPreview;

public class GDHttpClientTest {
	
	private GDHttpClient client, clientAuth;
	private static long accountID = -1;
	private static String password = null;
	private static boolean skipTests = false;
	
	@BeforeClass
	public static void setupBeforeClass() throws Exception {
		if (System.getenv().containsKey("JDASH_SKIP_TESTS")) {
			skipTests = true;
			System.out.println("JDASH_SKIP_TESTS environment variable is present. Skipping tests.");
		}
		
		if (System.getenv().containsKey("JDASH_TEST_ACCOUNT_ID")) {
			try {
				accountID = Long.parseLong(System.getenv().get("JDASH_TEST_ACCOUNT_ID"));
			} catch (NumberFormatException e) {
				accountID = -1;
			}
		}
		
		if (System.getenv().containsKey("JDASH_TEST_ACCOUNT_PASSWORD"))
			password = System.getenv().get("JDASH_TEST_ACCOUNT_PASSWORD");
	}

	@Before
	public void setUp() throws Exception {
		if (skipTests)
			return;
		
		this.client = new GDHttpClient();
		this.clientAuth = new GDHttpClient(accountID, password);
		System.out.println("\n------------------------------\n");
	}

	@Test
	public void test_level_fetch() throws Exception {
		if (skipTests)
			return;
		
		GDLevelHttpRequest levelReq = new GDLevelHttpRequest(38693063);
		GDLevel level = client.fetch(levelReq);
		assertNotNull(level);
		System.out.println(level);
	}
	
	@Test
	public void test_level_caseLevelNotFound_fetch() throws Exception {
		if (skipTests)
			return;
		
		GDLevelHttpRequest levelReq = new GDLevelHttpRequest(0);
		assertNull(client.fetch(levelReq));
	}
	
	@Test
	public void test_levelSearch_sonicwave_fetch() throws Exception {
		if (skipTests)
			return;
		
		GDLevelSearchHttpRequest lvlSearchReq = new GDLevelSearchHttpRequest("sonic wave", 0);
		GDComponentList<GDLevelPreview> lvlList = client.fetch(lvlSearchReq);
		assertNotNull(lvlList);
		
		System.out.println(lvlList);
	}
	
	@Test
	public void test_levelSearch_autoplayarea_fetch() throws Exception {
		if (skipTests)
			return;
		
		GDLevelSearchHttpRequest lvlSearchReq = new GDLevelSearchHttpRequest("auto play area", 0);
		GDComponentList<GDLevelPreview> lvlList = client.fetch(lvlSearchReq);
		assertNotNull(lvlList);
		
		System.out.println(lvlList);
	}

	@Test
	public void test_user_fetch() throws Exception {
		if (skipTests)
			return;
		
		GDUserHttpRequest req = new GDUserHttpRequest(2795);
		GDUser user = client.fetch(req);
		assertNotNull(user);
		System.out.println(user);
	}
	
	@Test
	public void test_userSearch_fetch() throws Exception {
		if (skipTests)
			return;
		
		GDUserSearchHttpRequest userSearchReq = new GDUserSearchHttpRequest("viprin", 0);
		GDComponentList<GDUserPreview> userList = client.fetch(userSearchReq);
		assertNotNull(userList);
		
		System.out.println(userList);
	}
	
	@Test
	public void test_userSearch_greenUser_fetch() throws Exception {
		if (skipTests)
			return;
		
		GDUserSearchHttpRequest userSearchReq = new GDUserSearchHttpRequest("f3lixram", 0);
		GDComponentList<GDUserPreview> userList = client.fetch(userSearchReq);
		assertNotNull(userList);
		
		System.out.println(userList);
	}
	
	@Test
	public void test_messageList_fetch() throws Exception {
		if (skipTests)
			return;
		
		if (accountID == -1 || password == null)
			return;
		GDMessageListHttpRequest messageListReq = new GDMessageListHttpRequest(0);
		GDComponentList<GDMessage> messageList = clientAuth.fetch(messageListReq);
		assertNotNull(messageList);
		
		System.out.println(messageList);
	}
	
	@Test
	public void test_messageRead_fetch() throws Exception {
		if (skipTests)
			return;
		
		if (accountID == -1 || password == null)
			return;
		GDMessageReadHttpRequest messageReadReq = new GDMessageReadHttpRequest(37201512);
		GDMessage message = clientAuth.fetch(messageReadReq);
		assertNotNull(message);
		
		System.out.println(message);
	}
	
	@Test
	public void test_messageSend_fetch() throws Exception {
		if (skipTests)
			return;
		
		if (accountID == -1 || password == null)
			return;
		GDMessageSendHttpRequest req = new GDMessageSendHttpRequest(98006, "Test", "This is a test message.");
		GDBoolean bool = clientAuth.fetch(req);
		assertNotNull(bool);
		assertTrue(bool.isSuccess());
	}

	@Test
	public void test_songInfo_fetch() throws Exception {
		if (skipTests)
			return;
		
		GDSongInfoHttpRequest req = new GDSongInfoHttpRequest(467339);
		
		GDSong song = client.fetch(req);
		
		assertNotNull(song);
		assertEquals("Dimrain47", song.getSongAuthorName());
		assertTrue(song.isCustom());
		
		System.out.println(song);
	}
	
	@Test
	public void test_timelyLevel_fetch() throws Exception {
		if (skipTests)
			return;
		
		GDTimelyLevelHttpRequest req = new GDTimelyLevelHttpRequest(false, client);
		
		GDTimelyLevel tl = client.fetch(req);
		
		assertNotNull(tl);
		System.out.println(tl);
	}

}
