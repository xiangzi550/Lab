<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入实验室编号" suffix-icon="el-icon-search" v-model="id"></el-input>
      <el-select v-model="value" placeholder="and" style="width: 100px">
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-input style="width: 200px" placeholder="请输入实验室名称" suffix-icon="el-icon-message" class="ml-5" v-model="name"></el-input>
      <el-select v-model="value" placeholder="and" style="width: 100px">
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-input style="width: 200px" placeholder="请输入所属学院" suffix-icon="el-icon-position" class="ml-5" v-model="faculty"></el-input>
      <el-select v-model="type" placeholder="请选择实验室类型">
                    <el-option
                        v-for="item in typeoptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
      </el-select>
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
          action="http://localhost:9090/lab/import"
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
      <el-table-column prop="id" label="实验室编号" >          </el-table-column>
      <el-table-column prop="name" label="实验室名称" width="100">          </el-table-column>
      <el-table-column prop="picture" label="实验室图片" >      </el-table-column>
      <el-table-column prop="faculty" label="所属学院" >         </el-table-column>
      <el-table-column prop="capacity" label="容纳人数">         </el-table-column>
      <el-table-column prop="type" label="实验室类型">         </el-table-column>
      <el-table-column prop="responsibler" label="负责人" >         </el-table-column>
      <el-table-column prop="tel" label="电话" >         </el-table-column>
      <el-table-column prop="address" label="地址" >         </el-table-column>
      <el-table-column prop="detail" label="详情" >         </el-table-column>
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
              @confirm="del(scope.row.id)"
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
    <el-dialog title="实验室信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="90px" size="big">
        <el-form-item label="实验室编号">
          <el-input v-model="form.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="实验室名称">
          <el-input v-model.trim = "form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="照片">
          <el-input v-model="form.picture" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="所属学院">
          <el-input v-model.trim = "form.faculty" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="容纳人数">
          <el-input v-model="form.capacity" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="实验室类型">
          <el-input v-model="type" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="负责人">
          <el-input v-model="form.responsibler" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.tel" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="详情">
          <el-input
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4}"
              placeholder="请输入内容"
              v-model="form.detail">
          </el-input>
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
  name: "Lab",
  data(){
    return{
      typeoptions: [{
        value: '1',
        label: '教学型实验室'
      }, {
        value: '2',
        label: '省级实验室'
      }, {
        value: '3',
        label: '国家级实验室'
      }, ],
      building: '',
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
      id:"",
      faculty:"",
      name:"",
      type:"",
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
      this.request.get("/lab/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          id: this.id,
          name: this.name,
          faculty: this.faculty,
          type :this.type,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total= res.data.total
      })
    },
    save(){
      this.request.post("/lab",this.form).then(res=>{
        if(res.data) {
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
    del(id){
      this.request.delete("/lab/"+id).then(res=>{
        if(res.data){
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
      let id=this.multipleSelection.map(v => v.id)//把对象数组编程纯数字数组[{} {} {}]->[1,2,3]
      this.request.post("/lab/del/batch",id).then(res=>{
        if(res.data){
          this.$message.success("批量删除成功")
          this.load()
        } else{
          this.$message.error("批量删除失败")
        }
      })
    },
    reset(){
      this.id=""
      this.faculty=""
      this.name=""
      this.type=""
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
      window.open("http://localhost:9090/lab/export")
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