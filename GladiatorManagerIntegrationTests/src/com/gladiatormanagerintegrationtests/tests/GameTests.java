package com.gladiatormanagerintegrationtests.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gladiatormanager.Globals;
import com.gladiatormanager.comstructs.Response;
import com.gladiatormanager.comstructs.game.GetMercenariesResponse;
import com.gladiatormanager.game.Mercenary;
import com.gladiatormanagerintegrationtests.JsonRequests;
import com.gladiatormanagerintegrationtests.ServerConnection;
import com.gladiatormanagerintegrationtests.TestHelpers;
import com.google.gson.Gson;

public class GameTests
{
  private static final String TEST_PASSWORD = "testerpw";
  private static final String TEST_USERNAME = "tester";
  private static final String TEST_EMAIL = "tester@test.com";
  private static final String TEST_TEAM_NAME = "TestTeamName";

  @Test
  public void getMercenariesTest() throws Exception
  {
    TestHelpers.resetDatabase();
    String authToken = TestHelpers.registerAccount(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD).authToken;
    TestHelpers.setTeamName(TEST_EMAIL, authToken, TEST_TEAM_NAME);

    GetMercenariesResponse response = TestHelpers.getMercenaries(TEST_EMAIL, authToken);

    assertEquals(true, response.result);
    assertEquals(10, response.mercenaries.size());
  }

  @Test
  public void chooseInitialMercenariesTest() throws Exception
  {
    TestHelpers.resetDatabase();
    String authToken = TestHelpers.registerAccount(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD).authToken;
    TestHelpers.setTeamName(TEST_EMAIL, authToken, TEST_TEAM_NAME);
    List<Mercenary> mercenaries = TestHelpers.getMercenaries(TEST_EMAIL, authToken).mercenaries;
    List<Mercenary> mercenariesKept = new ArrayList<Mercenary>();
    List<Integer> mercenariesIds = new ArrayList<Integer>();
    for (int i = 0; i < Globals.Config.STARTER_MERCENARIES_TO_BE_CHOSEN; i++)
    {
      mercenariesIds.add(mercenaries.get(i).id);
      mercenariesKept.add(mercenaries.get(i));
    }

    String request = JsonRequests.chooseInitialMercenaries(TEST_EMAIL, authToken, mercenariesIds);
    Response response = new Gson().fromJson(ServerConnection.sendMessage("/chooseinitialmercenaries", request), Response.class);

    assertEquals(true, response.result);
    List<Mercenary> mercenariesLeft = TestHelpers.getMercenaries(TEST_EMAIL, authToken).mercenaries;
    assertEquals(mercenariesLeft, mercenariesKept);
  }
}
