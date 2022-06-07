package com.alfabank.exchange.service;

import com.alfabank.exchange.client.ExchangeRatesApi;
import com.alfabank.exchange.exception.InternalServiceException;
import com.alfabank.exchange.exception.InvalidExchangeException;
import com.alfabank.exchange.response.OpenExchangeRatesResponse;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

  private ExchangeRatesApi exchangeRatesApi;
  private String openExchangeRatesApp_id;
  private String base;

  public ExchangeRateServiceImpl(
      ExchangeRatesApi exchangeRatesApi,
      @Value("${openExchangeRates.app_id}") String openExchangeRatesApp_id,
      @Value("${openExchangeRates.base}") String base
  ) {
    this.exchangeRatesApi = exchangeRatesApi;
    this.openExchangeRatesApp_id = openExchangeRatesApp_id;
    this.base = base;
  }

  @Override
  public OpenExchangeRatesResponse getLatestExchangeRates(String symbols)
      throws InternalServiceException {
    OpenExchangeRatesResponse latestExchangeRate = exchangeRatesApi
        .getLatestExchangeRates(openExchangeRatesApp_id, base, symbols);
    if (latestExchangeRate == null) {
      throw new InternalServiceException("Internal service error, try again later");
    }
    return latestExchangeRate;
  }

  @Override
  public OpenExchangeRatesResponse getHistoricalExchangeRates(LocalDate date, String symbols)
      throws InternalServiceException {
    OpenExchangeRatesResponse historicalExchangeRates = exchangeRatesApi
        .getHistoricalExchangeRates(date.toString(), openExchangeRatesApp_id, base, symbols);
    if (historicalExchangeRates == null) {
      throw new InternalServiceException("Internal service error, try again later");
    }
    return historicalExchangeRates;
  }

  @Override
  public int compareExchangeRates(String symbols)
      throws InvalidExchangeException, InternalServiceException {
    OpenExchangeRatesResponse todayExchangeRate = getLatestExchangeRates(symbols);
    OpenExchangeRatesResponse yesterdayExchangeRate = getHistoricalExchangeRates(LocalDate.now().minusDays(1), symbols);
    if (todayExchangeRate.getRates().get(symbols) == null ||
        yesterdayExchangeRate.getRates().get(symbols) == null) {
      throw new InvalidExchangeException();
    }
    return Double.compare(
        todayExchangeRate.getRates().get(symbols),
        yesterdayExchangeRate.getRates().get(symbols)
    );
  }


}
