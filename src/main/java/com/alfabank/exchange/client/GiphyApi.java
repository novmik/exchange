package com.alfabank.exchange.client;

import com.alfabank.exchange.response.GifObjectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Giphy", url = "${giphy.url}")
public interface GiphyApi {

  /**
   * GIPHY Random returning a single random GIF
   * related to the word or phrase entered.
   *
   * @param api_key GIPHY API Key
   * @param tag Filters results by specified tag
   * @return {@link GifObjectResponse}
   */
  @GetMapping("/random?api_key={api_key}&tag={tag}")
  GifObjectResponse getRandomGifByTag(
      @PathVariable("api_key") String api_key,
      @PathVariable("tag") String tag
  );

}
