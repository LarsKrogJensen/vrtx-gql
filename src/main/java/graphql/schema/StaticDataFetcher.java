package graphql.schema;


import java.util.concurrent.CompletableFuture;

public class StaticDataFetcher implements DataFetcher {


    private final Object value;

    public StaticDataFetcher(Object value) {
        this.value = value;
    }

    @Override
    public CompletableFuture<Object> get(DataFetchingEnvironment environment) {
        CompletableFuture<Object> promise = new CompletableFuture<>();
        promise.complete(value);
        return promise;
    }
}
