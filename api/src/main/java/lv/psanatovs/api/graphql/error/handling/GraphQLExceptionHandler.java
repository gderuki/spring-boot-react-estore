package lv.psanatovs.api.graphql.error.handling;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import lv.psanatovs.api.exception.ProductNotFoundException;
import lv.psanatovs.api.graphql.error.ProductNotFoundGraphQLError;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof ProductNotFoundException) {
            return new ProductNotFoundGraphQLError(ex.getMessage());
        }
        return super.resolveToSingleError(ex, env);
    }
}