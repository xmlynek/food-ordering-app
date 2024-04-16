import {Layout} from "antd";
import React from "react";
import Navbar from "../navbar/Navigation.tsx";
import AppFooter from "../footer/AppFooter.tsx";

import styles from './LayoutWrapper.module.css';

const {Content} = Layout;

interface LayoutWrapperProps {
  children: React.ReactNode;
}

const LayoutWrapper: React.FC<LayoutWrapperProps> = ({children}) => {

  return (
      <Layout className={styles.layout}>
        <Navbar />
        <Content className={styles.content}>
          <div className={styles.contentInner}>
            {children}
          </div>
        </Content>
        <AppFooter/>
      </Layout>
  );
};

export default LayoutWrapper;