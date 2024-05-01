import {Layout, Row, Col, Typography, Space} from 'antd';

import styles from './AppFooter.module.css';

const {Footer} = Layout;
const {Text} = Typography;

const AppFooter = () => {
  const currentYear = new Date().getFullYear();

  return (
      <Footer className={styles.footer}>
        <Row justify="space-around" align="middle" gutter={[16, 16]}>
          <Col span={8} order={1} xs={24} sm={12} md={8} lg={8} xl={8} className={styles.column}>
            <Space direction="vertical" size="middle" className={styles.spaceVertical}>
              <Text className={styles.footerTitle} strong>Food Ordering Application</Text>
              <Text className={styles.footerText}>The best place to find your next meal.</Text>
            </Space>
          </Col>

          <Col span={8} xs={{span: 24, order: 3}} sm={{span: 24, order: 3}}
               md={{span: 24, order: 3}} lg={{order: 2, span: 8}} xl={{order: 2, span: 8}}
               className={styles.column}>
            <Text className={styles.footerText}>&copy; {currentYear} Filip Mlýnek - Food Ordering
              Application</Text>
          </Col>

          <Col span={8} xs={{span: 24, order: 2}} sm={{span: 12, order: 2}} md={{span: 8, order: 2}}
               lg={8} xl={8}
               className={styles.column}>
            <Space direction="vertical" size="middle" className={styles.spaceVertical}>
              <Text className={styles.footerTitle} strong>Contact Info</Text>
              <Text className={styles.footerText}>Email: <a href="mailto:xmlynek@stuba.sk"
                                                            className={styles.link}>xmlynek@stuba.sk</a></Text>
            </Space>
          </Col>
        </Row>
      </Footer>
  );
};

export default AppFooter;