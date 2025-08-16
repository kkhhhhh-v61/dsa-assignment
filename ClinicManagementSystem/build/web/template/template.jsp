<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/x-icon" href="images/icon.png">
    <title>Loading... | Clinic Management System</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/script.js" defer></script>
    <script src="js/page-header.js" defer></script>
    <script src="js/datetime.js" defer></script>
  </head>
  <body>
    <%@ include file="include/client-selection.html" %> <%-- New!!! --%>
    <%@ include file="include/header.html" %>
    <div id="main-container">
      <%@ include file="include/sidebar.html" %>
      <div class="main-content-wrapper">
        <%@ include file="include/header-breadcrumb.html" %>
        <main id="main-content">
          
        </main>
      </div>
    </div>
    <%@ include file="include/footer.html" %>
  </body>
</html>