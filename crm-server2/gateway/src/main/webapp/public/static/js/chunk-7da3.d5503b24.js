(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-7da3"],{"/SD7":function(e,t,n){"use strict";var i=n("hxdy");n.n(i).a},"2LBq":function(e,t,n){"use strict";var i=n("nGe4");n.n(i).a},"9GgJ":function(e,t,n){"use strict";var i={name:"XrHeader",components:{},props:{iconClass:[String,Array],iconColor:String,label:String,showSearch:{type:Boolean,default:!1},searchIconType:{type:String,default:"text"},placeholder:{type:String,default:"请输入内容"},ftTop:{type:String,default:"15px"},content:[String,Number],inputAttr:{type:Object,default:function(){}}},data:function(){return{search:""}},computed:{},watch:{content:{handler:function(){this.search!=this.content&&(this.search=this.content)},immediate:!0}},mounted:function(){},beforeDestroy:function(){},methods:{inputChange:function(){this.$emit("update:content",this.search)},searchClick:function(){this.$emit("search",this.search)}}},a=(n("zIzm"),n("KHd+")),o=Object(a.a)(i,function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("flexbox",{staticClass:"xr-header"},[e.iconClass?n("div",{staticClass:"xr-header__icon",style:{backgroundColor:e.iconColor}},[n("i",{class:e.iconClass})]):e._e(),e._v(" "),n("div",{staticClass:"xr-header__label"},[e.$slots.label?e._t("label"):[e._v(e._s(e.label))]],2),e._v(" "),e.showSearch?n("el-input",e._b({staticClass:"xr-header__search",class:{"is-text":"text"===e.searchIconType},style:{"margin-top":e.ftTop},attrs:{placeholder:e.placeholder},on:{input:e.inputChange},nativeOn:{keyup:function(t){return"button"in t||!e._k(t.keyCode,"enter",13,t.key,"Enter")?e.searchClick(t):null}},model:{value:e.search,callback:function(t){e.search=t},expression:"search"}},"el-input",e.inputAttr,!1),["text"===e.searchIconType?n("el-button",{attrs:{slot:"append",type:"primary"},nativeOn:{click:function(t){return e.searchClick(t)}},slot:"append"},[e._v("搜索")]):n("el-button",{attrs:{slot:"append",icon:"el-icon-search"},nativeOn:{click:function(t){return e.searchClick(t)}},slot:"append"})],1):e._e(),e._v(" "),n("div",{staticClass:"xr-header__ft",style:{top:e.ftTop}},[e._t("ft")],2)],1)},[],!1,null,"7bba770c",null);o.options.__file="index.vue";t.a=o.exports},AtGn:function(e,t,n){},Ntga:function(e,t,n){"use strict";var i=n("AtGn");n.n(i).a},gqmq:function(e,t,n){"use strict";n.r(t);var i=n("Rb0w"),a={name:"JurisdictionCreate",components:{},props:{show:{type:Boolean,default:!1},action:{type:Object,default:function(){return{type:"save"}}}},data:function(){return{loading:!1,roleName:"",remark:"",showTreeData:[],defaultProps:{children:"childMenu",label:"menuName"}}},computed:{diaTitle:function(){return"save"==this.action.type?"新建":"编辑"}},watch:{show:function(e){e&&this.initInfo()}},mounted:function(){},methods:{initInfo:function(){"update"==this.action.type?(this.roleName=this.action.data.roleName,this.remark=this.action.data.remark):(this.roleName="",this.remark="",this.$refs.tree&&this.$refs.tree.setCheckedKeys([])),0==this.showTreeData.length?this.getRulesList():this.checkTreeByUpdateInfo()},getRulesList:function(){var e=this;this.loading=!0,Object(i.a)().then(function(t){e.showTreeData=t.data.data?[t.data.data]:[],e.checkTreeByUpdateInfo(),e.loading=!1}).catch(function(){e.loading=!1})},checkTreeByUpdateInfo:function(){var e=this;this.$nextTick(function(){if(e.$refs.tree){"update"==e.action.type&&e.$refs.tree.setCheckedKeys(e.getUserModuleRules(e.action.data.menuIds));var t=e.$refs.tree.$children&&e.$refs.tree.$children.length?e.$refs.tree.$children[0].$el:null;t&&(t=t.children&&t.children.length?t.children[0]:null)&&(t.style.display="none")}})},sureClick:function(){var e=this;if(this.roleName){this.loading=!0;var t=this.$refs.tree.getCheckedKeys(),n={roleName:this.roleName,remark:this.remark,menuIds:t};"update"==this.action.type&&(n.roleId=this.action.data.roleId),Object(i.d)(n).then(function(t){e.loading=!1,e.$message.success("操作成功"),e.$emit("submite"),e.closeView()}).catch(function(){e.loading=!1})}else this.$message.error("请填写权限名称")},closeView:function(){this.$emit("update:show",!1)},getUserModuleRules:function(e){e||(e=[]);for(var t=this.showTreeData[0],n=!1,i=this.copyItem(e),a=0;a<t.childMenu.length;a++){var o=t.childMenu[a];o.childMenu||(o.childMenu=[]);for(var s=0;s<e.length;s++){for(var l=e[s],r=[],c=0;c<o.childMenu.length;c++){var d=o.childMenu[c];d.menuId==l&&r.push(d)}r.length!=o.childMenu.length&&(n=!0,this.removeItem(i,o.menuId))}}n&&this.removeItem(i,t.menuId);for(var u=[],h=0;h<i.length;h++){var p=i[h];p&&u.push(parseInt(p))}return u},copyItem:function(e){for(var t=[],n=0;n<e.length;n++)t.push(e[n]);return t},removeItem:function(e,t){for(var n=-1,i=0;i<e.length;i++)if(t==e[i]){n=i;break}n>0&&e.splice(n,1)},containItem:function(e,t){for(var n=0;n<e.length;n++)if(t==e[n])return!0;return!1}}},o=(n("Ntga"),n("KHd+")),s=Object(o.a)(a,function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-dialog",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticClass:"create-dialog",attrs:{title:e.diaTitle,visible:e.show,"modal-append-to-body":!0,"append-to-body":!0,"close-on-click-modal":!1,width:"700px"},on:{close:e.closeView}},[n("div",{staticClass:"label-input"},[n("label",{staticClass:"label-title"},[e._v("权限名称")]),e._v(" "),n("el-input",{attrs:{maxlength:100,placeholder:"请输入权限名称"},model:{value:e.roleName,callback:function(t){e.roleName=t},expression:"roleName"}})],1),e._v(" "),n("div",{staticClass:"label-input"},[n("label",{staticClass:"label-title"},[e._v("权限描述")]),e._v(" "),n("el-input",{attrs:{rows:2,maxlength:300,type:"textarea",placeholder:"请输入权限描述"},model:{value:e.remark,callback:function(t){e.remark=t},expression:"remark"}})],1),e._v(" "),n("label",{staticClass:"label-title"},[e._v("权限配置")]),e._v(" "),n("div",{staticClass:"jurisdiction-content-checkbox"},[n("el-tree",{ref:"tree",staticStyle:{height:"0"},attrs:{data:e.showTreeData,indent:0,"expand-on-click-node":!1,props:e.defaultProps,"show-checkbox":"","node-key":"menuId","empty-text":"","default-expand-all":""}})],1),e._v(" "),n("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{attrs:{type:"primary"},on:{click:e.sureClick}},[e._v("确 定")]),e._v(" "),n("el-button",{on:{click:e.closeView}},[e._v("取 消")])],1)])},[],!1,null,"d0eeee28",null);s.options.__file="JurisdictionCreate.vue";var l=s.exports,r=n("jzeO"),c=n("9GgJ"),d={name:"SystemProject",components:{JurisdictionCreate:l,Reminder:r.a,XrHeader:c.a},mixins:[],data:function(){return{loading:!1,tableHeight:document.documentElement.clientHeight-196,list:[],createAction:{type:"save"},jurisdictionCreateShow:!1}},computed:{},mounted:function(){var e=this;window.onresize=function(){e.tableHeight=document.documentElement.clientHeight-196},this.getList()},methods:{getList:function(){var e=this;this.loading=!0,Object(i.c)().then(function(t){e.list=t.data,e.loading=!1}).catch(function(){e.loading=!1})},addJurisdiction:function(){this.createAction={type:"save"},this.jurisdictionCreateShow=!0},handleRowClick:function(e,t,n){},handleClick:function(e,t){var n=this;"edit"===e?(this.createAction={type:"update",data:t.row},this.jurisdictionCreateShow=!0):"delete"===e&&this.$confirm("您确定要删除吗?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){Object(i.b)({roleId:t.row.roleId}).then(function(e){n.list.splice(t.$index,1),n.$message({type:"success",message:"操作成功"})}).catch(function(){})}).catch(function(){n.$message({type:"info",message:"已取消删除"})})}}},u=(n("2LBq"),Object(o.a)(d,function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"main"},[n("xr-header",{attrs:{"icon-class":"wk wk-project","icon-color":"#33D08F",label:"自定义项目权限"}}),e._v(" "),n("div",{staticClass:"main-body"},[n("div",{staticClass:"main-table-header"},[n("reminder",{staticClass:"project-reminder",attrs:{content:"为不同场景下的项目成员所需的权限设置匹配的项目、任务列表、任务的操作权限"}}),e._v(" "),n("el-button",{staticClass:"main-table-header-button xr-btn--orange",attrs:{type:"primary",icon:"el-icon-plus"},on:{click:e.addJurisdiction}},[e._v("新建权限")])],1),e._v(" "),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticClass:"main-table",staticStyle:{width:"100%"},attrs:{id:"examine-table",data:e.list,height:e.tableHeight,"highlight-current-row":""},on:{"row-click":e.handleRowClick}},[n("el-table-column",{attrs:{"show-overflow-tooltip":"",prop:"roleName",width:"150",label:"项目权限"}}),e._v(" "),n("el-table-column",{attrs:{"show-overflow-tooltip":"",prop:"remark",label:"项目描述"}}),e._v(" "),n("el-table-column",{attrs:{fixed:"right",label:"操作",width:"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-button",{attrs:{disabled:2==t.row.label,type:"text",size:"small"},on:{click:function(n){e.handleClick("edit",t)}}},[e._v("编辑")]),e._v(" "),n("el-button",{attrs:{disabled:5==t.row.roleType,type:"text",size:"small"},on:{click:function(n){e.handleClick("delete",t)}}},[e._v("删除")])]}}])})],1)],1),e._v(" "),n("jurisdiction-create",{attrs:{show:e.jurisdictionCreateShow,action:e.createAction},on:{"update:show":function(t){e.jurisdictionCreateShow=t},submite:e.getList}})],1)},[],!1,null,"67cf1b8c",null));u.options.__file="index.vue";t.default=u.exports},hxdy:function(e,t,n){},ihDC:function(e,t,n){},jzeO:function(e,t,n){"use strict";var i={name:"Reminder",components:{},props:{closeShow:{type:Boolean,default:!1},content:{type:String,default:"内容"},fontSize:{type:String,default:"13"}},data:function(){return{}},computed:{},mounted:function(){},destroyed:function(){},methods:{close:function(){this.$emit("close")}}},a=(n("/SD7"),n("KHd+")),o=Object(a.a)(i,function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("flexbox",{staticClass:"reminder-wrapper"},[n("flexbox",{staticClass:"reminder-body",attrs:{align:"stretch"}},[n("i",{staticClass:"wk wk-warning reminder-icon"}),e._v(" "),n("div",{staticClass:"reminder-content",style:{"font-size":e.fontSize+"px"},domProps:{innerHTML:e._s(e.content)}}),e._v(" "),e._t("default"),e._v(" "),e.closeShow?n("i",{staticClass:"el-icon-close close",on:{click:e.close}}):e._e()],2)],1)},[],!1,null,"d9a726d6",null);o.options.__file="Reminder.vue";t.a=o.exports},nGe4:function(e,t,n){},zIzm:function(e,t,n){"use strict";var i=n("ihDC");n.n(i).a}}]);