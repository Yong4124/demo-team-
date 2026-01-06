const CONFIG = {
    jobsApi: "/api/main/jobs",
    noticesApi: "/api/main/notices"
};

/* =========================
   기업 정보 데이터
   ========================= */
const COMPANY_DATA = {
    'hyundai_ca': { logo: '/img/main/jido_logo/logo_hyundai.png', nameUs: 'Hyundai Motor America', nameKr: 'Hyundai Motor Company', industry: 'Automotive', foundedYear: '1985', revenue: '$31.5B', locations: '10550 Talbert Avenue, Fountain Valley, CA\n81 Bunsen Irvine, CA\n5759 Highway 58, California City, CA\n12610 East End Avenue, Chino, CA\n8880 Rio San Diego Drive, Suite #600, San Diego, CA', website: 'https://www.hyundaiusa.com' },
    'samsung_ca': { logo: '/img/main/jido_logo/logo_samsung.png', nameUs: 'Samsung Semiconductor', nameKr: 'Samsung Electronics', industry: 'Semiconductors', foundedYear: '1969', revenue: '$200B+', locations: '3655 North First Street, San Jose, CA 95134', website: 'https://www.samsung.com' },
    'skhynix_ca': { logo: '/img/main/jido_logo/logo_skhynix.png', nameUs: 'SK hynix America', nameKr: 'SK hynix', industry: 'Semiconductors', foundedYear: '1983', revenue: '$30B+', locations: '3101 North First Street, San Jose, CA 95134', website: 'https://www.skhynix.com' },
    'solidigm': { logo: '/img/main/jido_logo/logo_solidigm.png', nameUs: 'Solidigm', nameKr: 'SK hynix (Solidigm)', industry: 'Data Storage', foundedYear: '2021', revenue: '$3B+', locations: '2440 Sand Hill Road, Menlo Park, CA 94025', website: 'https://www.solidigm.com' },
    'lgenergy_az': { logo: '/img/main/jido_logo/logo_lgenergys.png', nameUs: 'LG Energy Solution Arizona', nameKr: 'LG Energy Solution', industry: 'Battery Manufacturing', foundedYear: '2020', revenue: '$15B+', locations: 'Queen Creek, AZ', website: 'https://www.lgensol.com' },
    'samsung_tx': { logo: '/img/main/jido_logo/logo_samsung.png', nameUs: 'Samsung Austin Semiconductor', nameKr: 'Samsung Electronics', industry: 'Semiconductors', foundedYear: '1996', revenue: '$200B+', locations: '12100 Samsung Blvd, Austin, TX 78754', website: 'https://www.samsung.com/us/sas/' },
    'dl': { logo: '/img/main/jido_logo/logo_dl.png', nameUs: 'DL Chemical America', nameKr: 'DL Chemical', industry: 'Petrochemicals', foundedYear: '1976', revenue: '$5B+', locations: 'Houston, TX', website: 'https://www.dl.co.kr' },
    'soulbrain': { logo: '/img/main/jido_logo/logo_soulbrain.png', nameUs: 'Soulbrain America', nameKr: 'Soulbrain', industry: 'Electronic Materials', foundedYear: '1986', revenue: '$1B+', locations: 'Austin, TX', website: 'https://www.soulbrain.com' },
    'lottechemical': { logo: '/img/main/jido_logo/logo_lottechemical.png', nameUs: 'LOTTE Chemical Louisiana', nameKr: 'LOTTE Chemical', industry: 'Petrochemicals', foundedYear: '1976', revenue: '$15B+', locations: 'Lake Charles, LA', website: 'https://www.lottechem.com' },
    'hyundai_la': { logo: '/img/main/jido_logo/logo_hyundai.png', nameUs: 'Hyundai Steel America', nameKr: 'Hyundai Steel', industry: 'Steel Manufacturing', foundedYear: '1953', revenue: '$15B+', locations: 'Baton Rouge, LA', website: 'https://www.hyundai-steel.com' },
    'doosan_nd': { logo: '/img/main/jido_logo/logo_doosan.png', nameUs: 'Doosan Bobcat North America', nameKr: 'Doosan Bobcat', industry: 'Construction Equipment', foundedYear: '1947', revenue: '$5B+', locations: '250 E Beaton Dr, West Fargo, ND 58078', website: 'https://www.bobcat.com' },
    'doosan_mn': { logo: '/img/main/jido_logo/logo_doosan.png', nameUs: 'Doosan Bobcat', nameKr: 'Doosan Bobcat', industry: 'Construction Equipment', foundedYear: '1947', revenue: '$5B+', locations: 'Litchfield, MN', website: 'https://www.bobcat.com' },
    'sksiltron': { logo: '/img/main/jido_logo/logo_sksiltron.png', nameUs: 'SK Siltron America', nameKr: 'SK Siltron', industry: 'Semiconductor Wafers', foundedYear: '2017', revenue: '$2B+', locations: 'Bay City, MI', website: 'https://www.sksiltron.com' },
    'lgenergy_mi': { logo: '/img/main/jido_logo/logo_lgenergys.png', nameUs: 'LG Energy Solution Michigan', nameKr: 'LG Energy Solution', industry: 'Battery Manufacturing', foundedYear: '2020', revenue: '$15B+', locations: 'Holland, MI', website: 'https://www.lgensol.com' },
    'samsungsdi': { logo: '/img/main/jido_logo/logo_sdi.png', nameUs: 'Samsung SDS America', nameKr: 'Samsung SDS', industry: 'IT Services', foundedYear: '2016', revenue: '$0.03B', locations: '100 Challenger Road 6 Fl. Ridgefield Park, NJ', website: 'https://www.samsungsds.com' },
    'skhynix_in': { logo: '/img/main/jido_logo/logo_skhynix.png', nameUs: 'SK hynix America', nameKr: 'SK hynix', industry: 'Semiconductors', foundedYear: '1983', revenue: '$30B+', locations: 'West Lafayette, IN', website: 'https://www.skhynix.com' },
    'lgchem_oh': { logo: '/img/main/jido_logo/logo_lgchem.png', nameUs: 'LG Chem Ohio', nameKr: 'LG Chem', industry: 'Chemicals', foundedYear: '1947', revenue: '$30B+', locations: 'Lordstown, OH', website: 'https://www.lgchem.com' },
    'lgenergy_oh': { logo: '/img/main/jido_logo/logo_lgenergys.png', nameUs: 'LG Energy Solution Ohio', nameKr: 'LG Energy Solution', industry: 'Battery Manufacturing', foundedYear: '2020', revenue: '$15B+', locations: 'Lordstown, OH', website: 'https://www.lgensol.com' },
    'lotte_ny': { logo: '/img/main/jido_logo/logo_lotte2.png', nameUs: 'LOTTE Biologics', nameKr: 'LOTTE Biologics', industry: 'Biopharmaceuticals', foundedYear: '2022', revenue: '$1B+', locations: 'Syracuse, NY', website: 'https://www.lottebiologics.com' },
    'lgchem_ma': { logo: '/img/main/jido_logo/logo_lgchem.png', nameUs: 'LG Chem America', nameKr: 'LG Chem', industry: 'Chemicals', foundedYear: '1947', revenue: '$30B+', locations: 'Woburn, MA', website: 'https://www.lgchem.com' },
    'hyundai_ma': { logo: '/img/main/jido_logo/logo_hyundai.png', nameUs: 'Hyundai America Technical Center', nameKr: 'Hyundai Motor Company', industry: 'Automotive R&D', foundedYear: '1986', revenue: '$40B+', locations: 'Cambridge, MA', website: 'https://www.hyundai.com' },
    'skpharmteco': { logo: '/img/main/jido_logo/logo_pmc.png', nameUs: 'SK pharmteco', nameKr: 'SK pharmteco', industry: 'Pharmaceuticals', foundedYear: '2021', revenue: '$1B+', locations: 'King of Prussia, PA', website: 'https://www.skpharmteco.com' },
    'skbiopharma': { logo: '/img/main/jido_logo/logo_biopmc.png', nameUs: 'SK biopharmaceuticals', nameKr: 'SK biopharmaceuticals', industry: 'Pharmaceuticals', foundedYear: '1993', revenue: '$1B+', locations: 'Philadelphia, PA', website: 'https://www.skbp.com' },
    'hanwhaocean': { logo: '/img/main/jido_logo/logo_hanwhaocean.png', nameUs: 'Hanwha Ocean America', nameKr: 'Hanwha Ocean', industry: 'Shipbuilding', foundedYear: '1973', revenue: '$8B+', locations: 'Philadelphia, PA', website: 'https://www.hanwhaocean.com' },
    'lgnova': { logo: '/img/main/jido_logo/logo_lgnova.png', nameUs: 'LG NOVA', nameKr: 'LG NOVA', industry: 'Innovation Hub', foundedYear: '2021', revenue: 'N/A', locations: 'Santa Clara, CA', website: 'https://www.lgnova.com' },
    'lgenergy_va': { logo: '/img/main/jido_logo/logo_lgenergys.png', nameUs: 'LG Energy Solution Virginia', nameKr: 'LG Energy Solution', industry: 'Battery Manufacturing', foundedYear: '2020', revenue: '$15B+', locations: 'Danville, VA', website: 'https://www.lgensol.com' },
    'samsung_va': { logo: '/img/main/jido_logo/logo_samsung.png', nameUs: 'Samsung Electronics America', nameKr: 'Samsung Electronics', industry: 'Electronics', foundedYear: '1969', revenue: '$200B+', locations: 'Fairfax, VA', website: 'https://www.samsung.com' },
    'lsgreenlink': { logo: '/img/main/jido_logo/logo_lsgreenlink.png', nameUs: 'LS GreenLink', nameKr: 'LS Cable & System', industry: 'Cable Manufacturing', foundedYear: '2023', revenue: '$5B+', locations: 'Clarksville, TN', website: 'https://www.lscns.com' },
    'hankook': { logo: '/img/main/jido_logo/logo_nakook.png', nameUs: 'Hankook Tire America', nameKr: 'Hankook Tire', industry: 'Tire Manufacturing', foundedYear: '1941', revenue: '$6B+', locations: 'Clarksville, TN', website: 'https://www.hankooktire.com' },
    'doosan_sc': { logo: '/img/main/jido_logo/logo_doosan.png', nameUs: 'Doosan Bobcat', nameKr: 'Doosan Bobcat', industry: 'Construction Equipment', foundedYear: '1947', revenue: '$5B+', locations: 'Statesville, NC', website: 'https://www.bobcat.com' },
    'lgenergy_sc': { logo: '/img/main/jido_logo/logo_lgenergys.png', nameUs: 'LG Energy Solution', nameKr: 'LG Energy Solution', industry: 'Battery Manufacturing', foundedYear: '2020', revenue: '$15B+', locations: 'Columbia, SC', website: 'https://www.lgensol.com' },
    'lgchem_sc': { logo: '/img/main/jido_logo/logo_lgchem.png', nameUs: 'LG Chem', nameKr: 'LG Chem', industry: 'Chemicals', foundedYear: '1947', revenue: '$30B+', locations: 'Columbia, SC', website: 'https://www.lgchem.com' },
    'lgelect': { logo: '/img/main/jido_logo/logo_lgelect.png', nameUs: 'LG Electronics', nameKr: 'LG Electronics', industry: 'Electronics', foundedYear: '1958', revenue: '$60B+', locations: 'Clarksville, TN', website: 'https://www.lg.com' },
    'hyosung': { logo: '/img/main/jido_logo/logo_hyosung.png', nameUs: 'HYOSUNG HICO America', nameKr: 'HYOSUNG Heavy Industries', industry: 'Heavy Industries', foundedYear: '2018', revenue: '$3B+', locations: 'Millen, GA', website: 'https://www.hyosunghico.com' },
    'skon_ga': { logo: '/img/main/jido_logo/logo_skon.png', nameUs: 'SK On Georgia', nameKr: 'SK On', industry: 'Battery Manufacturing', foundedYear: '2021', revenue: '$5B+', locations: '1760 SK Blvd, Commerce, GA 30529', website: 'https://www.skon.com' },
    'kumho': { logo: '/img/main/jido_logo/logo_kumho.png', nameUs: 'Kumho Tire USA', nameKr: 'Kumho Tire', industry: 'Tire Manufacturing', foundedYear: '1960', revenue: '$2B+', locations: 'Macon, GA', website: 'https://www.kumhotire.com' },
    'hyundai_ga': { logo: '/img/main/jido_logo/logo_hyundai.png', nameUs: 'Hyundai Motor Group Metaplant America', nameKr: 'Hyundai Motor Company', industry: 'Automotive', foundedYear: '2022', revenue: '$40B+', locations: 'Bryan County, GA', website: 'https://www.hyundai.com' },
    'kia': { logo: '/img/main/jido_logo/logo_nia.png', nameUs: 'Kia Georgia', nameKr: 'Kia Corporation', industry: 'Automotive', foundedYear: '2009', revenue: '$20B+', locations: '7777 Kia Pkwy, West Point, GA 31833', website: 'https://www.kia.com' },
    'skbattery': { logo: '/img/main/jido_logo/logo_skbattery.png', nameUs: 'SK Battery America', nameKr: 'SK On', industry: 'Battery Manufacturing', foundedYear: '2019', revenue: '$5B+', locations: 'Commerce, GA', website: 'https://www.skbatteryamerica.com' },
    'skc': { logo: '/img/main/jido_logo/logi_skc.png', nameUs: 'SKC America', nameKr: 'SKC', industry: 'Chemicals/Films', foundedYear: '1976', revenue: '$3B+', locations: 'Covington, GA', website: 'https://www.skc.co.kr' },
    'lgenergy_ga': { logo: '/img/main/jido_logo/logo_lgenergys.png', nameUs: 'LG Energy Solution Georgia', nameKr: 'LG Energy Solution', industry: 'Battery Manufacturing', foundedYear: '2020', revenue: '$15B+', locations: 'Dalton, GA', website: 'https://www.lgensol.com' },
    'qcells': { logo: '/img/main/jido_logo/logo_qcells.png', nameUs: 'Hanwha Q CELLS America', nameKr: 'Hanwha Q CELLS', industry: 'Solar Energy', foundedYear: '2015', revenue: '$5B+', locations: '2625 Buttonbush Ct, Dalton, GA 30721', website: 'https://www.hanwhaqcells.com' }
};

/* =========================
   로그인/로그아웃
   ========================= */
window.logout = async function() {
    if (!confirm('로그아웃 하시겠습니까?')) return;
    try {
        await fetch('/api/personal/logout', { method: 'POST', credentials: 'include' }).catch(() => {});
        await fetch('/api/company/logout', { method: 'POST', credentials: 'include' }).catch(() => {});
        alert('로그아웃 되었습니다.');
        window.location.href = '/';
    } catch (e) {
        window.location.href = '/';
    }
};

async function checkLoginStatus() {
    try {
        const r1 = await fetch('/api/personal/check-session', { method: 'GET', credentials: 'include' });
        if (r1.ok) {
            const d = await r1.json();
            if (d.loggedIn && d.memberType === 'PERSONAL') {
                updateLoginUI(d.loginId, '개인회원');
                return;
            }
        }
        const r2 = await fetch('/api/company/check-session', { method: 'GET', credentials: 'include' });
        if (r2.ok) {
            const d = await r2.json();
            if (d.loggedIn && d.memberType === 'COMPANY') {
                updateLoginUI(d.loginId, '기업회원');
                return;
            }
        }
    } catch (e) {}
}

function updateLoginUI(userName, memberType) {
    const l = document.getElementById('loginLink');
    const r = document.getElementById('registerLink');
    if (l) l.style.display = 'none';
    if (r) r.style.display = 'none';

    const u = document.getElementById('userInfo');
    const o = document.getElementById('logoutBtn');
    if (u) {
        u.textContent = `${userName}님 (${memberType})`;
        u.style.display = 'inline';
    }
    if (o) o.style.display = 'inline';
}

window.goToMyPage = async function() {
    try {
        const r = await fetch('/api/personal/check-session', { method: 'GET', credentials: 'include' });
        if (r.ok) {
            const d = await r.json();
            if (d.loggedIn && d.memberType === 'PERSONAL') {
                window.location.href = '/mypage.html';
                return;
            }
        }
    } catch (e) {}

    try {
        const r = await fetch('/api/company/check-session', { method: 'GET', credentials: 'include' });
        if (r.ok) {
            const d = await r.json();
            if (d.loggedIn && d.memberType === 'COMPANY') {
                window.location.href = '/company_mypage.html';
                return;
            }
        }
    } catch (e) {}

    alert('로그인이 필요합니다.');
    window.location.href = '/login.html';
};

/* =========================
   Swiper
   ========================= */
function initSwiper() {
    if (typeof Swiper === "undefined") return;

    new Swiper(".mySwiper", {
        spaceBetween: 30,
        centeredSlides: true,
        loop: true,
        autoplay: { delay: 3500, disableOnInteraction: false },
        speed: 900,
        on: {
            init: function() {
                const t = Math.max(1, this.slides.length - this.loopedSlides * 2);
                const total = document.querySelector(".total");
                const current = document.querySelector(".current");
                if (total) total.textContent = String(t).padStart(2, "0");
                if (current) current.textContent = String(this.realIndex + 1).padStart(2, "0");
            },
            slideChangeTransitionStart: function() {
                const f = document.querySelector(".bar .fill");
                if (f) f.style.width = "0%";
            },
            slideChange: function() {
                const c = document.querySelector(".current");
                if (c) c.textContent = String(this.realIndex + 1).padStart(2, "0");
            },
            autoplayTimeLeft: function(_s, _t, p) {
                const f = document.querySelector(".bar .fill");
                if (f) f.style.width = `${(1 - p) * 100}%`;
            }
        }
    });
}

function goSearch() {
    const q = (document.getElementById("searchword")?.value || "").trim();
    window.location.href = q ? `/jobs?q=${encodeURIComponent(q)}` : "/jobs";
}
window.goSearch = goSearch;

/* =========================
   Cards
   ========================= */
function renderJobCard(job) {
    const col = document.createElement("div");
    col.className = "col-3";

    const a = document.createElement("a");
    a.className = "job_link";
    a.href = job.detailUrl || "/jobs";

    const thumb = job.thumbUrl ? `<img src="${job.thumbUrl}" alt="">` : '';
    const logo = job.logoUrl ? `<img src="${job.logoUrl}" alt="">` : '';

    a.innerHTML = `
    <div class="job_nohover">
      <div class="box">
        <div class="top_img">${thumb}</div>
        <div class="box_in">
          <div class="logo">${logo}</div>
          <div class="nm">${escapeHtml(job.companyName || '')}</div>
          <div class="txt">${escapeHtml(job.summary || '')}</div>
        </div>
      </div>
      <div class="arrow_wrap"><span class="arrow"></span></div>
    </div>
  `;

    col.appendChild(a);
    return col;
}

function renderNoticeCard(n) {
    const col = document.createElement("div");
    col.className = "col-3";

    const a = document.createElement("a");
    a.className = "notice_link";
    a.href = n.detailUrl || "/notices";

    a.innerHTML = `
    <div class="box">
      <div class="txt">${escapeHtml(n.title || '')}</div>
      <div class="date">${escapeHtml(n.date || '')}</div>
    </div>
  `;

    col.appendChild(a);
    return col;
}

async function loadMainCards() {
    const jw = document.getElementById("jobCards");
    const nw = document.getElementById("noticeCards");

    try {
        const [jr, nr] = await Promise.all([
            fetch(CONFIG.jobsApi, { cache: "no-store" }).catch(() => null),
            fetch(CONFIG.noticesApi, { cache: "no-store" }).catch(() => null)
        ]);

        if (jw && jr?.ok) {
            const jobs = await jr.json();
            jw.innerHTML = "";
            (Array.isArray(jobs) ? jobs : []).slice(0, 8).forEach(j => jw.appendChild(renderJobCard(j)));
        }

        if (nw && nr?.ok) {
            const notices = await nr.json();
            nw.innerHTML = "";
            (Array.isArray(notices) ? notices : []).slice(0, 4).forEach(n => nw.appendChild(renderNoticeCard(n)));
        }
    } catch (e) {}
}

/* =========================
   지도 팝업
   ========================= */
window.jidoView = function(key) {
    const popup = document.getElementById("companyPopup");
    const content = document.getElementById("popupContent");
    if (!popup || !content) return;

    const company = COMPANY_DATA[key];
    if (!company) {
        content.innerHTML = "<p style='padding:50px; text-align:center;'>기업 정보를 찾을 수 없습니다.</p>";
        popup.classList.add('show');
        return;
    }

    const locationsHtml = escapeHtml(company.locations || '').replace(/\n/g, '<br>');

    content.innerHTML = `
    <div class="popup_left">
      <div class="popup_company">
        <div class="logo"><img src="${company.logo}" alt=""></div>
        <div class="name">${escapeHtml(company.nameUs)}</div>
        <a href="${company.website}" target="_blank" class="btn" rel="noopener">Website</a>
      </div>
      <div class="popup_company">
        <div class="logo"><img src="${company.logo}" alt=""></div>
        <div class="name">${escapeHtml(company.nameKr)}</div>
        <a href="${company.website}" target="_blank" class="btn" rel="noopener">Website</a>
      </div>
    </div>

    <div class="popup_right">
      <div class="popup_section">
        <h4>About the Company</h4>
        <table class="popup_table">
          <tr><td>Company Name</td><td>${escapeHtml(company.nameUs)}</td></tr>
          <tr><td>Industry</td><td>${escapeHtml(company.industry)}</td></tr>
          <tr><td>Founding Year</td><td>${escapeHtml(company.foundedYear)}</td></tr>
          <tr><td>Revenue</td><td>${escapeHtml(company.revenue)}</td></tr>
          <tr><td>Locations</td><td>${locationsHtml}</td></tr>
        </table>
      </div>

      <div class="popup_section">
        <h4>About the Korea Headquarters</h4>
        <table class="popup_table">
          <tr><td>Company Name</td><td>${escapeHtml(company.nameKr)}</td></tr>
          <tr><td>Industry</td><td>${escapeHtml(company.industry)}</td></tr>
          <tr><td>Founding Year</td><td>${escapeHtml(company.foundedYear)}</td></tr>
          <tr><td>Revenue</td><td>${escapeHtml(company.revenue)}</td></tr>
        </table>
      </div>
    </div>
  `;

    popup.classList.add('show');
};

window.jidoClose = function() {
    const popup = document.getElementById("companyPopup");
    if (popup) popup.classList.remove('show');
};

/* 팝업 바깥 클릭 시 닫기 */
document.addEventListener('click', function(e) {
    const popup = document.getElementById("companyPopup");
    if (!popup) return;
    if (e.target === popup) popup.classList.remove('show');
});

function escapeHtml(str) {
    return String(str ?? '').replace(/[&<>"']/g, m => ({
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': "&quot;",
        "'": "&#039;"
    }[m]));
}

/* =========================
   초기화
   ========================= */
document.addEventListener("DOMContentLoaded", () => {
    initSwiper();
    loadMainCards();
    checkLoginStatus();

    const input = document.getElementById("searchword");
    if (input) {
        input.addEventListener("keydown", e => {
            if (e.key === "Enter") {
                e.preventDefault();
                goSearch();
            }
        });
    }
});
