import request from '@/utils/request'

// 查询图书借阅列表
export function listBook(query) {
  return request({
    url: '/borrow/book/list',
    method: 'get',
    params: query
  })
}

// 查询图书借阅详细
export function getBook(bookId) {
  return request({
    url: '/borrow/book/' + bookId,
    method: 'get'
  })
}

// 新增图书借阅
export function addBook(data) {
  return request({
    url: '/borrow/book',
    method: 'post',
    data: data
  })
}

// 修改图书借阅
export function updateBook(data) {
  return request({
    url: '/borrow/book',
    method: 'put',
    data: data
  })
}

// 删除图书借阅
export function delBook(bookId) {
  return request({
    url: '/borrow/book/' + bookId,
    method: 'delete'
  })
}
