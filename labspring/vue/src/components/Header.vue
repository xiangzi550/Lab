<template>
<div style="font-size: 12px;  line-height: 60px; display: flex">
  <div style="flex: 1; font-size: 20px">
    <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
    <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
      <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
      <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
  <el-dropdown style="width: 150px; cursor: pointer; text-align: right" >
    <div style="display: inline-block">
<!--      <img src="" alt=""-->
<!--           style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">-->
      <span>{{ user.name }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
    </div>
    <el-dropdown-menu slot="dropdown" >
      <el-dropdown-item style="font-size: 16px; padding: 5px 0"><span @click="personinfo">个人信息</span> </el-dropdown-item>
      <el-dropdown-item style="font-size: 16px; padding: 5px 0">
        <span style="text-decoration: none" @click="loginout">退出</span>
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</div>
</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass:String,
    collapse:Function,
  },
  computed: {
    currentPathName () {
      return this.$store.state.currentPathName;　　//需要监听的数据
    }
  },
  // watch: {
  //   currentPathName (newVal, oldVal) {
  //     console.log(newVal)
  //   }
  // },
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  methods:{
    loginout(){
      this.$router.push("/login")
      localStorage.removeItem("user")
      this.$message.success("退出成功")
    },
    personinfo(){
      this.$router.push("/person")
    },
  }
}

</script>

<style scoped>

</style>