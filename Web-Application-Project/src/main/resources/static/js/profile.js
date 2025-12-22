const token = localStorage.getItem("jwt_token");
if (!token) window.location.href = "/login";

const headers = { Authorization: `Bearer ${token}` };

window.onload = () => {
  loadBasicInfo();
  loadStats()
  loadWrongWords();
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
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json",
  };

  try {
    let res = await fetch(`/user-stats/get-own-stats`, { headers });
    let data = await res.json();

    if (res.status === 200 && data.userStatsDTO) {
      const statsId = data.userStatsDTO.id;
      await fetch(`/user-stats/update-stats/${statsId}`, {
        method: "PUT",
        headers: headers,
      });
    } else if (res.status === 404 || data.status === 404) {
      const userEmail = localStorage.getItem("user_email");
      await fetch(`/user-stats/create-user-stats`, {
        method: "POST",
        headers: headers,
        body: JSON.stringify({ email: userEmail }),
      });
    }

    res = await fetch(`/user-stats/get-own-stats`, { headers });
    data = await res.json();

    const stats = data.userStats || data.userStatsDTO;

    if (stats) {
      document.getElementById("stat-wpm").innerText = stats.bestWpm || 0;
      document.getElementById("stat-acc").innerText =
        (stats.averageAccuracy ? Math.round(stats.averageAccuracy) : 0) + "%";
      document.getElementById("stat-words").innerText =
        stats.totalWordsTyped || 0;
    }
  } catch (err) {
    console.error("Stats Load Error:", err);
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

// 4. Loads wrong words
async function loadWrongWords() {
  const container = document.getElementById("wrong-words-container");
  try {
    const userId = localStorage.getItem("user_id");
    // ensure UserWordController endpoint is open
    const res = await fetch(`/user-words/get-wrong-words-by-user/${userId}`, { headers });
    const data = await res.json();

    const list = data.userWordDTOs || data || [];

    if (list.length === 0) {
      container.innerHTML = "<p style='color:var(--muted)'>No wrong words found. Good job!</p>";
      return;
    }

    // Save to localStorage for Practice Mode
    const practiceList = list.map(item => ({
      word: item.word || item.wordContent,
      id: item.wordId
    }));
    localStorage.setItem("practice_words", JSON.stringify(practiceList));

    // render table
    let html = `
    <table style="width:100%; text-align:left; color:white;">
        <thead>
            <tr style="border-bottom:1px solid #333;">
                <th>Word</th>
                <th>Mistakes</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>`;

    list.forEach(item => {
      html += `
            <tr id="row-${item.id}">
                <td style="padding:8px; color:#ff4961;">${item.word || item.wordContent}</td>
                <td style="padding:8px;">${item.count}</td>
                <td style="padding:8px;">
                    <button onclick="deleteWrongWord(${item.id})" style="background:transparent; border:1px solid #ff4961; color:#ff4961; padding:2px 8px; cursor:pointer;">Delete</button>
                </td>
            </tr>`;
    });

    html += `</tbody></table>
    <div style="margin-top:15px;">
        <button onclick="startPractice()" style="background:#ff4961; color:white; padding:10px 20px; border:none; border-radius:4px; cursor:pointer; font-weight:bold;">
           Practice Weak Words
        </button>
    </div>`;

    container.innerHTML = html;

  } catch (err) {
    console.error(err);
    container.innerHTML = "<p>Error loading words.</p>";
  }
}

async function deleteWrongWord(id) {
  if (!confirm("Remove this word?")) return;
  try {
    await fetch(`/user-words/delete-wrong-word/${id}`, { method: "DELETE", headers });
    document.getElementById(`row-${id}`).remove();
  } catch (err) {
    alert("Failed to delete.");
  }
}

function startPractice() {
  localStorage.setItem("preferredMode", "PRACTICE_WRONG");
  window.location.href = "/game";
}