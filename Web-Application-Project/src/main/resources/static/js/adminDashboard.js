const token = localStorage.getItem("jwt_token");
const role = localStorage.getItem("user_role");

// Basic Security Check
if (!token || !role || !role.toUpperCase().includes("ADMIN")) {
  window.location.href = "/";
}

window.onload = () => {
  const emailEl = document.getElementById("admin-email");
  const roleEl = document.getElementById("admin-role");

  const savedEmail = localStorage.getItem("user_email");
  if (savedEmail) {
    emailEl.innerText = savedEmail;
  } else {
    try {
      // Decode JWT to get email if not in localStorage
      const payload = JSON.parse(
        atob(token.split(".")[1].replace(/-/g, "+").replace(/_/g, "/"))
      );
      emailEl.innerText = payload.sub || payload.email || "Admin User";
    } catch (e) {
      emailEl.innerText = "Administrator";
    }
  }
  roleEl.innerText = role;
};

function logout() {
  localStorage.clear();
  window.location.href = "/";
}
