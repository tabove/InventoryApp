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
<meta charset="UTF-8">
<title>商品管理</title>
</head>
<body>
	<h1>商品検索</h1>
	<form action="Result" method="get">
	<table border="1">
		<tr>
			<td>商品名:<input type="text" name="name" value="<%= searchKeyword %>"></td>
			<td>
				商品分類:
				<select name="bunrui">
					<option value="" <%= selectedCategory.isEmpty() ? "selected" : "" %> ></option>
					<option value="衣服" <%= "衣服".equals(selectedCategory) ? "selected" : "" %>>衣服</option>
					<option value="キッチン用品" <%= "キッチン用品".equals(selectedCategory) ? "selected" : "" %>>キッチン用品</option>
					 <option value="事務用品" <%= "事務用品".equals(selectedCategory) ? "selected" : "" %>>事務用品</option>
				</select>
			</td>
		</tr>
	</table>	
	<input type="submit" value="検索">
	</form>

	<form method="post">
	<table border="1">
		<tbody>
			<tr>
				<th>選択</th>
				<th>商品ID</th>
				<th>商品名</th>
				<th>商品分類</th>
				<th>販売単価</th>
				<th>仕入単価</th>
			</tr>
			<% for (Shohin s : (List<Shohin>) searchResults) { %>
            <tr>
                <td><input type="radio" name="selectedShohin" value="<%= s.getShohinId() %>"></td>
                <td><%= s.getShohinId() %></td>
                <td><%= s.getShohinMei() %></td>
                <td><%= s.getShohinBunrui() %></td>
                <td><%= s.getHanbaiTanka() %></td>
                <td><%= s.getShiireTanka() %></td>
            </tr>
        <% } %>
		</tbody>
	</table>
	<table>
		<tr>
			<td>
				<a href="Register">追加</a>
			</td>
			<td>
				<input type="submit" formaction="Update" value="更新" formmethod="get">
			</td>
			<td>
				<input type="submit" formaction="Delete" value="削除" formmethod="post">
			</td>
			<td>
				<input type="submit" formaction="CSVFileOperator" value="CSV全件出力" formmethod="post">
			</td>
		</tr>
	</table>
	</form>
</body>
</html>