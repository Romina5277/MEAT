import gql from 'graphql-tag';
import * as ApolloReactCommon from '@apollo/react-common';
import * as ApolloReactHooks from '@apollo/react-hooks';
export type Maybe<T> = T | null;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: string;
  String: string;
  Boolean: boolean;
  Int: number;
  Float: number;
};

export type Application = {
  id: Scalars['String'];
  code: Scalars['String'];
  name: Scalars['String'];
  locations: Array<Location>;
  tickers: Array<Ticker>;
  templates: Array<Ticker>;
};

export type ApplicationInput = {
  code: Scalars['String'];
  name: Scalars['String'];
};

export type CreateLocationInput = {
  name: Scalars['String'];
  locationnumber: Scalars['Int'];
};

export type CreateMessageInput = {
  text: Scalars['String'];
  title?: Maybe<Scalars['String']>;
  language: Scalars['String'];
};

export type CreateTickerInput = {
  title: Scalars['String'];
  timeFrom: Scalars['String'];
  timeTo: Scalars['String'];
  locationnumbers: Array<Scalars['Int']>;
  messages: Array<CreateMessageInput>;
};

export type EditApplicationInput = {
  id: Scalars['String'];
  code: Scalars['String'];
  name: Scalars['String'];
};

export type EditLocationInput = {
  id: Scalars['String'];
  name: Scalars['String'];
  locationnumber: Scalars['Int'];
};

export type EditMessageInput = {
  id: Scalars['String'];
  text: Scalars['String'];
  language: Scalars['String'];
  title: Scalars['String'];
};

export type EditTickerInput = {
  id: Scalars['String'];
  title: Scalars['String'];
  timeFrom: Scalars['String'];
  timeTo: Scalars['String'];
  messages: Array<EditMessageInput>;
  locationnumbers: Array<Scalars['Int']>;
};

export type Location = {
  id: Scalars['String'];
  name: Scalars['String'];
  locationnumber: Scalars['Int'];
};

export type Message = {
  id: Scalars['String'];
  title: Scalars['String'];
  text: Scalars['String'];
  language: Scalars['String'];
};

export type Mutation = {
  createApplication?: Maybe<Application>;
  editApplication?: Maybe<Application>;
  deleteApplication?: Maybe<Scalars['Int']>;
  createLocation?: Maybe<Location>;
  editLocation?: Maybe<Location>;
  deleteLocation?: Maybe<Scalars['Boolean']>;
  createTicker?: Maybe<Ticker>;
  editTicker?: Maybe<Ticker>;
  deleteTicker?: Maybe<Scalars['Boolean']>;
  createTemplate?: Maybe<Ticker>;
  editTemplate?: Maybe<Ticker>;
  deleteTemplate?: Maybe<Scalars['Boolean']>;
};


export type MutationCreateApplicationArgs = {
  input: ApplicationInput;
};


export type MutationEditApplicationArgs = {
  input: EditApplicationInput;
};


export type MutationDeleteApplicationArgs = {
  applicationId: Scalars['String'];
};


export type MutationCreateLocationArgs = {
  applicationId: Scalars['String'];
  input: CreateLocationInput;
};


export type MutationEditLocationArgs = {
  applicationId: Scalars['String'];
  input: EditLocationInput;
};


export type MutationDeleteLocationArgs = {
  applicationId: Scalars['String'];
  locationId: Scalars['String'];
};


export type MutationCreateTickerArgs = {
  applicationId: Scalars['String'];
  input: CreateTickerInput;
};


export type MutationEditTickerArgs = {
  applicationId: Scalars['String'];
  input: EditTickerInput;
};


export type MutationDeleteTickerArgs = {
  applicationId: Scalars['String'];
  tickerId: Scalars['String'];
};


export type MutationCreateTemplateArgs = {
  applicationId: Scalars['String'];
  input: CreateTickerInput;
};


export type MutationEditTemplateArgs = {
  applicationId: Scalars['String'];
  input: EditTickerInput;
};


export type MutationDeleteTemplateArgs = {
  applicationId: Scalars['String'];
  templateId: Scalars['String'];
};

export type Query = {
  applications: Array<Application>;
  application?: Maybe<Application>;
  location?: Maybe<Location>;
  ticker?: Maybe<Ticker>;
  template?: Maybe<Ticker>;
  message?: Maybe<Message>;
};


export type QueryApplicationArgs = {
  id: Scalars['String'];
};


export type QueryLocationArgs = {
  applicationId: Scalars['String'];
  locationId: Scalars['String'];
};


export type QueryTickerArgs = {
  applicationId: Scalars['String'];
  tickerId: Scalars['String'];
};


export type QueryTemplateArgs = {
  applicationId: Scalars['String'];
  templateId: Scalars['String'];
};


export type QueryMessageArgs = {
  applicationId: Scalars['String'];
  tickerId: Scalars['String'];
  messageId: Scalars['String'];
};

export type Template = {
  id: Scalars['String'];
  title: Scalars['String'];
  timeFrom: Scalars['String'];
  timeTo: Scalars['String'];
  locationnumbers: Array<Scalars['Int']>;
  messages: Array<Message>;
};

export type Ticker = {
  id: Scalars['String'];
  title: Scalars['String'];
  timeFrom: Scalars['String'];
  timeTo: Scalars['String'];
  locationnumbers: Array<Scalars['Int']>;
  messages: Array<Message>;
};

export type SaveTemplateDataFragment = (
  Pick<Ticker, 'id' | 'title' | 'timeFrom' | 'timeTo' | 'locationnumbers'>
  & { messages: Array<Pick<Message, 'id' | 'title' | 'text' | 'language'>> }
);

export type SaveTickerFragmentFragment = (
  Pick<Ticker, 'id' | 'title' | 'timeFrom' | 'timeTo' | 'locationnumbers'>
  & { messages: Array<Pick<Message, 'id' | 'title' | 'text' | 'language'>> }
);

export type ApplicationDataFragment = (
  Pick<Application, 'id' | 'code' | 'name'>
  & { locations: Array<Pick<Location, 'name' | 'locationnumber'>>, tickers: Array<(
    Pick<Ticker, 'id' | 'title' | 'timeFrom' | 'timeTo'>
    & { messages: Array<Pick<Message, 'id' | 'text' | 'language'>> }
  )>, templates: Array<(
    Pick<Ticker, 'id' | 'title' | 'timeFrom' | 'timeTo'>
    & { messages: Array<Pick<Message, 'id' | 'text' | 'language'>> }
  )> }
);

export type LocationDataFragment = Pick<Location, 'name' | 'locationnumber'>;

export type MessageDataFragment = Pick<Message, 'id' | 'title' | 'text' | 'language'>;

export type SaveApplicationFragmentFragment = Pick<Application, 'code' | 'name'>;

export type TemplateDataFragment = (
  Pick<Ticker, 'id' | 'title' | 'locationnumbers' | 'timeFrom' | 'timeTo'>
  & { messages: Array<Pick<Message, 'id' | 'title' | 'text' | 'language'>> }
);

export type TickerDataFragment = (
  Pick<Ticker, 'id' | 'title' | 'timeFrom' | 'timeTo' | 'locationnumbers'>
  & { messages: Array<Pick<Message, 'id' | 'title' | 'text' | 'language'>> }
);

export type CreateApplicationMutationVariables = Exact<{
  input: ApplicationInput;
}>;


export type CreateApplicationMutation = { createApplication?: Maybe<SaveApplicationFragmentFragment> };

export type DeleteApplicationMutationVariables = Exact<{
  applicationId: Scalars['String'];
}>;


export type DeleteApplicationMutation = Pick<Mutation, 'deleteApplication'>;

export type EditApplicationMutationVariables = Exact<{
  input: EditApplicationInput;
}>;


export type EditApplicationMutation = { editApplication?: Maybe<SaveApplicationFragmentFragment> };

export type CreateLocationMutationVariables = Exact<{
  applicationId: Scalars['String'];
  input: CreateLocationInput;
}>;


export type CreateLocationMutation = { createLocation?: Maybe<LocationDataFragment> };

export type DeleteLocationMutationVariables = Exact<{
  applicationId: Scalars['String'];
  locationId: Scalars['String'];
}>;


export type DeleteLocationMutation = Pick<Mutation, 'deleteLocation'>;

export type EditLocationMutationVariables = Exact<{
  applicationId: Scalars['String'];
  input: EditLocationInput;
}>;


export type EditLocationMutation = { editLocation?: Maybe<LocationDataFragment> };

export type CreateTemplateMutationVariables = Exact<{
  applicationId: Scalars['String'];
  input: CreateTickerInput;
}>;


export type CreateTemplateMutation = { createTemplate?: Maybe<SaveTemplateDataFragment> };

export type DeleteTemplateMutationVariables = Exact<{
  applicationId: Scalars['String'];
  templateId: Scalars['String'];
}>;


export type DeleteTemplateMutation = Pick<Mutation, 'deleteTemplate'>;

export type EditTemplateMutationVariables = Exact<{
  applicationId: Scalars['String'];
  input: EditTickerInput;
}>;


export type EditTemplateMutation = { editTemplate?: Maybe<SaveTemplateDataFragment> };

export type CreateTickerMutationVariables = Exact<{
  applicationId: Scalars['String'];
  input: CreateTickerInput;
}>;


export type CreateTickerMutation = { createTicker?: Maybe<SaveTickerFragmentFragment> };

export type DeleteTickerMutationVariables = Exact<{
  applicationId: Scalars['String'];
  tickerId: Scalars['String'];
}>;


export type DeleteTickerMutation = Pick<Mutation, 'deleteTicker'>;

export type EditTickerMutationVariables = Exact<{
  applicationId: Scalars['String'];
  input: EditTickerInput;
}>;


export type EditTickerMutation = { editTicker?: Maybe<SaveTickerFragmentFragment> };

export type ApplicationQueryVariables = Exact<{
  id: Scalars['String'];
}>;


export type ApplicationQuery = { application?: Maybe<ApplicationDataFragment> };

export type ApplicationsQueryVariables = Exact<{ [key: string]: never; }>;


export type ApplicationsQuery = { applications: Array<ApplicationDataFragment> };

export type LocationQueryVariables = Exact<{
  applicationId: Scalars['String'];
  locationId: Scalars['String'];
}>;


export type LocationQuery = { location?: Maybe<LocationDataFragment> };

export type MessageQueryVariables = Exact<{
  applicationId: Scalars['String'];
  tickerId: Scalars['String'];
  messageId: Scalars['String'];
}>;


export type MessageQuery = { message?: Maybe<MessageDataFragment> };

export type TemplateQueryVariables = Exact<{
  applicationId: Scalars['String'];
  templateId: Scalars['String'];
}>;


export type TemplateQuery = { template?: Maybe<TemplateDataFragment> };

export type TickerQueryVariables = Exact<{
  applicationId: Scalars['String'];
  tickerId: Scalars['String'];
}>;


export type TickerQuery = { ticker?: Maybe<TickerDataFragment> };

export const SaveTemplateDataFragmentDoc = gql`
    fragment SaveTemplateData on Ticker {
  id
  title
  timeFrom
  timeTo
  locationnumbers
  messages {
    id
    title
    text
    language
  }
}
    `;
export const SaveTickerFragmentFragmentDoc = gql`
    fragment SaveTickerFragment on Ticker {
  id
  title
  timeFrom
  timeTo
  locationnumbers
  messages {
    id
    title
    text
    language
  }
}
    `;
export const ApplicationDataFragmentDoc = gql`
    fragment ApplicationData on Application {
  id
  code
  name
  locations {
    name
    locationnumber
  }
  tickers {
    id
    title
    timeFrom
    timeTo
    messages {
      id
      text
      language
    }
  }
  templates {
    id
    title
    timeFrom
    timeTo
    messages {
      id
      text
      language
    }
  }
}
    `;
export const LocationDataFragmentDoc = gql`
    fragment LocationData on Location {
  name
  locationnumber
}
    `;
export const MessageDataFragmentDoc = gql`
    fragment MessageData on Message {
  id
  title
  text
  language
}
    `;
export const SaveApplicationFragmentFragmentDoc = gql`
    fragment SaveApplicationFragment on Application {
  code
  name
}
    `;
export const TemplateDataFragmentDoc = gql`
    fragment TemplateData on Ticker {
  id
  title
  locationnumbers
  timeFrom
  timeTo
  messages {
    id
    title
    text
    language
  }
}
    `;
export const TickerDataFragmentDoc = gql`
    fragment TickerData on Ticker {
  id
  title
  timeFrom
  timeTo
  locationnumbers
  messages {
    id
    title
    text
    language
  }
}
    `;
export const CreateApplicationDocument = gql`
    mutation CreateApplication($input: ApplicationInput!) {
  createApplication(input: $input) {
    ...SaveApplicationFragment
  }
}
    ${SaveApplicationFragmentFragmentDoc}`;
export type CreateApplicationMutationFn = ApolloReactCommon.MutationFunction<CreateApplicationMutation, CreateApplicationMutationVariables>;

/**
 * __useCreateApplicationMutation__
 *
 * To run a mutation, you first call `useCreateApplicationMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useCreateApplicationMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [createApplicationMutation, { data, loading, error }] = useCreateApplicationMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useCreateApplicationMutation(baseOptions?: ApolloReactHooks.MutationHookOptions<CreateApplicationMutation, CreateApplicationMutationVariables>) {
        return ApolloReactHooks.useMutation<CreateApplicationMutation, CreateApplicationMutationVariables>(CreateApplicationDocument, baseOptions);
      }
export type CreateApplicationMutationHookResult = ReturnType<typeof useCreateApplicationMutation>;
export type CreateApplicationMutationResult = ApolloReactCommon.MutationResult<CreateApplicationMutation>;
export type CreateApplicationMutationOptions = ApolloReactCommon.BaseMutationOptions<CreateApplicationMutation, CreateApplicationMutationVariables>;
export const DeleteApplicationDocument = gql`
    mutation DeleteApplication($applicationId: String!) {
  deleteApplication(applicationId: $applicationId)
}
    `;
export type DeleteApplicationMutationFn = ApolloReactCommon.MutationFunction<DeleteApplicationMutation, DeleteApplicationMutationVariables>;

/**
 * __useDeleteApplicationMutation__
 *
 * To run a mutation, you first call `useDeleteApplicationMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useDeleteApplicationMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [deleteApplicationMutation, { data, loading, error }] = useDeleteApplicationMutation({
 *   variables: {
 *      applicationId: // value for 'applicationId'
 *   },
 * });
 */
export function useDeleteApplicationMutation(baseOptions?: ApolloReactHooks.MutationHookOptions<DeleteApplicationMutation, DeleteApplicationMutationVariables>) {
        return ApolloReactHooks.useMutation<DeleteApplicationMutation, DeleteApplicationMutationVariables>(DeleteApplicationDocument, baseOptions);
      }
export type DeleteApplicationMutationHookResult = ReturnType<typeof useDeleteApplicationMutation>;
export type DeleteApplicationMutationResult = ApolloReactCommon.MutationResult<DeleteApplicationMutation>;
export type DeleteApplicationMutationOptions = ApolloReactCommon.BaseMutationOptions<DeleteApplicationMutation, DeleteApplicationMutationVariables>;
export const EditApplicationDocument = gql`
    mutation EditApplication($input: EditApplicationInput!) {
  editApplication(input: $input) {
    ...SaveApplicationFragment
  }
}
    ${SaveApplicationFragmentFragmentDoc}`;
export type EditApplicationMutationFn = ApolloReactCommon.MutationFunction<EditApplicationMutation, EditApplicationMutationVariables>;

/**
 * __useEditApplicationMutation__
 *
 * To run a mutation, you first call `useEditApplicationMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useEditApplicationMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [editApplicationMutation, { data, loading, error }] = useEditApplicationMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useEditApplicationMutation(baseOptions?: ApolloReactHooks.MutationHookOptions<EditApplicationMutation, EditApplicationMutationVariables>) {
        return ApolloReactHooks.useMutation<EditApplicationMutation, EditApplicationMutationVariables>(EditApplicationDocument, baseOptions);
      }
export type EditApplicationMutationHookResult = ReturnType<typeof useEditApplicationMutation>;
export type EditApplicationMutationResult = ApolloReactCommon.MutationResult<EditApplicationMutation>;
export type EditApplicationMutationOptions = ApolloReactCommon.BaseMutationOptions<EditApplicationMutation, EditApplicationMutationVariables>;
export const CreateLocationDocument = gql`
    mutation CreateLocation($applicationId: String!, $input: CreateLocationInput!) {
  createLocation(applicationId: $applicationId, input: $input) {
    ...LocationData
  }
}
    ${LocationDataFragmentDoc}`;
export type CreateLocationMutationFn = ApolloReactCommon.MutationFunction<CreateLocationMutation, CreateLocationMutationVariables>;

/**
 * __useCreateLocationMutation__
 *
 * To run a mutation, you first call `useCreateLocationMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useCreateLocationMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [createLocationMutation, { data, loading, error }] = useCreateLocationMutation({
 *   variables: {
 *      applicationId: // value for 'applicationId'
 *      input: // value for 'input'
 *   },
 * });
 */
export function useCreateLocationMutation(baseOptions?: ApolloReactHooks.MutationHookOptions<CreateLocationMutation, CreateLocationMutationVariables>) {
        return ApolloReactHooks.useMutation<CreateLocationMutation, CreateLocationMutationVariables>(CreateLocationDocument, baseOptions);
      }
export type CreateLocationMutationHookResult = ReturnType<typeof useCreateLocationMutation>;
export type CreateLocationMutationResult = ApolloReactCommon.MutationResult<CreateLocationMutation>;
export type CreateLocationMutationOptions = ApolloReactCommon.BaseMutationOptions<CreateLocationMutation, CreateLocationMutationVariables>;
export const DeleteLocationDocument = gql`
    mutation DeleteLocation($applicationId: String!, $locationId: String!) {
  deleteLocation(applicationId: $applicationId, locationId: $locationId)
}
    `;
export type DeleteLocationMutationFn = ApolloReactCommon.MutationFunction<DeleteLocationMutation, DeleteLocationMutationVariables>;

/**
 * __useDeleteLocationMutation__
 *
 * To run a mutation, you first call `useDeleteLocationMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useDeleteLocationMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [deleteLocationMutation, { data, loading, error }] = useDeleteLocationMutation({
 *   variables: {
 *      applicationId: // value for 'applicationId'
 *      locationId: // value for 'locationId'
 *   },
 * });
 */
export function useDeleteLocationMutation(baseOptions?: ApolloReactHooks.MutationHookOptions<DeleteLocationMutation, DeleteLocationMutationVariables>) {
        return ApolloReactHooks.useMutation<DeleteLocationMutation, DeleteLocationMutationVariables>(DeleteLocationDocument, baseOptions);
      }
export type DeleteLocationMutationHookResult = ReturnType<typeof useDeleteLocationMutation>;
export type DeleteLocationMutationResult = ApolloReactCommon.MutationResult<DeleteLocationMutation>;
export type DeleteLocationMutationOptions = ApolloReactCommon.BaseMutationOptions<DeleteLocationMutation, DeleteLocationMutationVariables>;
export const EditLocationDocument = gql`
    mutation EditLocation($applicationId: String!, $input: EditLocationInput!) {
  editLocation(applicationId: $applicationId, input: $input) {
    ...LocationData
  }
}
    ${LocationDataFragmentDoc}`;
export type EditLocationMutationFn = ApolloReactCommon.MutationFunction<EditLocationMutation, EditLocationMutationVariables>;

/**
 * __useEditLocationMutation__
 *
 * To run a mutation, you first call `useEditLocationMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useEditLocationMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [editLocationMutation, { data, loading, error }] = useEditLocationMutation({
 *   variables: {
 *      applicationId: // value for 'applicationId'
 *      input: // value for 'input'
 *   },
 * });
 */
export function useEditLocationMutation(baseOptions?: ApolloReactHooks.MutationHookOptions<EditLocationMutation, EditLocationMutationVariables>) {
        return ApolloReactHooks.useMutation<EditLocationMutation, EditLocationMutationVariables>(EditLocationDocument, baseOptions);
      }
export type EditLocationMutationHookResult = ReturnType<typeof useEditLocationMutation>;
export type EditLocationMutationResult = ApolloReactCommon.MutationResult<EditLocationMutation>;
export type EditLocationMutationOptions = ApolloReactCommon.BaseMutationOptions<EditLocationMutation, EditLocationMutationVariables>;
export const CreateTemplateDocument = gql`
    mutation CreateTemplate($applicationId: String!, $input: CreateTickerInput!) {
  createTemplate(applicationId: $applicationId, input: $input) {
    ...SaveTemplateData
  }
}
    ${SaveTemplateDataFragmentDoc}`;
export type CreateTemplateMutationFn = ApolloReactCommon.MutationFunction<CreateTemplateMutation, CreateTemplateMutationVariables>;

/**
 * __useCreateTemplateMutation__
 *
 * To run a mutation, you first call `useCreateTemplateMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useCreateTemplateMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [createTemplateMutation, { data, loading, error }] = useCreateTemplateMutation({
 *   variables: {
 *      applicationId: // value for 'applicationId'
 *      input: // value for 'input'
 *   },
 * });
 */
export function useCreateTemplateMutation(baseOptions?: ApolloReactHooks.MutationHookOptions<CreateTemplateMutation, CreateTemplateMutationVariables>) {
        return ApolloReactHooks.useMutation<CreateTemplateMutation, CreateTemplateMutationVariables>(CreateTemplateDocument, baseOptions);
      }
export type CreateTemplateMutationHookResult = ReturnType<typeof useCreateTemplateMutation>;
export type CreateTemplateMutationResult = ApolloReactCommon.MutationResult<CreateTemplateMutation>;
export type CreateTemplateMutationOptions = ApolloReactCommon.BaseMutationOptions<CreateTemplateMutation, CreateTemplateMutationVariables>;
export const DeleteTemplateDocument = gql`
    mutation DeleteTemplate($applicationId: String!, $templateId: String!) {
  deleteTemplate(applicationId: $applicationId, templateId: $templateId)
}
    `;
export type DeleteTemplateMutationFn = ApolloReactCommon.MutationFunction<DeleteTemplateMutation, DeleteTemplateMutationVariables>;

/**
 * __useDeleteTemplateMutation__
 *
 * To run a mutation, you first call `useDeleteTemplateMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useDeleteTemplateMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [deleteTemplateMutation, { data, loading, error }] = useDeleteTemplateMutation({
 *   variables: {
 *      applicationId: // value for 'applicationId'
 *      templateId: // value for 'templateId'
 *   },
 * });
 */
export function useDeleteTemplateMutation(baseOptions?: ApolloReactHooks.MutationHookOptions<DeleteTemplateMutation, DeleteTemplateMutationVariables>) {
        return ApolloReactHooks.useMutation<DeleteTemplateMutation, DeleteTemplateMutationVariables>(DeleteTemplateDocument, baseOptions);
      }
export type DeleteTemplateMutationHookResult = ReturnType<typeof useDeleteTemplateMutation>;
export type DeleteTemplateMutationResult = ApolloReactCommon.MutationResult<DeleteTemplateMutation>;
export type DeleteTemplateMutationOptions = ApolloReactCommon.BaseMutationOptions<DeleteTemplateMutation, DeleteTemplateMutationVariables>;
export const EditTemplateDocument = gql`
    mutation EditTemplate($applicationId: String!, $input: EditTickerInput!) {
  editTemplate(applicationId: $applicationId, input: $input) {
    ...SaveTemplateData
  }
}
    ${SaveTemplateDataFragmentDoc}`;
export type EditTemplateMutationFn = ApolloReactCommon.MutationFunction<EditTemplateMutation, EditTemplateMutationVariables>;

/**
 * __useEditTemplateMutation__
 *
 * To run a mutation, you first call `useEditTemplateMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useEditTemplateMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [editTemplateMutation, { data, loading, error }] = useEditTemplateMutation({
 *   variables: {
 *      applicationId: // value for 'applicationId'
 *      input: // value for 'input'
 *   },
 * });
 */
export function useEditTemplateMutation(baseOptions?: ApolloReactHooks.MutationHookOptions<EditTemplateMutation, EditTemplateMutationVariables>) {
        return ApolloReactHooks.useMutation<EditTemplateMutation, EditTemplateMutationVariables>(EditTemplateDocument, baseOptions);
      }
export type EditTemplateMutationHookResult = ReturnType<typeof useEditTemplateMutation>;
export type EditTemplateMutationResult = ApolloReactCommon.MutationResult<EditTemplateMutation>;
export type EditTemplateMutationOptions = ApolloReactCommon.BaseMutationOptions<EditTemplateMutation, EditTemplateMutationVariables>;
export const CreateTickerDocument = gql`
    mutation CreateTicker($applicationId: String!, $input: CreateTickerInput!) {
  createTicker(applicationId: $applicationId, input: $input) {
    ...SaveTickerFragment
  }
}
    ${SaveTickerFragmentFragmentDoc}`;
export type CreateTickerMutationFn = ApolloReactCommon.MutationFunction<CreateTickerMutation, CreateTickerMutationVariables>;

/**
 * __useCreateTickerMutation__
 *
 * To run a mutation, you first call `useCreateTickerMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useCreateTickerMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [createTickerMutation, { data, loading, error }] = useCreateTickerMutation({
 *   variables: {
 *      applicationId: // value for 'applicationId'
 *      input: // value for 'input'
 *   },
 * });
 */
export function useCreateTickerMutation(baseOptions?: ApolloReactHooks.MutationHookOptions<CreateTickerMutation, CreateTickerMutationVariables>) {
        return ApolloReactHooks.useMutation<CreateTickerMutation, CreateTickerMutationVariables>(CreateTickerDocument, baseOptions);
      }
export type CreateTickerMutationHookResult = ReturnType<typeof useCreateTickerMutation>;
export type CreateTickerMutationResult = ApolloReactCommon.MutationResult<CreateTickerMutation>;
export type CreateTickerMutationOptions = ApolloReactCommon.BaseMutationOptions<CreateTickerMutation, CreateTickerMutationVariables>;
export const DeleteTickerDocument = gql`
    mutation DeleteTicker($applicationId: String!, $tickerId: String!) {
  deleteTicker(applicationId: $applicationId, tickerId: $tickerId)
}
    `;
export type DeleteTickerMutationFn = ApolloReactCommon.MutationFunction<DeleteTickerMutation, DeleteTickerMutationVariables>;

/**
 * __useDeleteTickerMutation__
 *
 * To run a mutation, you first call `useDeleteTickerMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useDeleteTickerMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [deleteTickerMutation, { data, loading, error }] = useDeleteTickerMutation({
 *   variables: {
 *      applicationId: // value for 'applicationId'
 *      tickerId: // value for 'tickerId'
 *   },
 * });
 */
export function useDeleteTickerMutation(baseOptions?: ApolloReactHooks.MutationHookOptions<DeleteTickerMutation, DeleteTickerMutationVariables>) {
        return ApolloReactHooks.useMutation<DeleteTickerMutation, DeleteTickerMutationVariables>(DeleteTickerDocument, baseOptions);
      }
export type DeleteTickerMutationHookResult = ReturnType<typeof useDeleteTickerMutation>;
export type DeleteTickerMutationResult = ApolloReactCommon.MutationResult<DeleteTickerMutation>;
export type DeleteTickerMutationOptions = ApolloReactCommon.BaseMutationOptions<DeleteTickerMutation, DeleteTickerMutationVariables>;
export const EditTickerDocument = gql`
    mutation EditTicker($applicationId: String!, $input: EditTickerInput!) {
  editTicker(applicationId: $applicationId, input: $input) {
    ...SaveTickerFragment
  }
}
    ${SaveTickerFragmentFragmentDoc}`;
export type EditTickerMutationFn = ApolloReactCommon.MutationFunction<EditTickerMutation, EditTickerMutationVariables>;

/**
 * __useEditTickerMutation__
 *
 * To run a mutation, you first call `useEditTickerMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useEditTickerMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [editTickerMutation, { data, loading, error }] = useEditTickerMutation({
 *   variables: {
 *      applicationId: // value for 'applicationId'
 *      input: // value for 'input'
 *   },
 * });
 */
export function useEditTickerMutation(baseOptions?: ApolloReactHooks.MutationHookOptions<EditTickerMutation, EditTickerMutationVariables>) {
        return ApolloReactHooks.useMutation<EditTickerMutation, EditTickerMutationVariables>(EditTickerDocument, baseOptions);
      }
export type EditTickerMutationHookResult = ReturnType<typeof useEditTickerMutation>;
export type EditTickerMutationResult = ApolloReactCommon.MutationResult<EditTickerMutation>;
export type EditTickerMutationOptions = ApolloReactCommon.BaseMutationOptions<EditTickerMutation, EditTickerMutationVariables>;
export const ApplicationDocument = gql`
    query Application($id: String!) {
  application(id: $id) {
    ...ApplicationData
  }
}
    ${ApplicationDataFragmentDoc}`;

/**
 * __useApplicationQuery__
 *
 * To run a query within a React component, call `useApplicationQuery` and pass it any options that fit your needs.
 * When your component renders, `useApplicationQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useApplicationQuery({
 *   variables: {
 *      id: // value for 'id'
 *   },
 * });
 */
export function useApplicationQuery(baseOptions?: ApolloReactHooks.QueryHookOptions<ApplicationQuery, ApplicationQueryVariables>) {
        return ApolloReactHooks.useQuery<ApplicationQuery, ApplicationQueryVariables>(ApplicationDocument, baseOptions);
      }
export function useApplicationLazyQuery(baseOptions?: ApolloReactHooks.LazyQueryHookOptions<ApplicationQuery, ApplicationQueryVariables>) {
          return ApolloReactHooks.useLazyQuery<ApplicationQuery, ApplicationQueryVariables>(ApplicationDocument, baseOptions);
        }
export type ApplicationQueryHookResult = ReturnType<typeof useApplicationQuery>;
export type ApplicationLazyQueryHookResult = ReturnType<typeof useApplicationLazyQuery>;
export type ApplicationQueryResult = ApolloReactCommon.QueryResult<ApplicationQuery, ApplicationQueryVariables>;
export const ApplicationsDocument = gql`
    query Applications {
  applications {
    ...ApplicationData
  }
}
    ${ApplicationDataFragmentDoc}`;

/**
 * __useApplicationsQuery__
 *
 * To run a query within a React component, call `useApplicationsQuery` and pass it any options that fit your needs.
 * When your component renders, `useApplicationsQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useApplicationsQuery({
 *   variables: {
 *   },
 * });
 */
export function useApplicationsQuery(baseOptions?: ApolloReactHooks.QueryHookOptions<ApplicationsQuery, ApplicationsQueryVariables>) {
        return ApolloReactHooks.useQuery<ApplicationsQuery, ApplicationsQueryVariables>(ApplicationsDocument, baseOptions);
      }
export function useApplicationsLazyQuery(baseOptions?: ApolloReactHooks.LazyQueryHookOptions<ApplicationsQuery, ApplicationsQueryVariables>) {
          return ApolloReactHooks.useLazyQuery<ApplicationsQuery, ApplicationsQueryVariables>(ApplicationsDocument, baseOptions);
        }
export type ApplicationsQueryHookResult = ReturnType<typeof useApplicationsQuery>;
export type ApplicationsLazyQueryHookResult = ReturnType<typeof useApplicationsLazyQuery>;
export type ApplicationsQueryResult = ApolloReactCommon.QueryResult<ApplicationsQuery, ApplicationsQueryVariables>;
export const LocationDocument = gql`
    query Location($applicationId: String!, $locationId: String!) {
  location(applicationId: $applicationId, locationId: $locationId) {
    ...LocationData
  }
}
    ${LocationDataFragmentDoc}`;

/**
 * __useLocationQuery__
 *
 * To run a query within a React component, call `useLocationQuery` and pass it any options that fit your needs.
 * When your component renders, `useLocationQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useLocationQuery({
 *   variables: {
 *      applicationId: // value for 'applicationId'
 *      locationId: // value for 'locationId'
 *   },
 * });
 */
export function useLocationQuery(baseOptions?: ApolloReactHooks.QueryHookOptions<LocationQuery, LocationQueryVariables>) {
        return ApolloReactHooks.useQuery<LocationQuery, LocationQueryVariables>(LocationDocument, baseOptions);
      }
export function useLocationLazyQuery(baseOptions?: ApolloReactHooks.LazyQueryHookOptions<LocationQuery, LocationQueryVariables>) {
          return ApolloReactHooks.useLazyQuery<LocationQuery, LocationQueryVariables>(LocationDocument, baseOptions);
        }
export type LocationQueryHookResult = ReturnType<typeof useLocationQuery>;
export type LocationLazyQueryHookResult = ReturnType<typeof useLocationLazyQuery>;
export type LocationQueryResult = ApolloReactCommon.QueryResult<LocationQuery, LocationQueryVariables>;
export const MessageDocument = gql`
    query Message($applicationId: String!, $tickerId: String!, $messageId: String!) {
  message(applicationId: $applicationId, tickerId: $tickerId, messageId: $messageId) {
    ...MessageData
  }
}
    ${MessageDataFragmentDoc}`;

/**
 * __useMessageQuery__
 *
 * To run a query within a React component, call `useMessageQuery` and pass it any options that fit your needs.
 * When your component renders, `useMessageQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useMessageQuery({
 *   variables: {
 *      applicationId: // value for 'applicationId'
 *      tickerId: // value for 'tickerId'
 *      messageId: // value for 'messageId'
 *   },
 * });
 */
export function useMessageQuery(baseOptions?: ApolloReactHooks.QueryHookOptions<MessageQuery, MessageQueryVariables>) {
        return ApolloReactHooks.useQuery<MessageQuery, MessageQueryVariables>(MessageDocument, baseOptions);
      }
export function useMessageLazyQuery(baseOptions?: ApolloReactHooks.LazyQueryHookOptions<MessageQuery, MessageQueryVariables>) {
          return ApolloReactHooks.useLazyQuery<MessageQuery, MessageQueryVariables>(MessageDocument, baseOptions);
        }
export type MessageQueryHookResult = ReturnType<typeof useMessageQuery>;
export type MessageLazyQueryHookResult = ReturnType<typeof useMessageLazyQuery>;
export type MessageQueryResult = ApolloReactCommon.QueryResult<MessageQuery, MessageQueryVariables>;
export const TemplateDocument = gql`
    query Template($applicationId: String!, $templateId: String!) {
  template(applicationId: $applicationId, templateId: $templateId) {
    ...TemplateData
  }
}
    ${TemplateDataFragmentDoc}`;

/**
 * __useTemplateQuery__
 *
 * To run a query within a React component, call `useTemplateQuery` and pass it any options that fit your needs.
 * When your component renders, `useTemplateQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useTemplateQuery({
 *   variables: {
 *      applicationId: // value for 'applicationId'
 *      templateId: // value for 'templateId'
 *   },
 * });
 */
export function useTemplateQuery(baseOptions?: ApolloReactHooks.QueryHookOptions<TemplateQuery, TemplateQueryVariables>) {
        return ApolloReactHooks.useQuery<TemplateQuery, TemplateQueryVariables>(TemplateDocument, baseOptions);
      }
export function useTemplateLazyQuery(baseOptions?: ApolloReactHooks.LazyQueryHookOptions<TemplateQuery, TemplateQueryVariables>) {
          return ApolloReactHooks.useLazyQuery<TemplateQuery, TemplateQueryVariables>(TemplateDocument, baseOptions);
        }
export type TemplateQueryHookResult = ReturnType<typeof useTemplateQuery>;
export type TemplateLazyQueryHookResult = ReturnType<typeof useTemplateLazyQuery>;
export type TemplateQueryResult = ApolloReactCommon.QueryResult<TemplateQuery, TemplateQueryVariables>;
export const TickerDocument = gql`
    query Ticker($applicationId: String!, $tickerId: String!) {
  ticker(applicationId: $applicationId, tickerId: $tickerId) {
    ...TickerData
  }
}
    ${TickerDataFragmentDoc}`;

/**
 * __useTickerQuery__
 *
 * To run a query within a React component, call `useTickerQuery` and pass it any options that fit your needs.
 * When your component renders, `useTickerQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useTickerQuery({
 *   variables: {
 *      applicationId: // value for 'applicationId'
 *      tickerId: // value for 'tickerId'
 *   },
 * });
 */
export function useTickerQuery(baseOptions?: ApolloReactHooks.QueryHookOptions<TickerQuery, TickerQueryVariables>) {
        return ApolloReactHooks.useQuery<TickerQuery, TickerQueryVariables>(TickerDocument, baseOptions);
      }
export function useTickerLazyQuery(baseOptions?: ApolloReactHooks.LazyQueryHookOptions<TickerQuery, TickerQueryVariables>) {
          return ApolloReactHooks.useLazyQuery<TickerQuery, TickerQueryVariables>(TickerDocument, baseOptions);
        }
export type TickerQueryHookResult = ReturnType<typeof useTickerQuery>;
export type TickerLazyQueryHookResult = ReturnType<typeof useTickerLazyQuery>;
export type TickerQueryResult = ApolloReactCommon.QueryResult<TickerQuery, TickerQueryVariables>;