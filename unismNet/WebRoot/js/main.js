$(function(){
    bindTabEvent();
    bindTabMenuEvent();
});
//绑定tab的双击事件、右键事件
function bindTabEvent(){
    $(".tabs-inner").live('dblclick', function(){
        var subtitle = $(this).children("span").text();
        if ($(this).next().is('.tabs-close')) {
            $('#tabs').tabs('close', subtitle);
        }
    });
    $(".tabs-inner").live('contextmenu', function(e){
        $('#mm').menu('show', {
            left: e.pageX,
            top: e.pageY
        });
        var subtitle = $(this).children("span").text();
        $('#mm').data("currtab", subtitle);
        return false;
    });
}

//绑定tab右键菜单事件
function bindTabMenuEvent(){
    //关闭当前
    $('#mm-tabclose').click(function(){
        var currtab_title = $('#mm').data("currtab");
        if ($(this).next().is('.menu-item')) {
            $('#easyui-tabs').tabs('close', currtab_title);
        }
    });
    //全部关闭
    $('#mm-tabcloseall').click(function(){
        $('.tabs-inner span').each(function(i, n){
            if ($(this).parent().next().is('.tabs-p-tool')) {
                var t = $(n).text();
                $('#easyui-tabs').tabs('close', t);
            }
        });
    });
    //关闭除当前之外的TAB
    $('#mm-tabcloseother').click(function(){
        var currtab_title = $('#mm').data("currtab");
        $('.tabs-inner span').each(function(i, n){
            if ($(this).parent().next().is('.tabs-p-tool')) {
                var t = $(n).text();
                if (t != currtab_title) 
                    $('#easyui-tabs').tabs('close', t);
            }
        });
    });
    //关闭当前右侧的TAB
    $('#mm-tabcloseright').click(function(){
        var nextall = $('.tabs-selected').nextAll();
        if (nextall.length == 0) {
            alert('已经是最后一个了');
            return false;
        }
        nextall.each(function(i, n){
            var t = $('a:eq(0) span', $(n)).text();
            $('#easyui-tabs').tabs('close', t);
        });
        return false;
    });
    //关闭当前左侧的TAB
    $('#mm-tabcloseleft').click(function(){
        var prevall = $('.tabs-selected').prevAll();
        if (prevall.length == 1) {
            alert('已经是第一个了');
            return false;
        }
        prevall.each(function(i, n){
            var t = $('a:eq(0) span', $(n)).text();
            $('#easyui-tabs').tabs('close', t);
        });
        return false;
    });
}

$(document).ready(function(){
    $('.easyui-tree').tree({
        onClick: function(node){
            //alert(node.text);
            // add a new tab panel 
            if ($('.easyui-tabs').tabs('exists', node.text)) {
                $('.easyui-tabs').tabs('select', node.text);
            }
            else {
				if (node.id == "61") {
					$('.easyui-tabs').tabs('add', {
	                    title: node.text,
	                    href: '/unismNet/control/product/product_list.action',
	                    content: '',
	                    closable: true,
	                    tools: [{}]
                	});
				}else {
					$('.easyui-tabs').tabs('add', {
	                    title: node.text,
	                    href: '/unismNet/control/scene/scene_list.action',
	                    content: '',
	                    closable: true,
	                    tools: [{}]
                	});
				}
                
            }
            
            
        }
    });
    
    $('#easyui-tabs').tabs({
        border: true,
        onSelect: function(title){
            //alert(title + ' is selected');
        }
    });
});

$(function(){
    windowResize();
    $(window).resize(function(){
        windowResize();
    });
});

function getWindowHeight(){
    return $(window).height();
}

function getWindowWidth(){
    return $(window).width();
}

function windowResize(){
    var width = getWindowWidth();
    var height = getWindowHeight();
    $('.easyui-layout').width(width);
    $('.easyui-layout').height(height);
    $('.easyui-layout').layout();
}
