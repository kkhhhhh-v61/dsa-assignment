document.addEventListener("DOMContentLoaded", function () {
  const sidebar = document.getElementById("sidebar");
  const currentPage = window.location.pathname.split("/").pop() || "index.jsp";
  const STORAGE_KEY = "sidebarActiveItem";

  // Initialize active state from localStorage or set default
  function initActiveState() {
    const storedState = localStorage.getItem(STORAGE_KEY);
    const defaultActiveItem = currentPage === "index.jsp" ? "dashboard" : "";
    
    try {
      if (storedState) {
        const { item, subItem } = JSON.parse(storedState);
        // First set the active item
        setActiveItem(item, subItem);
        
        // If it's not the dashboard, ensure the submenu is expanded
        if (item !== 'dashboard') {
          const activeSection = document.querySelector(`[data-item-id="${item}"]`);
          if (activeSection) {
            const toggleBtn = activeSection.querySelector('.nav-item-toggle');
            const submenu = activeSection.querySelector('.nav-submenu');
            if (toggleBtn && submenu) {
              // Force open the submenu
              toggleBtn.classList.add('active');
              submenu.style.maxHeight = submenu.scrollHeight + "px";
              submenu.style.opacity = "1";
              submenu.style.padding = "var(--spacing-xs) 0 var(--spacing-sm) 0";
              submenu.style.visibility = "visible";
            }
          }
        }
      } else if (defaultActiveItem) {
        setActiveItem(defaultActiveItem);
      }
    } catch (e) {
      console.error('Error initializing active state:', e);
      if (defaultActiveItem) {
        setActiveItem(defaultActiveItem);
      }
    }
  }

  // Set active item and store in localStorage
  function setActiveItem(itemId, subItemId = null) {
    // If clicking dashboard, collapse all other submenus
    if (itemId === 'dashboard') {
      document.querySelectorAll(".nav-item-toggle.active").forEach(toggle => {
        if (!toggle.closest('[data-item-id="dashboard"]')) {
          const submenu = toggle.nextElementSibling;
          if (submenu && submenu.classList.contains('nav-submenu')) {
            toggle.classList.remove('active');
            submenu.style.maxHeight = "0";
            submenu.style.opacity = "0";
            submenu.style.padding = "0";
            submenu.style.visibility = "hidden";
          }
        }
      });
    }

    // Remove active class from all items
    document.querySelectorAll(".nav-item, .nav-subitem").forEach((item) => {
      item.classList.remove("active");
    });

    // If we have a subitem, activate both parent and subitem
    if (subItemId) {
      const subItem = document.querySelector(`[data-subitem-id="${subItemId}"]`);
      if (subItem) {
        subItem.classList.add("active");
        // Also activate the parent menu item
        const parentSection = subItem.closest('.nav-section');
        if (parentSection) {
          const parentItem = parentSection.querySelector('.nav-item');
          if (parentItem) parentItem.classList.add("active");
          // Store both parent and subitem in localStorage
          localStorage.setItem(STORAGE_KEY, JSON.stringify({
            item: parentSection.dataset.itemId,
            subItem: subItemId
          }));
          return;
        }
      }
    }

    // Handle regular menu items
    const targetSection = document.querySelector(`[data-item-id="${itemId}"]`);
    if (targetSection) {
      const navItem = targetSection.querySelector('.nav-item');
      if (navItem) {
        navItem.classList.add("active");
        // Store only the main item in localStorage
        localStorage.setItem(STORAGE_KEY, JSON.stringify({
          item: itemId,
          subItem: null
        }));
      }
    }
  }

  // Toggle submenu
  function toggleSubmenu(toggleBtn, itemId) {
    const submenu = toggleBtn.nextElementSibling;
    const isExpanding = !toggleBtn.classList.contains("active");
    
    // If we're expanding, close all other submenus first
    if (isExpanding) {
      document.querySelectorAll('.nav-item-toggle.active').forEach(btn => {
        if (btn !== toggleBtn) {
          const otherSubmenu = btn.nextElementSibling;
          btn.classList.remove('active');
          otherSubmenu.style.maxHeight = "0";
          otherSubmenu.style.opacity = "0";
          otherSubmenu.style.padding = "0";
          otherSubmenu.style.visibility = "hidden";
        }
      });
    }

    // Toggle the clicked menu
    toggleBtn.classList.toggle("active", isExpanding);
    
    // Toggle submenu with animation
    if (isExpanding) {
      // Open submenu
      submenu.style.maxHeight = submenu.scrollHeight + "px";
      submenu.style.opacity = "1";
      submenu.style.padding = "var(--spacing-xs) 0 var(--spacing-sm) 0";
      submenu.style.visibility = "visible";
      
      // Set this as active item if it's a direct nav item
      if (itemId) {
        setActiveItem(itemId);
      }
    } else {
      // Close submenu with animation
      submenu.style.maxHeight = "0";
      submenu.style.opacity = "0";
      submenu.style.padding = "0";
      // Only hide after transition completes
      setTimeout(() => {
        if (!toggleBtn.classList.contains('active')) {
          submenu.style.visibility = "hidden";
        }
      }, 200); // Match this with your CSS transition duration
    }
  }

  // Initialize all submenu toggle buttons
  function initializeSubmenus() {
    document.querySelectorAll(".nav-item-toggle").forEach((toggle) => {
      const submenu = toggle.nextElementSibling;
      if (!submenu || !submenu.classList.contains('nav-submenu')) return;
      
      // Only initialize if not already initialized
      if (toggle.classList.contains('active')) {
        submenu.style.maxHeight = submenu.scrollHeight + "px";
        submenu.style.opacity = "1";
        submenu.style.padding = "var(--spacing-xs) 0 var(--spacing-sm) 0";
        submenu.style.visibility = "visible";
      } else {
        submenu.style.maxHeight = "0";
        submenu.style.opacity = "0";
        submenu.style.padding = "0";
        submenu.style.visibility = "hidden";
      }
    });
  }

  // Handle submenu toggle clicks
  document.querySelectorAll(".nav-item-toggle").forEach((toggle) => {
    const itemId =
      toggle.closest("[data-item-id]")?.dataset.itemId ||
      (toggle.closest(".nav-section:first-child") ? "dashboard" : null);

    toggle.addEventListener("click", function (e) {
      e.preventDefault();
      e.stopPropagation();
      toggleSubmenu(this, itemId);
    });
  });

  // Handle direct link clicks for main menu items
  document.querySelectorAll(".nav-item:not(.nav-item-toggle)").forEach((item) => {
    const itemId = item.closest('[data-item-id]')?.dataset.itemId;
    if (itemId) {
      item.addEventListener("click", (e) => {
        // Only prevent default if it's a hash link
        if (item.getAttribute('href') === '#') {
          e.preventDefault();
        }
        // Don't stop propagation to allow navigation
        setActiveItem(itemId);
      });
    }
  });
  
  // Handle submenu item clicks
  document.querySelectorAll(".nav-subitem").forEach((item) => {
    const parentSection = item.closest('.nav-section');
    const parentId = parentSection?.dataset.itemId;
    const subItemId = item.dataset.subitemId;
    
    if (parentId && subItemId) {
      item.addEventListener("click", (e) => {
        // Only prevent default if it's a hash link
        if (item.getAttribute('href') === '#') {
          e.preventDefault();
        }
        // Don't stop propagation to allow navigation
        setActiveItem(parentId, subItemId);
      });
    }
  });

  // Close submenus when clicking outside, but preserve active states
  document.addEventListener("click", function (e) {
    const clickedElement = e.target;
    const isClickInsideSidebar = clickedElement.closest(".sidebar-nav");
    const isClickOnActiveItem = clickedElement.closest(".nav-item.active, .nav-subitem.active");
    const activeSubmenuItem = document.querySelector('.nav-subitem.active');
    
    // Only proceed if we're not clicking on the sidebar or any of its children
    // or if we're clicking on an already active item
    if (!isClickInsideSidebar && !isClickOnActiveItem) {
      document.querySelectorAll(".nav-item-toggle.active").forEach((toggle) => {
        const submenu = toggle.nextElementSibling;
        const parentSection = toggle.closest('.nav-section');
        const isActiveSection = parentSection && parentSection.querySelector('.nav-item.active, .nav-subitem.active');
        const hasActiveSubmenuItem = parentSection && parentSection.querySelector('.nav-subitem.active');
        
        // Only close the submenu if it's not the active section and doesn't have an active subitem
        if (submenu && !isActiveSection && !hasActiveSubmenuItem) {
          toggle.classList.remove("active");
          submenu.style.maxHeight = "0";
          submenu.style.opacity = "0";
          submenu.style.padding = "0";
          // Only hide after transition completes
          setTimeout(() => {
            if (!toggle.classList.contains('active')) {
              submenu.style.visibility = "hidden";
            }
          }, 200);
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

  // Initialize active state and submenus
  initActiveState();
  initializeSubmenus();
  
  // Re-initialize submenus after a short delay to ensure all elements are rendered
  setTimeout(initializeSubmenus, 100);

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
