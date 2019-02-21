
import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { injectGlobal, ThemeProvider } from 'styled-components';
import defaultTheme from '../../configs/theme';

export default class Theme extends PureComponent {

  componentWillMount() {
    const { theme } = this.props;

    injectGlobal`
      html,
      body {
        background-color: ${theme.color.background};
        font-size: ${theme.baseFontSize};
        margin: 0;
      }

      body {
        padding: 0;
      }
      
      * {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
      }
    `;
  }

  render = () => {
    const { children, theme } = this.props;
    return (
      <ThemeProvider theme={theme}>
        {children}
      </ThemeProvider>
    );
  };
}

Theme.propTypes = {
  children: PropTypes.node.isRequired,
  theme: PropTypes.object,
};

Theme.defaultProps = {
  theme: defaultTheme,
};
