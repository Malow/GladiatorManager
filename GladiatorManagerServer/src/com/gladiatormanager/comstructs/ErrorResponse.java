package com.gladiatormanager.comstructs;

public class ErrorResponse extends Response
{
  public String error;

  public ErrorResponse(boolean result, String error)
  {
    super(result);
    this.error = error;
  }
}
