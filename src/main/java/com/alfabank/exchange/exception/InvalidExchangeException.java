package com.alfabank.exchange.exception;

public class InvalidExchangeException extends Exception {

  /**
   * invalid_base Status Code:400 (from openexchangerates.org)
   * @return String
   */
  @Override
  public String getMessage() {
    return "Client requested rates for an unsupported base currency";
  }
}
