package com.alfabank.exchange.client;

import com.alfabank.exchange.response.OpenExchangeRatesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "OpenExchangeRates", url = "${openExchangeRates.url}")
public interface ExchangeRatesApi {

  /**
   * Get the latest exchange rates
   * available from the Open Exchange Rates API.
   *
   * https://openexchangerates.org/api/latest.json
   *
   * @param app_id unique App ID
   * @param base base currency (3-letter code, default: USD)
   * @param symbols Limit results to specific currencies
   *               (comma-separated list of 3-letter codes)
   * @return {@link OpenExchangeRatesResponse}
   */
  @GetMapping("/latest.json?app_id={app_id}&base={base}&symbols={symbols}")
  OpenExchangeRatesResponse getLatestExchangeRates(
      @PathVariable("app_id") String app_id,
      @PathVariable("base") String base,
      @PathVariable("symbols") String symbols
  );

  /**
   * Get historical exchange rates for
   * any date available from the Open Exchange Rates API.
   *
   * @param date The requested date in YYYY-MM-DD format (required)
   * @param app_id unique App ID
   * @param base base currency (3-letter code, default: USD)
   * @param symbols Limit results to specific currencies
   *               (comma-separated list of 3-letter codes)
   * @return {@link OpenExchangeRatesResponse}
   */
  @GetMapping("/historical/{date}.json?app_id={app_id}&base={base}&symbols={symbols}")
  OpenExchangeRatesResponse getHistoricalExchangeRates(
      @PathVariable("date") String date,
      @PathVariable("app_id") String app_id,
      @PathVariable("base") String base,
      @PathVariable("symbols") String symbols
  );

}
