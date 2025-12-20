const API_BASE = "";

document.getElementById("loginForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const identifier = document.getElementById("identifier").value.trim();
  const password = document.getElementById("password").value;

  if (!identifier || !password) {
    alert("Enter both fields");
    return;
  }

  const payload = { email: identifier, password };

  try {
    const res = await fetch(`${API_BASE}/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });

    const data = await res.json();

    if (res.ok) {
      if (data.token) {
        localStorage.setItem("jwt_token", data.token);
      }

      const userId = data.user.id;
      if (userId) {
        localStorage.setItem("user_id", userId);
      } else {
        console.warn("Backend did not return userId in login response");
      }

      if (data.role) localStorage.setItem("user_role", data.role);
      localStorage.setItem("user_email", identifier);

      const role = data.role ? data.role.toUpperCase() : "USER";

      if (role === "ADMIN") {
        window.location.href = "/admin";
      } else {
        window.location.href = "/game";
      }
    } else {
      alert("Login failed: " + (data.message || "Check credentials"));
    }
  } catch (err) {
    console.error("Login error:", err);
    alert("Failed to connect to server.");
  }
});
