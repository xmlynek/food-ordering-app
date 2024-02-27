import {Avatar, List, Typography} from "antd";
import {useQuery} from "@tanstack/react-query";
import {useNavigate} from "react-router-dom";

const {Title} = Typography;

const RestaurantsListPage = () => {

  const navigate = useNavigate();

  const fetchRestaurants = async (): Promise<Restaurant[]> => {
    const response = await fetch('http://localhost:8085/api/restaurants');
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return response.json();
  };

  const {
    data: restaurants,
    error,
    isPending,
  } = useQuery<Restaurant[], Error>({queryKey: ['restaurants'], queryFn: fetchRestaurants});

  if (error) return 'An error has occurred: ' + error.message

  return (
      <div style={{padding: '20px'}}>
        <Title level={1}>List of your restaurants</Title>
        <List
            loading={isPending}
            itemLayout="horizontal"
            pagination={{position: 'bottom', align: 'start', pageSize: 10}}
            dataSource={restaurants}
            renderItem={item => (
                <List.Item onClick={() => navigate(item.id)} >
                  <List.Item.Meta
                      avatar={<Avatar />}
                      title={
                        <p>{item.name}</p>} // Replace href with a link to the restaurant detail page
                      description={item?.isAvailable? 'Available' : 'Not available'}
                  />
                </List.Item>
            )}
        />
      </div>
  );
};

export default RestaurantsListPage;