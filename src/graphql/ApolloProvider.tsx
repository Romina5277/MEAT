import React, { ReactNode, ReactElement} from "react";
import { ApolloProvider } from '@apollo/react-hooks';
import ApolloClient from 'apollo-client';
import { InMemoryCache, NormalizedCacheObject } from 'apollo-cache-inmemory';
import { HttpLink } from 'apollo-link-http';

function initClient(): ApolloClient<NormalizedCacheObject> {
    const uri = 'http://localhost:8080/graphql';
    const httpLink = new HttpLink({
        uri,
        headers: {},
    });

    return new ApolloClient({
        link: httpLink,
        cache: new InMemoryCache(),
    });
}

interface Props {
    children: ReactNode;
}

function ApolloProviderContainer({ children }: Props): ReactElement {
    const client = initClient();
    return <ApolloProvider client={client}>{children}</ApolloProvider>;
}

export { ApolloProviderContainer };