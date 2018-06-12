/*！
 * 预加载控件和控件的配置信息，以及对控件初始化
 */
define([
    "jquery", "underscore", "backbone","bootstrap"
    , "svg"
    , "svg.draggable"
    , "jquery-ui"
    , "jquery.datetimeduck"
    , "jquery.spinner"
    , "select2"
    , "collections/snippets"
    , "collections/my-form-snippets"
    , "collections/SectionCollection"
    , "models/CrfModel", "models/SectionModel"
    , "views/GlobalView"
    , "views/button"
    , "views/TargetTabsView"
    , "views/PreviewView"
    , "views/conditions/ruleApply"
    , "text!data/placeholder.json"
    , "text!data/input.json", "text!data/select.json", "text!data/selectmultiple.json", "text!data/buttons.json"
    , "text!data/radio.json", "text!data/checkbox.json"
    , "text!data/textarea.json", "text!data/date.json", "text!data/datetime.json", "text!data/file.json"
    , "text!data/title.json", "text!data/frame.json", "text!data/calculate.json"
    , "text!data/telphone.json"
    , "text!data/table.json"
    , "text!data/inputmail.json"
    , "text!data/numbervalue.json"
    , "text!data/pictureshow.json"
    , "text!templates/app/render.htm", "text!templates/app/about.htm"
    , "text!templates/popover/popover-modal.htm"
    , "app/crf-main"
    , "helper/ReadURL"
    , "helper/Utils"
    , "helper/ViewUtils"
    , "helper/ModelUtils"
    , "helper/pubsub"
    , "views/itemTree"
    , "htmlMultiSelectSuggest"
], function ($, _, Backbone,bootstrap
    , Svg
    , SvgDraggable
    , jqueryUI
    , jqueryDatetime
    , jquerySpinner
    , Select2
    , SnippetsCollection
    , MyFormSnippetsCollection
    , SectionCollection
    , CrfModel, SectionModel
    , GlobalView
    , ButtonView
    , TargetTabsView
    , PreviewView
    , RuleApply
    , PlaceHolder
    , inputJSON, selectJSON, selectMultipleJSON, buttonsJSON
    , radioJSON, checkboxJSON
    , textAreaJSON, dateJSON, dateTimeJSON, fileJSON
    , titleJSON, frameJSON, calculateJSON
    , telphoneJSON
    , tableJSON
    , inputMailJSON
    , numberValue
    , pictureShowJson
    , renderTab, aboutTab
    , _PopoverModal
    , crfMain
    , ReadURL
    , Utils
    , ViewUtils
    , ModelUtils
    , PubSub
    , ItemTree
    , htmlMultiSelectSuggest
) {
    return {
        prevContext: undefined,

        initialize: function () {
            var that = this;
            that.customFun();
            that.crfInfo = ReadURL.initialCRF();
            new ButtonView({
                title: "标题"
                , collection: new SnippetsCollection(this.toModel(titleJSON))
                , isBase: true
            });
            new ButtonView({
                title: "组合"
                , collection: new SnippetsCollection(this.toModel(frameJSON))
                , isBase: true

            });
            new ButtonView({
                title: "单行文本框"
                , collection: new SnippetsCollection(this.toModel(inputJSON))
                , isBase: true
            });
            new ButtonView({
                title: "文本域"
                , collection: new SnippetsCollection(this.toModel(textAreaJSON))
                , isBase: true
            });
            new ButtonView({
                title: "单选下拉框"
                , collection: new SnippetsCollection(this.toModel(selectJSON))
                , isBase: true
            });
            new ButtonView({
                title: "多选下拉框"
                , collection: new SnippetsCollection(this.toModel(selectMultipleJSON))
                , isBase: true
            });
            new ButtonView({
                title: "单选按钮"
                , collection: new SnippetsCollection(this.toModel(radioJSON))
                , isBase: true
            });
            new ButtonView({
                title: "复选按钮"
                , collection: new SnippetsCollection(this.toModel(checkboxJSON))
                , isBase: true
            });
            new ButtonView({
                title: "占位符"
                , collection: new SnippetsCollection(this.toModel(PlaceHolder))
                , isBase: true
            });
            new ButtonView({
                title: "文件上传"
                , collection: new SnippetsCollection(this.toModel(fileJSON))
                , isExtend: true
            });
            new ButtonView({
                title: "日期"
                , collection: new SnippetsCollection(this.toModel(dateJSON))
                , isExtend: true
            });
            new ButtonView({
                title: "时间"
                , collection: new SnippetsCollection(this.toModel(dateTimeJSON))
                , isExtend: true
            });
            new ButtonView({
                title: "数值"
                , collection: new SnippetsCollection(this.toModel(numberValue))
                , isExtend: true
            });
            new ButtonView({
                title: "电话"
                , collection: new SnippetsCollection(this.toModel(telphoneJSON))
                , isExtend: true
            });
            new ButtonView({
                title: "邮箱"
                , collection: new SnippetsCollection(this.toModel(inputMailJSON))
                , isExtend: true
            });
            new ButtonView({
                title: "表格"
                , collection: new SnippetsCollection(this.toModel(tableJSON))
                , isExtend: true
            });
            new ButtonView({
             title: "图例"
             , collection: new SnippetsCollection(this.toModel(pictureShowJson))
             , isExtend: true
             });

            if(ReadURL.getParams().operateType != "preView"){
                //公共控件入口
                new ItemTree();//控件树添加事件
            }
            //Section容器
            window.sectionCollection = new SectionCollection();
            //控件自增序列号
            window.ControlSerialNumber = 0;
            if (that.crfInfo.data) {
                window.ControlSerialNumber = that.crfInfo.data.controlSerialNumber;
                window.crfModel = new CrfModel(that.crfInfo.data);
            } else {
                window.crfModel = new CrfModel(that.crfInfo);
            }
            crfModel.set("sections", sectionCollection);
            if (that.crfInfo.data && that.crfInfo.data.hasOwnProperty("sections")) {
                for (var i = 0; i < that.crfInfo.data.sections.length; i++) {
                    var sectionModel = new SectionModel(that.crfInfo.data.sections[i]);
                    sectionCollection.add(sectionModel);
                    Utils.revertBeautifyCrf(sectionModel);
                    Utils.beautifyCrf(sectionModel);
                }
            } else {
                //默认生成一个section
                crfMain.addSection(sectionCollection);
            }

            //模板
            Utils.refreshTemplateList("../crf/getTemplateList.html", {});
            new GlobalView();
            //Make the first tab active!
            $("#formtabs").find("li").removeClass("active");
            $("#components").find(".tab-pane").removeClass("active");
            $("#itemComponents").find(".tab-pane").removeClass("active");
            $("#formtabs").find("li").first().addClass("active");
            $("#components").find(".tab-pane").first().addClass("active");
            $("#itemComponents").find(".tab-pane").first().addClass("active");

            crfMain.loadInit(sectionCollection);

            // 初始化FieldList  保存表单控件信息
            ModelUtils.initFieldList();
            //更改CRF配置页签属性
            $("#tab_content_crf .form-control").change(function (event) {
                event.preventDefault();
                var map = {
                    "crf_name": "name",
                    "crf_description": "desc",
                    "crf_version": "ver",
                    "crf_revision_notes": "verlog"
                };
                var id = $(this).attr("id");
                var newValue = $(this).val();
                crfModel.set(map[id], newValue);
            });
            $("#formtabs").on('show.bs.tab', function (e) {
                e.target // newly activated tab
                e.relatedTarget // previous active tab
                //可在此处增加校验，也可在数据提交时增加校验
            });
            $("#preview").on("click", function (e) {
                e.preventDefault();
                e.stopPropagation();
                var $newTabs = that.getPrewTabsDomStr(crfModel);
                $("#previewTabs").html($newTabs);
                if(ReadURL.getParams().operateType && ReadURL.getParams().operateType == "preView") {
                    _.each(crfModel.get("sections").models, function (section) {
                        new RuleApply({section: section});
                    });
                }
                // 控制预览的画面和编辑器画面一样宽
                var width = $("#targetFormTabs").width();
                $("#myModal .modal-lg").css("width",(width+36)+"");
                $("#modalTitle").text("CRF预览");
                $('#myModal').modal({
                    "show": true,
                    "backdrop": false
                });
                //点击modal的关闭按钮销毁临时preview对象
                $('#myModal').on('hidden.bs.modal', function (e) {
                    $newTabs.remove();
                    //preview.remove();
                });
                $('#previewTabs [data-toggle="tooltip"]').tooltip();
            });
            $("#saveCrf").on("click", function (e) {
                var crfParams = ReadURL.getParams();
                if (!crfParams.crfSn) {
                    var crfName = $("#crf_name").val();
                    var count = crfMain.checksumCrfNameByStudySn(crfName, crfParams.studySn);
                    if (count > 0) {
                        $("#MSG_021").modal({"show":true}) ;
                        return;
                    }
                }
                if (that.validateCrfJSON(sectionCollection)) {
                    var crfModejJson = ViewUtils.recursiveCrfModel(crfModel.toJSON());
                    
                    var successMsg = crfMain.insertCrf(crfModejJson, crfParams);
                    $("#publishCrf").attr("crfVersionId", successMsg.crfVersionId);
                    $("#publishCrf").attr("flag", successMsg.flag);
                    
                    if(successMsg.flag == true && $("#publishCrf").prop("disabled") == undefined){
	                    var url = window.location.origin + window.location.pathname;
	                    if(crfParams.operateType == null){
	                    	url += "?&operateType=edit";
	                    } else{
	                    	url += "?&operateType=" + crfParams.operateType;
	                    }
	                    url += "&crfVersionId=" + successMsg.crfVersionId;
	                    url += "&crfSn=" + crfParams.crfSn;
	                    url += "&studySn=" + crfParams.studySn;
	                    
	                    window.location.href = url;
                    }
                }
            });
            $("#publishCrf").on("click", function () {
            	$("#prompt").attr("href", "#");
                $("#promptModal").find(".msgContent").text("变更版本状态会删除变更控件对应的数据，确定保存并更改版本状态？");
                $("#prompt").trigger("click");
                $("#promptModal").on("hidden.bs.modal",function(e){

                });
            	
            	$("#promptModal").find(".yesBtn").on("click",function(e){
            		e.preventDefault();
                    $("#promptModal").modal("hide");
                    $(window).unbind("beforeunload");
                  //先保存
                	$("#publishCrf").attr("disabled",false);
                	$("#saveCrf").click();
                	if($("#publishCrf").attr("flag") != "true"){
                		return false;
                	}
                	//后发布
                	var crfParams = ReadURL.getParams();
                	var crfVersionId = $("#publishCrf").attr("crfVersionId");
                    $.ajax({
                        type: "POST",
                        url: "../crf/changeCrfVersionStatus.html",
                        data: {
                            "crfVersionId": crfVersionId,
                            "status": '002'
                        },
                        success: function (result) {
//                        	$("#publishCrf").attr("disabled",true);
//                        	$("#message").attr("href", "#");
//                            $("#msgModal").find(".msgContent").text("发布成功！");
//                            $("#message").trigger("click");
//                            $('#msgModal').on('hidden.bs.modal', function(e) {
//                				// do something...
//                				 $(this).removeData("b
//                				 //window.location.href = "../basicInformation/jumpFormDesign.html?studySn=" + crfParams.studySn
//                			}) 
                        	$("#publishCrf").attr("disabled",true);
                        	//患者表单版本同步
                            $.post("../crf/unifycrfversion.html",{crfVersionId:crfVersionId});
                        	window.location.href = "../basicInformation/jumpFormDesign.html?studySn=" + crfParams.studySn
                        },
                        error: function (result, status) {
                        	$("#publishCrf").attr("disabled",true);
                            $("#message").attr("href", "#");
                            $("#msgModal").find(".msgContent").text("更新失败");
                            $("#message").trigger("click");
                        }
                    });
                });
                /*if (confirm("确定保存并更改版本状态？")) {
                	//先保存
                	$("#publishCrf").attr("disabled",false);
                	$("#saveCrf").click();
                	if($("#publishCrf").attr("flag") != "true"){
                		return false;
                	}
                	//后发布
                	var crfParams = ReadURL.getParams();
                	var crfVersionId = $("#publishCrf").attr("crfVersionId");
                    $.ajax({
                        type: "POST",
                        url: "../crf/changeCrfVersionStatus.html",
                        data: {
                            "crfVersionId": crfVersionId,
                            "status": '002'
                        },
                        success: function (result) {
                        	$("#publishCrf").attr("disabled",true);
                        	window.location.href = "../basicInformation/jumpFormDesign.html?studySn=" + crfParams.studySn
                        },
                        error: function (result, status) {
                        	$("#publishCrf").attr("disabled",true);
                            $("#message").attr("href", "#");
                            $("#msgModal").find(".msgContent").text("更新失败");
                            $("#message").trigger("click");
                        }
                    });
                }*/
            });
            $("#crfRealForm").on("click", function () {
                $("#modalTitle").text("CRF提交表单");
                var crfFormView = new CrfFormView({model: crfModel});
                var saveBtn = $("<button type='button' class='btn btn-primary'>保存</button>");
                saveBtn.on("click", function () {
                    $("#MSG_003").modal({"show":true}) ;
                    $('#myModal').modal("hide");
                });
                $(".modal-footer button").before(saveBtn);
                $('#myModal').modal("show");
                //点击modal的关闭按钮销毁临时preview对象
                $('#myModal').on('hidden.bs.modal', function (e) {
                    $("#modalTitle").text("CRF提交表单");
                    crfFormView.remove();
                    saveBtn.remove();
                });
            });
            $("#tab_content_crf .form-control").change(function (event) {
                crfModel.set(that.getCrfInfo());
            });

            if (ReadURL.getParams().operateType && ReadURL.getParams().operateType == "preView") {
            	//var $newTabs = that.getPrewTabsDomStr(crfModel);
            	//$("#crfDesignPanel").empty();
            	//$("<div/>").appendTo("#crfDesignPanel").addClass("previewContent col-md-8 col-md-offset-2");
            	//$("#crfDesignPanel .previewContent").html($newTabs);
            	//$("#crfOperateContent").hide();
            	//$("#indexDesign").show();
            	
            	//预览改造：保留左侧配置
            	//隐藏移除控件类型页签
            	$("#formtabs").find("a[href='#tab_content_item']").parent().hide();
            	$("#components").find("#tab_content_item").remove();
            	//禁用已有控件拖拽，复制，删除
            	PubSub.off("mySnippetDrag");
            	PubSub.off("snippetRemove");
            	PubSub.off("snippetCopy");
            	PubSub.off("saveSnippetOptionModal");
            	//表单配置禁用
            	$("#components").find("textarea,input").attr("disabled", "disabled");
            	//移除section的添加删除功能
            	$("#formBuilderTabs").find("#li_addSection").remove();
            	$("#formBuilderTabs").find(".tabClose").remove();

                //移除发布
            	$("#crfOperateContent").find("#publishCrf").remove();
            	//预览改造完毕 by wu_yuanayun

            	if(ReadURL.getParams().overView == "true"){
            		//移除保存
                	$("#crfOperateContent").find("#saveCrf").remove();
                    $(".addRuleButton").closest(".form-group").remove();
                	$("#formDesignReturn").attr("href","../basicInformation/jumpFormManagement.html") ;
                }
            }else{
                $(".addRuleButton").closest(".form-group").remove();
            }
            
            
        },
        
        /**
         * 移除填充内容
         */
        removeItemList: function (that) {
            $(that).parent().remove();

        },
        /**
         * 初始化表单校检设置
         */
        initFormValidateSetting: function () {
            $("#form_crf").validate({
                debug: false,
                rules: {
                    crfname: "required",
                    description: "required"
                }
            });
            $("#form_section").validate({
                debug: true,
                rules: {
                    description: "required"
                }
            });
        },
        getCrfInfo: function () {
            return {
                "name": $("#crf_name").val().trim(),
                "desc": $("#crf_description").val().trim(),
                "ver": $("#crf_version").val().trim(),
                "verlog": $("#crf_revision_notes").val().trim()

            }
        }
        , getTemplateListSuccessFun: function (result) {
            var resultJSON = $.parseJSON(result)
            _.each(resultJSON, function (templateJSON) {
                new ButtonView({
                    title: templateJSON.templateName
                    , model: templateJSON
                    , isTemplate: true
                });
            });

        }
        , validateCrfJSON: function (sectionCollection) {
            var flag = true;
            if ($("#crf_name").val().trim() == '') {
                $("#MSG_022").modal({"show":true});
                $("#formtabs").find("li").removeClass("active");
                $("#components").find(".tab-pane").removeClass("active");
                $("#itemComponents").find(".tab-pane").removeClass("active");
                $("#formtabs").find("li").first().addClass("active");
                $("#components").find(".tab-pane").first().addClass("active");
                $("#itemComponents").find(".tab-pane").first().addClass("active");
                return false;
            }
            for (var i = 0; i < sectionCollection.length; i++) {
                if (sectionCollection.at(i).get("name") == '') {
                    //名称为空的Section
                    flag = false;
                    break;
                }
            }
            return flag;

        },
        /**
         * 生成预览dom
         * @param crfModel
         * @returns {*|jQuery|HTMLElement}
         */
        getPrewTabsDomStr: function (crfModel) {
            var preview = new PreviewView({model: crfModel.toJSON()});
            var $newTabs = $($("#targetFormTabs").html());
            $newTabs.find("#li_addSection").remove();
            $newTabs.find(".tabClose").remove();
            $newTabs.find(".tabLeftMove").remove();
            $newTabs.find(".tabRightMove").remove();
            $newTabs.find("li button").remove();
            var liAs = $newTabs.find("li a");
            _.each(liAs, function (liA) {
                var href = $(liA).attr("href");
                var newHref = href + "preview";
                $(liA).attr("href", newHref);
                $newTabs.find(href).attr("id", newHref.replace("#", ""));
            });
            $newTabs.find(".component").removeClass("question-selected");
            $newTabs.find("." + $.datepicker.markerClassName).removeClass($.datepicker.markerClassName);
            $newTabs.find(".topRight").remove();
            $newTabs.find(".addBtn").removeClass("disabled");
            $newTabs.find(".addBtn").addClass("btn btn-primary");
            $newTabs.find(".component").removeAttr("id");
            $newTabs.find(".targetGroup").removeAttr("id");
            $newTabs.find(".group").removeAttr("id");
            $newTabs.find(".imgplaceholder").find("img").hide();;
            return $newTabs;
        }
        ,customFun:function(){
            if(!String.prototype.trim) {
                String.prototype.trim = function () {
                    return this.replace(/^\s+|\s+$/g,'');
                };
            }
        }
        , toModel: function(jsonStr) {
            return _.map(JSON.parse(jsonStr), function(jsonObj){
                var model = ModelUtils.copyOwnProps(jsonObj);
                model["fields"] = ModelUtils.viewFieldsToStorageFields(jsonObj["fields"]);
                return model;
            })
        }
    }

});
