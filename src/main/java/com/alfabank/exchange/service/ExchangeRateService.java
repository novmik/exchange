package com.alfabank.exchange.service;

import com.alfabank.exchange.exception.InternalServiceException;
import com.alfabank.exchange.exception.InvalidExchangeException;
import com.alfabank.exchange.response.OpenExchangeRatesResponse;
import java.time.LocalDate;

public interface ExchangeRateService {

  /**
   *
   * @param symbols Limit results to specific currencies
   *                (comma-separated list of 3-letter codes)
   * @return {@link OpenExchangeRatesResponse}
   * @throws {@link InvalidExchangeException}
   * @throws {@link InternalServiceException}
   */
  OpenExchangeRatesResponse getLatestExchangeRates(String symbols) throws InvalidExchangeException, InternalServiceException;

  /**
   *
   * @param date The requested date
   * @param symbols Limit results to specific currencies
   *              (comma-separated list of 3-letter codes)
   * @return {@link OpenExchangeRatesResponse}
   * @throws {@link InvalidExchangeException}
   * @throws {@link InternalServiceException}
   */
  OpenExchangeRatesResponse getHistoricalExchangeRates(LocalDate date, String symbols) throws InvalidExchangeException, InternalServiceException;

  /**
   * Сравнение курса валюты сегодня с предыдущим днём к USD.
   *
   * @param symbols валюта
   * @return 0, если курс валюты не изменился;
   * Negative value, если курс опустился;
   * Positive value, если курс поднялся;
   * @throws {@link InvalidExchangeException}
   * @throws {@link InternalServiceException}
   */
  int compareExchangeRates(String symbols) throws InvalidExchangeException, InternalServiceException;

}
