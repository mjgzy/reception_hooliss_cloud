		var u_name=$("#u_name").val();
	//页数点击
	function pageClick(doc,dataJson,totalPage){
		var parentDoc=doc.parent().parent().parent();
		var watchData=parentDoc.prev().find("ul");
		doc.css({"background-color":"#b01330","color":" #fff","border-color":"#b01330"}).siblings()
		.css({"background-color":"white","color":"#997239","border-color":"#ddd"});
		var cPage=parseInt(doc.text());	//获取当前页码
		fanPage(dataJson,cPage-1,watchData,parentDoc,totalPage);
		parentDoc.find(".CurrentPage").val(cPage-1);
	}
	//
	/**
	 * 页数初始化
	 * length:数据总长度
	 * doc:操作的分页元素
	 * pageSize:每页显示条数
	 * return:总页数
	 */
	function pageInit(length,doc,pageSize){
		doc.find(".page_size").html("");
		doc.find(".pageTotal").html("");
		doc.find(".pre").hide();
		doc.find(".CurrentPage").val("0");
		var totalPage=0;
		if(length%pageSize==0){
			totalPage=length/pageSize;
		}else{
			totalPage=parseInt(length/pageSize)+1;
		}
		if (totalPage==1) {
			doc.find(".next").hide();
		}else{
			doc.find(".next").show();
		}
		doc.find(".page_size").append("<span  class='cur pagenum' >1</span>&nbsp;");
		doc.find(".pageTotal").text(totalPage);
		if(totalPage>pageSize/2){
			
			for(var i=2;i<5;i++){
				doc.find(".page_size").append("<a class='pagenum'>"+i+"</a>&nbsp;");
			}
			if(totalPage>pageSize){
				doc.find(".page_size").append("<span>&hellip;</span>&nbsp;").append("<a >"+parseInt(totalPage/2)+"</a>&nbsp;")
				.append("<span>&hellip;</span>").append("<a >"+totalPage+"</a>&nbsp;");
			}else{
				doc.find(".page_size").append("<span>&hellip;</span>&nbsp;").append("<a class='pagenum' >"+totalPage+"</a>&nbsp;");
			}
		}else{
			for(var i=2;i<totalPage+1;i++){
				doc.find(".page_size").append("<a class='pagenum'>"+i+"</a>&nbsp;");
			}
		}
		return totalPage;	//返回总页数
	}
	/**
	 * 
	 * @param data:后台返回的数据
	 * @param cPage:当前页
	 * @param doc:操作分页元素
	 */
	function fanPage(data,cPage,doc,parentDoc,totalPage){
		if(cPage!=0){
			parentDoc.find(".pre").show();
		}else{
			parentDoc.find(".pre").hide();
		}
		if (cPage==totalPage-1) {
			parentDoc.find(".next").hide();
		}else{
			parentDoc.find(".next").show();
		}
		//来自调用页面的js方法
		PageTurning(data,cPage,doc);
	
	}
	
	/**
	 * 点击上一页方法
	 * @param dataJson:后台数据
	 * @param watchData:操作的数据显示元素
	 * @param doc:分页标签
	 */
	function preClick(dataJson,watchData,doc,totalPage,pageSize){
		doc.find(".next").show();
		var cPage=parseInt(doc.find(".CurrentPage").val());
		var cupage=doc.find(".CurrentPage").val();
		if(cupage==0){
			doc.find(".pre").hide();
		}else{
			//调用本js的fanPage方法
			fanPage(dataJson,cPage-1,watchData,doc,pageSize,totalPage);
			doc.find(".CurrentPage").val(cPage-1);
			doc.find(".page_size").children().eq(cPage-1).css({"background-color":"#b01330","color":" #fff","border-color":"#b01330"}).siblings()
		.css({"background-color":"white","color":"#997239","border-color":"#ddd"});
		}
	}
	/**
	 * 点击下一页
	 * @param dataJson:后台数据
	 * @param watchData:操作的数据显示元素
	 * @param doc:分页标签
	 */
	function  nextClick(dataJson,watchData,doc,totalPage,pageSize){
		doc.find(".pre").show();
		var cPage=parseInt(doc.find(".CurrentPage").val());
			console.log(cPage);
			if(cPage==totalPage-2){
				doc.find(".next").hide();
				doc.find(".page_size").children().eq(cPage+1).css({"background-color":"#b01330","color":" #fff","border-color":"#b01330"}).siblings()
	.css({"background-color":"white","color":"#997239","border-color":"#ddd"});
			}else{
				doc.find(".page_size").children().eq(cPage+1).css({"background-color":"#b01330","color":" #fff","border-color":"#b01330"}).siblings()
		.css({"background-color":"white","color":"#997239","border-color":"#ddd"});
			}
			fanPage(dataJson,cPage+1,watchData,doc,totalPage);
			doc.find(".CurrentPage").val(cPage+1);
			
		
	}
	