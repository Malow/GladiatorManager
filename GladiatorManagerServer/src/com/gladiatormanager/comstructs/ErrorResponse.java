package com.gladiatormanager.comstructs;

public class ErrorResponse implements Response
{
  public boolean result;
  public String error;

  public ErrorResponse(boolean result, String error)
  {
    this.result = result;
    this.error = error;
  }
}
