import request from '@/utils/request'

/**
 * 获取字典列表
 */
export function dictList(id) {
  return request({
    url: `/admin/cmn/dict/findChildData/${id}`,
    method: 'get'
  })
}
