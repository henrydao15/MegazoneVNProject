(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-3d9d"],{"7qmR":function(e,t,i){"use strict";i.d(t,"a",function(){return n}),i.d(t,"g",function(){return l}),i.d(t,"e",function(){return s}),i.d(t,"b",function(){return o}),i.d(t,"f",function(){return r}),i.d(t,"d",function(){return c}),i.d(t,"c",function(){return d});var a=i("t3Un");function n(e){return Object(a.a)({url:"oaExamineCategory/saveOrUpdateOaExamineSort",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function l(e){return Object(a.a)({url:"oaExamine/setOaExamine",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function s(e){return Object(a.a)({url:"oaExamine/myInitiate",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function o(e){return Object(a.a)({url:"oaExamine/deleteOaExamine",method:"post",data:e})}function r(e){return Object(a.a)({url:"oaExamine/queryOaExamineInfo/"+e,method:"post"})}function c(e){return Object(a.a)({url:"oaExamine/getField",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function d(e){return Object(a.a)({url:"oaExamine/export ",method:"post",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"},responseType:"blob"})}},Spbr:function(e,t,i){"use strict";var a=i("jFQp");i.n(a).a},jFQp:function(e,t,i){},oWzH:function(e,t,i){"use strict";t.a={methods:{getCategoryIcon:function(e){var t=e&&"string"==typeof e?e.split(","):[];return t.length>1?{icon:t[0],color:t[1]}:{icon:"wk wk-approve",color:"#9376FF"}}}}},slnp:function(e,t,i){"use strict";var a=i("7qmR"),n=i("Ew9n"),l=i("3biH"),s=i("/407"),o=i("hwzM"),r=i("iWcH"),c=i("7Qib"),d=i("oWzH"),u=i("m77o"),p=i("IEYw"),m={name:"ExamineDetail",components:{SlideView:n.a,ExamineInfo:l.a,RelatedBusinessCell:s.a,CRMFullScreenDetail:function(){return Promise.all([i.e("chunk-0b36"),i.e("chunk-4b55"),i.e("chunk-16ba")]).then(i.bind(null,"3z7q"))},FileCell:o.a,WkFieldView:r.default},filters:{fileName:function(e){return(e.name&&e.name.length>10?e.name.substring(0,10)+"...":e.name)+"("+Object(c.h)(e.size)+")"}},mixins:[d.a,p.a],props:{id:[String,Number],detailIndex:[String,Number],detailSection:[String,Number],noListenerClass:{type:Array,default:function(){return["list-box"]}}},data:function(){return{loading:!1,categoryId:"",type:"",detail:null,list:[],categoryName:"",fileList:[],imgList:[],travelList:[],travelField:[{prop:"vehicle",label:"交通工具"},{prop:"trip",label:"单程往返"},{prop:"startAddress",label:"出发城市"},{prop:"endAddress",label:"目的城市"},{prop:"startTime",label:"开始时间"},{prop:"endTime",label:"结束时间"},{prop:"duration",label:"时长（天）"},{prop:"description",label:"备注"}],expensesField:[{prop:"startAddress",label:"出发城市"},{prop:"endAddress",label:"目的城市"},{prop:"startTime",label:"开始时间"},{prop:"endTime",label:"结束时间"},{prop:"traffic",label:"交通费（元）"},{prop:"stay",label:"住宿费（元）"},{prop:"diet",label:"餐饮费（元）"},{prop:"other",label:"其他费用（元）"},{prop:"description",label:"费用明细描述"}],relatedID:"",relatedCRMType:"",showRelatedDetail:!1}},computed:{relatedListData:function(){for(var e=this,t={},i=["contacts","customer","business","contract"],a=function(a){var n=i[a],l=e.detail[n+"List"]||[];l.length>0&&(t[n]=l.map(function(e){return e.id&&(e[n+"Id"]=e.id),e}))},n=0;n<i.length;n++)a(n);return t},detailIcon:function(){return this.getCategoryIcon(this.detail.examineIcon)}},watch:{id:function(e){this.detail=null,this.getDetail()}},mounted:function(){},methods:{viewAfterEnter:function(){this.getDetail()},getBaseInfo:function(){var e=this;this.loading=!0,Object(a.d)({examineId:this.id,isDetail:1,type:1}).then(function(t){var i=t.data||[];i.forEach(function(t){"examine_cause"===t.formType?e.type=5:"business_cause"===t.formType&&(e.type=3),"detail_table"===t.formType&&(e.getFormItemDefaultProperty(t,!1),t.value=e.getItemValue(t,null,"update"))}),e.list=i,e.loading=!1}).catch(function(){e.loading=!1})},getDetail:function(){var e=this;this.loading=!0,Object(a.f)(this.id).then(function(t){e.loading=!1,e.categoryId=t.data.categoryId,e.type=t.data.type,e.getBaseInfo(),e.detail=t.data,e.categoryName=e.detail.categoryTitle,e.fileList=e.detail.file,e.imgList=e.detail.img,e.travelList=e.detail.examineTravelList}).catch(function(){e.loading=!1,e.hideView()})},hideView:function(){this.$emit("hide-view")},checkRelatedDetail:function(e,t){this.relatedID=t[e+"Id"]||t.id,this.relatedCRMType=e,this.showRelatedDetail=!0},handleFile:function(e,t,i){"preview"===e?t&&t.length>0&&this.$wkPreviewFile.preview({index:i,data:t}):"download"===e&&Object(c.d)({path:t.url,name:t.name})},imgZoom:function(e,t){this.$wkPreviewFile.preview({index:t,data:e})},downloadFile:function(e){Object(c.d)({path:e.url,name:e.name})},examineHandle:function(e){this.$emit("on-examine-handle",e,this.detailIndex),this.$emit("handle",e,this.detailIndex)},getCommonShowValue:function(e){return Object(u.a)(e.formType,e.value,"",e)},isBlockShowField:function(e){return["map_address","file","detail_table"].includes(e.formType)}}},f=(i("Spbr"),i("KHd+")),h=Object(f.a)(m,function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("slide-view",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticClass:"d-view",attrs:{"listener-ids":["workbench-main-container"],"no-listener-class":e.noListenerClass},on:{afterEnter:e.viewAfterEnter,close:e.hideView}},[e.detail?i("flexbox",{staticClass:"detail-main",attrs:{orient:"vertical"}},[i("flexbox",{staticClass:"detail-header"},[i("div",{staticClass:"header-icon",style:{backgroundColor:e.detailIcon.color}},[i("i",{class:["wk","wk-"+e.detailIcon.icon]})]),e._v(" "),i("div",{staticClass:"header-name"},[e._v(e._s(e.categoryName))])]),e._v(" "),i("el-form",{staticClass:"detail-body"},[i("flexbox",{attrs:{gutter:0,align:"stretch",wrap:"wrap"}},e._l(e.list,function(t,a){return"examine_cause"!==t.formType&&"business_cause"!==t.formType?i("flexbox-item",{key:a,staticClass:"b-cell",class:[{"is-block":e.isBlockShowField(t)},"is-"+t.formType],attrs:{span:.5}},[i("div",{staticClass:"b-cell-b"},[i("div",{staticClass:"b-cell-name"},[e._v(e._s(t.name))]),e._v(" "),i("div",{staticClass:"b-cell-value"},[i("wk-field-view",{attrs:{props:t,"form-type":t.formType,value:t.value},scopedSlots:e._u([{key:"default",fn:function(i){return i.data,[e._v("\n                  "+e._s(e.getCommonShowValue(t))+"\n                ")]}}])})],1)])]):e._e()})),e._v(" "),e.imgList.length>0?i("div",{staticClass:"img-box"},e._l(e.imgList,function(t,a){return i("div",{key:a,staticClass:"img-list",on:{click:function(t){e.imgZoom(e.imgList,a)}}},[i("img",{directives:[{name:"src",rawName:"v-src",value:t.url,expression:"imgItem.url"}],key:t.url,staticStyle:{"object-fit":"contain","vertical-align":"top"}})])})):e._e(),e._v(" "),e.fileList.length?i("div",{staticClass:"section"},[i("div",{staticClass:"section__hd"},[i("i",{staticClass:"wukong wukong-file"}),e._v(" "),i("span",[e._v("附件")]),e._v(" "),i("span",[e._v("("+e._s(e.fileList.length)+")")])]),e._v(" "),i("div",{staticClass:"section__bd"},e._l(e.fileList,function(t,a){return i("file-cell",{key:a,attrs:{data:t,list:e.fileList,"cell-index":a}})}))]):e._e(),e._v(" "),e.type&&3==e.type&&e.travelList&&e.travelList.length>0?i("div",{staticClass:"table-sections"},[i("div",{staticClass:"table-sections__title"},[e._v("行程")]),e._v(" "),i("el-table",{staticStyle:{"font-size":"13px"},attrs:{data:e.travelList,align:"center","header-align":"center",stripe:""}},e._l(e.travelField,function(e,t){return i("el-table-column",{key:t,attrs:{prop:e.prop,label:e.label,"show-overflow-tooltip":""}})}))],1):e._e(),e._v(" "),e.type&&5==e.type&&e.travelList&&e.travelList.length>0?i("div",{staticClass:"table-sections"},[i("div",{staticClass:"table-sections__title"},[e._v("报销")]),e._v(" "),i("el-table",{staticStyle:{"font-size":"13px"},attrs:{data:e.travelList,align:"center","header-align":"center",stripe:""}},[e._l(e.expensesField,function(e,t){return i("el-table-column",{key:t,attrs:{prop:e.prop,label:e.label,"show-overflow-tooltip":""}})}),e._v(" "),i("el-table-column",{attrs:{label:"发票",width:"50"},scopedSlots:e._u([{key:"default",fn:function(t){return[i("flexbox",{attrs:{justify:"center"}},[i("el-button",{attrs:{type:"text"},nativeOn:{click:function(i){e.handleFile("preview",t.row.img,0)}}},[e._v(e._s(t.row.img?t.row.img.length:0)+"张")])],1)]}}])})],2)],1):e._e(),e._v(" "),Object.keys(e.relatedListData).length>0?i("div",{staticClass:"section"},[i("div",{staticClass:"section__hd"},[i("i",{staticClass:"wukong wukong-relevance"}),e._v(" "),i("span",[e._v("相关信息")])]),e._v(" "),i("div",{staticClass:"section__bd"},e._l(e.relatedListData,function(t,a){return i("div",{key:a},e._l(t,function(t,n){return i("related-business-cell",{key:n,attrs:{data:t,"cell-index":n,type:a,"show-foot":!1},nativeOn:{click:function(i){e.checkRelatedDetail(a,t)}}})}))}))]):e._e(),e._v(" "),i("div",{staticClass:"examine-section"},[i("examine-info",{staticClass:"create-sections-content",attrs:{id:e.id,"record-id":e.detail.examineRecordId,"examine-type":"oa_examine"},on:{"on-handle":e.examineHandle}})],1)],1)],1):e._e(),e._v(" "),i("c-r-m-full-screen-detail",{attrs:{visible:e.showRelatedDetail,"crm-type":e.relatedCRMType,id:e.relatedID},on:{"update:visible":function(t){e.showRelatedDetail=t}}})],1)},[],!1,null,"7267d1e6",null);h.options.__file="ExamineDetail.vue";t.a=h.exports}}]);