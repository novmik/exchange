package com.alfabank.exchange.controller;

import com.alfabank.exchange.exception.InternalServiceException;
import com.alfabank.exchange.exception.InvalidExchangeException;
import com.alfabank.exchange.service.ExchangeRateServiceImpl;
import com.alfabank.exchange.service.GiphyApiServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeRateDifferenceController {

  private final ExchangeRateServiceImpl exchangeRateService;
  private final GiphyApiServiceImpl giphyApiService;

  public ExchangeRateDifferenceController(
      ExchangeRateServiceImpl exchangeRateService,
      GiphyApiServiceImpl giphyApiService) {
    this.exchangeRateService = exchangeRateService;
    this.giphyApiService = giphyApiService;
  }

  @GetMapping("/exchange/{exchange}/gif")
  public ResponseEntity<String> getGifDependingExchangeRate(@PathVariable String exchange)
      throws InvalidExchangeException, InternalServiceException {
    String gifUrl = giphyApiService.getGifUrlByCompareExchange(exchangeRateService.compareExchangeRates(exchange));
    return ResponseEntity.ok(gifUrl);
  }

  @ExceptionHandler(InvalidExchangeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> invalidExchangeExceptionHandler(InvalidExchangeException exc) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(exc.getMessage());
  }

  @ExceptionHandler(InternalServiceException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleInternalServiceException(InternalServiceException exc) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(exc.getMessage());
  }

}
