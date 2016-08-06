package com.gladiatormanagerintegrationtests.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gladiatormanager.comstructs.game.GetMercenariesResponse;
import com.gladiatormanagerintegrationtests.JsonRequests;
import com.gladiatormanagerintegrationtests.ServerConnection;
import com.gladiatormanagerintegrationtests.TestHelpers;
import com.google.gson.Gson;

public class GameTests
{
  private static final String TEST_PASSWORD = "testerpw";
  private static final String TEST_USERNAME = "tester";
  private static final String TEST_EMAIL = "tester@test.com";

  @Test
  public void getMercenariesTest() throws Exception
  {
    TestHelpers.resetDatabase();
    String authToken = TestHelpers.registerAccount(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD).authToken;
    String request = JsonRequests.getMercenaries(TEST_EMAIL, authToken);
    GetMercenariesResponse response = new Gson().fromJson(ServerConnection.sendMessage("/getmercenaries", request), GetMercenariesResponse.class);

    assertEquals(true, response.result);
    assertEquals(0, response.mercenaries.size());
  }
}
