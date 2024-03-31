import {Space, Typography} from "antd";

const {Title} = Typography;

const HomePage = () => {
  return (
      <div style={{padding: '20px'}}>
        <Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>
          <Title level={1}>Food Delivery System</Title>
        </ Space>
      </div>
  );
};

export default HomePage;