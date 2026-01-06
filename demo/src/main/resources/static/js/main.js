const progressCircle = document.querySelector(".autoplay-progress svg");
const progressContent = document.querySelector(".autoplay-progress span");
var swiper = new Swiper(".mySwiper", {
    spaceBetween: 30,
    centeredSlides: true,
    loop: true,
    autoplay: {
        delay: 3500,
        disableOnInteraction: false
    },
    speed: 2500,
    on: {
        init: function () {
          document.querySelector(".total").textContent = String(this.slides.length).padStart(2, "0");
          document.querySelector(".current").textContent = String(this.realIndex + 1).padStart(2, "0");
        },
        slideChangeTransitionStart: function () {
          document.querySelector(".bar .fill").style.width = "0%";
        },
        slideChange: function () {
          document.querySelector(".current").textContent = String(this.realIndex + 1).padStart(2, "0");
        },
        autoplayTimeLeft(s, time, progress) {
          document.querySelector(".bar .fill").style.width = `${(1 - progress) * 100}%`;
        }
      }
});

// job_link 클래스에 마우스 오버 시 active 클래스 추가/제거 (992px 이상에서만 동작)
document.addEventListener("DOMContentLoaded", function() {
   const jobLinks = document.querySelectorAll(".job_link");
   
   jobLinks.forEach(function(jobLink) {
       jobLink.addEventListener("mouseenter", function() {
           if (window.innerWidth >= 50) {
               this.classList.add("active");
           }
       });
       
       jobLink.addEventListener("mouseleave", function() {
           if (window.innerWidth >= 50) {
               this.classList.remove("active");
           }
       });
   });
});

function jidoClose(event) {
  event.preventDefault();
  const cpbBoxGroup = document.querySelector('.cpb_box_group');
  cpbBoxGroup.style.display = 'none';
}

document.querySelectorAll('.jido_slink').forEach(function(el){
  el.addEventListener('mouseenter', function(){
    el.classList.add('on');   // 마우스 올렸을 때 on 클래스 추가
  });
  el.addEventListener('mouseleave', function(){
    el.classList.remove('on'); // 마우스 벗어나면 on 클래스 제거
  });
});

/***** 설정 *****/
const JIDO_JSON_URL = '/js/new_jido_company_info.json'; // JSON 경로
const LOGO_BASE_PATH = '/img/main/jido_logo/'; // 로고 기본 경로

/***** 내부 캐시 *****/
let __jidoCache = null;

/***** 유틸 함수 *****/
function isEmpty(val) {
  if (val === null || val === undefined) return true;
  if (typeof val === 'string') {
    const noTags = val.replace(/<[^>]*>/g, '').trim();
    return noTags.length === 0;
  }
  if (Array.isArray(val)) return val.length === 0;
  if (typeof val === 'object') return Object.keys(val).length === 0;
  return false;
}

function el(tag, className, attrs = {}) {
  const $ = document.createElement(tag);
  if (className) $.className = className;
  for (const [k, v] of Object.entries(attrs)) {
    if (k === 'text') $.textContent = v;
    else if (k === 'html') $.innerHTML = v;
    else $.setAttribute(k, v);
  }
  return $;
}

function addRow($ul, label, value, { html = false } = {}) {
  if (isEmpty(value)) return;
  const li = el('li', '');
  const th = el('div', 'th', { text: label });
  const td = el('div', 'td', html ? { html: value } : { text: value });
  li.appendChild(th);
  li.appendChild(td);
  $ul.appendChild(li);
}

function makeLogoSrc(logo) {
  if (isEmpty(logo)) return null;
  if (/^https?:\/\//i.test(logo) || logo.startsWith('/')) return logo;
  return LOGO_BASE_PATH + logo;
}

/***** 공통 블록 *****/
function buildHeaderBlock({ logo, title, website }) {
  const wrap = el('div', 'cnp_logo');
  const src = makeLogoSrc(logo);
  if (src) {
    const logoArea = el('div', 'logo_area');
    const img = el('img', '', { src, alt: '' });
    logoArea.appendChild(img);
    wrap.appendChild(logoArea);
  }
  if (!isEmpty(title)) wrap.appendChild(el('div', 'cnp_tit', { text: title }));
  if (!isEmpty(website)) {
    const site = el('div', 'cnp_website');
    const a = el('a', 'cpb_link', { href: website, target: '_blank', rel: 'noopener', text: 'Website' });
    site.appendChild(a);
    wrap.appendChild(site);
  }
  return wrap;
}

function buildInfoBlock(titleText) {
  const info = el('div', 'cnp_info');
  if (!isEmpty(titleText)) info.appendChild(el('div', 'tit', { text: titleText }));
  const ul = el('ul', 'cplist');
  info.appendChild(ul);
  return { info, ul };
}

/***** 항목 렌더러 *****/
function renderCorporateItem(item) {
  const box = el('div', 'cnp_logo_item');
  const header = buildHeaderBlock({
    logo: item.logo,
    title: item.companyName,
    website: item.website
  });
  box.appendChild(header);

  const { info, ul } = buildInfoBlock('About the Company');
  addRow(ul, 'Company Name', item.companyName);
  addRow(ul, 'Industry', item.industry);
  addRow(ul, 'Founding Year', item.foundedYear);
  addRow(ul, 'Revenue', item.revenue);
  addRow(ul, 'Locations', item.locations, { html: true });
  addRow(ul, 'Notes', item.notes);
  box.appendChild(info);

  if (box.children.length === 1 && ul.children.length === 0) return null;
  return box;
}

function renderGroupItem(group) {
  const box = el('div', 'cnp_logo_item groupcom');
  const header = buildHeaderBlock({
    logo: group.logo,
    title: group.groupName,
    website: group.website
  });
  box.appendChild(header);

  const { info, ul } = buildInfoBlock('About the Korea Headquarters');
  addRow(ul, 'Company Name', group.groupName);
  addRow(ul, 'Industry', group.industry);
  addRow(ul, 'Founded Year', group.foundedYear);
  addRow(ul, 'revenue', group.revenue);
  addRow(ul, 'Headquarters', group.headquarters);
  addRow(ul, 'Notes', group.notes);
  box.appendChild(info);

  if (box.children.length === 1 && ul.children.length === 0) return null;
  return box;
}

/***** JSON 로딩 *****/
async function getJidoData() {
  if (!__jidoCache) {
    const res = await fetch(JIDO_JSON_URL, { cache: 'no-store' });
    if (!res.ok) throw new Error('new_jido_company_info.json 로드 실패');
    __jidoCache = await res.json();
  }
  return __jidoCache;
}

/***** 핵심 함수 *****/
async function jidoView(event, key) {
  event?.preventDefault?.();

  const groupBox = document.querySelector('.cpb_box_group');
  const wrap = groupBox?.querySelector('.company_pop_wrap_box');
  const container = wrap?.querySelector('.company_agent');
  if (!container) return console.error('company_agent 컨테이너를 찾을 수 없습니다.');

  try {
    const data = await getJidoData();
    const entry = data[key];
    container.innerHTML = '';

    if (!entry) {
      container.innerHTML = '<p style="padding:16px;">데이터가 없습니다.</p>';
      groupBox.style.display = 'block';
      return;
    }

    // corporateList 렌더링
    if (Array.isArray(entry.corporateList)) {
      entry.corporateList.forEach(item => {
        const node = renderCorporateItem(item);
        if (node) container.appendChild(node);
      });
    }

    // groupInfo 렌더링
    if (entry.groupInfo && !isEmpty(entry.groupInfo)) {
      const gnode = renderGroupItem(entry.groupInfo);
      if (gnode) container.appendChild(gnode);
    }

    if (!container.children.length) {
      container.innerHTML = '<p style="padding:16px;">표시할 항목이 없습니다.</p>';
    }

    // 오직 cpb_box_group만 표시
    groupBox.style.display = 'block';

  } catch (err) {
    console.error(err);
    container.innerHTML = '<p style="padding:16px;color:#d00">데이터 로드 중 오류 발생</p>';
    groupBox.style.display = 'block';
  }
}

/***** 닫기 *****/
function jidoClose(event) {
  event?.preventDefault?.();
  const box = event?.currentTarget?.closest('.cpb_box_group');
  if (box) box.style.display = 'none';
}

/***** 전역 등록 *****/
window.jidoView = jidoView;
window.jidoClose = jidoClose;
