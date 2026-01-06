// admin_stats.js (개인회원 부분만 예시로 교체)
(() => {
    let activeTab = "personal";

    const $ = (sel) => document.querySelector(sel);
    const $$ = (sel) => Array.from(document.querySelectorAll(sel));

    async function fetchPersonalMonthly() {
        const token = localStorage.getItem("ADMIN_TOKEN");
        const from = $("#p_from")?.value;
        const to = $("#p_to")?.value;

        const res = await fetch(`/api/personal/admin/stats/join-monthly?from=${encodeURIComponent(from)}&to=${encodeURIComponent(to)}`, {
            headers: {
                "Content-Type": "application/json",
                ...(token ? { "Authorization": `Bearer ${token}` } : {})
            }
        });

        if (!res.ok) throw new Error("통계 조회 실패");
        return await res.json(); // { total, rows:[{ym,count}] }
    }

    async function renderPersonal() {
        const tbody = $("#p_tbody");
        try {
            const { total, rows } = await fetchPersonalMonthly();

            $("#p_total").textContent = `* 전체 회원수 : ${total}`;

            if (!rows || rows.length === 0) {
                tbody.innerHTML = `<tr><td colspan="3" style="text-align:center;">조회 결과가 없습니다.</td></tr>`;
                return;
            }

            tbody.innerHTML = rows.map((r, idx) => `
        <tr>
          <td>${idx + 1}</td>
          <td>${r.ym}</td>
          <td>${r.count}</td>
        </tr>
      `).join("");
        } catch (e) {
            console.error(e);
            tbody.innerHTML = `<tr><td colspan="3" style="text-align:center;">데이터를 불러오지 못했습니다.</td></tr>`;
            $("#p_total").textContent = `* 전체 회원수 : 0`;
        }
    }

    function render(tabKey) {
        if (tabKey === "personal") renderPersonal();
        // company 등 다른 탭은 나중에 같은 방식으로 확장
    }

    function setActiveTab(tabKey) {
        activeTab = tabKey;
        $$("#statTabs .tab").forEach(btn => btn.classList.toggle("active", btn.dataset.tab === tabKey));
        $$(".tab-panel").forEach(panel => panel.style.display = "none");
        $(`#panel-${tabKey}`)?.style && ($(`#panel-${tabKey}`).style.display = "");
        render(tabKey);
    }

    function bindEvents() {
        // 탭 클릭
        $$("#statTabs .tab").forEach(btn => btn.addEventListener("click", () => setActiveTab(btn.dataset.tab)));

        // 개인회원 검색 버튼 클릭(HTML에 data-search="personal" 존재) :contentReference[oaicite:8]{index=8}
        document.querySelector('button[data-search="personal"]')
            ?.addEventListener("click", () => renderPersonal());
    }

    function init() {
        bindEvents();
        render(activeTab);
    }

    document.addEventListener("DOMContentLoaded", init);
})();
