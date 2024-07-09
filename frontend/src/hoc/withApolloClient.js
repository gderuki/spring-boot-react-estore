// Node modules
import React from 'react';
import { useApolloClient } from '@apollo/client';

export function withApolloClient(WrappedComponent) {
  return function ApolloClientInjector(props) {
    const client = useApolloClient();
    return <WrappedComponent {...props} apolloClient={client} />;
  };
}