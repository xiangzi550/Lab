<template>
<div style="width: 500px;padding: 20px;border: 1px solid #ccc">
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
      <el-form-item>
        <el-button type="primary" @click="save">确 定</el-button>
      </el-form-item>
    </el-form>
</div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Person",
  data(){
    return{
      form:{},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.request.get("/user/username/" + this.user.username).then(res=>{
      if(res.code==='200'){
        this.form=res.data
      }

    })
  },
  methods:{
    save(){
      this.request.post("/user",this.form).then(res=>{
        if(res.data) {
          this.$message.success("保存成功")
        } else{
          this.$message.error("保存失败")
        }
      })
    },
  }
}
</script>

<style scoped>

</style>