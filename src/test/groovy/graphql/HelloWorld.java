package graphql;


import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;
import static org.junit.Assert.assertEquals;

public class HelloWorld {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        GraphQLObjectType queryType = newObject()
                .name("helloWorldQuery")
                .field(newFieldDefinition()
                        .type(GraphQLString)
                        .name("hello")
                        .staticValue("world"))
                .build();

        GraphQLSchema schema = GraphQLSchema.newSchema()
                .query(queryType)
                .build();
        Map<String, Object> result = (Map<String, Object>)new GraphQL(schema).execute("{hello}").get().getData();
        System.out.println(result);
    }

    @Test
    public void helloWorldTest() throws ExecutionException, InterruptedException {
        GraphQLObjectType queryType = newObject()
                .name("helloWorldQuery")
                .field(newFieldDefinition()
                        .type(GraphQLString)
                        .name("hello")
                        .staticValue("world"))
                .build();

        GraphQLSchema schema = GraphQLSchema.newSchema()
                .query(queryType)
                .build();
        Map<String, Object> result = (Map<String, Object>)new GraphQL(schema).execute("{hello}").get().getData();
        assertEquals("world", result.get("hello"));
    }
}
