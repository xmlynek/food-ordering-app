import React, {useEffect, useState} from "react";
import {Input, List} from "antd";
import {useNavigate, useSearchParams} from "react-router-dom";
import {
  BasicRestaurantRestDTO,
} from "../../model/restApiDto.ts";
import {PageableRestApiResponse} from "../../model/pageable.ts";
import {fetchRestaurants} from "../../client/catalogRestaurantsApiClient.ts";
import {useQuery} from "@tanstack/react-query";


interface RestaurantListProps {
}

const parseQueryParamAsNumber = (paramValue: string | null, defaultValue: number): number => {
  const result = paramValue !== null ? parseInt(paramValue, 10) : NaN;
  return !isNaN(result) ? result : defaultValue;
};

const RestaurantList: React.FC<RestaurantListProps> = ({}: RestaurantListProps) => {
  const [searchParams, setSearchParams] = useSearchParams();
  const navigate = useNavigate();
  const [currentPage, setCurrentPage] = useState<number>(parseQueryParamAsNumber(searchParams.get('page'), 1));
  const [pageSize, setPageSize] = useState<number>(parseQueryParamAsNumber(searchParams.get('size'), 10));
  const [searchTerm, setSearchTerm] = useState(searchParams.get('searchValue') || '');

  const {
    data: restaurantsPage,
    error,
    isPending,
    refetch,
  } = useQuery<PageableRestApiResponse<BasicRestaurantRestDTO>, Error>({
    queryKey: ['restaurants', currentPage, pageSize],
    queryFn: fetchRestaurants.bind(null, currentPage - 1, pageSize, searchTerm),
  });

  useEffect(() => {
    refetch();
  }, [currentPage, pageSize, searchTerm]);

  const handlePageChange = (page: number, pageSize: number) => {
    setCurrentPage(() => page);
    setPageSize(() => pageSize);
    setSearchParams((prev) => {
      const updatedParams = new URLSearchParams(prev);
      updatedParams.set('page', page.toString());
      updatedParams.set('size', pageSize.toString());
      return updatedParams;
    });
  };

  const handleOnSearch = (searchValue: string) => {
    setSearchTerm(() => searchValue);
    setSearchParams((prev) => {
      const updatedParams = new URLSearchParams(prev);
      if (searchValue) {
        updatedParams.set('searchValue', searchValue);
      } else {
        updatedParams.delete('searchValue');
      }
      return updatedParams;
    });
  }

  if (error) return 'An error has occurred: ' + error.message

  return (
      <>
        <Input.Search
            placeholder="Search restaurants..."
            defaultValue={searchTerm}
            allowClear
            enterButton
            onSearch={handleOnSearch}
            style={{marginBottom: 20}}
        />
        <List
            loading={isPending}
            itemLayout="horizontal"
            size="large"
            pagination={{
              position: 'bottom',
              align: 'start',
              pageSize: pageSize,
              current: currentPage,
              total: restaurantsPage?.totalElements,
              onChange: handlePageChange,
              onShowSizeChange: handlePageChange,
              showSizeChanger: true,
              showQuickJumper: true,
              showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`,
              responsive: true,
            }}
            dataSource={restaurantsPage?.content || []}
            renderItem={item => (
                <List.Item onClick={() => navigate(item.id)}>
                  <List.Item.Meta
                      // avatar={<Avatar/>}
                      title={
                        <p>{item.name}</p>} // Replace href with a link to the restaurant detail page
                      description={item?.isAvailable ? 'Available' : 'Not available'}
                  />
                </List.Item>
            )
            }
        />
      </>
  );
};

export default RestaurantList;