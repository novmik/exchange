package com.alfabank.exchange.response;

import java.util.Map;

/**
 * OpenExchange Objects are returned from most of OpenExchange API's Endpoints.
 */
public class OpenExchangeRatesResponse {

  private String disclaimer;
  private String license;
  private int timestamp;
  private String base;
  private Map<String,Double> rates;

  public Map<String, Double> getRates() {
    return rates;
  }

  public void setRates(Map<String, Double> rates) {
    this.rates = rates;
  }

  public OpenExchangeRatesResponse(String disclaimer, String license, int timestamp,
      String base, Map<String, Double> rates) {
    this.disclaimer = disclaimer;
    this.license = license;
    this.timestamp = timestamp;
    this.base = base;
    this.rates = rates;
  }
}
