<template>
  <div class="app-container">
    <div class="el-toolbar">
      <div class="el-toolbar-body" style="justify-content: flex-start;">
        <el-button type="primary" size="small" @click="exportData">导出<i class="el-icon-download el-icon--right" /></el-button>
        <el-upload class="inline-block" action="/admin/cmn/dict/importData" :on-success="handleSuccess" :data="{ type: 'dictionary' }" :show-file-list="false">
          <el-button size="small" type="success">点击上传<i class="el-icon-upload el-icon--right" /></el-button>
        </el-upload>
      </div>
    </div>
    <el-table
      :data="list"
      style="width: 100%"
      row-key="id"
      border
      lazy
      :load="getChild"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="名称" width="230" align="left">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>

      <el-table-column label="编码" width="220">
        <template slot-scope="{row}">
          {{ row.dictCode }}
        </template>
      </el-table-column>
      <el-table-column label="值" width="230" align="left">
        <template slot-scope="scope">
          <span>{{ scope.row.value }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { dictList } from '@/api/dict'

export default {
  data() {
    return {
      list: []
    }
  },
  created() {
    this.getDictList(1)
  },
  methods: {
    getDictList(id) {
      dictList(id)
        .then(response => {
          this.list = response.data
        })
    },
    getChild(tree, treeNode, resolve) {
      dictList(tree.id).then(response => {
        resolve(response.data)
      })
    },
    exportData() {
      window.location.href = 'http://localhost:8202/admin/cmn/dict/exportDictData'
    },
    handleSuccess(response) {
      // 处理上传成功后的逻辑
      console.log('上传成功！')
    }
  }
}
</script>

<style scoped lang="scss">
.el-toolbar{
  margin-bottom: 10px;
}
.inline-block {
  display: inline-block;
  margin-left: 10px;
}
</style>
