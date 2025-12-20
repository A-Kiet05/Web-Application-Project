const API_BASE = "http://localhost:8081";
const token = localStorage.getItem("jwt_token");

async function fetchUserSessions() {
  const userId = document.getElementById("userIdInput").value;
  const resultsArea = document.getElementById("resultsArea");

  if (!userId) {
    alert("Please enter a User ID");
    return;
  }

  resultsArea.innerHTML =
    '<div class="empty-state">Retrieving user history...</div>';

  try {
    // Updated endpoint to reflect fetching by User ID
    const res = await fetch(
      `${API_BASE}/game-session/get-session-by-id/${userId}`,
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );

    if (!res.ok) throw new Error("Could not find data for this User ID");

    const data = await res.json();
    renderSessions(data.sessionResponses);
  } catch (err) {
    resultsArea.innerHTML = `<div class="empty-state" style="color: #ef4444;">Error: ${err.message}</div>`;
  }
}

function renderSessions(sessions) {
  const resultsArea = document.getElementById("resultsArea");

  if (!sessions || sessions.length === 0) {
    resultsArea.innerHTML =
      '<div class="empty-state">This user has not completed any game sessions yet.</div>';
    return;
  }

  resultsArea.innerHTML = sessions
    .map(
      (s) => `
            <div class="session-card">
                <div class="card-header">
                    <span style="font-weight: 700; color: var(--accent);">Session Log</span>
                    <span class="mode-badge">${s.mode}</span>
                </div>
                
                <div class="stats-row">
                    <div class="stat-item">
                        <span class="stat-label">WPM</span>
                        <span class="stat-value highlight">${s.wpm}</span> 
                        <small style="color:var(--muted)">(${
                          s.rawWpm
                        } raw)</small>
                    </div>
                    <div class="stat-item">
                        <span class="stat-label">ACCURACY</span>
                        <span class="stat-value highlight">${s.accuracy}%</span>
                    </div>
                </div>

                <div class="stats-row">
                    <div class="stat-item">
                        <span class="stat-label">TOTAL WORDS</span>
                        <span class="stat-value">${s.wordsTyped}</span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-label">CORRECT / WRONG</span>
                        <span class="stat-value">${
                          s.correctWords
                        } / <span style="color:#ef4444">${
        s.incorrectWords
      }</span></span>
                    </div>
                </div>

                <div class="stats-row">
                    <div class="stat-item">
                        <span class="stat-label">DURATION</span>
                        <span class="stat-value">${s.duration} seconds</span>
                    </div>
                </div>

                <span class="timestamp">Recorded: ${new Date(
                  s.createdAt
                ).toLocaleString()}</span>
            </div>
        `
    )
    .join("");
}
