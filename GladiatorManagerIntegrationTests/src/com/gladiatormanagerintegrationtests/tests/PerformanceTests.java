package com.gladiatormanagerintegrationtests.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.gladiatormanager.comstructs.account.LoginResponse;
import com.gladiatormanagerintegrationtests.TestHelpers;

public class PerformanceTests
{
  private static final int THREAD_COUNT = 10;
  private static final int REQUESTS_PER_THREAD = 10;

  @Before
  public void setup() throws Exception
  {
    TestHelpers.resetDatabaseTable("mercenaries");
    TestHelpers.resetDatabaseTable("accounts");
  }

  public static class RegisterAndLoginRunner implements Runnable
  {
    private int id;

    public RegisterAndLoginRunner(int id)
    {
      this.id = id;
    }

    public void run()
    {
      try
      {
        for (int i = 0; i < REQUESTS_PER_THREAD; i++)
        {
          LoginResponse registerResponse = TestHelpers.registerAccount(this.id + "-" + i + "@test.com", this.id + "-" + i, this.id + "-" + i);
          assertEquals(true, registerResponse.result);
          assertEquals(true, TestHelpers.isValidToken(registerResponse.authToken));

          LoginResponse loginResponse = TestHelpers.login(this.id + "-" + i + "@test.com", this.id + "-" + i);
          assertEquals(true, loginResponse.result);
          assertEquals(true, TestHelpers.isValidToken(loginResponse.authToken));

        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }

  }

  @Test
  public void registerAndLoginPerformanceTest() throws Exception
  {
    ArrayList<RegisterAndLoginRunner> runners = new ArrayList<RegisterAndLoginRunner>();
    ArrayList<Thread> threads = new ArrayList<Thread>();
    for (int i = 0; i < THREAD_COUNT; i++)
    {
      RegisterAndLoginRunner r = new RegisterAndLoginRunner(i);
      Thread t = new Thread(r);
      runners.add(r);
      threads.add(t);
    }
    for (int i = 0; i < THREAD_COUNT; i++)
    {
      threads.get(i).start();
    }
    for (int i = 0; i < THREAD_COUNT; i++)
    {
      threads.get(i).join();
    }
  }
}
