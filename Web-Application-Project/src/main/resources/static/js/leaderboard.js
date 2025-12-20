const API_BASE = "";
const leaderboardEndpoint = `${API_BASE}/scores/leader-board?top=10`;
const token = localStorage.getItem("jwt_token");

function goBack() {
const role = localStorage.getItem("user_role") || "USER";
const upperRole = role.toUpperCase();
if (upperRole.includes("ADMIN")) {
  window.location.href = "/admin";
} else {
  window.location.href = "/game";
}}

function extractScores(resp) {
  if (!resp) return [];
  if (Array.isArray(resp)) return resp;
  return resp.scoreDTOs || resp.scores || resp.data || resp.result || [];
}

function extractUsername(item) {
  if (!item) return "Unknown";
  if (item.user)
    return (
      item.user.username || item.user.fullName || item.user.email || "User"
    );
  return item.username || item.userName || "User#" + (item.userId || "");
}

function extractDate(item) {
  return item.createdAt || item.created || item.timestamp || "";
}

async function loadLeaderboard() {
  const tbody = document.getElementById("scoreBody");
  const refreshBtn = document.getElementById("refreshBtn");
  tbody.innerHTML = `<tr><td colspan="4" class="center">Loading...</td></tr>`;
  refreshBtn.disabled = true;
  try {
    const headers = { Accept: "application/json" };
    if (token) headers["Authorization"] = `Bearer ${token}`;
    const res = await fetch(leaderboardEndpoint, {
      method: "GET",
      headers,
    });
    if (!res.ok) {
      const txt = await res.text().catch(() => "");
      document.getElementById("errorBox").style.display = "block";
      document.getElementById(
        "errorBox"
      ).textContent = `Failed to load: ${res.status}`;
      tbody.innerHTML = `<tr><td colspan="4" class="center">Unable to load scores</td></tr>`;
      return;
    }
    const data = await res.json().catch(() => null);
    const scores = extractScores(data);
    if (!scores.length) {
      tbody.innerHTML = `<tr><td colspan="4" class="center">No scores yet</td></tr>`;
      return;
    }
    tbody.innerHTML = "";
    scores.forEach((s, i) => {
      const username = extractUsername(s);
      const scoreVal = s.score ?? s.wpm ?? s.points ?? "--";
      const dateText = extractDate(s);
      const tr = document.createElement("tr");
      tr.innerHTML = `<td class="rank">#${
        i + 1
      }</td><td>${username}</td><td class="score">${scoreVal}</td><td>${dateText}</td>`;
      tbody.appendChild(tr);
    });
  } catch (err) {
    console.error("Network error", err);
    document.getElementById(
      "scoreBody"
    ).innerHTML = `<tr><td colspan="4" class="center">Error connecting to server</td></tr>`;
    document.getElementById("errorBox").style.display = "block";
    document.getElementById("errorBox").textContent =
      "Network error — check server/CORS";
  } finally {
    refreshBtn.disabled = false;
  }
}

window.addEventListener("load", loadLeaderboard);
