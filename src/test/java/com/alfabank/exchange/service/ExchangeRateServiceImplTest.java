package com.alfabank.exchange.service;

import com.alfabank.exchange.client.ExchangeRatesApi;
import com.alfabank.exchange.exception.InternalServiceException;
import com.alfabank.exchange.exception.InvalidExchangeException;
import com.alfabank.exchange.response.OpenExchangeRatesResponse;
import java.time.LocalDate;
import java.util.HashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExchangeRateServiceImplTest {

  public static final OpenExchangeRatesResponse EXCHANGE_HIGH =
      new OpenExchangeRatesResponse("disclaimer", "license", 111, "USD", new HashMap<>());
  public static final OpenExchangeRatesResponse EXCHANGE_LOW =
      new OpenExchangeRatesResponse("disclaimer2", "license2", 222, "USD", new HashMap<>());

  static {
    EXCHANGE_HIGH.getRates().put("RUB", 11.1);
    EXCHANGE_LOW.getRates().put("RUB", 1.1);
  }

  @Mock
  private ExchangeRatesApi exchangeRatesApi;
  @InjectMocks
  private ExchangeRateServiceImpl underTest;

  @BeforeEach
  void setUp() {
    Mockito.when(exchangeRatesApi.getLatestExchangeRates(
        Mockito.any(),
        Mockito.any(),
        Mockito.eq("SYMBOLS"))).thenReturn(null);
    Mockito.when(exchangeRatesApi.getLatestExchangeRates(
        Mockito.any(),
        Mockito.any(),
        Mockito.eq("RUB"))).thenReturn(EXCHANGE_HIGH);
    Mockito.when(exchangeRatesApi.getHistoricalExchangeRates(
        Mockito.eq(LocalDate.now().minusDays(1).toString()),
        Mockito.any(),
        Mockito.any(),
        Mockito.eq("RUB"))).thenReturn(EXCHANGE_LOW);
    Mockito.when(exchangeRatesApi.getHistoricalExchangeRates(
        Mockito.eq("1999-01-01"),
        Mockito.any(),
        Mockito.any(),
        Mockito.eq("RUB"))).thenReturn(null);
  }

  @Test
  void canGetLatestExchangeRates() throws InternalServiceException {
    Assertions.assertThat(underTest.getLatestExchangeRates("RUB"))
        .isEqualTo(EXCHANGE_HIGH);
  }

  @Test
  void willThrowWhenGetLatestExchangeRatesWithWrongSymbols() {
    Assertions.assertThatThrownBy(
        () -> underTest.getLatestExchangeRates("SYMBOLS"))
        .isInstanceOf(InternalServiceException.class);
  }

  @Test
  void canGetHistoricalExchangeRates() throws InternalServiceException {
    Assertions.assertThat(underTest.getHistoricalExchangeRates(LocalDate.now().minusDays(1), "RUB"))
        .isEqualTo(EXCHANGE_LOW);
  }

  @Test
  void willThrowWhenGetHistoricalExchangeRatesWithWrongDate() {
    Assertions.assertThatThrownBy(
        () -> underTest.getHistoricalExchangeRates(LocalDate.of(1999,1,1), "RUB"))
        .isInstanceOf(InternalServiceException.class);
  }

  @Test
  void compareExchangeRates() throws InvalidExchangeException, InternalServiceException {
    Assertions.assertThat(underTest.compareExchangeRates("RUB"))
        .isEqualTo(1);
  }
}