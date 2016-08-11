package com.gladiatormanagerintegrationtests.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.gladiatormanager.comstructs.Response;
import com.gladiatormanager.comstructs.account.LoginResponse;
import com.gladiatormanagerintegrationtests.JsonRequests;
import com.gladiatormanagerintegrationtests.ServerConnection;
import com.gladiatormanagerintegrationtests.TestHelpers;
import com.google.gson.Gson;

public class AccountTests
{
  private static final String TEST_PASSWORD = "testerpw";
  private static final String TEST_USERNAME = "tester";
  private static final String TEST_EMAIL = "tester@test.com";
  private static final String TEST_TEAM_NAME = "TestTeamName";

  private Response sendPasswordResetToken(String email) throws Exception
  {
    String request = JsonRequests.sendPasswordReset(email);
    return new Gson().fromJson(ServerConnection.sendMessage("/sendpwresettoken", request), Response.class);
  }

  @Before
  public void resetDatabase() throws Exception
  {
    TestHelpers.resetDatabase();
  }

  @Test
  public void registerTest() throws Exception
  {
    LoginResponse response = TestHelpers.registerAccount(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD);

    assertEquals(true, response.result);
    assertEquals(true, TestHelpers.isValidToken(response.authToken));
  }

  @Test
  public void loginTest() throws Exception
  {
    TestHelpers.registerAccount(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD);
    String request = JsonRequests.login(TEST_EMAIL, TEST_PASSWORD);

    LoginResponse response = new Gson().fromJson(ServerConnection.sendMessage("/login", request), LoginResponse.class);

    assertEquals(true, response.result);
    assertEquals(true, TestHelpers.isValidToken(response.authToken));
  }

  @Test
  public void sendPasswordResetTokenTest() throws Exception
  {
    TestHelpers.registerAccount(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD);

    Response response = sendPasswordResetToken(TEST_EMAIL);

    assertEquals(true, response.result);
    assertEquals(true, TestHelpers.isValidToken(TestHelpers.getPasswordResetToken(TEST_EMAIL)));
  }

  @Test
  public void resetPasswordTest() throws Exception
  {
    TestHelpers.registerAccount(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD);
    sendPasswordResetToken(TEST_EMAIL);

    String pwResetToken = TestHelpers.getPasswordResetToken(TEST_EMAIL);
    String request = JsonRequests.resetPassword(TEST_EMAIL, TEST_PASSWORD, pwResetToken);
    LoginResponse response = new Gson().fromJson(ServerConnection.sendMessage("/resetpw", request), LoginResponse.class);

    assertEquals(true, response.result);
    assertEquals(true, TestHelpers.isValidToken(response.authToken));
  }

  @Test
  public void setTeamNameTest() throws Exception
  {
    LoginResponse loginResponse = TestHelpers.registerAccount(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD);

    Response response = TestHelpers.setTeamName(TEST_EMAIL, loginResponse.authToken, TEST_TEAM_NAME);

    assertEquals(true, response.result);
  }
}
