import {Layout, Row, Col, Typography, Space, Divider} from 'antd';

const {Footer} = Layout;
const {Text, Link} = Typography;

const AppFooter = () => {
  return (
      <Footer style={{ textAlign: 'center', padding: '24px' }}>
        <Row justify="space-around" align="middle" gutter={[16, 16]}>
          <Col xs={24} sm={24} md={8} lg={8} xl={8}>
            <Space direction="vertical" size="middle" style={{ display: 'flex', justifyContent: 'center' }}>
              <Text strong>Foodie's Heaven</Text>
              <Text type="secondary">The best place to find your next meal.</Text>
            </Space>
          </Col>

          <Col xs={24} sm={24} md={8} lg={8} xl={8} style={{ textAlign: 'center' }}>
            <Space split={<Divider type="vertical" />} >
              <Link href="#">About Us</Link>
              <Link href="#">Terms of Service</Link>
              <Link href="#">Contact Us</Link>
            </Space>
          </Col>

          <Col xs={24} sm={24} md={8} lg={8} xl={8}>
            <Space direction="vertical" size="middle" style={{ display: 'flex', justifyContent: 'center' }}>
              <Text strong>Contact Info</Text>
              <Text>Email: contact@foodiesheaven.com</Text>
              <Text>Phone: (123) 456-7890</Text>
            </Space>
          </Col>
        </Row>
      </Footer>
  );
};

export default AppFooter;