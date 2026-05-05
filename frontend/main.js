import * as api from './api.js';

// ═══ State ═══
let currentPage = 'home';
let currentBaby = null;
let babies = [];

// ═══ Router ═══
function navigate(page) {
  currentPage = page;
  render();
  document.querySelectorAll('.nav-item').forEach(n => {
    n.classList.toggle('active', n.dataset.page === page);
  });
}

// ═══ Toast ═══
function toast(msg, type = '') {
  const el = document.createElement('div');
  el.className = `toast ${type}`;
  el.textContent = msg;
  document.body.appendChild(el);
  requestAnimationFrame(() => el.classList.add('show'));
  setTimeout(() => { el.classList.remove('show'); setTimeout(() => el.remove(), 400); }, 2500);
}

// ═══ Render ═══
function render() {
  const app = document.getElementById('app');
  const pages = { home: homePage, vaccines: vaccinesPage, growth: growthPage, nutrition: nutritionPage, register: registerPage };
  app.innerHTML = (pages[currentPage] || homePage)() + navBar();
  bindEvents();
}

// ═══ Nav Bar ═══
function navBar() {
  const items = [
    { id: 'home', icon: '🏠', label: 'Home' },
    { id: 'vaccines', icon: '💉', label: 'Vaccines' },
    { id: 'growth', icon: '📊', label: 'Growth' },
    { id: 'nutrition', icon: '🍌', label: 'Nutrition' },
  ];
  return `<nav class="nav-bar">${items.map(i => `
    <button class="nav-item ${currentPage === i.id ? 'active' : ''}" data-page="${i.id}">
      <span style="font-size:20px">${i.icon}</span>
      <span class="nav-label">${i.label}</span>
    </button>`).join('')}</nav>`;
}

// ═══ HOME PAGE ═══
function homePage() {
  if (!currentBaby) {
    return `<div class="page">
      <div class="hero">
        <span class="hero-emoji">👶</span>
        <h1 class="hero-title">Shishu-Sneh</h1>
        <p class="hero-subtitle">Smart Baby's First Year Guide</p>
      </div>
      <div class="empty-state">
        <span class="empty-icon">🍼</span>
        <h2 class="empty-title">Welcome, New Parent!</h2>
        <p class="empty-text">Register your baby to start tracking vaccinations, growth milestones, and get AI-powered nutrition advice.</p>
        <button class="btn btn-primary btn-block" id="goRegister">✨ Register Your Baby</button>
      </div>
    </div>`;
  }

  const b = currentBaby;
  const dob = new Date(b.dateOfBirth);
  const ageMonths = Math.floor((Date.now() - dob) / (30.44 * 24 * 60 * 60 * 1000));
  const ageWeeks = Math.floor((Date.now() - dob) / (7 * 24 * 60 * 60 * 1000));

  return `<div class="page">
    <div class="hero">
      <span class="hero-emoji">👶</span>
      <h1 class="hero-title">Hello, ${b.name}!</h1>
      <p class="hero-subtitle">${ageMonths} months old · Born ${dob.toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: 'numeric' })}</p>
    </div>

    <div class="stats-grid">
      <div class="stat-card coral">
        <span class="stat-icon">📅</span>
        <span class="stat-value">${ageWeeks}</span>
        <span class="stat-label">Weeks</span>
      </div>
      <div class="stat-card sage">
        <span class="stat-icon">⚖️</span>
        <span class="stat-value">${b.birthWeight}</span>
        <span class="stat-label">Birth kg</span>
      </div>
      <div class="stat-card blue">
        <span class="stat-icon">💉</span>
        <span class="stat-value">21</span>
        <span class="stat-label">Vaccines</span>
      </div>
    </div>

    <div class="quick-actions">
      <button class="quick-action" data-page="vaccines">
        <span class="quick-action-icon coral">💉</span>
        <span class="quick-action-text">Vaccination Schedule</span>
      </button>
      <button class="quick-action" data-page="growth">
        <span class="quick-action-icon sage">📈</span>
        <span class="quick-action-text">Growth Tracker</span>
      </button>
      <button class="quick-action" data-page="nutrition">
        <span class="quick-action-icon gold">🍲</span>
        <span class="quick-action-text">Feeding Guide</span>
      </button>
      <button class="quick-action" id="goRegister">
        <span class="quick-action-icon blue">➕</span>
        <span class="quick-action-text">Add Baby</span>
      </button>
    </div>

    <div class="section">
      <div class="section-header">
        <h2 class="section-title">Next Milestones</h2>
      </div>
      <div class="card">
        <p style="font-size:14px;color:var(--slate)">🎯 <strong>${getMilestone(ageMonths)}</strong></p>
      </div>
    </div>
  </div>`;
}

function getMilestone(months) {
  const milestones = [
    [0, 'Head lifting · First smile · Recognizes voices'],
    [3, 'Rolling over · Grasping objects · Cooing sounds'],
    [6, 'Sitting up · First solid foods · Babbling'],
    [9, 'Crawling · Pulling to stand · Says "mama/dada"'],
    [12, 'First steps · First words · Pincer grasp'],
  ];
  for (let i = milestones.length - 1; i >= 0; i--) {
    if (months >= milestones[i][0]) return milestones[i][1];
  }
  return milestones[0][1];
}

// ═══ REGISTER PAGE ═══
function registerPage() {
  return `<div class="page">
    <div class="hero">
      <span class="hero-emoji">✨</span>
      <h1 class="hero-title">Register Baby</h1>
      <p class="hero-subtitle">Tell us about your little one</p>
    </div>
    <div class="card">
      <div class="form-group">
        <label class="form-label">Baby's Name</label>
        <input class="form-input" id="regName" type="text" placeholder="e.g. Arya" />
      </div>
      <div class="form-group">
        <label class="form-label">Date of Birth</label>
        <input class="form-input" id="regDob" type="date" />
      </div>
      <div class="form-group">
        <label class="form-label">Birth Weight (kg)</label>
        <input class="form-input" id="regWeight" type="number" step="0.1" placeholder="e.g. 3.2" />
      </div>
      <button class="btn btn-primary btn-block" id="submitRegister" style="margin-top:8px">
        🍼 Register Baby
      </button>
    </div>

    ${babies.length > 0 ? `
    <div class="section" style="margin-top:24px">
      <div class="section-header"><h2 class="section-title">Registered Babies</h2></div>
      ${babies.map(b => `
        <div class="log-item" style="cursor:pointer" data-select-baby="${b.id}">
          <div class="log-icon weight">👶</div>
          <div class="log-details">
            <div class="log-value">${b.name}</div>
            <div class="log-meta">Born: ${b.dateOfBirth} · ${b.birthWeight} kg</div>
          </div>
        </div>`).join('')}
    </div>` : ''}
  </div>`;
}

// ═══ VACCINES PAGE ═══
function vaccinesPage() {
  if (!currentBaby) return nobabyState('vaccines');
  return `<div class="page">
    <div class="hero" style="background:linear-gradient(135deg, var(--sage-dark), var(--sage), var(--sage-light))">
      <span class="hero-emoji">💉</span>
      <h1 class="hero-title">Vaccinations</h1>
      <p class="hero-subtitle">${currentBaby.name}'s immunization schedule</p>
    </div>
    <div id="vaccineList"><div class="loader"><div class="loader-dots"><span></span><span></span><span></span></div></div></div>
  </div>`;
}

// ═══ GROWTH PAGE ═══
function growthPage() {
  if (!currentBaby) return nobabyState('growth');
  return `<div class="page">
    <div class="hero" style="background:linear-gradient(135deg, var(--blue), #7BA3EF, var(--blue-light))">
      <span class="hero-emoji">📊</span>
      <h1 class="hero-title">Growth Tracker</h1>
      <p class="hero-subtitle">${currentBaby.name}'s weight & height logs</p>
    </div>
    <button class="btn btn-primary btn-block" id="openLogModal" style="margin-bottom:20px">📝 Add New Entry</button>
    <div id="healthLogs"><div class="loader"><div class="loader-dots"><span></span><span></span><span></span></div></div></div>
  </div>`;
}

// ═══ NUTRITION PAGE ═══
function nutritionPage() {
  const ingredients = ['🍚 Rice', '🫘 Dal', '🍌 Banana', '🥔 Potato', '🥕 Carrot', '🍠 Sweet Potato', '🥚 Egg', '🍎 Apple', '🥛 Milk', '🌾 Ragi'];
  return `<div class="page">
    <div class="hero" style="background:linear-gradient(135deg, var(--gold), #E8B838, var(--gold-light))">
      <span class="hero-emoji">🍲</span>
      <h1 class="hero-title">AI Nutrition Guide</h1>
      <p class="hero-subtitle">Powered by Google Gemini</p>
    </div>
    <div class="card" style="margin-bottom:20px">
      <div class="card-title" style="margin-bottom:12px">Baby's Age (months)</div>
      <input class="form-input" id="nutAge" type="number" placeholder="e.g. 8" value="${currentBaby ? Math.floor((Date.now() - new Date(currentBaby.dateOfBirth)) / (30.44*24*60*60*1000)) : ''}" />
    </div>
    <div class="section">
      <div class="section-header"><h2 class="section-title">Select Ingredients</h2></div>
      <div class="chip-container" id="chipContainer">
        ${ingredients.map(i => `<button class="chip" data-ingredient="${i.split(' ')[1]}">${i}</button>`).join('')}
      </div>
    </div>
    <button class="btn btn-primary btn-block" id="generateGuide">✨ Generate Feeding Guide</button>
    <div id="nutritionResult" style="margin-top:20px"></div>
  </div>`;
}

function nobabyState(page) {
  return `<div class="page">
    <div class="empty-state" style="margin-top:60px">
      <span class="empty-icon">👶</span>
      <h2 class="empty-title">No Baby Registered</h2>
      <p class="empty-text">Please register your baby first to access this feature.</p>
      <button class="btn btn-primary" id="goRegister">Register Baby</button>
    </div>
  </div>`;
}

// ═══ Bind Events ═══
function bindEvents() {
  // Navigation
  document.querySelectorAll('.nav-item').forEach(el => {
    el.addEventListener('click', () => navigate(el.dataset.page));
  });
  document.querySelectorAll('.quick-action[data-page]').forEach(el => {
    el.addEventListener('click', () => navigate(el.dataset.page));
  });
  document.querySelectorAll('#goRegister').forEach(el => {
    el.addEventListener('click', () => navigate('register'));
  });

  // Register
  const regBtn = document.getElementById('submitRegister');
  if (regBtn) regBtn.addEventListener('click', handleRegister);

  // Select existing baby
  document.querySelectorAll('[data-select-baby]').forEach(el => {
    el.addEventListener('click', () => {
      const id = parseInt(el.dataset.selectBaby);
      currentBaby = babies.find(b => b.id === id);
      toast(`Switched to ${currentBaby.name}`, 'success');
      navigate('home');
    });
  });

  // Vaccines load
  if (currentPage === 'vaccines' && currentBaby) loadVaccines();

  // Growth load + modal
  if (currentPage === 'growth' && currentBaby) loadHealthLogs();
  const logBtn = document.getElementById('openLogModal');
  if (logBtn) logBtn.addEventListener('click', openLogModal);

  // Nutrition chips + generate
  document.querySelectorAll('.chip').forEach(el => {
    el.addEventListener('click', () => el.classList.toggle('selected'));
  });
  const genBtn = document.getElementById('generateGuide');
  if (genBtn) genBtn.addEventListener('click', handleNutrition);
}

// ═══ Handlers ═══
async function handleRegister() {
  const name = document.getElementById('regName').value.trim();
  const dob = document.getElementById('regDob').value;
  const weight = parseFloat(document.getElementById('regWeight').value);
  if (!name || !dob || !weight) { toast('Please fill all fields', 'error'); return; }

  try {
    const baby = await api.registerBaby({ name, dateOfBirth: dob, birthWeight: weight });
    currentBaby = baby;
    babies.push(baby);
    toast(`${baby.name} registered! 🎉`, 'success');
    navigate('home');
  } catch (e) { toast('Registration failed', 'error'); }
}

async function loadVaccines() {
  try {
    const vaccines = await api.getVaccines(currentBaby.id);
    const today = new Date().toISOString().split('T')[0];
    const completed = vaccines.filter(v => v.dueDate < today).length;
    const container = document.getElementById('vaccineList');

    container.innerHTML = `
      <div class="card" style="margin-bottom:16px">
        <div style="display:flex;justify-content:space-between;align-items:center">
          <span style="font-size:14px;font-weight:600">Progress</span>
          <span style="font-size:13px;color:var(--sage-dark);font-weight:700">${completed}/${vaccines.length}</span>
        </div>
        <div class="progress-bar"><div class="progress-fill" style="width:${(completed/vaccines.length)*100}%"></div></div>
      </div>
      <div class="filter-row">
        <button class="filter-chip active" data-filter="all">All</button>
        <button class="filter-chip" data-filter="upcoming">Upcoming</button>
        <button class="filter-chip" data-filter="completed">Completed</button>
      </div>
      <div class="vaccine-list">${vaccines.map(v => {
        const status = v.dueDate < today ? 'completed' : 'upcoming';
        return `<div class="vaccine-item" data-status="${status}">
          <div class="vaccine-dot ${status}"></div>
          <div class="vaccine-info">
            <div class="vaccine-name">${v.vaccineName}</div>
            <div class="vaccine-date">${v.dueAt} · ${new Date(v.dueDate).toLocaleDateString('en-IN', {day:'numeric',month:'short',year:'numeric'})}</div>
          </div>
          <span class="vaccine-badge ${status}">${status}</span>
        </div>`;
      }).join('')}</div>`;

    container.querySelectorAll('.filter-chip').forEach(chip => {
      chip.addEventListener('click', () => {
        container.querySelectorAll('.filter-chip').forEach(c => c.classList.remove('active'));
        chip.classList.add('active');
        const f = chip.dataset.filter;
        container.querySelectorAll('.vaccine-item').forEach(item => {
          item.style.display = (f === 'all' || item.dataset.status === f) ? 'flex' : 'none';
        });
      });
    });
  } catch (e) { document.getElementById('vaccineList').innerHTML = '<p style="text-align:center;color:var(--red)">Failed to load vaccines</p>'; }
}

async function loadHealthLogs() {
  try {
    const logs = await api.getHealthLogs(currentBaby.id);
    const container = document.getElementById('healthLogs');
    if (logs.length === 0) {
      container.innerHTML = `<div class="empty-state"><span class="empty-icon">📏</span><h2 class="empty-title">No Logs Yet</h2><p class="empty-text">Tap "Add New Entry" to record weight & height.</p></div>`;
      return;
    }
    container.innerHTML = logs.map(l => `
      <div class="log-item">
        <div class="log-icon weight">⚖️</div>
        <div class="log-details">
          <div class="log-value">${l.weight} kg · ${l.height} cm</div>
          <div class="log-meta">${new Date(l.date).toLocaleDateString('en-IN', {day:'numeric',month:'short',year:'numeric'})} ${l.milestoneAchieved ? '· 🎯 ' + l.milestoneAchieved : ''}</div>
        </div>
      </div>`).join('');
  } catch (e) { document.getElementById('healthLogs').innerHTML = '<p style="text-align:center;color:var(--red)">Failed to load logs</p>'; }
}

function openLogModal() {
  const overlay = document.createElement('div');
  overlay.className = 'modal-overlay';
  overlay.innerHTML = `<div class="modal-sheet">
    <div class="modal-handle"></div>
    <h2 class="modal-title">📝 Add Health Log</h2>
    <div class="form-group"><label class="form-label">Date</label><input class="form-input" id="logDate" type="date" value="${new Date().toISOString().split('T')[0]}" /></div>
    <div class="form-group"><label class="form-label">Weight (kg)</label><input class="form-input" id="logWeight" type="number" step="0.1" placeholder="e.g. 6.5" /></div>
    <div class="form-group"><label class="form-label">Height (cm)</label><input class="form-input" id="logHeight" type="number" step="0.1" placeholder="e.g. 65" /></div>
    <div class="form-group"><label class="form-label">Milestone (optional)</label><input class="form-input" id="logMilestone" type="text" placeholder="e.g. First tooth" /></div>
    <button class="btn btn-primary btn-block" id="submitLog">Save Entry</button>
    <button class="btn btn-secondary btn-block" id="closeModal" style="margin-top:8px">Cancel</button>
  </div>`;
  document.body.appendChild(overlay);
  overlay.querySelector('#closeModal').addEventListener('click', () => overlay.remove());
  overlay.addEventListener('click', e => { if (e.target === overlay) overlay.remove(); });
  overlay.querySelector('#submitLog').addEventListener('click', async () => {
    const data = {
      date: document.getElementById('logDate').value,
      weight: parseFloat(document.getElementById('logWeight').value),
      height: parseFloat(document.getElementById('logHeight').value),
      milestoneAchieved: document.getElementById('logMilestone').value || null
    };
    if (!data.date || !data.weight || !data.height) { toast('Fill date, weight & height', 'error'); return; }
    try {
      await api.addHealthLog(currentBaby.id, data);
      toast('Health log saved! 📊', 'success');
      overlay.remove();
      loadHealthLogs();
    } catch (e) { toast('Failed to save', 'error'); }
  });
}

async function handleNutrition() {
  const age = parseInt(document.getElementById('nutAge').value);
  const selected = [...document.querySelectorAll('.chip.selected')].map(c => c.dataset.ingredient);
  if (!age) { toast('Enter baby\'s age', 'error'); return; }
  if (selected.length === 0) { toast('Select at least 1 ingredient', 'error'); return; }

  const result = document.getElementById('nutritionResult');
  result.innerHTML = '<div class="loader"><div class="loader-dots"><span></span><span></span><span></span></div></div><p style="text-align:center;color:var(--slate);margin-top:8px">Gemini is thinking...</p>';

  try {
    const data = await api.getNutrition({ ageInMonths: age, ingredients: selected });
    const html = data.feedingGuide
      .replace(/### (.*)/g, '<h3>$1</h3>')
      .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
      .replace(/\* (.*)/g, '<li>$1</li>')
      .replace(/---/g, '<hr>')
      .replace(/\n/g, '<br>');
    result.innerHTML = `<div class="ai-response">${html}</div>`;
  } catch (e) { result.innerHTML = '<p style="text-align:center;color:var(--red)">Failed to generate guide. Try again.</p>'; }
}

// ═══ Init ═══
async function init() {
  try {
    babies = await api.getAllBabies();
    if (babies.length > 0) currentBaby = babies[0];
  } catch (e) { console.warn('Backend not reachable'); }
  render();
}

init();
