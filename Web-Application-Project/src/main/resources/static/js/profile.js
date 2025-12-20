const token = localStorage.getItem("jwt_token");
if (!token) window.location.href = "/login";

const headers = { Authorization: `Bearer ${token}` };

window.onload = () => {
  loadBasicInfo();
  loadStats();
  loadAchievements();
};

// 1. Load User Info
async function loadBasicInfo() {
  try {
    const res = await fetch("/user/my-info", { headers });
    const data = await res.json();
    if (data.status === 200) {
      const user = data.user;
      document.getElementById("full-name").innerText =
        user.fullName || user.username;
      document.getElementById("email-display").innerText = user.email;
      document.getElementById("role-badge").innerText = user.role;
      document.getElementById("avatar-initial").innerText = (
        user.fullName || user.username
      )
        .charAt(0)
        .toUpperCase();
    }
  } catch (err) {
    console.error("Info Load Error", err);
  }
}

// 2. Load Stats
async function loadStats() {
  try {
    const res = await fetch("/user-stats/get-own-stats", { headers });
    const data = await res.json();
    // Check if userStats exists in the response wrapper
    const stats = data.userStats || data.userStatsDTO;

    if (stats) {
      document.getElementById("stat-wpm").innerText = stats.bestWpm || 0;
      document.getElementById("stat-acc").innerText =
        (stats.averageAccuracy || 0) + "%";
      document.getElementById("stat-words").innerText =
        stats.totalWordsTyped || 0;
    }
  } catch (err) {
    console.error("Stats Load Error", err);
  }
}

// 3. Load Achievements
async function loadAchievements() {
  try {
    const res = await fetch("/user-achievement/get-own-achievement", {
      headers,
    });
    const data = await res.json();
    const list = data.userAchievementResponses || []; // Adjust based on your Response DTO

    const container = document.getElementById("achievements-container");
    container.innerHTML = "";

    if (list.length === 0) {
      container.innerHTML =
        "<p style='color:var(--muted)'>No achievements unlocked yet.</p>";
      return;
    }

    list.forEach((item) => {
      const div = document.createElement("div");
      div.className = "achievement-card unlocked";
      div.innerHTML = `
                        <div class="badge-icon">🏆</div>
                        <div>
                            <div style="font-weight:bold">Achievement #${
                              item.achievementId
                            }</div>
                            <div style="font-size:12px; color:var(--muted)">Unlocked: ${new Date(
                              item.unlockedAt
                            ).toLocaleDateString()}</div>
                        </div>
                    `;
      container.appendChild(div);
    });
  } catch (err) {
    console.error("Achievement Load Error", err);
  }
}

function logout() {
  localStorage.clear();
  window.location.href = "/";
}
