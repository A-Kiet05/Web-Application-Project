const API_BASE = "http://localhost:8081";
const token = localStorage.getItem("jwt_token");

async function loadCategories() {
  try {
    const res = await fetch(`${API_BASE}/category/get-all`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    const data = await res.json();
    const select = document.getElementById("categorySelect");
    select.innerHTML = data.categoryDTOs
      .map((c) => `<option value="${c.id}">${c.name}</option>`)
      .join("");
  } catch (err) {
    console.error("Failed to load categories", err);
  }
}

async function fetchAllWords() {
  try {
    const res = await fetch(`${API_BASE}/words/all`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    const data = await res.json();
    renderWords(data.wordDTOs);
  } catch (err) {
    document.getElementById("wordListBody").innerHTML =
      '<tr><td colspan="4" style="color:var(--danger)">Connection Error</td></tr>';
  }
}

function renderWords(words) {
  const body = document.getElementById("wordListBody");
  if (!words || words.length === 0) {
    body.innerHTML =
      '<tr><td colspan="4" style="text-align: center; padding: 40px;">Database empty.</td></tr>';
    return;
  }
  body.innerHTML = words
    .map(
      (w) => `
            <tr>
                <td class="id-cell">#${w.id}</td>
                <td class="word-cell">${w.word}</td>
                <td><span class="cat-badge">Category ${w.categoryId}</span></td>
                <td style="text-align: right;">
                    <button class="btn-delete" onclick="deleteWord(${w.id})">Delete</button>
                </td>
            </tr>
        `
    )
    .join("");
}

async function createWord() {
  const word = document.getElementById("wordInput").value;
  const categoryId = document.getElementById("categorySelect").value;

  if (!word) return alert("Please enter a word");

  const res = await fetch(`${API_BASE}/words/create-words`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({ word, categoryId }),
  });

  if (res.ok) {
    document.getElementById("wordInput").value = "";
    fetchAllWords();
  }
}

async function deleteWord(id) {
  if (!confirm("Are you sure you want to remove this term?")) return;
  const res = await fetch(`${API_BASE}/words/delete-word/${id}`, {
    method: "DELETE",
    headers: { Authorization: `Bearer ${token}` },
  });
  if (res.ok) fetchAllWords();
}

function filterWords() {
  const input = document.getElementById("searchBar").value.toLowerCase();
  const rows = document.querySelectorAll("#wordListBody tr");
  rows.forEach((row) => {
    const text = row.querySelector(".word-cell").innerText.toLowerCase();
    row.style.display = text.includes(input) ? "" : "none";
  });
}

loadCategories();
fetchAllWords();
