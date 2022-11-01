<template>
  <el-container style="min-height: 100vh">

    <el-aside :width="sideWidth + 'px'" style="box-shadow: 2px 0 6px rgb(0 21 41 / 35%);">
<!--<Aside/>-->
      <Aside :isCollapse="isCollapse":logoTextShow="logoTextShow" />
    </el-aside>

    <el-container>
      <el-header style="font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px; display: flex">
        <div style="flex: 1; font-size: 20px">
          <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
        </div>
        <el-dropdown style="width: 70px; cursor: pointer">
          <span>王小虎</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
          <el-dropdown-menu slot="dropdown" >
            <el-dropdown-item style="font-size: 14px; padding: 5px 0">个人信息</el-dropdown-item>
            <el-dropdown-item style="font-size: 14px; padding: 5px 0">退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

      </el-header>

      <el-main>
        <div style="margin-bottom: 30px">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>用户管理</el-breadcrumb-item>
          </el-breadcrumb>
        </div>

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
          <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
          <el-button type="primary">导出 <i class="el-icon-top"></i></el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
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
      </el-main>

    </el-container>
  </el-container>
</template>

<script>
import Aside from "@/components/Aside";

export default {
  name: 'Home',
  data() {
    return {


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
      multipleSelection:[],
      msg: "Hello 颜朋祥",
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      headerBg: 'headerBg'
    }
  },
  //请求分页查询数据
  created() {
    this.load()
  },
  components: {
    Aside
  },
  methods: {
    collapse() {  // 点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {  // 收缩
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {   // 展开
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
    //iden参数是身份证号
    getAge(iden) {
      let val = iden.length;
      let myDate = new Date();
      let month = myDate.getMonth() + 1;
      let day = myDate.getDate();
      let age = 0;

      if (val === 18) {
        age = myDate.getFullYear() - iden.substring(6, 10) - 1;
        if (iden.substring(10, 12) < month || iden.substring(10, 12) == month && iden.substring(12, 14) <= day) age++;
      }
      //把算出的年龄给复制给from表单
      this.$set(this.dialogFormVisible,"age",age);
    },
    load(){
      this.request.get("/user/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          name: this.name,
          level:this.level
        }
      })
          .then(res => {
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
    }
  }
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>