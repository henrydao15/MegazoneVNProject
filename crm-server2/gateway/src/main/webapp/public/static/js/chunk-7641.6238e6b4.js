(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-7641"],{"0NIv":function(t,a,i){"use strict";var e=i("Qnj9");i.n(e).a},"9PTB":function(t,a,i){"use strict";a.a={data:function(){return{summaryData:null}},methods:{getSummariesData:function(t){this.summaryData=t||{}},getSummaries:function(t){var a=this,i=[];return t.columns.forEach(function(t,e){i[e]=a.summaryData?a.summaryData[t.property]:""}),i}}}},"9kPm":function(t,a,i){"use strict";a.a={data:function(){return{showTable:!0}},methods:{mixinSortFn:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:[],a=arguments.length>1&&void 0!==arguments[1]?arguments[1]:null,i=arguments.length>2&&void 0!==arguments[2]?arguments[2]:"ascending";if("[object Array]"!==Object.prototype.toString.call(t))return[];if(!a)return t;t.sort(function(t,e){if(t[a]===e[a])return 0;var n=isNaN(Number(t[a]))||isNaN(Number(e[a]))?t[a]<e[a]:Number(t[a])<Number(e[a]);return"descending"===i?n?1:-1:n?-1:1})}}}},Qnj9:function(t,a,i){},xI5Q:function(t,a,i){"use strict";i.r(a);var e=i("QbLZ"),n=i.n(e),s=i("31UX"),o=i("9kPm"),r=i("9PTB"),l=i("MT78"),c=i.n(l),u=i("JgLm"),d={name:"CustomerTotalStatistics",mixins:[s.a,o.a,r.a],data:function(){return{loading:!1,axisOption:null,postParams:{},dataIndex:null,list:[],axisList:[],fieldList:[{field:"realname",name:"员工姓名",sortable:!0},{field:"customerNum",name:"新增客户数",sortable:!0},{field:"dealCustomerNum",name:"成交客户数",sortable:!0},{field:"dealCustomerRate",name:"客户成交率(%)",sortable:!0},{field:"contractMoney",name:"合同总金额",sortable:!0},{field:"receivablesMoney",name:"回款金额",sortable:!0}]}},computed:{listPostParams:function(){var t=n()({},this.postParams);if(void 0!==this.dataIndex&&null!==this.dataIndex){var a=this.axisList[this.dataIndex];delete t.type,t.startTime=a.type,t.endTime=a.type}return t}},mounted:function(){this.initAxis()},methods:{searchClick:function(t){this.postParams=t,this.getDataList(),this.getRecordList()},getDataList:function(){var t=this;this.loading=!0,Object(u.s)(this.postParams).then(function(a){t.loading=!1;var i=a.data||[];t.axisList=i;for(var e=[],n=[],s=[],o=0;o<i.length;o++){var r=i[o];e.push(r.dealCustomerNum),n.push(r.customerNum),s.push(r.type)}t.axisOption.xAxis[0].data=s,t.axisOption.series[0].data=e,t.axisOption.series[1].data=n,t.chartObj.setOption(t.axisOption,!0)}).catch(function(){t.loading=!1})},getRecordList:function(t){var a=this;this.dataIndex=t,this.list=[],this.loading=!0,Object(u.t)(this.listPostParams).then(function(t){a.loading=!1;var i=t.data||{};a.list=i.list||[],a.getSummariesData(i.total)}).catch(function(){a.loading=!1})},initAxis:function(){var t=this;this.chartObj=c.a.init(document.getElementById("axismain")),this.chartObj.on("click",function(a){t.getRecordList(a.dataIndex)}),this.axisOption={color:["#6ca2ff","#ff7474"],toolbox:this.toolbox,tooltip:{trigger:"axis",axisPointer:{type:"shadow"}},legend:{data:["成交客户数","新增客户数"],bottom:"0px",itemWidth:14},grid:{top:"40px",left:"20px",right:"20px",bottom:"40px",containLabel:!0,borderColor:"#fff"},xAxis:[{type:"category",data:[],axisTick:{alignWithLabel:!0,lineStyle:{width:0}},axisLabel:{color:"#333"},axisLine:{lineStyle:{color:"#333"}},splitLine:{show:!1}}],yAxis:[{type:"value",name:"新增客户数",axisTick:{alignWithLabel:!0,lineStyle:{width:0}},axisLabel:{color:"#333",formatter:"{value} 个"},axisLine:{lineStyle:{color:"#333"}},splitLine:{show:!1}}],series:[{name:"成交客户数",type:"bar",yAxisIndex:0,barMaxWidth:15,data:[]},{name:"新增客户数",type:"bar",yAxisIndex:0,barMaxWidth:15,data:[]}]}},exportClick:function(){this.requestExportInfo(u.u,this.listPostParams)}}},m=(i("0NIv"),i("KHd+")),h=Object(m.a)(d,function(){var t=this,a=t.$createElement,i=t._self._c||a;return i("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"main-container"},[i("filtrate-handle-view",{staticClass:"filtrate-bar",attrs:{title:"客户总量分析","module-type":"customer"},on:{load:function(a){t.loading=!0},change:t.searchClick}}),t._v(" "),i("div",{staticClass:"content"},[t._m(0),t._v(" "),i("div",{staticClass:"table-content"},[i("div",{staticClass:"handle-bar"},[i("el-button",{staticClass:"export-btn",on:{click:t.exportClick}},[t._v("导出")])],1),t._v(" "),t.showTable?i("el-table",{attrs:{data:t.list,"summary-method":t.getSummaries,height:"400","show-summary":"",stripe:"",border:"","highlight-current-row":""},on:{"sort-change":function(a){var i=a.prop,e=a.order;return t.mixinSortFn(t.list,i,e)}}},t._l(t.fieldList,function(t,a){return i("el-table-column",{key:a,attrs:{prop:t.field,label:t.name,"min-width":"130",sortable:"custom",align:"center","header-align":"center","show-overflow-tooltip":""}})})):t._e()],1)])],1)},[function(){var t=this.$createElement,a=this._self._c||t;return a("div",{staticClass:"axis-content"},[a("div",{attrs:{id:"axismain"}})])}],!1,null,"1305b864",null);h.options.__file="CustomerTotalStatistics.vue";a.default=h.exports}}]);