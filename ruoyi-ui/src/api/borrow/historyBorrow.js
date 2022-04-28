import request from '@/utils/request'

// 查询借阅历史信息列表
export function listHistoryBorrow(query) {
  return request({
    url: '/borrow/borrow/listHistory',
    method: 'get',
    params: query
  })
}

// 删除借阅信息
export function delBorrow(borrowId) {
  return request({
    url: '/borrow/borrow/' + borrowId,
    method: 'delete'
  })
}
