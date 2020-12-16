//v.1.6 build 80512

/*
Copyright DHTMLX LTD. http://www.dhtmlx.com
To use this component please contact sales@dhtmlx.com to obtain license
*/





 dhtmlXTreeObject.prototype.setOnLoadingStart_lf=function(func){if (typeof(func)=="function") this.onXLS_2=func;else this.onXLS_2=eval(func)};dhtmlXTreeObject.prototype.setOnLoadingEnd_lf=function(func){if (typeof(func)=="function") this.onXLE_2=func;else this.onXLE_2=eval(func)};dhtmlXTreeObject.prototype.enableLoadingItem=function(text) {this.setOnLoadingStart(this._showFakeItem);this.setOnLoadingEnd(this._hideFakeItem);this.setOnLoadingStart=this.setOnLoadingStart_lf;this.setOnLoadingEnd=this.setOnLoadingEnd_lf;this._tfi_text=text||"Loading..."};dhtmlXTreeObject.prototype._showFakeItem=function(tree,id) {if (this.onXLS_2)this.onXLS_2(tree,id);if ((id===null)||(this._globalIdStorageFind("fake_load_xml_"+id))) return;this.insertNewItem(id,"fake_load_xml_"+id,this._tfi_text)};dhtmlXTreeObject.prototype._hideFakeItem=function(tree,id) {if (this.onXLE_2)this.onXLE_2(tree,id);if (id===null)return;this.deleteItem("fake_load_xml_"+id)};//(c)dhtmlx ltd. www.dhtmlx.com
//v.1.6 build 80512

/*
Copyright DHTMLX LTD. http://www.dhtmlx.com
To use this component please contact sales@dhtmlx.com to obtain license
*/