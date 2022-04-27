import request from '@/utils/request'

// 查询图书位置列表
export function listLocation(query) {
  return request({
    url: '/library/location/list',
    method: 'get',
    params: query
  })
}

// 查询图书位置详细
export function getLocation(locaId) {
  return request({
    url: '/library/location/' + locaId,
    method: 'get'
  })
}

// 新增图书位置
export function addLocation(data) {
  return request({
    url: '/library/location',
    method: 'post',
    data: data
  })
}

// 修改图书位置
export function updateLocation(data) {
  return request({
    url: '/library/location',
    method: 'put',
    data: data
  })
}

// 删除图书位置
export function delLocation(locaId) {
  return request({
    url: '/library/location/' + locaId,
    method: 'delete'
  })
}
