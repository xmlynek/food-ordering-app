import { useState, useMemo } from 'react';

type SortDirection = 'ascend' | 'descend' | '';
type SortConfig<T> = {
  key: keyof T;
  direction: SortDirection;
};

// @ts-ignore
function useSortableData<T>(items: T[], initialSort: SortConfig<T> = { key: '', direction: '' }) {
  const [sortConfig, setSortConfig] = useState<SortConfig<T>>(initialSort);

  const sortedItems = useMemo(() => {
    let sortableItems = [...items];
    if (sortConfig.key && sortConfig.direction) {
      sortableItems.sort((a, b) => {
        if (a[sortConfig.key] < b[sortConfig.key]) {
          return sortConfig.direction === 'ascend' ? -1 : 1;
        }
        if (a[sortConfig.key] > b[sortConfig.key]) {
          return sortConfig.direction === 'ascend' ? 1 : -1;
        }
        return 0;
      });
    }
    return sortableItems;
  }, [items, sortConfig]);

  const requestSort = (key: keyof T) => {
    let direction: SortDirection = 'ascend';
    if (sortConfig.key === key && sortConfig.direction === 'ascend') {
      direction = 'descend';
    }
    setSortConfig({ key, direction });
  };

  const clearSort = () => {
    // @ts-ignore
    setSortConfig({ key: '', direction: '' });
  };

  return { items: sortedItems, requestSort, sortConfig, clearSort };
}

export default useSortableData;