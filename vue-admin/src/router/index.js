import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },
  {
    path: '/hosp',
    component: Layout,
    redirect: '/hosp/hospital/list',
    name: 'Hosp',
    alwaysShow: true,
    meta: { title: '医院管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'hospital/list',
        name: '医院列表',
        component: () => import('@/views/hosp/list'),
        meta: { title: '医院列表', icon: 'table' }
      },
      {
        path: 'hospSet/list',
        name: 'HospSet',
        component: () => import('@/views/hosp/hospitalSet/index'),
        meta: { title: '医院信息', icon: 'table' }
      },
      {
        path: 'hospital/show/:id',
        name: '查看',
        component: () => import('@/views/hosp/show'),
        meta: { title: '查看', noCache: true },
        hidden: true
      },
      {
        path: 'hospSet/add',
        name: 'AddHospSet',
        component: () => import('@/views/hosp/hospitalSet/form'),
        meta: { title: '添加信息' },
        hidden: true
      },
      {
        path: 'hospSet/edit/:id',
        name: 'EditHospSet',
        component: () => import('@/views/hosp/hospitalSet/form'),
        meta: { title: '编辑信息', noCache: true },
        hidden: true
      }
    ]
  },
  {
    path: '/cmn',
    component: Layout,
    redirect: '/cmn/list',
    name: '数据管理',
    alwaysShow: true,
    meta: { title: '数据管理', icon: 'example' },
    children: [
      {
        path: 'list',
        name: '数据字典',
        component: () => import('@/views/dict/list'),
        meta: { title: '数据字典', icon: 'table' }
      }
    ]
  },
  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://element.eleme.io/#/zh-CN/component/installation',
        meta: { title: 'elementUI', icon: 'link' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
