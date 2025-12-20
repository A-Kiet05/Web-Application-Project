const API_BASE = "http://localhost:8081";
const token = localStorage.getItem("jwt_token");

// Fetch and display all achievements
async function loadAchievements() {
  try {
    const res = await fetch(`${API_BASE}/achievement/get-all`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    const data = await res.json();
    renderTable(data.achievementDTOs);
  } catch (err) {
    console.error("Fetch error:", err);
  }
}

function renderTable(achievements) {
  const tbody = document.getElementById("achievementList");
  if (!achievements || achievements.length === 0) {
    tbody.innerHTML = `<tr><td colspan="4" style="text-align:center;">No achievements found.</td></tr>`;
    return;
  }

  tbody.innerHTML = achievements
    .map(
      (ach) => `
            <tr>
                <td><span class="code-badge">${ach.code}</span></td>
                <td>
                    <div style="font-weight:600;">${ach.name}</div>
                    <div style="font-size:12px; color:var(--muted);">${ach.description}</div>
                </td>
                <td><span class="condition-badge">${ach.conditionType}</span></td>
                <td class="actions">
                    <button class="btn-icon" onclick="viewDetails(${ach.id})">Details</button>
                    <button class="btn-icon delete" onclick="deleteAchievement(${ach.id})">Delete</button>
                </td>
            </tr>
        `
    )
    .join("");
}

// Create new achievement
async function createAchievement() {
  const code = document.getElementById("achCode").value.trim();
  const name = document.getElementById("achName").value.trim();
  const description = document.getElementById("achDesc").value.trim();
  const conditionType = document.getElementById("achCondition").value.trim();

  // Validation logic
  const conditionRegex = /^(WPM_|ACCURACY_)\d+$/;
  if (!conditionRegex.test(conditionType)) {
    alert(
      "Error: Condition Type must start with WPM_ or ACCURACY_ followed by a number (e.g., WPM_60)"
    );
    return;
  }

  if (!code || !name || !description) {
    alert("All fields are required.");
    return;
  }

  try {
    const res = await fetch(`${API_BASE}/achievement/create-achievement`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ code, name, description, conditionType }),
    });

    if (res.ok) {
      alert("Achievement created successfully!");
      // Reset form
      document
        .querySelectorAll("input, textarea")
        .forEach((el) => (el.value = ""));
      loadAchievements();
    } else {
      const error = await res.json();
      alert("Failed: " + (error.message || "Unknown error"));
    }
  } catch (err) {
    alert("Network error occurred.");
  }
}

// Get Achievement by ID (for inspection)
async function viewDetails(id) {
  try {
    const res = await fetch(
      `${API_BASE}/achievement/get-achievement-by-id/${id}`,
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );
    const data = await res.json();
    const ach = data.achievement;
    alert(`
Achievement Details:
ID: ${ach.id}
Code: ${ach.code}
Name: ${ach.name}
Condition: ${ach.conditionType}
Description: ${ach.description}
            `);
  } catch (err) {
    alert("Could not fetch achievement details.");
  }
}

// Delete Achievement
async function deleteAchievement(id) {
  if (!confirm("Are you sure you want to delete this achievement?")) return;

  try {
    const res = await fetch(
      `${API_BASE}/achievement/delete-achievement/${id}`,
      {
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` },
      }
    );

    if (res.ok) {
      loadAchievements();
    } else {
      alert("Failed to delete achievement.");
    }
  } catch (err) {
    alert("Network error.");
  }
}

// Initial load
window.onload = loadAchievements;
