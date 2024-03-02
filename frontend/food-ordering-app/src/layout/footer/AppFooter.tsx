import {Layout, Row, Col, Typography, Space, Divider} from 'antd';

const {Footer} = Layout;
const {Text, Link} = Typography;

const AppFooter = () => {
  return (
      <Footer style={{textAlign: 'center', padding: '24px 50px'}}>
        <Row justify="space-between" align="middle">
          <Col>
            <Space direction="vertical" size="middle">
              <Text strong>Foodie's Heaven</Text>
              <Text type="secondary">The best place to find your next meal.</Text>
            </Space>
          </Col>

          <Col>
            <Space split={<Divider type="vertical"/>} wrap>
              <Link href="#">About Us</Link>
              <Link href="#">Terms of Service</Link>
              <Link href="#">Privacy Policy</Link>
              <Link href="#">Contact Us</Link>
            </Space>
          </Col>

          <Col>
            <Space direction="vertical" size="middle">
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