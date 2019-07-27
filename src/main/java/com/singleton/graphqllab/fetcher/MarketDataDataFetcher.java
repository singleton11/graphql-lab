package com.singleton.graphqllab.fetcher;

import com.singleton.graphqllab.client.IexTradingClient;
import com.singleton.graphqllab.domain.MarketData;
import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MarketDataDataFetcher {

  @Value("${iex.apikey}")
  private String apiKey;

  private final IexTradingClient client;

  public DataFetcher<MarketData> get() {
    return environment -> {
      final String symbol = environment.getArgument("symbol");
      return client.get(symbol, apiKey);
    };
  }
}
