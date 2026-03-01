package com.restapi.store.vehicleregistration.config;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.web.multipart.MultipartFile;

@Configuration
public class GraphQlConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(uploadScalar());
    }

    public GraphQLScalarType uploadScalar() {
        return GraphQLScalarType.newScalar()
                .name("Upload")
                .description("A file part in a multipart request")
                .coercing(new Coercing<MultipartFile, Void>() {
                    @Override
                    public Void serialize(Object dataFetcherResult) {
                        throw new CoercingSerializeException("Upload is an input-only type");
                    }

                    @Override
                    public MultipartFile parseValue(Object input) {
                        if (input instanceof MultipartFile file) {
                            return file;
                        }
                        throw new CoercingParseValueException("Expected type MultipartFile but was " + input.getClass());
                    }

                    @Override
                    public MultipartFile parseLiteral(Object input) {
                        throw new CoercingParseLiteralException("Must use variables to specify Upload values");
                    }
                })
                .build();
    }
}