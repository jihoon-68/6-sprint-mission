// API 엔드포인트 (Spring Boot에서 제공하는 사용자 목록)
const API_URL = "/api/users"; // 예: GET /users

async function fetchUsers() {
  try {
    const response = await fetch(API_URL);
    if (!response.ok) throw new Error("API 호출 실패");
    const users = await response.json();
    renderUsers(users);
  } catch (err) {
    console.error(err);
    document.getElementById("user-list").innerHTML =
      "<p>사용자 목록을 불러오지 못했습니다.</p>";
  }
}

function renderUsers(users) {
  const list = document.getElementById("user-list");
  list.innerHTML = ""; // 초기화

  users.forEach(user => {
    const card = document.createElement("div");
    card.className = "user-card";

    const img = document.createElement("img");
    img.src = user.profileId
      ? `/files/${user.profileId}` // 백엔드에서 프로필 파일 제공
      : "https://via.placeholder.com/50"; // 기본 이미지

    const info = document.createElement("div");
    info.className = "user-info";
    info.innerHTML = `
      <strong>${user.username}</strong>
      <span>${user.email}</span>
      <span class="status ${user.online ? "online" : "offline"}">
        ${user.online ? "온라인" : "오프라인"}
      </span>
    `;

    card.appendChild(img);
    card.appendChild(info);
    list.appendChild(card);
  });
}

// 페이지 로딩 시 실행
fetchUsers();
