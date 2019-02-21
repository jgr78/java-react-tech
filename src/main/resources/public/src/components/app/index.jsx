import React from 'react';
import { Provider } from 'react-redux';
import Theme from '../theme';
import createStore from '../../store';
import Layout from '../layout';
import Header from './Header';
import Main from './Main';
import Footer from './Footer';
import "../../../styles.css"

const store = createStore();
window.store = store;

export default () => (
  <Provider store={store}>
    <Theme>
      <Layout>
        <Header />
        <Main />
        <Footer />
      </Layout>
    </Theme>
  </Provider>
);
