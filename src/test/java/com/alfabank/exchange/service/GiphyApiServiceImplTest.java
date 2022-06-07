package com.alfabank.exchange.service;

import com.alfabank.exchange.client.GiphyApi;
import com.alfabank.exchange.exception.InternalServiceException;
import com.alfabank.exchange.response.GifObjectResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GiphyApiServiceImplTest {

  private static final GifObjectResponse TEST_MORE_TAG = new GifObjectResponse();
  private static final GifObjectResponse TEST_LESS_TAG = new GifObjectResponse();

  @Mock
  private GiphyApi giphyApi;
  @InjectMocks
  private GiphyApiServiceImpl underTest;

  @BeforeEach
  void setUp() {
    Mockito.when(giphyApi.getRandomGifByTag(Mockito.any(), Mockito.eq("TEST_MORE_TAG")))
        .thenReturn(TEST_MORE_TAG);
    Mockito.when(giphyApi.getRandomGifByTag(Mockito.any(), Mockito.eq("TEST_LESS_TAG")))
        .thenReturn(TEST_LESS_TAG);
  }

  @Test
  void getRandomGifObjectResponseByTag() throws InternalServiceException {
    Assertions.assertThat(underTest.getRandomGifObjectResponseByTag("TEST_MORE_TAG"))
        .isEqualTo(TEST_MORE_TAG);
    Assertions.assertThat(underTest.getRandomGifObjectResponseByTag("TEST_LESS_TAG"))
        .isEqualTo(TEST_LESS_TAG);
  }

  @Test
  void willThrowWhenGetRandomGifObjectResponseByTagWithEquals() throws InternalServiceException {
    Assertions.assertThatThrownBy(
        () -> underTest.getGifUrlByCompareExchange(0))
            .isInstanceOf(InternalServiceException.class)
                .hasMessage("Курс валюты не изменился.");
  }
}