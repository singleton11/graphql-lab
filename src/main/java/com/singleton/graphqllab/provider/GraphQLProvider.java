package com.singleton.graphqllab.provider;


import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.singleton.graphqllab.fetcher.MarketDataDataFetcher;
import graphql.GraphQL;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import java.io.IOException;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GraphQLProvider {

  @Value("classpath:schema.graphqls")
  private Resource resource;

  private GraphQL graphQL;

  private final MarketDataDataFetcher marketDataDataFetcher;

  @Bean
  public GraphQL graphQL() {
    return graphQL;
  }

  @PostConstruct
  public void init() throws IOException {
    final var url = resource.getURL();
    final var sdl = Resources.toString(url, Charsets.UTF_8);
    final var graphQLSchema = buildSchema(sdl);
    graphQL = GraphQL.newGraphQL(graphQLSchema).build();
  }

  private GraphQLSchema buildSchema(final String sdl) {
    final var typeRegistry = new SchemaParser().parse(sdl);
    final var runtimeWiring = buildWiring();
    final var schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
  }

  private RuntimeWiring buildWiring() {
    return RuntimeWiring
        .newRuntimeWiring()
        .scalar(ExtendedScalars.Date)
        .type(
            newTypeWiring("Query")
                .dataFetcher("marketDataBySymbol", marketDataDataFetcher.get())
        )
        .build();
  }
}
