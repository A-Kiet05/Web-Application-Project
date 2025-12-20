const API_BASE = "http://localhost:8081";
const token = localStorage.getItem("jwt_token");

// Security Check
const userRole = localStorage.getItem("user_role");
if (!token || !userRole || !userRole.toUpperCase().includes("ADMIN")) {
  alert("Access Denied: Administrative privileges required.");
  window.location.href = "login.html";
}

async function fetchUsers() {
  try {
    const res = await fetch(`${API_BASE}/user/Get-all`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    if (!res.ok) throw new Error("Network response was not ok");
    const data = await res.json();
    renderUsers(data.userDTOs);
  } catch (err) {
    document.getElementById(
      "userList"
    ).innerHTML = `<div style="color: var(--danger); padding: 40px;">Failed to synchronize user data. Please check server status.</div>`;
  }
}

function renderUsers(users) {
  if (!users || users.length === 0) {
    document.getElementById("userList").innerHTML =
      '<div class="loading">No registered users found.</div>';
    return;
  }

  let html = `<table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>User Identity</th>
                        <th>Contact</th>
                        <th>Role</th>
                        <th>Performance Summary</th>
                    </tr>
                </thead>
                <tbody>`;

  users.forEach((user) => {
    const stats = user.userStats || { totalWordsTyped: 0, bestWpm: 0 };
    html += `
                <tr class="expandable-row" onclick="toggleDetails(${user.id})">
                    <td style="font-family: 'Roboto Mono', monospace; color: var(--muted);">#${
                      user.id
                    }</td>
                    <td>
                        <div style="font-weight: 700;">${user.fullName}</div>
                        <div style="font-size: 12px; color: var(--muted);">@${
                          user.username
                        }</div>
                    </td>
                    <td style="color: var(--muted);">${user.email}</td>
                    <td><span class="role-pill">${user.role}</span></td>
                    <td>
                        <span class="stats-badge">Peak: ${
                          stats.bestWpm
                        } WPM</span>
                        <span class="stats-badge" style="background: rgba(255,255,255,0.03); color: var(--muted); border-color: transparent;">
                            Words: ${stats.totalWordsTyped}
                        </span>
                    </td>
                </tr>
                <tr id="details-${user.id}" class="details-pane">
                    <td colspan="5">
                        <div class="details-content">
                            <div>
                                <h4>Session History</h4>
                                <div class="scroll-box">
                                    ${renderSessions(user.gameSessions)}
                                </div>
                            </div>
                            <div>
                                <h4>Score History</h4>
                                <div class="scroll-box">
                                    ${renderScores(user.scores)}
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>`;
  });

  html += `</tbody></table>`;
  document.getElementById("userList").innerHTML = html;
}

function renderScores(scores) {
  if (!scores || scores.length === 0)
    return "<p style='color:var(--muted); font-size:12px;'>No scores recorded.</p>";
  return `<table class="sub-table">
                <thead><tr><th>Points</th><th>Date Recorded</th></tr></thead>
                ${scores
                  .map(
                    (s) => `
                    <tr>
                        <td style="color:var(--success); font-weight:700;">${
                          s.score
                        }</td>
                        <td style="color:var(--muted);">${new Date(
                          s.createdAt
                        ).toLocaleString()}</td>
                    </tr>
                `
                  )
                  .join("")}
            </table>`;
}

function renderSessions(sessions) {
  if (!sessions || sessions.length === 0)
    return "<p style='color:var(--muted); font-size:12px;'>No game sessions logged.</p>";
  return `<table class="sub-table">
                <thead><tr><th>Mode</th><th>WPM</th><th>Acc</th><th>Date</th></tr></thead>
                ${sessions
                  .map(
                    (s) => `
                    <tr>
                        <td><span style="color:var(--accent)">${
                          s.mode
                        }</span></td>
                        <td>${s.wpm}</td>
                        <td>${s.accuracy}%</td>
                        <td style="color:var(--muted);">${new Date(
                          s.createdAt
                        ).toLocaleDateString()}</td>
                    </tr>
                `
                  )
                  .join("")}
            </table>`;
}

function toggleDetails(id) {
  const el = document.getElementById(`details-${id}`);
  const isVisible = el.style.display === "table-row";

  // Close all other panes first (optional, for cleaner UI)
  document
    .querySelectorAll(".details-pane")
    .forEach((pane) => (pane.style.display = "none"));

  el.style.display = isVisible ? "none" : "table-row";
}

window.onload = fetchUsers;
