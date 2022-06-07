package com.alfabank.exchange.controller;

import com.alfabank.exchange.exception.InternalServiceException;
import com.alfabank.exchange.exception.InvalidExchangeException;
import com.alfabank.exchange.service.ExchangeRateServiceImpl;
import com.alfabank.exchange.service.GiphyApiServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ExchangeRateDifferenceController.class)
@ExtendWith(SpringExtension.class)
class ExchangeRateDifferenceControllerTest {

  @MockBean
  private ExchangeRateServiceImpl exchangeRateService;
  @MockBean
  GiphyApiServiceImpl giphyApiService;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() throws InvalidExchangeException, InternalServiceException {
    Mockito.when(exchangeRateService.compareExchangeRates("RUB")).thenReturn(1);
    Mockito.when(exchangeRateService.compareExchangeRates("AAA"))
        .thenThrow(InvalidExchangeException.class);
    Mockito.when(exchangeRateService.compareExchangeRates("BBB"))
        .thenThrow(InternalServiceException.class);
    Mockito.when(giphyApiService.getGifUrlByCompareExchange(1)).thenReturn("TEST URL");

  }

  @Test
  void getGifDependingExchangeRate() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/exchange/" + "RUB" + "/gif")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("TEST URL"));
  }

  @Test
  void willThrowWhenGetGifDependingExchangeRateWithWrongSymbols() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/exchange/" + "AAA" + "/gif")
            .accept(MediaType.ALL))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string("Client requested rates for an unsupported base currency"));
  }

  @Test
  void willThrowWhenGetGifDependingExchangeRateWithWrong() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/exchange/" + "BBB" + "/gif")
            .accept(MediaType.ALL))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string(""));
  }
}