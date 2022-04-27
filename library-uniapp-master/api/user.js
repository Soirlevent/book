import { request } from '@/common/request'
// 修改用户
export function updateUser(param) {
  return request({
    'url': '/system/user/profile',
    'method': 'PUT',
    'param': param
  })
}
// 用户密码重置
export function resetUserPwd(oldPassword, newPassword) {
  const param = {
    oldPassword,
    newPassword
  }
  return request({
    'url': '/system/user/profile/updatePwd',
    'method': 'PUT',
    'params': param
  })
}
