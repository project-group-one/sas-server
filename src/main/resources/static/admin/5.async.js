(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[5],{"/6Gx":function(e,t,a){"use strict";var n=a("g09b");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=n(a("uSa0")),l=r.default;t.default=l},"8yZj":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.dateFormat=t.types=void 0;var n=[{text:"type0-text",value:"type0"},{text:"type1-text",value:"type1"},{text:"type2-text",value:"type2"},{text:"type3-text",value:"type3"}];t.types=n;var r="YYYY-MM-DD";t.dateFormat=r},B1MN:function(e,t,a){"use strict";var n=a("tAuX"),r=a("g09b");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("2qtc");var l=r(a("kLXV"));a("8R5B");var i=r(a("aJyg")),u=r(a("gWZ8")),o=r(a("qIgq")),d=r(a("jHYr")),c=n(a("q1tI")),f=a("Hg0r"),s=function(e){(0,d.default)(e);var t=(0,f.useDispatch)(),a=(0,c.useState)([]),n=(0,o.default)(a,2),r=n[0],s=n[1],p=(0,c.useState)([]),m=(0,o.default)(p,2),v=m[0],g=m[1],y=(0,f.useSelector)(function(e){var t=e.organization.current;return{dataSource:e.organization.noOrgUsers.concat(t.users||[]).map(function(e){return{key:e.id,title:e.name}}),current:t}}),h=y.dataSource,b=y.current,E=(0,f.useSelector)(function(e){return e.organization.userModalVisible}),k=(0,f.useSelector)(function(e){return e.organization.noOrgUsers});(0,c.useEffect)(function(){return E&&(t({type:"organization/getNoOrgUsers"}),t({type:"organization/fetchItem"})),function(){}},[E]),(0,c.useEffect)(function(){return g(k.map(function(e){return e.id})),function(){}},[k]);var z=function(){t({type:"organization/set",payload:{userModalVisible:!1,current:{}}})},S=function(e){g(e)},C=function(e,t){s([].concat((0,u.default)(e),(0,u.default)(t)))};return c.default.createElement(l.default,{visible:E,title:"\u5206\u914d\u7528\u6237",onOk:function(){t({type:"organization/addUsers",payload:{orgId:b.id,userIds:h.filter(function(e){return v.includes(e.key)}).map(function(e){return e.key})}})},onCancel:z},c.default.createElement(i.default,{style:{marginLeft:34},dataSource:h,titles:["\u7ec4\u7ec7","\u7528\u6237"],targetKeys:v,selectedKeys:r,onChange:S,onSelectChange:C,render:function(e){return e.title}}))},p=s;t.default=p},Fcgg:function(e,t,a){"use strict";var n=a("g09b");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=n(a("iaSb")),l=r.default;t.default=l},INTL:function(e,t,a){"use strict";var n=a("g09b");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=n(a("q1tI")),l=r.default.createContext({}),i=l;t.default=i},cbgk:function(e,t,a){e.exports={tableList:"antd-pro-pages-organization-list-tableList",tableListOperator:"antd-pro-pages-organization-list-tableListOperator",tableListForm:"antd-pro-pages-organization-list-tableListForm",submitButtons:"antd-pro-pages-organization-list-submitButtons"}},eTcV:function(e,t,a){"use strict";var n=a("g09b"),r=a("tAuX");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("IzEo");var l=n(a("bx4M"));a("qVdP");var i=n(a("jsC+"));a("Pwec");var u=n(a("CtXQ"));a("lUTK");var o=n(a("BvKs"));a("14J3");var d=n(a("BMrR"));a("+L6B");var c=n(a("2/Rp"));a("jCWc");var f=n(a("kPKH"));a("5NDa");var s=n(a("5rEg")),p=n(a("p0pE")),m=n(a("d6i3")),v=n(a("1l/V"));a("2qtc");var g=n(a("kLXV"));a("/zsF");var y=n(a("PArb")),h=n(a("2Taf")),b=n(a("vZ4D")),E=n(a("l4Ni")),k=n(a("ujKo")),z=n(a("MhPg"));a("OaEy");var S=n(a("2fM7"));a("y8nQ");var C,M,w,V,x=n(a("Vl3Y")),F=r(a("q1tI")),L=a("Hg0r"),O=n(a("wd/R")),I=n(a("CkN6")),D=n(a("zHco")),R=n(a("Fcgg")),j=n(a("/6Gx")),P=n(a("zpl0")),_=n(a("cbgk")),A=(a("t3Un"),x.default.Item),N=(S.default.Option,function(e){return Object.keys(e).map(function(t){return e[t]}).join(",")}),T=(C=(0,L.connect)(function(e){var t=e.organization,a=e.loading;return{data:t.data,loading:a.effects["organization/fetch"]}}),M=x.default.create(),C(w=M((V=function(e){function t(){var e,a;(0,h.default)(this,t);for(var n=arguments.length,r=new Array(n),l=0;l<n;l++)r[l]=arguments[l];return a=(0,E.default)(this,(e=(0,k.default)(t)).call.apply(e,[this].concat(r))),a.state={selectedRows:[],formValues:{}},a.columns=[{title:"\u540d\u79f0",dataIndex:"name"},{title:"\u521b\u5efa\u65f6\u95f4",dataIndex:"createDate",sorter:!0,render:function(e){return F.default.createElement("span",null,(0,O.default)(e).format("YYYY-MM-DD HH:mm:ss"))}},{title:"\u64cd\u4f5c",render:function(e,t){return F.default.createElement(F.Fragment,null,F.default.createElement("a",{onClick:function(){a.props.dispatch({type:"organization/set",payload:{auditModalVisible:!0,id:t.id}}),a.props.dispatch({type:"organization/fetchItem"})}},"\u5ba1\u6838"),F.default.createElement(y.default,{type:"vertical"}),F.default.createElement("a",{onClick:function(){return a.props.dispatch({type:"organization/set",payload:{userModalVisible:!0,id:t.id}})}},"\u5206\u914d\u7528\u6237"),F.default.createElement(y.default,{type:"vertical"}),F.default.createElement("a",{onClick:function(){return a.handleDetailModalVisible(!0,t.id)}},"\u7f16\u8f91"),F.default.createElement(y.default,{type:"vertical"}),F.default.createElement("a",{onClick:a.handleRemove(t.id)},"\u5220\u9664"))}}],a.handleRemove=function(e){return function(){g.default.confirm({title:"\u662f\u5426\u786e\u8ba4\u5220\u9664?",onOk:function(){var t=(0,v.default)(m.default.mark(function t(){return m.default.wrap(function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,a.props.dispatch({type:"organization/remove",payload:e});case 2:a.setState(function(t){var a=t.selectedRows,n=Array.isArray(e)?e:[e];return{selectedRows:a.filter(function(e){return!n.some(function(t){return t===e.id})})}});case 3:case"end":return t.stop()}},t)}));function n(){return t.apply(this,arguments)}return n}()})}},a.handleStandardTableChange=function(e,t,n){var r=a.props.dispatch,l=a.state.formValues,i=Object.keys(t).reduce(function(e,a){var n=(0,p.default)({},e);return n[a]=N(t[a]),n},{}),u=(0,p.default)({current:e.current,pageSize:e.pageSize},l,i);n.field&&(u.sorter="".concat(n.field,",").concat(n.order)),r({type:"organization/fetch",payload:u})},a.handleMenuClick=function(e){var t=a.props.dispatch,n=a.state.selectedRows;if(0!==n.length)switch(e.key){case"remove":t({type:"rule/remove",payload:{key:n.map(function(e){return e.key})},callback:function(){a.setState({selectedRows:[]})}});break;default:break}},a.handleSelectRows=function(e){a.setState({selectedRows:e})},a.handleSearch=function(e){e.preventDefault();var t=a.props,n=t.dispatch,r=t.form;r.validateFields(function(e,t){if(!e){var r=(0,p.default)({},t,{updatedAt:t.updatedAt&&t.updatedAt.valueOf()});a.setState({formValues:r}),n({type:"organization/fetch",payload:r})}})},a.handleFormReset=function(){a.setState({formValues:{}}),a.props.dispatch({type:"organization/fetch",payload:{}})},a.handleDetailModalVisible=function(e,t){a.props.dispatch({type:"organization/toggleDetailModalVisible",payload:{visible:e,id:t}})},a}return(0,z.default)(t,e),(0,b.default)(t,[{key:"componentDidMount",value:function(){var e=this.props.dispatch;e({type:"organization/fetch"})}},{key:"renderSimpleForm",value:function(){var e=this.props.form.getFieldDecorator;return F.default.createElement(x.default,{onSubmit:this.handleSearch,layout:"inline"},F.default.createElement(d.default,{gutter:{md:8,lg:24,xl:48}},F.default.createElement(f.default,{md:8,sm:24},F.default.createElement(A,{label:"\u6807\u9898"},e("title")(F.default.createElement(s.default,{placeholder:"\u8bf7\u8f93\u5165"})))),F.default.createElement(f.default,{md:8,sm:24},F.default.createElement("span",{className:_.default.submitButtons},F.default.createElement(c.default,{type:"primary",htmlType:"submit"},"\u67e5\u8be2"),F.default.createElement(c.default,{style:{marginLeft:8},onClick:this.handleFormReset},"\u91cd\u7f6e")))))}},{key:"renderForm",value:function(){return this.renderSimpleForm()}},{key:"render",value:function(){var e=this,t=this.props,a=t.data,n=t.loading,r=this.state.selectedRows,d=F.default.createElement(o.default,{onClick:this.handleMenuClick,selectedKeys:[]},F.default.createElement(o.default.Item,{onClick:this.handleRemove(r.map(function(e){return e.id})),key:"remove"},"\u5220\u9664"));return F.default.createElement(D.default,{title:"\u7ec4\u7ec7"},F.default.createElement(l.default,{bordered:!1},F.default.createElement("div",{className:_.default.tableList},F.default.createElement("div",{className:_.default.tableListForm},this.renderForm()),F.default.createElement("div",{className:_.default.tableListOperator},F.default.createElement(c.default,{icon:"plus",type:"primary",onClick:function(){return e.handleDetailModalVisible(!0)}},"\u65b0\u5efa"),r.length>0&&F.default.createElement("span",null,F.default.createElement(i.default,{overlay:d},F.default.createElement(c.default,null,"\u66f4\u591a\u64cd\u4f5c ",F.default.createElement(u.default,{type:"down"}))))),F.default.createElement(I.default,{selectedRows:r,loading:n,data:a,columns:this.columns,onSelectRow:this.handleSelectRows,onChange:this.handleStandardTableChange}))),F.default.createElement(R.default,null),F.default.createElement(j.default,null),F.default.createElement(P.default,null))}}]),t}(F.PureComponent),w=V))||w)||w),q=T;t.default=q},iaSb:function(e,t,a){"use strict";var n=a("g09b"),r=a("tAuX");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("y8nQ");var l=n(a("Vl3Y"));a("2qtc");var i=n(a("kLXV"));a("T2oS");var u,o,d,c=n(a("W9HT")),f=n(a("d6i3")),s=n(a("p0pE")),p=n(a("1l/V")),m=n(a("2Taf")),v=n(a("vZ4D")),g=n(a("l4Ni")),y=n(a("ujKo")),h=n(a("MhPg")),b=r(a("q1tI")),E=a("Hg0r"),k=n(a("e3Uc")),z=n(a("INTL")),S=(a("8yZj"),n(a("sGws"))),C=(u=(0,k.default)({beforeOpen:function(e){e.isEdit&&e.dispatch({type:"organization/fetchItem",payload:e.id})},afterClose:function(e){e.dispatch({type:"organization/clearCurrent"})}}),u((d=function(e){function t(){var e,a;(0,m.default)(this,t);for(var n=arguments.length,r=new Array(n),l=0;l<n;l++)r[l]=arguments[l];return a=(0,g.default)(this,(e=(0,y.default)(t)).call.apply(e,[this].concat(r))),a.handleCancel=function(){a.props.dispatch({type:"organization/toggleDetailModalVisible",payload:{visible:!1}})},a.handleSubmit=function(){var e=a.props,t=e.form,n=e.current,r=e.isEdit;t.validateFieldsAndScroll(function(){var e=(0,p.default)(f.default.mark(function e(t,l){return f.default.wrap(function(e){while(1)switch(e.prev=e.next){case 0:if(!t){e.next=2;break}return e.abrupt("return");case 2:if(!r){e.next=7;break}return e.next=5,a.props.dispatch({type:"organization/update",payload:(0,s.default)({},n,l)});case 5:e.next=9;break;case 7:return e.next=9,a.props.dispatch({type:"organization/add",payload:(0,s.default)({},l)});case 9:a.handleCancel();case 10:case"end":return e.stop()}},e)}));return function(t,a){return e.apply(this,arguments)}}())},a}return(0,h.default)(t,e),(0,v.default)(t,[{key:"render",value:function(){var e=this.props,t=e.visible,a=e.form,n=e.loading,r=e.addLoading,l=e.updateLoading,u=e.isEdit,o=e.current;return b.default.createElement(i.default,{visible:t,onCancel:this.handleCancel,title:u?"\u7f16\u8f91\u7ec4\u7ec7":"\u6dfb\u52a0\u7ec4\u7ec7",confirmLoading:u?l:r,width:800,bodyStyle:{padding:0,minHeight:200},onOk:this.handleSubmit},b.default.createElement(c.default,{spinning:n},b.default.createElement(z.default.Provider,{value:{form:a,isEdit:u,current:o}},b.default.createElement(S.default,null))))}}]),t}(b.default.PureComponent),o=d))||o),M=(0,E.connect)(function(e){return{visible:e.organization.detailModalVisible,current:e.organization.current,id:e.organization.id,isEdit:"undefined"!==typeof e.organization.id,loading:e.loading.effects["organization/fetchItem"]||!1,addLoading:e.loading.effects["organization/add"]||!1,updateLoading:e.loading.effects["organization/update"]||!1}})(l.default.create()(C));t.default=M},sGws:function(e,t,a){"use strict";var n=a("g09b");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=n(a("jehZ"));a("5NDa");var l=n(a("5rEg")),i=n(a("2Taf")),u=n(a("vZ4D")),o=n(a("l4Ni")),d=n(a("ujKo")),c=n(a("MhPg"));a("y8nQ");var f=n(a("Vl3Y")),s=n(a("q1tI")),p=n(a("INTL")),m=(a("t3Un"),f.default.Item),v=function(e){function t(){var e,a;(0,i.default)(this,t);for(var n=arguments.length,r=new Array(n),l=0;l<n;l++)r[l]=arguments[l];return a=(0,o.default)(this,(e=(0,d.default)(t)).call.apply(e,[this].concat(r))),a.normFile=function(e){return Array.isArray(e)?e:e&&[e.file]},a.handleFileRemove=function(e){var t=a.context.form;t.setFieldsValue({path:void 0})},a.handleFileChange=function(e){var t=a.context.form;t.setFieldsValue({name:e.file.name})},a}return(0,c.default)(t,e),(0,u.default)(t,[{key:"render",value:function(){var e=this.context,t=e.form,a=(e.isEdit,e.current),n=t.getFieldDecorator,i={labelCol:{span:8},wrapperCol:{span:9}};return s.default.createElement("div",{style:{paddingTop:30}},s.default.createElement(m,(0,r.default)({},i,{label:"\u7ec4\u7ec7\u540d\u79f0"}),n("name",{rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u7ec4\u7ec7\u540d\u79f0"}],initialValue:a.name})(s.default.createElement(l.default,{placeholder:"\u8bf7\u8f93\u5165\u7ec4\u7ec7\u540d\u79f0"}))))}}]),t}(s.default.PureComponent);v.contextType=p.default;var g=v;t.default=g},uSa0:function(e,t,a){"use strict";var n=a("g09b");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("2qtc");var r=n(a("kLXV"));a("+L6B");var l=n(a("2/Rp")),i=n(a("p0pE"));a("y8nQ");var u=n(a("Vl3Y"));a("5NDa");var o=n(a("5rEg")),d=n(a("q1tI")),c=a("Hg0r"),f=o.default.TextArea,s=u.default.Item,p=function(e){var t=e.form,a=(0,c.useDispatch)(),n=(0,c.useSelector)(function(e){return e.organization.auditModalVisible}),u=(0,c.useSelector)(function(e){return e.organization.current}),o=function(e){t.validateFieldsAndScroll("SUCCESS"!==e?["errorMsg"]:[],function(t,n){t||a({type:"organization/auditOrganization",payload:(0,i.default)({},n,{status:e})}).then(function(){p()})})},p=function(){a({type:"organization/set",payload:{auditModalVisible:!1,current:{}}})},m=function(){o("FAIL")},v=function(){o("SUCCESS")};return d.default.createElement(r.default,{visible:n,onCancel:p,footer:d.default.createElement("div",{style:{textAlign:"right"}},d.default.createElement(l.default,{style:{marginRight:10},onClick:m},"\u5ba1\u6838\u9a73\u56de"),d.default.createElement(l.default,{onClick:v,type:"primary"},"\u5ba1\u6838\u901a\u8fc7"))},d.default.createElement(s,{label:"\u6750\u6599",colon:!1},d.default.createElement("img",{src:u.credential,alt:""})),d.default.createElement(s,{label:"\u9a73\u56de\u539f\u56e0",colon:!1},t.getFieldDecorator("errorMsg",{rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u9a73\u56de\u539f\u56e0"}]})(d.default.createElement(f,{style:{height:200},placeholder:"\u5ba1\u6838\u9a73\u56de\u65f6\u8bf7\u586b\u5199\u539f\u56e0"}))))},m=u.default.create()(p);t.default=m},zpl0:function(e,t,a){"use strict";var n=a("g09b");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=n(a("B1MN")),l=r.default;t.default=l}}]);