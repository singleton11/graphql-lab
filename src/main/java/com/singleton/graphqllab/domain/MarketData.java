package com.singleton.graphqllab.domain;

import java.time.LocalDate;
import lombok.Data;

@Data
public class MarketData {

  private String symbol;
  private String exchange;
  private LocalDate date;
  private String currency;
  private Double latestPrice;
}
