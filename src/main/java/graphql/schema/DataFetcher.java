package graphql.schema;


import java.util.concurrent.CompletionStage;

public interface DataFetcher {

    CompletionStage<Object> get(DataFetchingEnvironment environment);
}
