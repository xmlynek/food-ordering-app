import {useEffect, useState} from "react";
import {Avatar, List, Typography} from "antd";

const {Title} = Typography;

const mockRestaurants = [
  {
    id: 1,
    name: 'Italian Bistro',
    description: 'Authentic Italian cuisine with a modern twist.',
    logo: 'https://via.placeholder.com/64', // Placeholder image URL
  },
  {
    id: 2,
    name: 'Sushi Place',
    description: 'Fresh sushi and Japanese dishes.',
    logo: 'https://via.placeholder.com/64',
  },
  {
    id: 2,
    name: 'Sushi Place',
    description: 'Fresh sushi and Japanese dishes.',
    logo: 'https://via.placeholder.com/64',
  },
  {
    id: 2,
    name: 'Sushi Place',
    description: 'Fresh sushi and Japanese dishes.',
    logo: 'https://via.placeholder.com/64',
  },
  {
    id: 2,
    name: 'Sushi Place',
    description: 'Fresh sushi and Japanese dishes.',
    logo: 'https://via.placeholder.com/64',
  },
  {
    id: 2,
    name: 'Sushi Place',
    description: 'Fresh sushi and Japanese dishes.',
    logo: 'https://via.placeholder.com/64',
  },
  // Add more restaurants as needed
];

const RestaurantsListPage = () => {
  const [restaurants, setRestaurants] = useState(mockRestaurants);

  useEffect(() => {
    // This is where you would fetch data from an API in a real app
    setRestaurants(mockRestaurants);
  }, []);

  return (
      <div style={{padding: '20px'}}>
        <Title level={1}>List of your restaurants</Title>
        <List
            itemLayout="horizontal"
            pagination={{position: 'bottom', align: 'start', pageSize: 5}}
            dataSource={restaurants}
            renderItem={item => (
                <List.Item>
                  <List.Item.Meta
                      avatar={<Avatar src={item.logo}/>}
                      title={
                        <p>{item.name}</p>} // Replace href with a link to the restaurant detail page
                      description={item.description}
                  />
                </List.Item>
            )}
        />
      </div>
  );
};

export default RestaurantsListPage;