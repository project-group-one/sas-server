(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[11],{"5mhG":function(e,t,a){"use strict";var l=a("g09b"),r=a("tAuX");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("IzEo");var u=l(a("bx4M"));a("g9YV");var d=l(a("wCAj")),n=l(a("p0pE"));a("+L6B");var i=l(a("2/Rp"));a("5NDa");var s=l(a("5rEg")),o=l(a("eHn4"));a("BoS7");var c=l(a("Sdc0"));a("2qtc");var f=l(a("kLXV"));a("/zsF");var p,m,h,v=l(a("PArb")),b=l(a("2Taf")),E=l(a("vZ4D")),g=l(a("l4Ni")),y=l(a("ujKo")),q=l(a("MhPg")),w=r(a("q1tI")),C=a("Hg0r"),k=l(a("wd/R")),V=l(a("zHco")),M=l(a("cS9+")),I=l(a("NTbU")),S=l(a("vdin")),x=(p=(0,C.connect)(function(e){var t=e.loading,a=e.users;return{data:a.list,queryParams:a.queryParams,total:a.total,loading:t.effects["news/getUserList"]}}),p((h=function(e){function t(){var e,a;(0,b.default)(this,t);for(var l=arguments.length,r=new Array(l),u=0;u<l;u++)r[u]=arguments[u];return a=(0,g.default)(this,(e=(0,y.default)(t)).call.apply(e,[this].concat(r))),a.state={auditModalVisible:!1,targetUser:void 0},a.columns=[{title:"\u59d3\u540d",dataIndex:"name"},{title:"\u5e74\u9f84",dataIndex:"age"},{title:"\u7535\u8bdd\u53f7\u7801",dataIndex:"tel"},{title:"\u521b\u5efa\u65e5\u671f",dataIndex:"createDate",sorter:!0,render:function(e){return(0,k.default)(e).format("YYYY-MM-DD HH:mm:ss")}},{title:"\u64cd\u4f5c",dataIndex:"id",render:function(e,t){return w.default.createElement(w.default.Fragment,null,w.default.createElement("a",{onClick:function(){return a.handleEdit(t.id)}},"\u7f16\u8f91"),w.default.createElement(v.default,{type:"vertical"}),w.default.createElement("a",{onClick:function(){a.setState({auditModalVisible:!0,targetUser:t})}},"\u5ba1\u6838"),w.default.createElement(v.default,{type:"vertical"}),w.default.createElement(c.default,{checked:0===t.status,checkedChildren:"\u542f\u7528",unCheckedChildren:"\u51bb\u7ed3",onChange:function(e){f.default.confirm({title:"\u662f\u5426\u786e\u5b9a".concat(e?"\u542f\u7528":"\u51bb\u7ed3","\u8be5\u7528\u6237\uff1f"),onOk:function(){a.props.dispatch({type:"users/toggleUserStatus",payload:{targetUserId:t.id,enabled:e}})}})}}),w.default.createElement(v.default,{type:"vertical"}),w.default.createElement("a",{onClick:function(){}},"\u5220\u9664"))}}],a.query=function(e){a.props.dispatch({type:"users/getUserList",payload:e})},a.handleSearch=function(e,t){a.query((0,o.default)({},t,e))},a.handleEdit=function(e){e&&a.props.dispatch({type:"users/getUser",payload:e}),a.props.dispatch({type:"users/setEditModalVisible",payload:!0})},a}return(0,q.default)(t,e),(0,E.default)(t,[{key:"componentDidMount",value:function(){this.query({})}},{key:"render",value:function(){var e=this,t=this.props,a=t.data,l=t.loading,r=t.queryParams,o=t.total,c=this.state,f=c.auditModalVisible,p=c.targetUser,m={current:r.current,pageSize:r.pageSize,total:o,showTotal:function(e){return"\u5171".concat(e,"\u6761")},showSizeChanger:!0,showQuickJumper:!0};return w.default.createElement(V.default,{title:"\u7528\u6237"},w.default.createElement(u.default,{bordered:!1},w.default.createElement("div",{className:S.default["header-bar"]},w.default.createElement("div",{className:"filter-bar"},w.default.createElement(s.default.Search,{placeholder:"\u8bf7\u8f93\u5165\u59d3\u540d",style:{width:212},onPressEnter:function(t){return e.handleSearch(t.target.value,"name")}})),w.default.createElement("div",{className:"action-bar"},w.default.createElement(i.default,{type:"primary",icon:"plus",onClick:function(){return e.handleEdit()}},"\u6dfb\u52a0"))),w.default.createElement("div",{className:S.default["table-wrapper"]},w.default.createElement(d.default,{rowKey:"id",loading:l,columns:this.columns,dataSource:a,pagination:m,onChange:function(t,a,l){e.query((0,n.default)({},t,a))}}))),w.default.createElement(I.default,null),w.default.createElement(M.default,{visible:f,targetUser:p,onCancel:function(){return e.setState({auditModalVisible:!1})}}))}}]),t}(w.Component),m=h))||m),O=x;t.default=O},NTbU:function(e,t,a){"use strict";var l=a("g09b"),r=a("tAuX");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("2qtc");var u=l(a("kLXV"));a("OaEy");var d=l(a("2fM7"));a("y8nQ");var n=l(a("Vl3Y")),i=l(a("jehZ"));a("5NDa");var s,o,c,f=l(a("5rEg")),p=l(a("p0pE")),m=l(a("2Taf")),h=l(a("vZ4D")),v=l(a("l4Ni")),b=l(a("ujKo")),E=l(a("MhPg")),g=r(a("q1tI")),y=a("Hg0r"),q={labelCol:{span:4},wrapperCol:{span:18}},w=(s=(0,y.connect)(function(e){var t=e.users;return{visible:t.editModalVisible,user:t.user}}),s((c=function(e){function t(){var e,a;(0,m.default)(this,t);for(var l=arguments.length,r=new Array(l),u=0;u<l;u++)r[u]=arguments[u];return a=(0,v.default)(this,(e=(0,b.default)(t)).call.apply(e,[this].concat(r))),a.handleCancel=function(){a.props.dispatch({type:"users/setEditModalVisible",payload:!1}),a.props.dispatch({type:"users/setUser",payload:{}}),a.props.form.resetFields()},a.handleSubmit=function(e){e.preventDefault(),a.props.form.validateFields(function(e,t){if(!e){var l=a.props,r=l.user,u=l.dispatch;u({type:"users/updateUser",payload:(0,p.default)({id:r?r.id:void 0},t)})}})},a}return(0,E.default)(t,e),(0,h.default)(t,[{key:"render",value:function(){var e=this.props,t=e.visible,a=e.user,l=e.form,r=l.getFieldDecorator;return g.default.createElement(u.default,{visible:t,width:600,title:a.id?"\u7f16\u8f91\u7528\u6237":"\u6dfb\u52a0\u7528\u6237",maskClosable:!1,onCancel:this.handleCancel,onOk:this.handleSubmit},g.default.createElement(n.default,null,g.default.createElement(n.default.Item,(0,i.default)({},q,{label:"\u7528\u6237\u540d",required:!0}),r("username",{rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u7528\u6237\u540d"}],initialValue:a.username})(g.default.createElement(f.default,{placeholder:"\u8bf7\u8f93\u5165\u7528\u6237\u540d"}))),g.default.createElement(n.default.Item,(0,i.default)({},q,{label:"\u5bc6\u7801",required:!0}),r("password",{rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u5bc6\u7801"}],initialValue:a.password})(g.default.createElement(f.default,{placeholder:"\u8bf7\u8f93\u5165\u5bc6\u7801",type:"password"}))),g.default.createElement(n.default.Item,(0,i.default)({},q,{label:"\u7c7b\u578b",required:!0}),r("type",{rules:[{required:!0,message:"\u8bf7\u9009\u62e9\u7c7b\u578b"}],initialValue:a.type})(g.default.createElement(d.default,{placeholder:"\u8bf7\u9009\u62e9\u7c7b\u578b"},g.default.createElement(d.default.Option,{value:0},"\u666e\u901a\u7528\u6237"),g.default.createElement(d.default.Option,{value:1},"\u5355\u4f4d\u64cd\u4f5c\u5458"),g.default.createElement(d.default.Option,{value:2},"\u5ba1\u6838\u5458")))),g.default.createElement(n.default.Item,(0,i.default)({},q,{label:"\u767b\u5f55\u6743\u9650",required:!0}),r("status",{rules:[{required:!0,message:"\u8bf7\u9009\u62e9\u767b\u5f55\u6743\u9650"}],initialValue:a.status})(g.default.createElement(d.default,{placeholder:"\u8bf7\u9009\u62e9\u767b\u5f55\u6743\u9650"},g.default.createElement(d.default.Option,{value:0},"\u542f\u7528"),g.default.createElement(d.default.Option,{value:1},"\u7981\u7528")))),g.default.createElement(n.default.Item,(0,i.default)({},q,{label:"\u59d3\u540d",required:!0}),r("name",{rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u59d3\u540d"}],initialValue:a.name})(g.default.createElement(f.default,{placeholder:"\u8bf7\u8f93\u5165\u59d3\u540d"}))),g.default.createElement(n.default.Item,(0,i.default)({},q,{label:"\u5730\u5740",required:!0}),r("address",{rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u5730\u5740"}],initialValue:a.address})(g.default.createElement(f.default,{placeholder:"\u8bf7\u8f93\u5165\u5730\u5740"}))),g.default.createElement(n.default.Item,(0,i.default)({},q,{label:"\u7535\u8bdd",required:!0}),r("phone",{rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u7535\u8bdd"}],initialValue:a.phone})(g.default.createElement(f.default,{placeholder:"\u8bf7\u8f93\u5165\u7535\u8bdd"})))))}}]),t}(g.Component),o=c))||o),C=n.default.create()(w);t.default=C},"cS9+":function(e,t,a){"use strict";var l=a("g09b");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=l(a("ymoC")),u=r.default;t.default=u},vdin:function(e,t,a){e.exports={"header-bar":"antd-pro-pages-users-index-header-bar","table-wrapper":"antd-pro-pages-users-index-table-wrapper",tableList:"antd-pro-pages-users-index-tableList",tableListOperator:"antd-pro-pages-users-index-tableListOperator",tableListForm:"antd-pro-pages-users-index-tableListForm",submitButtons:"antd-pro-pages-users-index-submitButtons"}},ymoC:function(e,t,a){"use strict";var l=a("g09b");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("2qtc");var r=l(a("kLXV")),u=l(a("q1tI")),d=function(e){var t=e.visible,a=e.onCancel;e.targetUser;return u.default.createElement(r.default,{visible:t,okText:"\u5ba1\u6838\u901a\u8fc7",cancelText:"\u5ba1\u6838\u9a73\u56de",onOk:function(){},onCancel:a},u.default.createElement("img",{src:"",alt:""}))},n=d;t.default=n}}]);