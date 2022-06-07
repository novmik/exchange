package com.alfabank.exchange.service;

import com.alfabank.exchange.client.GiphyApi;
import com.alfabank.exchange.exception.InternalServiceException;
import com.alfabank.exchange.response.GifObjectResponse;
import com.alfabank.exchange.response.GifObjectResponse.GifObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GiphyApiServiceImpl implements GiphyApiService {

  private final GiphyApi giphyApi;
  private final String api_key;
  private final String moreTag;
  private final String lessTag;

  public GiphyApiServiceImpl(
      GiphyApi giphyApi,
      @Value("${giphy.api_key}")
          String api_key,
      @Value("${giphy.more-tag}")
          String moreTag,
      @Value("${giphy.less-tag}")
          String lessTag) {
    this.giphyApi = giphyApi;
    this.api_key = api_key;
    this.moreTag = moreTag;
    this.lessTag = lessTag;
  }

  @Override
  public GifObjectResponse getRandomGifObjectResponseByTag(String tag) throws InternalServiceException {
    GifObjectResponse gifObject = giphyApi.getRandomGifByTag(api_key, tag);
    if (gifObject == null) {
      throw new InternalServiceException("Internal service error, try again later");
    }
    return gifObject;
  }

  @Override
  public GifObject getRandomGifObjectByCompareExchange(int differenceExchange)
      throws InternalServiceException {
    GifObject gifObject;
    if (differenceExchange > 0) {
      gifObject = getRandomGifObjectResponseByTag(moreTag).getData();
    } else if (differenceExchange < 0) {
      gifObject = getRandomGifObjectResponseByTag(lessTag).getData();
    } else  {
      throw new InternalServiceException("Курс валюты не изменился.");
    }
    return gifObject;
  }

  @Override
  public String getGifUrlByCompareExchange(int differenceExchange) throws InternalServiceException {
    return getRandomGifObjectByCompareExchange(differenceExchange).getUrl();
  }
}
