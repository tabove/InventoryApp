<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<jsp:useBean id="searchResults" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="selectedCategory" class="java.lang.String" scope="session"/>
<jsp:useBean id="searchKeyword" class="java.lang.String" scope="session"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品登録</title>
</head>
<body>	
	<h1>商品登録</h1>

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
	
	<form action="Register" method="post">
		商品ID:<input type="text" name="id"><br>
		商品名:<input type="text" name="name"><br>
		商品分類:<input type="radio" name="bunrui" value="衣服">衣服
				 <input type="radio" name="bunrui" value="キッチン用品">キッチン用品
				 <input type="radio" name="bunrui" value="事務用品">事務用品<br>
		販売単価:<input type="text" name="sTanka"><br>
		仕入単価:<input type="text" name="tanka"><br>
		<input type="submit" value="登録">
	</form>
	
	<a href="index.jsp">戻る</a>
</body>
</html>