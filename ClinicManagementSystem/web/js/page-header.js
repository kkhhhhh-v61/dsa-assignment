document.addEventListener("DOMContentLoaded", function () {
  // Get current page name
  const pathArray = window.location.pathname.split("/");
  const currentPage = pathArray[pathArray.length - 1] || "index.jsp";

  // Fetch page data
  fetch("js/page-data.json")
    .then((response) => response.json())
    .then((data) => {
      const pageData = data[currentPage] || {
        title: "Page Not Found",
        breadcrumb: [
          { text: "Home", url: "index.jsp" },
          { text: "404 - Page Not Found", url: "#" },
        ],
      };

      // Update page title
      document.title = `${pageData.title} | Clinic Management System`;

      // Update header title
      const pageTitle = document.getElementById("page-title");
      if (pageTitle) {
        pageTitle.textContent = pageData.title;
      }

      // Update breadcrumb
      const breadcrumb = document.getElementById("breadcrumb");
      if (breadcrumb) {
        breadcrumb.innerHTML = pageData.breadcrumb
          .map((item, index, array) => {
            const isLast = index === array.length - 1;
            if (isLast) {
              return `<li class="breadcrumb-item active" aria-current="page">${item.text}</li>`;
            } else {
              // Add data attributes to store the current index for localStorage handling
              return `<li class="breadcrumb-item">
                                <a href="${item.url}" data-breadcrumb-index="${index}">${item.text}</a>
                            </li>`;
            }
          })
          .join("");

        // Add click event listeners to breadcrumb links
        breadcrumb
          .querySelectorAll("a[data-breadcrumb-index]")
          .forEach((link) => {
            link.addEventListener("click", function (e) {
              e.preventDefault();
              const targetUrl = this.getAttribute("href");
              const currentPage =
                window.location.pathname.split("/").pop() || "index.jsp";
              const breadcrumbIndex = parseInt(
                this.getAttribute("data-breadcrumb-index"),
                10
              );

              // Get the stored state
              const STORAGE_KEY = "sidebarActiveItem";
              let storedState = { item: "dashboard", subItem: null };

              try {
                const stored = sessionStorage.getItem(STORAGE_KEY);
                if (stored) {
                  storedState = JSON.parse(stored);
                }
              } catch (e) {
                console.error("Error parsing stored state:", e);
              }

              // If going to dashboard, reset to default
              if (targetUrl === "index.jsp" || targetUrl === "/") {
                storedState = { item: "dashboard", subItem: null };
              }
              // If the breadcrumb index is not the last, clear subItem
              else if (breadcrumbIndex < pageData.breadcrumb.length - 1) {
                storedState.subItem = null;
              }

              // Save the updated state
              sessionStorage.setItem(STORAGE_KEY, JSON.stringify(storedState));

              // Navigate to the target URL
              window.location.href = targetUrl;
            });
          });
      }
    })
    .catch((error) => {
      console.error("Error loading page data:", error);
      // Fallback to default title
      document.title = "Clinic Management System";

      const pageTitle = document.getElementById("page-title");
      if (pageTitle) {
        pageTitle.textContent = "Dashboard";
      }

      const breadcrumb = document.getElementById("breadcrumb");
      if (breadcrumb) {
        breadcrumb.innerHTML = `
                    <li class="breadcrumb-item">
                        <a href="index.jsp" data-breadcrumb-index="0">Home</a>
                    </li>
                    <li class="breadcrumb-item active" aria-current="page">Dashboard</li>
                `;

        // Add click event listener to Home breadcrumb
        const homeLink = breadcrumb.querySelector("a[data-breadcrumb-index]");
        if (homeLink) {
          homeLink.addEventListener("click", function (e) {
            e.preventDefault();
            // Reset to dashboard state
            sessionStorage.setItem(
              "sidebarActiveItem",
              JSON.stringify({
                item: "dashboard",
                subItem: null,
              })
            );
            window.location.href = "index.jsp";
          });
        }
      }
    });
});
