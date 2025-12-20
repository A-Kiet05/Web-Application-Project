const API_BASE = "http://localhost:8081";
const token = localStorage.getItem("jwt_token");

// Fetch on load
async function fetchCategories() {
  try {
    const res = await fetch(`${API_BASE}/category/get-all`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    const data = await res.json();
    renderCategories(data.categoryDTOs);
  } catch (err) {
    document.getElementById("categoryList").innerHTML =
      '<div class="empty-state" style="color:var(--danger)">Failed to load categories.</div>';
  }
}

function renderCategories(cats) {
  const list = document.getElementById("categoryList");
  if (!cats || cats.length === 0) {
    list.innerHTML =
      '<div class="empty-state">No categories found. Create one to get started.</div>';
    return;
  }

  list.innerHTML = cats
    .map(
      (c) => `
            <div class="cat-card">
                <div class="cat-info">
                    <strong>${c.name}</strong>
                    <p>${c.description || "No description provided."}</p>
                    <div class="cat-meta">
                        <span class="count-badge">ID: #${c.id}</span>
                        <span class="count-badge">Words: ${
                          c.words ? c.words.length : 0
                        }</span>
                    </div>
                </div>
                <button class="del-btn" onclick="deleteCategory(${
                  c.id
                })">Remove</button>
            </div>
        `
    )
    .join("");
}

async function createCategory() {
  const name = document.getElementById("catName").value;
  const description = document.getElementById("catDesc").value;

  if (!name) {
    alert("Please enter a category name.");
    return;
  }

  try {
    const res = await fetch(`${API_BASE}/category/create-category`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ name, description }),
    });

    if (res.ok) {
      document.getElementById("catName").value = "";
      document.getElementById("catDesc").value = "";
      fetchCategories();
    }
  } catch (err) {
    alert("Error creating category.");
  }
}

async function deleteCategory(id) {
  if (!confirm("Delete this category? This may affect words assigned to it."))
    return;

  try {
    const res = await fetch(`${API_BASE}/category/delete-category/${id}`, {
      method: "DELETE",
      headers: { Authorization: `Bearer ${token}` },
    });
    if (res.ok) fetchCategories();
  } catch (err) {
    alert("Error deleting category.");
  }
}

// Initial load
fetchCategories();
