<template>
<div>
  <div style="margin: 10px 0">
    <el-input style="width: 200px" placeholder="请输入用户名" suffix-icon="el-icon-search" v-model="username"></el-input>
    <el-select v-model="value" placeholder="and" style="width: 100px">
      <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
      </el-option>
    </el-select>
    <el-input style="width: 200px" placeholder="请输入姓名" suffix-icon="el-icon-message" class="ml-5" v-model="name"></el-input>
    <el-select v-model="value" placeholder="and" style="width: 100px">
      <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
      </el-option>
    </el-select>
    <el-input style="width: 200px" placeholder="请输入权限等级" suffix-icon="el-icon-position" class="ml-5" v-model="level"></el-input>
    <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
    <el-button type="warning" @click="reset">重置</el-button>
  </div>

  <div style="margin: 10px 0">
    <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
    <el-popconfirm
        class="ml-5"
        confirm-button-text='确定'
        cancel-button-text='取消'
        icon="el-icon-info"
        icon-color="red"
        title="确定批量删除勾选的数据吗？"
        @confirm="delBatch"
    >
      <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
    </el-popconfirm>
    <el-upload
        action="http://localhost:9090/user/import"
        :limit="1"
    style="display: inline-block"    :show-file-list="false"  accept=".xlsx" :on-success="handleExcelImportSuccess">
    <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
    </el-upload>
    <el-button type="primary" class="ml-5" @click="exp">导出 <i class="el-icon-top"></i></el-button>
  </div>

  <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
    <el-table-column
        type="selection"
        width="55">
    </el-table-column>
    <el-table-column prop="username" label="用户名" fit width="fit">          </el-table-column>
    <el-table-column prop="name" label="姓名" width="100">          </el-table-column>
    <el-table-column prop="gender" label="性别" >              </el-table-column>
    <el-table-column prop="age" label="年龄" >         </el-table-column>
    <el-table-column prop="tel" label="电话">         </el-table-column>
    <el-table-column prop="position" label="职位">         </el-table-column>
    <el-table-column prop="idcard" label="证件号" >         </el-table-column>
    <el-table-column prop="level" label="权限等级" >         </el-table-column>
    <el-table-column label="操作"  width="200" align="center">
      <template slot-scope="scope">
        <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
        <el-popconfirm
            class="ml-5"
            confirm-button-text='确定'
            cancel-button-text='取消'
            icon="el-icon-info"
            icon-color="red"
            title="确定删除吗？"
            @confirm="del(scope.row.username)"
        >
          <el-button type="danger" slot="reference" >删除 <i class="el-icon-remove-outline"></i></el-button>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  <div style="padding: 10px 0">
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="5"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>
  <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
    <el-form label-width="80px" size="big">
      <el-form-item label="用户名">
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model.trim = "form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <!--              <el-input v-model="form.gender" autocomplete="off"></el-input>-->
        <!--性别选择 -->
        <el-radio v-model="form.gender" label="男">男</el-radio>
        <el-radio v-model="form.gender" label="女">女</el-radio>
      </el-form-item>
      <el-form-item label="年龄">
        <!-- .number-->
        <el-input v-model="form.age" autocomplete="off" ></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.tel" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="职位">
        <el-input v-model="form.position" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="证件号">
        <el-input v-model="form.idcard" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="权限等级">
        <el-input v-model="form.level" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
export default {
  name: "User",
  data(){
    return{
      options: [{
        value: '选项1',
        label: 'and'
      }, {
        value: '选项2',
        label: 'or'
      }, {
        value: '选项3',
        label: 'not'
      }, ],
      value: '',
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:5,
      username:"",
      level:"",
      name:"",
      //添加信息表单
      dialogFormVisible:false,
      form:{},
      multipleSelection:[]
    }
  },
  created(){
    this.load()
  },
  methods:{

    load(){
      this.request.get("/user/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          name: this.name,
          level:this.level,
        }
      }).then(res => {
            console.log(res)
            this.tableData = res.records
            this.total= res.total
          })
    },
    save(){
      this.request.post("/user",this.form).then(res=>{
        if(res) {
          this.$message.success("添加成功")
          this.dialogFormVisible=false
          this.load()
        } else{
          this.$message.error("添加失败")
        }
      })
    },
    //*添加数据
    handleAdd(){
      this.dialogFormVisible=true
      this.form={}
    },
    //*编辑数据
    handleEdit(row){
      this.form=row
      this.dialogFormVisible=true
    },
    //*删除数据
    del(username){
      this.request.delete("/user/"+username).then(res=>{
        if(res){
          this.$message.success("删除成功")
          this.load()
        } else{
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection=val
    },
    delBatch(){
      let usernames=this.multipleSelection.map(v => v.username)//把对象数组编程纯数字数组[{} {} {}]->[1,2,3]
      this.request.post("/user/del/batch",usernames).then(res=>{
        if(res){
          this.$message.success("批量删除成功")
          this.load()
        } else{
          this.$message.error("批量删除失败")
        }
      })
    },
    reset(){
      this.username=""
      this.level=""
      this.name=""
      this.load()
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum=pageNum
      this.load()
    },
    exp(){
      window.open("http://localhost:9090/user/export")
    },
    handleExcelImportSuccess(){
      this.$message.success("导入成功")
      this.load()
    }
  }
}
</script>

<style scoped>
.headerBg {
  background: #eee!important;
}
</style>