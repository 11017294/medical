import request from '@/utils/request'

// 医院列表
export function getPageList(current, limit, searchObj) {
  return request({
    url: `/admin/hosp/hospital/list/${current}/${limit}`,
    method: 'get',
    params: searchObj
  })
}

// 查询dictCode查询下级数据字典
export function findByDictCode(dictCode) {
  return request({
    url: `/admin/cmn/dict/findByDictCode/${dictCode}`,
    method: 'get'
  })
}

// 根据id查询下级数据字典
export function findByParentId(dictCode) {
  return request({
    url: `/admin/cmn/dict/findChildData/${dictCode}`,
    method: 'get'
  })
}

export function updateStatus(id, status) {
  return request({
    url: `/admin/hosp/hospital/updateStatus/${id}/${status}`,
    method: 'post'
  })
}

export function getHospById(id) {
  return request({
    url: `/admin/hosp/hospital/show/${id}`,
    method: 'get'
  })
}

export function getDeptByHoscode(hoscode) {
  return request({
    url: `/admin/hosp/department/getDeptList/${hoscode}`,
    method: 'get'
  })
}

export function getScheduleRule(page, limit, hoscode, depcode) {
  return request({
    url: `/admin/hosp/schedule/getScheduleRule/${page}/${limit}/${hoscode}/${depcode}`,
    method: 'get'
  })
}

// 查询排班详情
export function getScheduleDetail(hoscode, depcode, workDate) {
  return request({
    url: `/admin/hosp/schedule/getScheduleDetail/${hoscode}/${depcode}/${workDate}`,
    method: 'get'
  })
}
