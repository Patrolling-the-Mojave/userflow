package com.hse.userflow.gateway.exception;

public class GateWayException extends RuntimeException {
  public GateWayException(String message, Throwable cause) {
    super(message, cause);
  }

  public GateWayException(String message){
    super(message);
  }
}
