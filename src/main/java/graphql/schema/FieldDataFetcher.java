package graphql.schema;


import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Fetches data directly from a field.
 */
public class FieldDataFetcher implements DataFetcher {

    /**
     * The name of the field.
     */
    private final String fieldName;

    /**
     * Ctor.
     *
     * @param fieldName The name of the field.
     */
    public FieldDataFetcher(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public CompletableFuture<Object> get(DataFetchingEnvironment environment) {
        CompletableFuture<Object> promise = new CompletableFuture<>();

        Object source = environment.getSource();
        if (source == null) return null;
        if (source instanceof Map) {
            promise.complete(((Map<?, ?>) source).get(fieldName));
            return promise;
        }
        promise.complete(getFieldValue(source, environment.getFieldType()));
        return promise;
    }

    /**
     * Uses introspection to get the field value.
     *
     * @param object     The object being acted on.
     * @param outputType The output type; ignored in this case.
     * @return An object, or null.
     */
    private Object getFieldValue(Object object, GraphQLOutputType outputType) {
        try {
            Field field = object.getClass().getField(fieldName);
            return field.get(object);
        } catch (NoSuchFieldException e) {
            return null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
