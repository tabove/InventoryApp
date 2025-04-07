<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List" %>
 <%@ page import="java.util.Set" %>
 <%@ page import="java.util.HashSet" %>
 <%@ page import="model.Shohin" %>
<jsp:useBean id="searchResults" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="selectedCategory" class="java.lang.String" scope="session"/>
<jsp:useBean id="searchKeyword" class="java.lang.String" scope="session"/>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/webapp/js/script.js"></script>
<meta charset="UTF-8">
<title>商品更新</title>
</head>
<body>
		<%-- エラーメッセージの表示 --%>
    <%
        List<String> messageList = (List<String>) request.getAttribute("messageList"); 
        if (messageList != null && !messageList.isEmpty()) {%>
    	<ul>
		   	<%
		     Set<String> uniqueMessage = new HashSet<>(messageList); // 重複を削除
		     for (String msg : messageList) {
		  	%>
 			 <li style="list-style: none;"><%= msg %></li>
	    	<%} %>
		 </ul>
	<%  
	}
	%>
	<% for (Shohin s : (List<Shohin>) searchResults) { %>
	<form action="Update" method="post">
		商品ID:<input type="text" name="id" readonly value="<%= s.getShohinId() %>"><br>
		商品名:<input type="text" name="name" value="<%= s.getShohinMei() %>"><br>
		商品分類:<input type="radio" name="bunrui" value="衣服" <%= "衣服".equals(s.getShohinBunrui()) ? "checked" : ""%>>衣服
       <input type="radio" name="bunrui" value="キッチン用品" <%= "キッチン用品".equals(s.getShohinBunrui()) ? "checked" : ""%>>キッチン用品
       <input type="radio" name="bunrui" value="事務用品" <%= "事務用品".equals(s.getShohinBunrui()) ? "checked" : ""%>>事務用品<br>
		販売単価:<input type="text" name="sTanka" value="<%= s.getHanbaiTanka() %>"><br>
		仕入単価:<input type="text" name="tanka" value="<%= s.getShiireTanka() %>"><br>
	<% } %>
		<input type="submit" value="登録">
		<a href="index.jsp">戻る</a>
	</form>
</body>
</html>