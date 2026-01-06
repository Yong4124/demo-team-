document.addEventListener("DOMContentLoaded", () => {
    // ✅ 로그인 토큰 체크 (기업회원도 동일)
    const token = localStorage.getItem("ADMIN_TOKEN");
    const name = localStorage.getItem("ADMIN_NAME") || "관리자";

    if (!token) {
        location.replace("/admin");   // 로그인 화면
        return;
    }

    // ✅ 상단 환영문구(있을 때만)
    const welcomeEl = document.getElementById("welcomeText");
    if (welcomeEl) welcomeEl.textContent = `${name}님 환영합니다.`;

    // ✅ 로그아웃 버튼
    document.getElementById("logoutBtn")?.addEventListener("click", () => {
        localStorage.removeItem("ADMIN_TOKEN");
        localStorage.removeItem("ADMIN_NAME");
        localStorage.removeItem("ADMIN_ROLE");
        location.replace("/admin");   // 로그인 화면
    });

    // ===========================
    // (기존 더미 데이터 렌더링)
    // ===========================
    const rows = [
        { no: 10, company: "효성중공업", manager: "서동섭", id: "hyosunghi", date: "2025-12-18", approve: "승인" },
        { no: 9, company: "SK On", manager: "Wonjae Kim", id: "wjk2703", date: "2025-12-18", approve: "승인" },
        { no: 8, company: "SK hynix America", manager: "Brandon Lee", id: "blee3101", date: "2025-12-13", approve: "승인" },
        { no: 7, company: "Samsung SDS", manager: "Lee Jae-min", id: "a00004", date: "2025-12-12", approve: "승인" },
        { no: 6, company: "SK On", manager: "Park Jung-su", id: "a00007", date: "2025-12-04", approve: "승인" },
        { no: 5, company: "test", manager: "test", id: "a00008", date: "2025-11-25", approve: "승인" },
        { no: 4, company: "한화솔루션 큐셀", manager: "주민주", id: "qcells2025", date: "2025-10-28", approve: "승인" },
        { no: 3, company: "Samsung Austin Semiconductor", manager: "Kim Su-han", id: "a00001", date: "2025-10-12", approve: "승인" },
        { no: 2, company: "HL-GA Battery Company", manager: "Park Jung-su", id: "a00017", date: "2025-10-12", approve: "승인" },
        { no: 1, company: "Hyundai Motor Group Metaplant America", manager: "Park Jung-su", id: "a00014", date: "2025-10-12", approve: "승인" }
    ];

    const tbody = document.getElementById("companyTbody");
    if (!tbody) return;

    tbody.innerHTML = rows.map(r => `
        <tr>
          <td>${r.no}</td>
          <td>${r.company}</td>
          <td>${r.manager}</td>
          <td>${r.id}</td>
          <td>${r.date}</td>
          <td><span class="status-approve">${r.approve}</span></td>
        </tr>
    `).join("");
});
