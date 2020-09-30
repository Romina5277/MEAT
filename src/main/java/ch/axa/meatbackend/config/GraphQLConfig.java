package ch.axa.meatbackend.config;

import ch.axa.meatbackend.graphql.resolver.Mutation;
import ch.axa.meatbackend.graphql.resolver.Query;
import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQLConfig {

    @Autowired
    private Query queryResolver;

    @Autowired
    private Mutation mutationResolver;

    @Bean
    public GraphQLSchema schema() {
        return SchemaParser
                .newParser()
                .file("graphql/meat.graphqls")
                .resolvers(
                        queryResolver,
                        mutationResolver
                )
                .build()
                .makeExecutableSchema();
    }

}
