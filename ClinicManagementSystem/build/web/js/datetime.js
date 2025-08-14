/**
 * Real-time clock and date display for the header
 * Updates every second to show current time and date
 */
document.addEventListener("DOMContentLoaded", function () {
  // DOM elements
  const timeElement = document.getElementById("current-time");
  const dateElement = document.getElementById("current-date");

  if (!timeElement || !dateElement) return;

  // Format time as HH:MM:SS AM/PM
  function formatTime(date) {
    let hours = date.getHours();
    const minutes = date.getMinutes().toString().padStart(2, "0");
    const ampm = hours >= 12 ? "PM" : "AM";

    // Convert to 12-hour format
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'

    return `${hours}:${minutes} ${ampm}`;
  }

  // Format date as Day, Month DD, YYYY
  function formatDate(date) {
    const options = {
      weekday: "long",
      year: "numeric",
      month: "long",
      day: "numeric",
    };
    return date.toLocaleDateString("en-US", options);
  }

  // Update the time and date display
  function updateDateTime() {
    const now = new Date();
    timeElement.textContent = formatTime(now);
    dateElement.textContent = formatDate(now);
  }

  // Update immediately and then every second
  updateDateTime();
  setInterval(updateDateTime, 1000);
});
