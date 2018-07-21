/********************************************************************************************************
 * 																										*
 * 	对jquery的datagrid进行扩展。																			*
 * 																										*
 ***功能点说明********************************************************************************************
 * 	1.单元格的tips功能    -- Created with yjl														*
 * 																										*	
 * 																										*		
 * 																										*
 ********************************************************************************************************/	


	/**
     * 
     * datagrid 扩展
     */
    $.extend($.fn.datagrid.methods, {
    	/******单元格的tips功能 [start]********************************************************************************/
        /**
         * 开打提示功能（基于1.3.3+版本）
         * @param {} jq
         * @param {} params 提示消息框的样式
         * @return {}
         */
        doCellTip:function (jq, params) {
            function showTip(showParams, td, e, dg) {
                //无文本，不提示。
                if ($(td).text() == "") return;
                params = params || {};
                var options = dg.data('datagrid');
                var styler = 'style="';
                if(showParams.width){
                    styler = styler + "width:" + showParams.width + ";";
                }
                if(showParams.maxWidth){
                    styler = styler + "max-width:" + showParams.maxWidth + ";";
                }
                if(showParams.minWidth){
                    styler = styler + "min-width:" + showParams.minWidth + ";";
                }
                styler += "word-break:break-all;word-wrap:break-word;";
                styler = styler + '"';
                showParams.content = '<div class="tipcontent" ' + styler + '>' + $.util.tagOutput(showParams.content) + '</div>';
                $(td).tooltip({
                    content:showParams.content,
                    trackMouse:true,
                    position:params.position,
                    onHide:function () {
                        $(this).tooltip('destroy');
                    },
                    onShow:function () {
                        var tip = $(this).tooltip('tip');
                        if(showParams.tipStyler){
                            tip.css(showParams.tipStyler);
                        }
                        if(showParams.contentStyler){
                            tip.find('div.tipcontent').css(showParams.contentStyler);
                        }
                    }
                }).tooltip('show');
            };
            return jq.each(function () {
                var grid = $(this);
                var options = $(this).data('datagrid');
                if (!options.tooltip) {
                    var panel = grid.datagrid('getPanel').panel('panel');
                    panel.find('.datagrid-body').each(function () {
                        var delegateEle = $(this).find('> div.datagrid-body-inner').length ? $(this).find('> div.datagrid-body-inner')[0] : this;
                        $(delegateEle).undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove').delegate('td[field]', {
                            'mouseover':function (e) {
                                //if($(this).attr('field')===undefined) return;
                                var that = this;
                                var setField = null;
                                if(params.specialShowFields && params.specialShowFields.sort){
                                    for(var i=0; i<params.specialShowFields.length; i++){
                                        if(params.specialShowFields[i].field == $(this).attr('field')){
                                            setField = params.specialShowFields[i];
                                        }
                                    }
                                }
                                if(setField==null){
                                    options.factContent = $(this).find('>div').clone().css({'margin-left':'-5000px', 'width':'auto', 'display':'inline', 'position':'absolute'}).appendTo('body');
                                    var factContentWidth = options.factContent.width();
                                    params.content = $(this).text();
                                    if (params.onlyShowInterrupt) {
                                        if (factContentWidth > $(this).width()) {
                                            showTip(params, this, e, grid);
                                        }
                                    } else {
                                        showTip(params, this, e, grid);
                                    }
                                }else{
                                    panel.find('.datagrid-body').each(function(){
                                        var trs = $(this).find('tr[datagrid-row-index="' + $(that).parent().attr('datagrid-row-index') + '"]');
                                        trs.each(function(){
                                            var td = $(this).find('> td[field="' + setField.showField + '"]');
                                            if(td.length){
                                                params.content = td.text();
                                            }
                                        });
                                    });
                                    showTip(params, this, e, grid);
                                }
                            },
                            'mouseout':function (e) {
                                if (options.factContent) {
                                    options.factContent.remove();
                                    options.factContent = null;
                                }
                            }
                        });
                    });
                }
            });
        },
        /**
         * 关闭消息提示功能（基于1.3.3版本）
         * @param {} jq
         * @return {}
         */
        cancelCellTip:function (jq) {
            return jq.each(function () {
                var data = $(this).data('datagrid');
                if (data.factContent) {
                    data.factContent.remove();
                    data.factContent = null;
                }
                var panel = $(this).datagrid('getPanel').panel('panel');
                panel.find('.datagrid-body').undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove');
            });
        },
        /******单元格的tips功能 [end]********************************************************************************/
        
        /******单元格的编辑功能 [start]******************************************************************************/
        editCell:function(jq, param) {
			return jq.each(function() {
				var opts = $(this).datagrid('options');
				var fields = $(this).datagrid('getColumnFields', true).concat(
						$(this).datagrid('getColumnFields'));
				for ( var i = 0; i < fields.length; i++) {
					var col = $(this).datagrid('getColumnOption', fields[i]);
					col.editor1 = col.editor;
					if (fields[i] != param.field) {
						col.editor = null;
					}
				}
				$(this).datagrid('beginEdit', param.index);
				for ( var i = 0; i < fields.length; i++) {
					var col = $(this).datagrid('getColumnOption', fields[i]);
					col.editor = col.editor1;
				}
			});
		},
        /******单元格的编辑功能 [end]******************************************************************************/
		/**********************rownumbers 自适应宽度[start]**************************************************************/
		fixRownumber:function (jq) {
	        return jq.each(function () {
	            var panel = $(this).datagrid("getPanel");
	            //获取最后一行的number容器,并拷贝一份
	            var clone = $(".datagrid-cell-rownumber", panel).last().clone();
	            //由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
	            clone.css({
	                "position" : "absolute",
	                left : -1000
	            }).appendTo("body");
	            var width = clone.width("auto").width();
	            //默认宽度是25,所以只有大于25的时候才进行fix
	            if (width > 25) {
	                //多加5个像素,保持一点边距
	                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
	                //修改了宽度之后,需要对容器进行重新计算,所以调用resize
	                $(this).datagrid("resize");
	                //一些清理工作
	                clone.remove();
	                clone = null;
	            } else {
	                //还原成默认状态
	                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
	            }
	        });
	    }
		/**********************rownumbers 自适应宽度[end]**************************************************************/
    });
    
 /********************************************************************************************************************************/   
 /********************************************************************************************************************************/   
   function JqueryDatagridExt(datagridObj){
	   this.datagridObj = datagridObj;
	   this.jd_ex_editIndex=undefined;//用于单元格编辑
	   
	   /******单元格的Tips功能******************************************************************************/
	    /**
	     * created by yjl
	     * 初始化单元格的tips，只需在加载完列表后调用此函数即可
	     * @param datagridName datagrid的名称
	     * @param onlyShowInterrupt true：内容超过单元格长度才显示，false：所有都显示
	     * @return
	     */
	   this.initCellTips = function(onlyShowInterrupt){
		   this.datagridObj.datagrid('doCellTip', {
	            onlyShowInterrupt : onlyShowInterrupt,
	            position : 'bottom',
	            maxWidth : '500px',
	            display:'block',
	            /*specialShowFields : [ {
	                field : 'status',
	                showField : 'statusDesc'
	            } ],*/
	            tipStyler : {
	                'backgroundColor' : '#FAFAFA',
	                borderColor : '#000000',
	                boxShadow : '1px 1px 3px #292929'
	            }
	        });
	   };
	   
	   /******单元格的编辑功能******************************************************************************/
	   this.endEditing = function() {
			if (this.jd_ex_editIndex == undefined) {
				return true;
			}
			if (this.datagridObj.datagrid('validateRow', this.jd_ex_editIndex)) {
				this.datagridObj.datagrid('endEdit', this.jd_ex_editIndex);
				this.jd_ex_editIndex = undefined;
				return true;
			} else {
				return false;
			}
		};
		
		/**
		 * 单元格编辑(在datagrid创建时加上该函数的调用即可)
		 * @param datagridObj
		 * @param index
		 * @param field
		 * @return
		 */
	    this.onClickCell = function(index, field){
			if (this.endEditing()){
				this.datagridObj.datagrid('selectRow', index).datagrid('editCell', {
					index : index,
					field : field
				});
				this.jd_ex_editIndex = index;
			}
		};
		
		/**
		 *使rownumber列号自适应宽度到5位数 
		 */
		this.fixRowNumbers = function(){
			if(this.datagridObj != null){
				this.datagridObj.datagrid("fixRownumber");
			}
		};
	   
   }
   
     