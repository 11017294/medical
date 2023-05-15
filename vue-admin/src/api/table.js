import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/vue-admin-template/table/list',
    method: 'get',
    params
  })
}

/**
 * 分页查询医院设置
 * @param current
 * @param limit
 * @param params
 * @returns {*}
 */
export function findHospSetPage(current, limit, params) {
  return request({
    url: `/admin/hosp/hospitalSet/findPage/${current}/${limit}`,
    method: 'post',
    params
  })
}
