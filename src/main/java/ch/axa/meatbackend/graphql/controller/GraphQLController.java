package ch.axa.meatbackend.graphql.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.execution.batched.BatchedExecutionStrategy;
import graphql.schema.GraphQLSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GraphQLController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphQLController.class);

    @Autowired
    private GraphQLSchema schema;

    private static final String QUERY_ARG = "query";
    private static final String VARIABLES_ARG = "variables";

    @CrossOrigin(origins = "*")
    @PostMapping("/graphql")
    public ResponseEntity<ExecutionResult> parseGraphQL(@RequestBody String body) throws IOException {
        StopWatch watch = new StopWatch();
        watch.start();

        ExecutionInput eI;
        try {
            eI = createExecutionInput(body);
        } catch (IOException e) {
            LOGGER.error("error parsing graphql request", e);
            watch.stop();
            throw e;
        }

        GraphQL build = GraphQL.newGraphQL(schema).queryExecutionStrategy(new BatchedExecutionStrategy()).build();

        ExecutionResult result = build.execute(eI);
        watch.stop();

        GraphQLResultType resultType = result.getErrors().size() == 0 ? GraphQLResultType.SUCCESS : GraphQLResultType.ERROR;
        if (GraphQLResultType.ERROR.equals(resultType)) {
            LOGGER.error("error using graphql: " + result.getErrors().get(0).getMessage());
        }


        return new ResponseEntity<>(result, resultType.status);
    }


    private ExecutionInput createExecutionInput(String body) throws IOException {
        Map<String, Object> values = new ObjectMapper().readValue(body, HashMap.class);
        if (!values.containsKey(QUERY_ARG)) {
            throw new IllegalArgumentException("no query found in payload!");
        }
        ExecutionInput.Builder builder = ExecutionInput.newExecutionInput().
                query(readQuery(values));
        if (values.containsKey(VARIABLES_ARG) && values.get(VARIABLES_ARG) != null) {
            builder.variables(readVariables(values));
        } else {
            builder.variables(new HashMap<>());
        }
        return builder.build();
    }

    private String readQuery(Map<String, Object> values) {
        return (String) values.get(QUERY_ARG);
    }

    private Map<String, Object> readVariables(Map<String, Object> values) {
        return (Map<String, Object>) values.get(VARIABLES_ARG);
    }


    enum GraphQLResultType {
        SUCCESS(HttpStatus.OK),
        ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

        final HttpStatus status;
        GraphQLResultType(HttpStatus status) {
            this.status = status;
        }
    }

}
