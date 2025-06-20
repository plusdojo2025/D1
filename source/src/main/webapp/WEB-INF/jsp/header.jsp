<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <!-- ドロップダウン付きヘッダー -->
    <header class="main-header">
      <div class="logo">
        <a href="<c:url value='/InfoScheduleServlet'/>">
          <img src="<c:url value='/img/IMG_ロゴ2.png'/>" width=150 height=400 alt="ロゴ">
        </a>
      </div>
      <nav class="nav-menu">
        <ul class="header-menu">
          <li class="menu-item">
            <a href="#">生徒管理</a>
            <ul class="sub-menu">
              <li><a href="<c:url value='/InfoScheduleServlet'/>">項目ごとに閲覧</a></li>
              <li><a href="<c:url value='/RegistStudentServlet'/>">生徒の登録</a></li>
            </ul>
          </li>
          <li class="menu-item">
            <a href="#">スケジュール</a>
            <ul class="sub-menu">
              <li><a href="<c:url value='/InfoScheduleServlet'/>">スケジュールの閲覧</a></li>
              <li><a href="<c:url value='/RegistScheduleServlet'/>">スケジュールの登録</a></li>
            </ul>
          </li>
          <li class="menu-item"><a href="<c:url value='/LoginServlet'/>">ログアウト</a></li>
        </ul>
      </nav>
    </header>
<body>

</body>
</html>