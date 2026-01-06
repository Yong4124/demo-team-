const CONFIG = {
    // ✅ MSA 기준: API Gateway or BFF에서 메인화면에 필요한 데이터만 합쳐서 내려줘도 되고,
    // 아래처럼 프론트에서 각각 호출해도 됨.
    jobsApi: "/api/main/jobs",        // (예) jobs-service 집계
    noticesApi: "/api/main/notices",  // (예) notices-service 집계
    companyMapApi: "/api/main/companies-map" // (선택) 지도 팝업 데이터
};

/* =========================
   기업 정보 데이터 (원본 사이트 기반)
   ========================= */
const COMPANY_DATA = {
    // California (CA)
    'hyundai_ca': { name: 'Hyundai Motor America', state: 'California', address: '10550 Talbert Ave, Fountain Valley, CA 92708', website: 'https://www.hyundaiusa.com', logo: '/img/main/jido_logo/logo_hyundai.png' },
    'samsung_ca': { name: 'Samsung Semiconductor', state: 'California', address: '3655 North First Street, San Jose, CA 95134', website: 'https://www.samsung.com/us/about-us/our-business/device-solutions/', logo: '/img/main/jido_logo/logo_samsung.png' },
    'skhynix_ca': { name: 'SK hynix America', state: 'California', address: '3101 North First Street, San Jose, CA 95134', website: 'https://www.skhynix.com', logo: '/img/main/jido_logo/logo_skhynix.png' },
    'solidigm': { name: 'Solidigm (SK hynix)', state: 'California', address: '2440 Sand Hill Road, Menlo Park, CA 94025', website: 'https://www.solidigm.com', logo: '/img/main/jido_logo/logo_solidigm.png' },

    // Arizona (AZ)
    'lgenergy_az': { name: 'LG Energy Solution Arizona', state: 'Arizona', address: 'Queen Creek, AZ', website: 'https://www.lgensol.com', logo: '/img/main/jido_logo/logo_lgenergys.png' },

    // Texas (TX)
    'samsung_tx': { name: 'Samsung Austin Semiconductor', state: 'Texas', address: '12100 Samsung Blvd, Austin, TX 78754', website: 'https://www.samsung.com/us/sas/', logo: '/img/main/jido_logo/logo_samsung.png' },
    'dl': { name: 'DL Chemical', state: 'Texas', address: 'Houston, TX', website: 'https://www.dl.co.kr', logo: '/img/main/jido_logo/logo_dl.png' },
    'soulbrain': { name: 'Soulbrain', state: 'Texas', address: 'Austin, TX', website: 'https://www.soulbrain.com', logo: '/img/main/jido_logo/logo_soulbrain.png' },

    // Louisiana (LA)
    'lottechemical': { name: 'LOTTE Chemical Louisiana', state: 'Louisiana', address: 'Lake Charles, LA', website: 'https://www.lottechem.com', logo: '/img/main/jido_logo/logo_lottechemical.png' },
    'hyundai_la': { name: 'Hyundai Steel America', state: 'Louisiana', address: 'Baton Rouge, LA', website: 'https://www.hyundai-steel.com', logo: '/img/main/jido_logo/logo_hyundai.png' },

    // North Dakota (ND)
    'doosan_nd': { name: 'Doosan Bobcat', state: 'North Dakota', address: '250 E Beaton Dr, West Fargo, ND 58078', website: 'https://www.bobcat.com', logo: '/img/main/jido_logo/logo_doosan.png' },

    // Minnesota (MN)
    'doosan_mn': { name: 'Doosan Bobcat', state: 'Minnesota', address: 'Litchfield, MN', website: 'https://www.bobcat.com', logo: '/img/main/jido_logo/logo_doosan.png' },

    // Michigan (MI)
    'sksiltron': { name: 'SK Siltron America', state: 'Michigan', address: 'Bay City, MI', website: 'https://www.sksiltron.com', logo: '/img/main/jido_logo/logo_sksiltron.png' },
    'lgenergy_mi': { name: 'LG Energy Solution Michigan', state: 'Michigan', address: 'Holland, MI', website: 'https://www.lgensol.com', logo: '/img/main/jido_logo/logo_lgenergys.png' },

    // Indiana (IN)
    'samsungsdi': { name: 'Samsung SDI America', state: 'Indiana', address: 'Kokomo, IN', website: 'https://www.samsungsdi.com', logo: '/img/main/jido_logo/logo_sdi.png' },
    'skhynix_in': { name: 'SK hynix', state: 'Indiana', address: 'West Lafayette, IN', website: 'https://www.skhynix.com', logo: '/img/main/jido_logo/logo_skhynix.png' },

    // Ohio (OH)
    'lgchem_oh': { name: 'LG Chem Ohio', state: 'Ohio', address: 'Lordstown, OH', website: 'https://www.lgchem.com', logo: '/img/main/jido_logo/logo_lgchem.png' },
    'lgenergy_oh': { name: 'LG Energy Solution Ohio', state: 'Ohio', address: 'Lordstown, OH', website: 'https://www.lgensol.com', logo: '/img/main/jido_logo/logo_lgenergys.png' },

    // Maine/New York (ME/NY)
    'lotte_me': { name: 'LOTTE Biologics', state: 'New York', address: 'Syracuse, NY', website: 'https://www.lottebiologics.com', logo: '/img/main/jido_logo/logo_lotte2.png' },

    // Massachusetts (MA)
    'lgchem_ma': { name: 'LG Chem America', state: 'Massachusetts', address: 'Woburn, MA', website: 'https://www.lgchem.com', logo: '/img/main/jido_logo/logo_lgchem.png' },
    'hyundai_ma': { name: 'Hyundai America Technical Center', state: 'Massachusetts', address: 'Cambridge, MA', website: 'https://www.hyundai.com', logo: '/img/main/jido_logo/logo_hyundai.png' },

    // Pennsylvania (PA)
    'skpharmteco': { name: 'SK pharmteco', state: 'Pennsylvania', address: 'King of Prussia, PA', website: 'https://www.skpharmteco.com', logo: '/img/main/jido_logo/logo_pmc.png' },
    'skbiopharma': { name: 'SK biopharmaceuticals', state: 'Pennsylvania', address: 'Philadelphia, PA', website: 'https://www.skbp.com', logo: '/img/main/jido_logo/logo_biopmc.png' },
    'hanwhaocean': { name: 'Hanwha Ocean', state: 'Pennsylvania', address: 'Philadelphia, PA', website: 'https://www.hanwhaocean.com', logo: '/img/main/jido_logo/logo_hanwhaocean.png' },

    // Virginia (VA)
    'lgnova': { name: 'LG NOVA', state: 'Virginia', address: 'Santa Clara, CA (HQ)', website: 'https://www.lgnova.com', logo: '/img/main/jido_logo/logo_lgnova.png' },
    'lgenergy_va': { name: 'LG Energy Solution Virginia', state: 'Virginia', address: 'Danville, VA', website: 'https://www.lgensol.com', logo: '/img/main/jido_logo/logo_lgenergys.png' },
    'samsung_va': { name: 'Samsung Electronics America', state: 'Virginia', address: 'Fairfax, VA', website: 'https://www.samsung.com', logo: '/img/main/jido_logo/logo_samsung.png' },

    // Tennessee (TN)
    'lsgreenlink': { name: 'LS GreenLink', state: 'Tennessee', address: 'Clarksville, TN', website: 'https://www.lscns.com', logo: '/img/main/jido_logo/logo_lsgreenlink.png' },
    'hankook': { name: 'Hankook Tire', state: 'Tennessee', address: 'Clarksville, TN', website: 'https://www.hankooktire.com', logo: '/img/main/jido_logo/logo_nakook.png' },

    // South Carolina (SC)
    'doosan_sc': { name: 'Doosan Bobcat', state: 'South Carolina', address: 'Statesville, NC', website: 'https://www.bobcat.com', logo: '/img/main/jido_logo/logo_doosan.png' },
    'lgenergy_sc': { name: 'LG Energy Solution', state: 'South Carolina', address: 'Columbia, SC', website: 'https://www.lgensol.com', logo: '/img/main/jido_logo/logo_lgenergys.png' },
    'lgchem_sc': { name: 'LG Chem', state: 'South Carolina', address: 'Columbia, SC', website: 'https://www.lgchem.com', logo: '/img/main/jido_logo/logo_lgchem.png' },
    'lgelect': { name: 'LG Electronics', state: 'Tennessee', address: 'Clarksville, TN', website: 'https://www.lg.com', logo: '/img/main/jido_logo/logo_lgelect.png' },

    // Georgia (GA)
    'hyosung': { name: 'HYOSUNG HICO', state: 'Georgia', address: 'Millen, GA', website: 'https://www.hyosunghico.com', logo: '/img/main/jido_logo/logo_hyosung.png' },
    'skon_ga': { name: 'SK On Georgia', state: 'Georgia', address: '1760 SK Blvd, Commerce, GA 30529', website: 'https://www.skon.com', logo: '/img/main/jido_logo/logo_skon.png' },
    'kumho': { name: 'Kumho Tire', state: 'Georgia', address: 'Macon, GA', website: 'https://www.kumhotire.com', logo: '/img/main/jido_logo/logo_kumho.png' },
    'hyundai_ga': { name: 'Hyundai Motor Group Metaplant America', state: 'Georgia', address: 'Bryan County, GA', website: 'https://www.hyundai.com', logo: '/img/main/jido_logo/logo_hyundai.png' },
    'kia': { name: 'Kia Georgia', state: 'Georgia', address: '7777 Kia Pkwy, West Point, GA 31833', website: 'https://www.kia.com', logo: '/img/main/jido_logo/logo_nia.png' },
    'skbattery': { name: 'SK Battery America', state: 'Georgia', address: 'Commerce, GA', website: 'https://www.skbatteryamerica.com', logo: '/img/main/jido_logo/logo_skbattery.png' },
    'skc': { name: 'SKC', state: 'Georgia', address: 'Covington, GA', website: 'https://www.skc.co.kr', logo: '/img/main/jido_logo/logi_skc.png' },
    'lgenergy_ga': { name: 'LG Energy Solution Georgia', state: 'Georgia', address: 'Dalton, GA', website: 'https://www.lgensol.com', logo: '/img/main/jido_logo/logo_lgenergys.png' },
    'qcells': { name: 'Hanwha Q CELLS', state: 'Georgia', address: '2625 Buttonbush Ct, Dalton, GA 30721', website: 'https://www.hanwhaqcells.com', logo: '/img/main/jido_logo/logo_qcells.png' }
};

function initSwiper() {
    if (typeof Swiper === "undefined") return;

    new Swiper(".mySwiper", {
        spaceBetween: 30,
        centeredSlides: true,
        loop: true,
        autoplay: { delay: 3500, disableOnInteraction: false },
        speed: 900,
        on: {
            init: function () {
                const totalSlides = this.slides.length - this.loopedSlides * 2;
                const total = Math.max(1, totalSlides);
                document.querySelector(".total").textContent = String(total).padStart(2, "0");
                document.querySelector(".current").textContent = String(this.realIndex + 1).padStart(2, "0");
            },
            slideChangeTransitionStart: function () {
                const fill = document.querySelector(".bar .fill");
                if (fill) fill.style.width = "0%";
            },
            slideChange: function () {
                const cur = document.querySelector(".current");
                if (cur) cur.textContent = String(this.realIndex + 1).padStart(2, "0");
            },
            autoplayTimeLeft: function (_s, _time, progress) {
                const fill = document.querySelector(".bar .fill");
                if (fill) fill.style.width = `${(1 - progress) * 100}%`;
            }
        }
    });
}

function goSearch() {
    const q = (document.getElementById("searchword")?.value || "").trim();
    window.location.href = q ? `/jobs?q=${encodeURIComponent(q)}` : "/jobs";
}

/* =========================
   채용공고 카드 렌더
   ========================= */
function renderJobCard(job) {
    const col = document.createElement("div");
    col.className = "col-3";

    const a = document.createElement("a");
    a.className = "job_link";
    a.href = job.detailUrl || "/jobs";

    // 기본 카드
    const noHover = document.createElement("div");
    noHover.className = "job_nohover";

    const box = document.createElement("div");
    box.className = "box";

    const topImg = document.createElement("div");
    topImg.className = "top_img";
    if (job.thumbUrl) {
        const img = document.createElement("img");
        img.src = job.thumbUrl;
        img.alt = "";
        topImg.appendChild(img);
    }

    const boxIn = document.createElement("div");
    boxIn.className = "box_in";

    const logo = document.createElement("div");
    logo.className = "logo";
    if (job.logoUrl) {
        const img = document.createElement("img");
        img.src = job.logoUrl;
        img.alt = "";
        logo.appendChild(img);
    }

    const nm = document.createElement("div");
    nm.className = "nm";
    nm.textContent = job.companyName || "";

    const txt = document.createElement("div");
    txt.className = "txt";
    txt.textContent = job.summary || "";

    boxIn.appendChild(logo);
    boxIn.appendChild(nm);
    boxIn.appendChild(txt);

    box.appendChild(topImg);
    box.appendChild(boxIn);
    noHover.appendChild(box);

    // 화살표
    const arrowWrap = document.createElement("div");
    arrowWrap.className = "arrow_wrap";
    const arrow = document.createElement("span");
    arrow.className = "arrow";
    arrowWrap.appendChild(arrow);
    noHover.appendChild(arrowWrap);

    // Hover 확장 카드(스크린샷처럼)
    const hover = document.createElement("div");
    hover.className = "job_hover_box";
    hover.innerHTML = `
    <div class="job_info">
      <div class="back_img">${job.hoverBgUrl ? `<img src="${job.hoverBgUrl}" alt="">` : ""}</div>
      <div class="jhb_info_box">
        <div class="jhb_logo">${job.logoUrl ? `<img src="${job.logoUrl}" alt="">` : ""}</div>
        <div class="jhb_ibox">
          <div class="jhb_itit">${escapeHtml(job.companyName || "")}</div>
          <div class="jhb_itxt">${escapeHtml(job.summary || "")}</div>
        </div>
      </div>
    </div>
  `;

    a.appendChild(noHover);
    a.appendChild(hover);

    col.appendChild(a);
    return col;
}

/* =========================
   공지 카드 렌더
   ========================= */
function renderNoticeCard(n) {
    const col = document.createElement("div");
    col.className = "col-3";

    const a = document.createElement("a");
    a.className = "notice_link";
    a.href = n.detailUrl || "/notices";

    const box = document.createElement("div");
    box.className = "box";

    const txt = document.createElement("div");
    txt.className = "txt";
    txt.textContent = n.title || "";

    const date = document.createElement("div");
    date.className = "date";
    date.textContent = n.date || "";

    box.appendChild(txt);
    box.appendChild(date);
    a.appendChild(box);
    col.appendChild(a);
    return col;
}

async function loadMainCards() {
    const jobWrap = document.getElementById("jobCards");
    const noticeWrap = document.getElementById("noticeCards");

    try {
        const [jobsRes, noticesRes] = await Promise.all([
            fetch(CONFIG.jobsApi, { cache: "no-store" }),
            fetch(CONFIG.noticesApi, { cache: "no-store" })
        ]);

        if (jobWrap && jobsRes.ok) {
            const jobs = await jobsRes.json();
            jobWrap.innerHTML = "";
            (Array.isArray(jobs) ? jobs : []).slice(0, 8).forEach((j) => jobWrap.appendChild(renderJobCard(j)));
        }

        if (noticeWrap && noticesRes.ok) {
            const notices = await noticesRes.json();
            noticeWrap.innerHTML = "";
            (Array.isArray(notices) ? notices : []).slice(0, 4).forEach((n) => noticeWrap.appendChild(renderNoticeCard(n)));
        }
    } catch (e) {
        console.error(e);
    }
}

/* hover (992px 이상에서만 활성) */
function initJobHover() {
    const links = document.querySelectorAll(".job_link");
    links.forEach((a) => {
        a.addEventListener("mouseenter", function () {
            if (window.innerWidth >= 992) this.classList.add("active");
        });
        a.addEventListener("mouseleave", function () {
            if (window.innerWidth >= 992) this.classList.remove("active");
        });
    });
}

/* =========================
   지도 팝업 (기업 정보 표시)
   ========================= */
function jidoView(event, key) {
    event?.preventDefault?.();

    const popup = document.getElementById("jidoPopup");
    const agent = document.getElementById("companyAgent");
    if (!popup || !agent) return;

    popup.style.display = "block";

    // COMPANY_DATA에서 기업 정보 가져오기
    const company = COMPANY_DATA[key];

    if (!company) {
        agent.innerHTML = "<p style='padding:20px; text-align:center;'>기업 정보를 찾을 수 없습니다.</p>";
        return;
    }

    agent.innerHTML = `
        <div class="company_pop_content">
            <div class="logo">
                <img src="${company.logo}" alt="${escapeHtml(company.name)}">
            </div>
            <div class="cnp_tit">${escapeHtml(company.name)}</div>
            <table class="info_table">
                <tr>
                    <th>주(State)</th>
                    <td>${escapeHtml(company.state)}</td>
                </tr>
                <tr>
                    <th>주소</th>
                    <td>${escapeHtml(company.address)}</td>
                </tr>
                <tr>
                    <th>웹사이트</th>
                    <td><a href="${company.website}" target="_blank" rel="noopener noreferrer">${company.website}</a></td>
                </tr>
            </table>
            <a href="${company.website}" target="_blank" rel="noopener noreferrer" class="btn_website">
                기업 웹사이트 방문
            </a>
        </div>
    `;
}

function jidoClose(event) {
    event?.preventDefault?.();
    const popup = document.getElementById("jidoPopup");
    if (popup) popup.style.display = "none";
}

function escapeHtml(str) {
    return String(str).replace(/[&<>"']/g, (m) => ({
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': "&quot;",
        "'": "&#039;"
    }[m]));
}

document.addEventListener("DOMContentLoaded", () => {
    initSwiper();
    loadMainCards();

    // enter 검색
    const input = document.getElementById("searchword");
    if (input) {
        input.addEventListener("keydown", (e) => {
            if (e.key === "Enter") {
                e.preventDefault();
                goSearch();
            }
        });
    }

    // 카드 hover (데이터 렌더 후 재바인딩 필요)
    const observer = new MutationObserver(() => initJobHover());
    const jobWrap = document.getElementById("jobCards");
    if (jobWrap) observer.observe(jobWrap, { childList: true });
});

window.goSearch = goSearch;
window.jidoView = jidoView;
window.jidoClose = jidoClose;