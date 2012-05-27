/*
'**********************************************
' 文件名: 免费天气插件jquery版本
' 版本:  mmWeather1.0
' 作者:  走过四季
' 电子邮件: maomaoysq@sohu.com
' 日期:  2010年01月01日
' 声明:  
'   本代码可以自由使用，但请保留此版权声明信息
'   如果您对本代码进行修改增强，
'   请发送一份给俺。
'**********************************************
 */

//jsCity、jsWeather为您html中的元素ID
function setWeather(tID, tCity, tTip, tWendu, tFengli) {
	$("#jsCity").html(
			"<a href='http://www.weather.com.cn/html/weather/" + tID
					+ ".shtml' target=_bank>" + tCity + "</a>");
	$("#jsWeather").html(tTip + " " + tWendu);
}

var cityid, city1, city2, weaXML, weaHTML
weaXML = "http://service.weather.com.cn/plugin/"
weaHTML = "http://m.weather.com.cn/data/"
$().ready(function() {
	cityid = $.cookie('wea_cityid');
	if (cityid == null) {
		LoadJS("http://fw.qq.com:80/ipaddress", function() {
			if (typeof IPData != "undefined") {
				city1 = IPData[2];
				city2 = IPData[3];
				city1 = city1.replace("省", "")
				city1 = city1.replace("市", "");
				if (city2 == "")
					city2 = city1;
				$.cookie('wea_cityip', IPData[0]);
				getLocalCity("data/city.xml", 0);
			}
		});
	} else {
		getWeather(cityid);
	}
});
function getLocalCity(turl, b) {
	$.ajax({
		type : "GET",
		url : weaXML + turl,
		dataType : "text",
		success : function(msg) {
			var cityArr = msg.split(",");
			for ( var i = 0; i < cityArr.length; i++) {
				cid = cityArr[i].split("|")[0];
				if (b < 3) {
					if (cityArr[i].split("|")[1] == city1
							|| cityArr[i].split("|")[1] == city2) {
						getLocalCity("data/city" + cid + ".xml", b + 1);
						break;
					}
				} else {
					cid = cityArr[i].split("|")[1];
					getWeather(cid);
					break;
				}
			}
		}
	});
}
function getWeather(cid) {
	$.cookie('wea_cityid', cid, {
		expires : 365
	});
	var weajs = weaHTML + cid + '.html';
	$.getJSON(weajs, function(objJson) {
		var cityname = objJson.weatherinfo.city; // 上海
		var id = objJson.weatherinfo.cityid; // 101020100
		var cityinfo1 = objJson.weatherinfo.weather1; // 晴转多云
		var cityinfo2 = objJson.weatherinfo.weather2;
		var wd1 = objJson.weatherinfo.wind1; // 北风3-4级
		var wd2 = objJson.weatherinfo.wind2;
		var fl1 = objJson.weatherinfo.fl1; // 3-4级
		var fl2 = objJson.weatherinfo.fl2;
		var temp1 = objJson.weatherinfo.temp1; // 4℃~-1℃
		var temp2 = objJson.weatherinfo.temp2;
		var img1 = objJson.weatherinfo.img1;
		var img2 = objJson.weatherinfo.img2;
		var img3 = objJson.weatherinfo.img3;
		var img4 = objJson.weatherinfo.img4;
		var index = objJson.weatherinfo.index;
		var index_d = objJson.weatherinfo.index_d;
		var index_xc = objJson.weatherinfo.index_xc;
		var index_uv = objJson.weatherinfo.index_uv;
		var date = objJson.weatherinfo.date;
		var date_y = objJson.weatherinfo.date_y;
		var imgtitle1 = objJson.weatherinfo.img_title1;
		var imgtitle2 = objJson.weatherinfo.img_title2;
		var imgsingle = objJson.weatherinfo.img_single;
		var imgtitlesingle = objJson.weatherinfo.img_title_single;

		setWeather(id, cityname, cityinfo1, temp1, wd1)
	});
}
function LoadJS(jsUrl, fCallBack) {
	var _script = document.createElement('script');
	_script.setAttribute('type', 'text/javascript');
	_script.setAttribute('charset', 'utf-8');
	_script.setAttribute('src', jsUrl);
	document.getElementsByTagName('head')[0].appendChild(_script);
	if (typeof fCallBack != "undefined") {
		if ($.browser.msie) {
			_script.onreadystatechange = function() {
				if (this.readyState == 'loaded'
						|| this.readyState == 'complete') {
					fCallBack();
				}
			};
		} else if ($.browser.mozilla) {
			_script.onload = function() {
				fCallBack();
			};
		} else {
			fCallBack();
		}
	}
}
