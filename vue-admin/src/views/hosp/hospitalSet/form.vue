<template>
  <div class="app-container">
    <h4>{{ title }}</h4>
    <el-form label-width="120px">
      <el-form-item label="医院名称">
        <el-input v-model="hospitalSet.hosname" />
      </el-form-item>
      <el-form-item label="医院编号">
        <el-input v-model="hospitalSet.hoscode" />
      </el-form-item>
      <el-form-item label="api基础路径">
        <el-input v-model="hospitalSet.apiUrl" />
      </el-form-item>
      <el-form-item label="联系人姓名">
        <el-input v-model="hospitalSet.contactsName" />
      </el-form-item>
      <el-form-item label="联系人手机">
        <el-input v-model="hospitalSet.contactsPhone" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { addHospSet, editHospSet, getHospSet } from '@/api/table'

export default {
  data() {
    return {
      title: '添加医院信息',
      hospitalSet: {}
    }
  },
  created() {
    if (this.$route.params && this.$route.params.id) {
      this.title = '编辑医院信息'
      const id = this.$route.params.id
      this.getHostSet(id)
    }
  },
  methods: {
    // 添加
    save() {
      addHospSet(this.hospitalSet)
        .then(response => {
          // 提示
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          // 跳转列表页面，使用路由跳转方式实现
          this.$router.push({ path: '/hosp/hospSet' })
        })
    },
    // 查询医院信息
    getHostSet(id) {
      getHospSet(id).then(res => {
        this.hospitalSet = res.data
      })
    },
    // 修改
    update() {
      editHospSet(this.hospitalSet)
        .then(response => {
          // 提示
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          // 跳转列表页面，使用路由跳转方式实现
          this.$router.push({ path: '/hosp/hospSet/list' })
        })
    },
    saveOrUpdate() {
      // 判断添加还是修改
      if (!this.hospitalSet.id) {
        this.save()
      } else {
        this.update()
      }
    }
  }
}
</script>

<style scoped lang="scss">

</style>
