// import {Image, Menu, Space, Typography} from "antd";
// import {
//   HistoryOutlined,
//   TruckOutlined,
//   UserOutlined
// } from "@ant-design/icons";
// import {Link} from "react-router-dom";
//
// const {Title} = Typography;
//
// import image from '../assets/deliveryimg.webp';


const HomePage = () => {
  // const menuItems = [
  //   {
  //     key: 'delivery',
  //     icon: <TruckOutlined/>,
  //     label: <Link to="/delivery">Orders Delivery</Link>,
  //   },
  //   {
  //     key: 'profile',
  //     icon: <UserOutlined/>,
  //     label: <Link to="/profile">Profile</Link>,
  //   },
  //   {
  //     key: 'delivery-history',
  //     icon: <HistoryOutlined/>,
  //     label: <Link to="/delivery-history">Delivery History</Link>,
  //   },
  // ];

  return (
      <div >
        {/*<Space direction="horizontal" style={{width: '100%', justifyContent: 'center'}}>*/}
        {/*  <Title level={1} style={{marginTop: 0}}>Food Delivery System</Title>*/}
        {/*</ Space>*/}
        {/*<Menu mode="horizontal" items={menuItems}/>*/}
        {/*<Image preview={false} src={image}/>*/}
      </div>
  );
};

export default HomePage;