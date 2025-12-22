const API_BASE = "";
document
  .getElementById("registerForm")
  .addEventListener("submit", async (e) => {
    e.preventDefault();
    const payload = {
      username: document.getElementById("username").value.trim(),
      fullName: document.getElementById("fullname").value.trim(),
      email: document.getElementById("email").value.trim(),
      password: document.getElementById("password").value,
      role: "USER",
    };
    try {
      const res = await fetch(`${API_BASE}/auth/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });
      const data = await res.json().catch(() => null);
      if (!res.ok) {
        alert("Register failed: " + (data?.message || res.status));
        return;
      }
      alert("Registration successful — please login");
      window.location.href = "/";
    } catch (err) {
      console.error(err);
      alert("Server error");
    }
  });
