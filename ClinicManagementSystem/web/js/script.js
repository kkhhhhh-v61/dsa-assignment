document.addEventListener("DOMContentLoaded", function () {
  const sidebar = document.getElementById("sidebar");

  // Toggle submenu
  function toggleSubmenu(toggleBtn) {
    const isExpanded = toggleBtn.classList.contains("active");
    const submenu = toggleBtn.nextElementSibling;

    // Toggle the clicked menu
    toggleBtn.classList.toggle("active");
    
    // Toggle submenu
    if (toggleBtn.classList.contains("active")) {
      // Open submenu
      submenu.style.maxHeight = "500px";
      submenu.style.opacity = "1";
      submenu.style.padding = "var(--spacing-xs) 0 var(--spacing-sm) 0";
      submenu.style.visibility = "visible";
    } else {
      // Close submenu
      submenu.style.maxHeight = "0";
      submenu.style.opacity = "0";
      submenu.style.padding = "0";
      submenu.style.visibility = "hidden";
    }

    if (toggleBtn.classList.contains("active")) {
      // Open submenu
      submenu.style.maxHeight = "500px";
      submenu.style.opacity = "1";
      submenu.style.padding = "var(--spacing-xs) 0 var(--spacing-sm) 0";
      submenu.style.visibility = "visible";
    } else {
      // Close submenu
      submenu.style.maxHeight = "0";
      submenu.style.opacity = "0";
      submenu.style.padding = "0";
      submenu.style.visibility = "hidden";
    }
  }

  // Handle submenu toggle clicks
  document.querySelectorAll(".nav-item-toggle").forEach((toggle) => {
    toggle.addEventListener("click", function (e) {
      e.preventDefault();
      e.stopPropagation();
      toggleSubmenu(this);
    });
  });

  // Close submenus when clicking outside
  document.addEventListener("click", function (e) {
    if (
      !e.target.closest(".nav-item-toggle") &&
      !e.target.closest(".nav-submenu")
    ) {
      document.querySelectorAll(".nav-item-toggle").forEach((toggle) => {
        if (toggle.classList.contains("active")) {
          toggle.classList.remove("active");
          const submenu = toggle.nextElementSibling;
          if (submenu && submenu.classList.contains("nav-submenu")) {
            submenu.style.maxHeight = "0";
            submenu.style.opacity = "0";
            submenu.style.padding = "0";
            submenu.style.visibility = "hidden";
          }
        }
      });
    }
  });

  // Handle window resize
  function handleResize() {
    if (window.innerWidth >= 768) {
      // On desktop, show overlay only when sidebar is expanded
      // If sidebar was closed on mobile, open it when resizing to desktop
    } else {
      // On mobile, handle overlay based on sidebar state
    }
  }

  // Initialize and add event listeners
  window.addEventListener("resize", handleResize);

  // Close sidebar when clicking outside on mobile
  document.addEventListener("click", function (e) {
    if (window.innerWidth < 768 && !sidebar.contains(e.target)) {
      // sidebar.classList.remove("expanded");
      // mainContent.classList.remove("expanded");
      // if (overlay) overlay.style.display = "none";
      // localStorage.setItem("sidebarOpen", "false");
    }
  });
});
