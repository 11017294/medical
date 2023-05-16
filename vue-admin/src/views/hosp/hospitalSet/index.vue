<template>
  <div class="app-container">
    <div style="margin: 20px">
      <el-button type="danger" size="small" @click="removeRows()">批量删除</el-button>
      <router-link to="/hosp/hospSet/add" style="margin-left: 10px;">
        <el-button type="primary" size="small">添加医院信息</el-button>
      </router-link>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="48" align="center" />
      <el-table-column align="center" label="ID" width="100">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column label="医院" align="center">
        <template slot-scope="scope">
          {{ scope.row.hosname }}
        </template>
      </el-table-column>
      <el-table-column label="编码" width="120" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.hoscode }}</span>
        </template>
      </el-table-column>
      <el-table-column label="联系人" width="150" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.contactsName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="手机" width="150" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.contactsPhone }}</span>
        </template>
      </el-table-column>
      <el-table-column label="链接" width="250" align="center">
        <template slot-scope="scope">
          {{ scope.row.apiUrl }}
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="状态" width="80" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" width="180" align="center">
        <template slot-scope="scope">
          {{ scope.row.createTime }}
        </template>
      </el-table-column>
      <el-table-column label="修改时间" width="180" align="center">
        <template slot-scope="scope">
          {{ scope.row.updateTime }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="250" align="center">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status === 0" type="warning" size="mini" @click="lockHostSet(scope.row.id, 1)">锁定</el-button>
          <el-button v-else type="success" size="mini" @click="lockHostSet(scope.row.id, 0)">解锁</el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)" />
          <router-link :to="'/hosp/hospSet/edit/'+scope.row.id" style="margin-left: 10px;">
            <el-button type="primary" size="mini" icon="el-icon-edit" />
          </router-link>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page.sync="current"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="limit"
      :total="total"
      layout="total, sizes, prev, pager, next, jumper"
      style="padding: 30px 0; text-align: center;"
      @current-change="fetchData"
      @size-change="handleSizeChange"
    />
  </div>
</template>

<script>
import { batchRemoveHospSet, findHospSetPage, lockHospSet, removeHospSet } from '@/api/table'

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      list: [],
      listLoading: true,
      current: 1,
      limit: 5,
      searchObj: {}, // 条件封装对象
      total: 0, // 总记录数
      multipleSelection: []
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    // 获取列表
    fetchData() {
      this.listLoading = true
      this.searchObj.hosname = '医院'
      findHospSetPage(this.current, this.limit, this.searchObj).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    // 调整页大小
    handleSizeChange(val) {
      this.limit = val
      this.fetchData()
    },
    // 删除
    removeDataById(id) {
      this.$confirm('此操作将永久删除医院信息，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // 确定执行then方法
        removeHospSet(id)
          .then(response => {
            this.$message({
              type: 'success',
              message: '删除成功！'
            })
            this.fetchData()
          })
      })
    },
    // 复选框选项发生变化的时候触发
    handleSelectionChange(selection) {
      this.multipleSelection = selection
    },
    // 批量删除
    removeRows() {
      const idList = []
      for (let i = 0; i < this.multipleSelection.length; i++) {
        idList.push(this.multipleSelection[i].id)
      }
      this.$confirm('此操作将永久删除这些医院信息，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        batchRemoveHospSet(idList)
          .then(response => {
            this.$message({
              type: 'success',
              message: '删除成功！'
            })
            this.fetchData()
          })
      })
    },
    // 数据锁定与解锁
    lockHostSet(id, status) {
      this.$confirm('此操作将修改医院信息状态，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        lockHospSet(id, status).then(res => {
          this.$message({
            type: 'success',
            message: '设置成功！'
          })
          this.fetchData()
        })
      })
    }
  }
}
</script>
<style scoped>

</style>
