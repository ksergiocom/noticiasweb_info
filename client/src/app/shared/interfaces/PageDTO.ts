export interface PageDTO<T> {
  data: T[];
  total: number;
  page: number;
  size: number;
  totalPages: number;
}