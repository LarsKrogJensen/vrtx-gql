package graphql.execution

import graphql.ExecutionResult
import graphql.Scalars
import graphql.language.Field
import graphql.schema.GraphQLList
import graphql.schema.GraphQLObjectType
import spock.lang.Specification

import java.util.concurrent.CompletableFuture

class ExecutionStrategySpec extends Specification {

    ExecutionStrategy executionStrategy

    def setup() {
        executionStrategy = new ExecutionStrategy() {
            @Override
            CompletableFuture<ExecutionResult> execute(ExecutionContext executionContext, GraphQLObjectType parentType, Object source, Map<String, List<Field>> fields) {
                CompletableFuture<ExecutionResult> promise = new CompletableFuture<>()
                promise.complete(null)
                return promise
            }
        }
    }

    def "completes value for a java.util.List"() {
        given:
        ExecutionContext executionContext = new ExecutionContext();
        Field field = new Field()
        def fieldType = new GraphQLList(Scalars.GraphQLString)
        def result = Arrays.asList("test")
        when:
        def resultPromise = executionStrategy.completeValue(executionContext, fieldType, [field], result)

        then:
        resultPromise.thenAccept({executionResult -> executionResult.data == ["test"]})
    }


    def "completes value for an array"() {
        given:
        ExecutionContext executionContext = new ExecutionContext();
        Field field = new Field()
        def fieldType = new GraphQLList(Scalars.GraphQLString)
        String[] result = ["test"]
        when:
        def resultPromise = executionStrategy.completeValue(executionContext, fieldType, [field], result)

        then:
        resultPromise.thenAccept({executionResult -> executionResult.data == ["test"]})
    }

}
