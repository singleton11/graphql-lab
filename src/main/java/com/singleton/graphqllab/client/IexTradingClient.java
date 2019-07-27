package com.singleton.graphqllab.client;

import com.singleton.graphqllab.domain.MarketData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "https://cloud.iexapis.com/stable", name = "marketData")
public interface IexTradingClient {

  @GetMapping("/stock/{symbol}/quote")
  MarketData get(@PathVariable("symbol") final String symbol,
                 @RequestParam("token") final String token);
}
