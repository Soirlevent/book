import request from '@/utils/request'

// 查询图书逾期列表
export function listOrder(query) {
  return request({
    url: '/over/order/list',
    method: 'get',
    params: query
  })
}

// 查询图书逾期详细
export function getOrder(overId) {
  return request({
    url: '/over/order/' + overId,
    method: 'get'
  })
}

// 新增图书逾期
export function addOrder(data) {
  return request({
    url: '/over/order',
    method: 'post',
    data: data
  })
}

// 修改图书逾期
export function updateOrder(data) {
  return request({
    url: '/over/order',
    method: 'put',
    data: data
  })
}

// 删除图书逾期
export function delOrder(overId) {
  return request({
    url: '/over/order/' + overId,
    method: 'delete'
  })
}
