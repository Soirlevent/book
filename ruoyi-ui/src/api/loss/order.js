import request from '@/utils/request'

// 查询图书遗失订单列表
export function listOrder(query) {
  return request({
    url: '/loss/order/list',
    method: 'get',
    params: query
  })
}

// 查询图书遗失订单详细
export function getOrder(lossId) {
  return request({
    url: '/loss/order/' + lossId,
    method: 'get'
  })
}

// 新增图书遗失订单
export function addOrder(data) {
  return request({
    url: '/loss/order',
    method: 'post',
    data: data
  })
}

// 修改图书遗失订单
export function updateOrder(data) {
  return request({
    url: '/loss/order',
    method: 'put',
    data: data
  })
}

// 删除图书遗失订单
export function delOrder(lossId) {
  return request({
    url: '/loss/order/' + lossId,
    method: 'delete'
  })
}

//取消图书遗失订单
export function cancelOrder(lossId) {
  return request({
    url: '/loss/order/cancel/' + lossId,
    method: 'get'
  })
}

