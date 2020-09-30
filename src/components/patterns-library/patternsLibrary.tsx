import React from 'react';
import createAXAInputTextReact from '@axa-ch/input-text/lib/index.react';
import createAXAButtonReact from '@axa-ch/button/lib/index.react';
import createAXAHeading from '@axa-ch/heading/lib/index.react';
import createAXAButtonLink from "@axa-ch/button-link/lib/index.react";

export const AXAInputText = createAXAInputTextReact(React.createElement);
export const AXAButton = createAXAButtonReact(React.createElement);
export const AXAHeading = createAXAHeading(React.createElement);
export const AXAButtonLink = createAXAButtonLink(React.createElement);