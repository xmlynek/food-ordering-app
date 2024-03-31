import {useState} from 'react';
import {useSearchParams} from 'react-router-dom';

interface PaginationHook {
  currentPage: number;
  pageSize: number;
  handlePageChange: (page: number, pageSize: number) => void;
}

export const usePagination = (defaultPageSize: number = 10): PaginationHook => {
  const [searchParams, setSearchParams] = useSearchParams();
  const defaultPage: number = parseInt(searchParams.get('page') || '1', 10);

  const [currentPage, setCurrentPage] = useState<number>(defaultPage);
  const [pageSize, setPageSize] = useState<number>(parseInt(searchParams.get('size') || `${defaultPageSize}`, 10));

  const handlePageChange = (page: number, pageSize: number): void => {
    setCurrentPage(page);
    setPageSize(pageSize);
    setSearchParams({page: page.toString(), size: pageSize.toString()});
  };

  return {currentPage, pageSize, handlePageChange};
};