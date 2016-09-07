package graphql.schema;


import java.util.concurrent.CompletableFuture;

public interface DataFetcher {

    CompletableFuture<Object> get(DataFetchingEnvironment environment);
}
