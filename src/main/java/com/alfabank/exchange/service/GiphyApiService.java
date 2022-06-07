package com.alfabank.exchange.service;

import com.alfabank.exchange.exception.InternalServiceException;
import com.alfabank.exchange.response.GifObjectResponse;
import com.alfabank.exchange.response.GifObjectResponse.GifObject;

public interface GiphyApiService {

  /**
   *
   * @param tag Filters results by specified tag
   * @return {@link GifObjectResponse}
   * @throws InternalServiceException
   */
  GifObjectResponse getRandomGifObjectResponseByTag(String tag) throws InternalServiceException;

  /**
   *
   * @param differenceExchange сравнение курса волюты
   * @return {@link GifObject}
   * @throws InternalServiceException
   */
  GifObject getRandomGifObjectByCompareExchange(int differenceExchange) throws InternalServiceException;

  String getGifUrlByCompareExchange(int differenceExchange) throws InternalServiceException;

}
