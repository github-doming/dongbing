/**
 * Created by xhgx on 2017/4/24.
 * zhge yilai nnnn xian
 */
/*本页面依赖于public-method.js*/
/*弹框的基本对象*/
function Layer(options){
    this.config = {
        layerBoxClass :"layerBox",
        layerclass:"",
        width:300,
        height:200,
        zIndex:1000,
        alerttit:"信息",
        setOverflow:"overflow-y:scroll",
        callback: function () { }
    }
    $.extend(this.config,options);

}
Layer.prototype = {
    /*创建弹出框*/
    _createDialog:function(state){
        var that = this;
        var str = "";
        that.config.zIndex++;
        var s = UDP.Public.view();
        str = '<div class="overlay" style="z-index:'+that.config.zIndex+'"><div class="animated zoomIn '+that.config.layerBoxClass+'" style = "width:'+ this.config.width+'px;height:'+this.config.height+'px"><h4 class="layerHeader">'+this.config.alerttit+'<a href="javascript:;" class="close_btn"></a></h4><div class="layerContianer '+this.config.layerclass+'" style="'+this.config.setOverflow+'"><input type="submit" value="无限弹出" class="inp_btn"/></div><span class="layer-size"></span></div></div>';
        $("body").append(str);
        $(".close_btn").click(function (){
            that.delDialog($(this));
        });
        $("."+that.config.layerBoxClass).eq($(".overlay").size()-1).css({left: (s.w- this.config.width)/2+"px",top: (s.h-this.config.height)/2+"px"});
        if(that.config.callback){
            that.config.callback.apply(this,[]);
        }
        return str;
    },
    /*移除弹框*/
    delDialog:function(ele){
        $(ele).parents(".overlay").remove();
    },
    /*移动弹框*/
    moveDialog:function(ele){
        var that = this;
        var s = UDP.Public.view();
        $(".layerHeader").mousedown(function(e){
            var theme = this;
            var moveEle = $(theme).parents("."+that.config.layerBoxClass);
            e.stopPropagation();
            e.preventDefault();
            var x = e.clientX - moveEle.offset().left;
            var y = e.clientY - moveEle.offset().top;
            $(document).mousemove(function(e){
                var lf = e.clientX-x;
                var tp = e.clientY-y;
                lf=lf<0 ? 0:lf;
                lf=lf>(s.w - moveEle.width()) ? (s.w - moveEle.width()):lf;
                tp=tp<0 ? 0:tp;
                tp=tp>(s.h - moveEle.height()) ? (s.h - moveEle.height()):tp;
                moveEle.css({left: lf+"px",top:tp+"px"});
            });
            $(document).mouseup(function(){
                $(this).unbind("mousemove");
            });
        });
    },
    /*手动调整弹框大小*/
    revampSize:function(){
        var that = this;
        var s = UDP.Public.view();
        $(".layer-size").mousedown(function(e){
            var theme = this;
            var moveEle = $(theme).parents("."+that.config.layerBoxClass);
            var x = e.clientX - moveEle.width();
            var y = e.clientY - moveEle.height();
           $(document).mousemove(function(e){
               var width = e.clientX-x+"px";
               var height = e.clientY-y+"px";
               width=width<0 ? 0:width;
               width=width>s.w ? s.w :width;
               height=height<0 ? 0:height;
               height=height>s.h ? s.h :height;
               moveEle.css({width:width,height:height});
           });
            $(document).mouseup(function(){
                $(document).unbind("mousemove");
            });
        });
    }
    ,show:function(){
        this._createDialog();
        this.moveDialog();
        this.revampSize();
    }
}

;(function(win){
    if(win["UDP"]){
        win["UDP"].Layer = Layer;
    }else{
        win.UDP = {Layer:Layer};
    }
})(window);


