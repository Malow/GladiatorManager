package com.gladiatormanager.database;

public interface Database
{
  // Methods
  public void start();

  public void close();

  public String loginAndGetAuthToken(String email, String password) throws UnexpectedException, WrongPasswordException;

  public String registerAndGetAuthToken(String email, String username, String password)
      throws UnexpectedException, EmailAlreadyExistsException, UsernameAlreadyExistsException;

  // Exceptions
  public static class UnexpectedException extends Exception
  {
    private static final long serialVersionUID = 1L;
    public String error = null;

    public UnexpectedException(String error)
    {
      this.error = error;
    }
  }

  public static class WrongPasswordException extends Exception
  {
    private static final long serialVersionUID = 2L;
  }

  public static class EmailAlreadyExistsException extends Exception
  {
    private static final long serialVersionUID = 3L;
  }

  public static class UsernameAlreadyExistsException extends Exception
  {
    private static final long serialVersionUID = 4L;
  }

  public static class WrongAuthentificationTokenException extends Exception
  {
    private static final long serialVersionUID = 5L;
  }
}
