import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/vue-admin-template/table/list',
    method: 'get',
    data: params
  })
}

/**
 * 分页查询医院信息
 */
export function findHospSetPage(current, limit, params) {
  return request({
    url: `/admin/hosp/hospitalSet/findPage/${current}/${limit}`,
    method: 'post',
    data: params
  })
}

/**
 * 添加医院信息
 */
export function addHospSet(params) {
  return request({
    url: '/admin/hosp/hospitalSet/add',
    method: 'post',
    data: params
  })
}

/**
 * 修改医院信息
 */
export function editHospSet(params) {
  return request({
    url: '/admin/hosp/hospitalSet/editHospSet',
    method: 'post',
    data: params
  })
}

/**
 * 删除医院信息
 */
export function removeHospSet(id) {
  return request({
    url: `/admin/hosp/hospitalSet/${id}`,
    method: 'delete'
  })
}

/**
 * 批量删除医院信息
 */
export function batchRemoveHospSet(params) {
  return request({
    url: '/admin/hosp/hospitalSet/batchRemove',
    method: 'delete',
    data: params
  })
}

/**
 * 设置医院状态
 */
export function lockHospSet(id, status) {
  return request({
    url: `/admin/hosp/hospitalSet/lockHospSet/${id}/${status}`,
    method: 'post'
  })
}
