package lv.psanatovs.api.graphql.error;

import graphql.GraphQLError;
import graphql.language.SourceLocation;
import org.springframework.graphql.execution.ErrorType;

import java.util.List;
import java.util.Map;

public record ProductNotFoundGraphQLError(String message) implements GraphQLError {

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null; // exclude from response
    }

    @Override
    public Map<String, Object> getExtensions() {
        return null; // exclude from response
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.NOT_FOUND;
    }
}