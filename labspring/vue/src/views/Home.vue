<template>
<div>
  <el-row>
    <el-col :span="12">
      <div id="main"  style="width: 600px;height: 500px"></div>
    </el-col>
    <el-col :span="12">
      <div id="pie"  style="width: 600px;height: 500px"></div>
    </el-col>
  </el-row>
</div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "Home",
  data(){
    return{

    }
  },
  mounted() {//页面元素选然后再触发
    var option = {
      title: {
        text: '各级实验室数量',
        subtext: '',
        left: 'center'
      },
      xAxis: {
        type: 'category',
        data: ["教学实验室", "校级实验室", "省级实验室", "国家级实验室"]
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line'
        },
        {
          data: [],
          type: 'bar'
        }
      ]
    };

    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);

    this.request.get("/echarts/labtype").then(res => {
      // 填空
      // option.xAxis.data = res.data.x
      option.series[0].data = res.data
      option.series[1].data = res.data
      // 数据准备完毕之后再set
      myChart.setOption(option);
      pieOption.series[0].data=[
        {name:"教学型实验室",value:res.data[0]},
        {name:"校级实验室",value:res.data[1]},
        {name:"省级实验室",value:res.data[2]},
        {name:"国家级实验室",value:res.data[3]},
      ]
      pieChart.setOption(pieOption)
    })

    //饼图
    var pieOption = {
      title: {
        text: '各级实验室数量',
        subtext: '比例图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          type: 'pie',
          radius: '70%',
          label:{
            normal:{
              show:true,
              position:'inner',
              textStyle:{
                fontWeight:300,
                fontsize:20,
                color:"#fff",
              },
              formatter:'{d}%'
            }
          },
          data: [          ],//
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };
    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);
    pieChart.setOption(pieOption)
  }
}
</script>

<style scoped>

</style>