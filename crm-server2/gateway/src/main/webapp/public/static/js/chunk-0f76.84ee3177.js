(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-0f76"],{"+hK9":function(e,t,n){"use strict";n.d(t,"A",function(){return i}),n.d(t,"C",function(){return s}),n.d(t,"G",function(){return c}),n.d(t,"H",function(){return l}),n.d(t,"B",function(){return u}),n.d(t,"E",function(){return d}),n.d(t,"F",function(){return h}),n.d(t,"z",function(){return m}),n.d(t,"J",function(){return p}),n.d(t,"y",function(){return f}),n.d(t,"x",function(){return y}),n.d(t,"K",function(){return v}),n.d(t,"w",function(){return b}),n.d(t,"v",function(){return x}),n.d(t,"u",function(){return S}),n.d(t,"I",function(){return w}),n.d(t,"D",function(){return g}),n.d(t,"V",function(){return C}),n.d(t,"U",function(){return T}),n.d(t,"T",function(){return j}),n.d(t,"S",function(){return _}),n.d(t,"N",function(){return O}),n.d(t,"O",function(){return k}),n.d(t,"Q",function(){return F}),n.d(t,"P",function(){return I}),n.d(t,"R",function(){return R}),n.d(t,"M",function(){return L}),n.d(t,"L",function(){return U}),n.d(t,"m",function(){return q}),n.d(t,"r",function(){return E}),n.d(t,"t",function(){return H}),n.d(t,"s",function(){return A}),n.d(t,"o",function(){return D}),n.d(t,"q",function(){return N}),n.d(t,"p",function(){return M}),n.d(t,"j",function(){return $}),n.d(t,"h",function(){return B}),n.d(t,"a",function(){return P}),n.d(t,"i",function(){return K}),n.d(t,"n",function(){return z}),n.d(t,"l",function(){return J}),n.d(t,"k",function(){return Q}),n.d(t,"b",function(){return V}),n.d(t,"d",function(){return G}),n.d(t,"e",function(){return X}),n.d(t,"f",function(){return Z}),n.d(t,"c",function(){return W}),n.d(t,"g",function(){return Y});var a=n("GQeE"),r=n.n(a),o=n("t3Un");function i(){return Object(o.a)({url:"hrmSalaryMonthRecord/addNextMonthSalary",method:"post"})}function s(e){var t=new FormData;return r()(e).forEach(function(n){t.append(n,e[n])}),Object(o.a)({url:"hrmSalaryMonthRecord/computeSalaryData",method:"post",data:t,headers:{"Content-Type":"multipart/form-data"}})}function c(e){return Object(o.a)({url:"hrmSalaryMonthRecord/queryPaySalaryNumByType/"+e,method:"post"})}function l(){return Object(o.a)({url:"hrmSalaryMonthRecord/queryNoPaySalaryEmployee",method:"post"})}function u(){return Object(o.a)({url:"hrmSalaryMonthRecord/queryEmployeeChangeNum",method:"post"})}function d(){return Object(o.a)({url:"hrmSalaryMonthRecord/querySalaryOptionHead",method:"post"})}function h(e){return Object(o.a)({url:"hrmSalaryMonthRecord/querySalaryPageList",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function m(){return Object(o.a)({url:"hrmSalaryMonthRecord/queryLastSalaryMonthRecord",method:"post"})}function p(e){return Object(o.a)({url:"hrmSalaryMonthRecord/querySalaryOptionCount/"+e,method:"post"})}function f(e){return Object(o.a)({url:"hrmSalaryHistoryRecord/queryHistorySalaryList",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function y(e){return Object(o.a)({url:"hrmSalaryHistoryRecord/queryHistorySalaryDetail",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function v(e){return Object(o.a)({url:"hrmSalaryMonthRecord/updateSalary",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function b(){return Object(o.a)({url:"hrmSalaryMonthRecord/downloadAttendanceTemp",method:"post",responseType:"blob",headers:{"Content-Type":"application/json;charset=UTF-8"}})}function x(){return Object(o.a)({url:"hrmSalaryMonthRecord/downCumulativeTaxOfLastMonthTemp",method:"post",responseType:"blob",headers:{"Content-Type":"application/json;charset=UTF-8"}})}function S(){return Object(o.a)({url:"hrmSalaryMonthRecord/downloadAdditionalDeductionTemp",method:"post",responseType:"blob",headers:{"Content-Type":"application/json;charset=UTF-8"}})}function w(e){return Object(o.a)({url:"hrmSalaryMonthRecord/submitExamine",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function g(e){return Object(o.a)({url:"hrmSalaryMonthRecord/deleteSalary/"+e,method:"post"})}function C(){return Object(o.a)({url:"hrmSalarySlipTemplate/querySlipTemplateList",method:"post"})}function T(e){return Object(o.a)({url:"hrmSalarySlipTemplateOption/queryTemplateOptionByTemplateId/"+e,method:"post"})}function j(e){return Object(o.a)({url:"hrmSalarySlipTemplate/deleteSlipTemplate/"+e,method:"post"})}function _(e){return Object(o.a)({url:"hrmSalarySlipTemplate/addSlipTemplate",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function O(e){return Object(o.a)({url:"hrmSalarySlipRecord/querySlipEmployeePageList",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function k(e){return Object(o.a)({url:"hrmSalarySlipRecord/sendSalarySlip",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function F(e){return Object(o.a)({url:"hrmSalarySlipRecord/querySendRecordList",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function I(e){return Object(o.a)({url:"hrmSalarySlipRecord/querySendDetailList",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function R(e){return Object(o.a)({url:"hrmSalarySlipRecord/setSlipRemarks",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function L(e){return Object(o.a)({url:"hrmSalarySlipRecord/querySlipDetail/"+e,method:"post"})}function U(e){return Object(o.a)({url:"hrmSalarySlipRecord/deleteSendRecord/"+e,method:"post"})}function q(e){return Object(o.a)({url:"hrmSalaryArchives/querySalaryArchivesList",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function E(e){return Object(o.a)({url:"hrmSalaryChangeTemplate/deleteChangeTemplate/"+e,method:"post"})}function H(){return Object(o.a)({url:"hrmSalaryChangeTemplate/queryChangeSalaryOption",method:"post"})}function A(){return Object(o.a)({url:"hrmSalaryChangeTemplate/queryChangeTemplateList",method:"post"})}function D(e){return Object(o.a)({url:"hrmSalaryChangeTemplate/setChangeTemplate",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function N(e){return Object(o.a)({url:"hrmSalaryArchives/setFixSalaryRecord",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function M(e){return Object(o.a)({url:"hrmSalaryArchives/setChangeSalaryRecord",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function $(e){return Object(o.a)({url:"hrmSalaryArchives/queryChangeOptionByTemplateId",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function B(){return Object(o.a)({url:"hrmSalaryArchives/queryBatchChangeOption",method:"post"})}function P(e){return Object(o.a)({url:"hrmSalaryArchives/batchChangeSalaryRecord",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function K(e){return Object(o.a)({url:"hrmSalaryArchives/querySalaryArchivesById/"+e,method:"post"})}function z(e){return Object(o.a)({url:"hrmSalaryArchives/queryChangeRecordList/"+e,method:"post"})}function J(e){return Object(o.a)({url:"hrmSalaryArchives/queryFixSalaryRecordById/"+e,method:"post"})}function Q(e){return Object(o.a)({url:"hrmSalaryArchives/queryChangeSalaryRecordById/"+e,method:"post"})}function V(e){return Object(o.a)({url:"hrmSalaryArchives/cancelChangeSalary/"+e,method:"post"})}function G(e){return Object(o.a)({url:"hrmSalaryArchives/deleteChangeSalary/"+e,method:"post"})}function X(){return Object(o.a)({url:"hrmSalaryArchives/downLoadChangeTemplate",method:"post",responseType:"blob",headers:{"Content-Type":"application/json;charset=UTF-8"}})}function Z(){return Object(o.a)({url:"hrmSalaryArchives/downLoadFixTemplate",method:"post",responseType:"blob",headers:{"Content-Type":"application/json;charset=UTF-8"}})}function W(e){var t=new FormData;return r()(e).forEach(function(n){t.append(n,e[n])}),Object(o.a)({url:"hrmSalaryArchives/exportChangeSalaryRecord",method:"post",data:t,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function Y(e){var t=new FormData;return r()(e).forEach(function(n){t.append(n,e[n])}),Object(o.a)({url:"hrmSalaryArchives/exportFixSalaryRecord",method:"post",data:t,headers:{"Content-Type":"application/json;charset=UTF-8"}})}},"+yOs":function(e,t,n){},"3O29":function(e,t,n){},"3biH":function(e,t,n){"use strict";var a=n("/861"),r=n("Ktth"),o=n.n(r),i=n("QhmF"),s=n("dAOq"),c={name:"CheckFlow",components:{},filters:{stepName:function(e){return"第"+o.a.encodeS(e)+"级"}},mixins:[s.a],props:{examineType:{type:String,default:""},id:[String,Number],show:Boolean},data:function(){return{loading:!1,list:[]}},computed:{},watch:{show:function(e){e&&(this.list=[],this.getDetail())}},mounted:function(){},methods:{getDetail:function(){var e=this;this.id&&(this.loading=!0,Object(a.b)({recordId:this.id}).then(function(t){e.loading=!1,e.list=t.data}).catch(function(){e.loading=!1}))},close:function(){this.$emit("close")}}},l=(n("zvLj"),n("KHd+")),u=Object(l.a)(c,function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"},{name:"empty",rawName:"v-empty",value:e.list,expression:"list"}],attrs:{"xs-empty-icon":"none","xs-empty-text":"暂无记录"}},[n("flexbox",{staticClass:"flow-head"},[n("div",{staticClass:"flow-head-name"},[e._v("审批流程")]),e._v(" "),n("i",{staticClass:"el-icon-close flow-head-close",on:{click:e.close}})]),e._v(" "),n("div",{staticClass:"flow-body"},e._l(e.list,function(t,a){return n("flexbox",{key:a,staticClass:"cf-flow-item",attrs:{align:"stretch",justify:"flex-start"}},[n("img",{staticClass:"cf-flow-item-img",attrs:{src:e.getStatusImageIcon(t.examineStatus)}}),e._v(" "),n("div",[n("flexbox",{staticClass:"cf-flow-item-head"},[n("div",[e._v(e._s(t.examineTime))])]),e._v(" "),n("flexbox",{staticClass:"cf-flow-item-info"},[n("div",{staticClass:"cf-flow-item-name"},[e._v(e._s(t.examineUserName))]),e._v(" "),n("div",[n("span",[e._v(e._s(e.getStatusName(t.examineStatus)))]),e._v("此申请")])]),e._v(" "),t.remarks?n("div",{staticClass:"cf-flow-item-content"},[e._v(e._s(t.remarks)+"\n          "),n("div",{staticClass:"cf-flow-item-content-arrow"})]):e._e()],1),e._v(" "),n("div",{staticClass:"cf-flow-item-line"})])}))],1)},[],!1,null,"6c65c940",null);u.options.__file="CheckFlow.vue";var d=u.exports,h=n("7szD"),m={name:"ExamineInfo",components:{ExamineHandle:i.a,CheckFlow:d},filters:{stepName:function(e){return"第"+o.a.encodeS(e)+"级"}},mixins:[s.a],props:{examineType:{type:String,default:""},id:[String,Number],recordId:[String,Number],ownerUserId:[String,Number]},data:function(){return{loading:!1,examineInfo:{},showFlowPopover:!1,examineHandleInfo:{status:1},showExamineHandle:!1}},computed:{examineFlowList:function(){return this.examineInfo.examineFlowList?this.examineInfo.examineFlowList:[]}},watch:{recordId:{handler:function(e){e&&(this.examineInfo={},this.getFlowStepList(),this.$refs.checkFlow&&this.$refs.checkFlow.getDetail())},deep:!0,immediate:!0}},mounted:function(){},methods:{getFlowStepList:function(){var e=this;this.recordId&&(this.loading=!0,Object(a.c)({recordId:this.recordId,ownerUserId:this.ownerUserId}).then(function(t){e.loading=!1;var n=t.data||{};e.examineInfo=n}).catch(function(){e.loading=!1}))},examineHandle:function(e){this.examineHandleInfo.status=e,this.showExamineHandle=!0},examineHandleClick:function(e){this.getFlowStepList(),this.$refs.checkFlow&&this.$refs.checkFlow.getDetail(),this.$emit("on-handle",e)},getDetailName:function(e){return e.userList&&0!==e.userList.length?1===e.userList.length?e.userList[0].realname:5===e.examineType?e.userList.length+"人"+h.b[1]:e.userList.length+"人"+h.b[e.type]:"XX"}}},p=(n("Pkiq"),Object(l.a)(m,function(){var e=this,t=e.$createElement,n=e._self._c||t;return e.examineFlowList&&e.examineFlowList.length>0?n("div",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticClass:"approval-flow"},[n("flexbox",{staticClass:"approval-flow__hd",attrs:{justify:"space-between"}},[n("div",{staticClass:"approval-flow__hd--left"},[n("span",{staticClass:"flow-title"},[n("i",{staticClass:"wk wk-approve"}),e._v("审批流信息\n      ")]),e._v(" "),n("el-popover",{attrs:{width:"300",trigger:"click"},model:{value:e.showFlowPopover,callback:function(t){e.showFlowPopover=t},expression:"showFlowPopover"}},[n("check-flow",{ref:"checkFlow",attrs:{show:e.showFlowPopover,id:e.recordId,"examine-type":e.examineType},on:{close:function(t){e.showFlowPopover=!1}}}),e._v(" "),n("el-button",{attrs:{slot:"reference",type:"text"},slot:"reference"},[e._v("查看历史审批流程")])],1)],1),e._v(" "),n("div",{staticClass:"approval-flow__hd--right"},[1==e.examineInfo.isCheck?n("el-button",{staticClass:"xr-btn--green",attrs:{icon:"wk wk-success"},on:{click:function(t){e.examineHandle(1)}}},[e._v("通过")]):e._e(),e._v(" "),1==e.examineInfo.isCheck?n("el-button",{staticClass:"xr-btn--red",attrs:{icon:"wk wk-close"},on:{click:function(t){e.examineHandle(2)}}},[e._v("拒绝")]):e._e(),e._v(" "),1==e.examineInfo.isRecheck?n("el-button",{staticClass:"xr-btn--primary",attrs:{icon:"wk wk-reset"},on:{click:function(t){e.examineHandle(4)}}},[e._v("撤回")]):e._e()],1)]),e._v(" "),n("flexbox",{staticClass:"check-items",attrs:{wrap:"wrap"}},e._l(e.examineFlowList,function(t,a){return n("el-popover",{key:a,attrs:{disabled:!t.userList||0==t.userList.length,placement:"bottom",trigger:"hover"}},[n("div",{staticClass:"popover-detail"},e._l(t.userList,function(t,a){return n("flexbox",{key:a,staticClass:"popover-detail-item",attrs:{align:"stretch"}},[n("img",{staticClass:"popover-detail-item-img",attrs:{src:e.getStatusImageIcon(t.examineStatus)}}),e._v(" "),n("div",[n("div",{staticClass:"popover-detail-item-time"},[e._v(e._s(t.examineTime))]),e._v(" "),n("flexbox",{staticClass:"popover-detail-item-examine"},[n("div",{staticClass:"examine-name"},[e._v(e._s(t.realname))]),e._v(" "),n("div",{staticClass:"examine-info"},[e._v(e._s(e.getStatusName(t.examineStatus))+"此申请")])])],1)])})),e._v(" "),n("flexbox",{staticClass:"check-item",attrs:{slot:"reference"},slot:"reference"},[n("img",{staticClass:"check-item-img",attrs:{src:e.getStatusImageIcon(t.examineStatus)}}),e._v(" "),n("div",{staticClass:"check-item-name"},[e._v(e._s(e.getDetailName(t)))]),e._v(" "),n("div",{staticClass:"check-item-status"},[e._v(e._s(e.getStatusName(t.examineStatus)))]),e._v(" "),e.examineFlowList.length-1!=a?n("i",{staticClass:"el-icon-arrow-right check-item-arrow"}):e._e()])],1)})),e._v(" "),n("examine-handle",{attrs:{show:e.showExamineHandle,id:e.id,"record-id":e.recordId,"examine-type":e.examineType,detail:e.examineInfo,status:e.examineHandleInfo.status},on:{close:function(t){e.showExamineHandle=!1},save:e.examineHandleClick}})],1):e._e()},[],!1,null,"36932ce2",null));p.options.__file="ExamineInfo.vue";t.a=p.exports},BAmj:function(e,t,n){"use strict";var a=n("3O29");n.n(a).a},F22v:function(e,t,n){},KdZ5:function(e,t,n){},Pkiq:function(e,t,n){"use strict";var a=n("KdZ5");n.n(a).a},Q5mk:function(e,t,n){"use strict";var a={name:"ExamineProgressDialog",components:{ExamineInfo:n("3biH").a},mixins:[],props:{detail:Object,visible:{type:Boolean,required:!0,default:!1}},data:function(){return{title:"详情"}},computed:{id:function(){return this.detail?this.detail.srecordId:""},recordId:function(){return this.detail?this.detail.examineRecordId:""},createUserId:function(){return this.detail?this.detail.createUserId:""}},watch:{visible:{handler:function(e){e&&this.recordId&&this.$refs.examineInfo&&this.$refs.examineInfo.refreshData()},immediate:!0}},mounted:function(){},methods:{handleCancel:function(){this.close()},close:function(){this.$emit("update:visible",!1)},handleExamineInfo:function(e){},handleBack:function(e){this.close(),this.$emit("change")}}},r=(n("dJo2"),n("KHd+")),o=Object(r.a)(a,function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-dialog",{attrs:{visible:e.visible,"append-to-body":!0,"close-on-click-modal":!1,title:e.title,"show-close":!1,width:"700px"}},[n("div",{staticClass:"form-add-dialog-body"},[n("examine-info",{ref:"examineInfo",staticClass:"examine-info",attrs:{id:e.id,"record-id":e.recordId,"owner-user-id":e.createUserId,"examine-type":"hrm_salary"},on:{info:e.handleExamineInfo,"on-handle":e.handleBack}})],1),e._v(" "),n("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{nativeOn:{click:function(t){return e.handleCancel(t)}}},[e._v("关闭")])],1)])},[],!1,null,"154e9faa",null);o.options.__file="ExamineProgressDialog.vue";t.a=o.exports},QhmF:function(e,t,n){"use strict";var a=n("/861"),r={name:"ExamineHandle",components:{},mixins:[n("nboU").a],props:{show:{type:Boolean,default:!1},status:{type:[String,Number],default:1},id:[String,Number],recordId:[String,Number],detail:{type:Object,default:function(){return{}}},examineType:{type:String,default:""}},data:function(){return{loading:!1,showDialog:!1,content:""}},computed:{title:function(){return 1==this.status?"审批通过":2==this.status?"审批拒绝":4==this.status?"撤回审批":""},placeholder:function(){return 1==this.status?"请输入审批意见（选填）":2==this.status?"请输入审批意见（必填）":4==this.status?"请输入撤回理由（必填）":""}},watch:{show:{handler:function(e){this.showDialog=e},deep:!0,immediate:!0}},mounted:function(){},methods:{submitInfo:function(){var e=this;2!=this.status&&4!=this.status||this.content?(this.loading=!0,Object(a.a)({typeId:this.id,recordId:this.recordId,status:this.status,remarks:this.content}).then(function(t){e.loading=!1,e.$message.success("操作成功"),"crm_contract"!=e.examineType&&"crm_invoice"!=e.examineType&&"crm_receivables"!=e.examineType||e.$store.dispatch("GetMessageNum"),e.resetInfo(),e.$bus.emit("examine-handle-bus"),e.$emit("save",{type:e.status}),e.hiddenView()}).catch(function(){e.loading=!1})):this.$message.error(this.placeholder)},handleClick:function(e){"cancel"==e?(this.hiddenView(),this.resetInfo()):"confirm"==e&&this.submitInfo()},hiddenView:function(){this.$emit("close")},resetInfo:function(){this.content=""}}},o=(n("BAmj"),n("KHd+")),i=Object(o.a)(r,function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-dialog",{ref:"wkDialog",attrs:{title:e.title,"append-to-body":!0,"close-on-click-modal":!1,visible:e.showDialog,width:"400px"},on:{"update:visible":function(t){e.showDialog=t},close:e.hiddenView}},[n("el-input",{attrs:{rows:5,maxlength:200,placeholder:e.placeholder,type:"textarea",resize:"none","show-word-limit":""},model:{value:e.content,callback:function(t){e.content=t},expression:"content"}}),e._v(" "),n("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{on:{click:function(t){e.handleClick("cancel")}}},[e._v("取 消")]),e._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:function(t){e.handleClick("confirm")}}},[e._v("确 定")])],1)],1)},[],!1,null,"0f23af5f",null);i.options.__file="ExamineHandle.vue";t.a=i.exports},dJo2:function(e,t,n){"use strict";var a=n("F22v");n.n(a).a},zvLj:function(e,t,n){"use strict";var a=n("+yOs");n.n(a).a}}]);