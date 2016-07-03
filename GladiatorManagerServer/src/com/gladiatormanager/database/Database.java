package com.gladiatormanager.database;

public interface Database
{
  // Methods
  public void start();

  public void close();

  public String getPasswordForAccount(String email) throws UnexpectedException, AccountNotFoundException;

  public boolean setPasswordForAccount(String email, String password, String authToken) throws UnexpectedException, AccountNotFoundException;

  public boolean setAuthTokenForAccount(String email, String authToken) throws UnexpectedException, AccountNotFoundException;

  public boolean createAccount(String email, String username, String password, String authToken)
      throws UnexpectedException, EmailAlreadyExistsException, UsernameAlreadyExistsException;

  public boolean setPasswordResetTokenForAccount(String email, String pwResetToken) throws UnexpectedException, AccountNotFoundException;

  public String getPasswordResetTokenForAccount(String email) throws UnexpectedException, AccountNotFoundException;

  // Exceptions
  public static class UnexpectedException extends Exception
  {
    private static final long serialVersionUID = 1L;
    public String error = null;

    public UnexpectedException(String error)
    {
      this.error = error;
    }

    @Override
    public String toString()
    {
      return this.error;
    }
  }

  public static class EmailAlreadyExistsException extends Exception
  {
    private static final long serialVersionUID = 2L;
  }

  public static class UsernameAlreadyExistsException extends Exception
  {
    private static final long serialVersionUID = 3L;
  }

  public static class WrongAuthentificationTokenException extends Exception
  {
    private static final long serialVersionUID = 4L;
  }

  public static class AccountNotFoundException extends Exception
  {
    private static final long serialVersionUID = 5L;
  }
}
