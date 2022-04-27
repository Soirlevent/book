import request from '@/utils/request'

// 查询图书分类列表
export function listType(query) {
  return request({
    url: '/library/type/list',
    method: 'get',
    params: query
  })
}

// 查询图书分类详细
export function getType(typeId) {
  return request({
    url: '/library/type/' + typeId,
    method: 'get'
  })
}

// 新增图书分类
export function addType(data) {
  return request({
    url: '/library/type',
    method: 'post',
    data: data
  })
}

// 修改图书分类
export function updateType(data) {
  return request({
    url: '/library/type',
    method: 'put',
    data: data
  })
}

// 删除图书分类
export function delType(typeId) {
  return request({
    url: '/library/type/' + typeId,
    method: 'delete'
  })
}
