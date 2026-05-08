import * as api from './api.js';

// ═══ State ═══
let currentPage = 'home';
let currentBaby = null;
let babies = [];

// ═══ Router ═══
function navigate(page) {
  currentPage = page;
  render();
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
  
  window.scrollTo(0, 0); // Reset scroll position
  app.innerHTML = (pages[currentPage] || homePage)() + navBar();
  bindEvents();
}

// ═══ Nav Bar ═══
function navBar() {
  const isNavActive = (page) => currentPage === page ? 'text-primary active-nav-shadow' : 'text-outline hover:text-primary';
  const isIconFilled = (page) => currentPage === page ? "font-variation-settings: 'FILL' 1;" : "";

  return `
  <nav class="fixed bottom-0 left-0 w-full flex justify-around items-center px-2 py-4 pb-8 bg-surface-container-lowest/90 backdrop-blur-md border-t border-surface-container-high rounded-t-3xl z-50">
      <a href="#" class="nav-item ${isNavActive('home')} flex flex-col items-center justify-center w-16 rounded-xl py-1 active:scale-90 transition-all duration-150" data-page="home">
          <span class="material-symbols-outlined text-[28px]" style="${isIconFilled('home')}">home</span>
          <span class="font-['Plus_Jakarta_Sans'] text-[10px] font-semibold mt-1">Home</span>
      </a>
      <a href="#" class="nav-item ${isNavActive('growth')} flex flex-col items-center justify-center w-16 rounded-xl py-1 active:scale-90 transition-all duration-150" data-page="growth">
          <span class="material-symbols-outlined text-[28px]" style="${isIconFilled('growth')}">show_chart</span>
          <span class="font-['Plus_Jakarta_Sans'] text-[10px] font-medium mt-1">Growth</span>
      </a>
      <a href="#" class="nav-item ${isNavActive('vaccines')} flex flex-col items-center justify-center w-16 rounded-xl py-1 active:scale-90 transition-all duration-150" data-page="vaccines">
          <span class="material-symbols-outlined text-[28px]" style="${isIconFilled('vaccines')}">shield</span>
          <span class="font-['Plus_Jakarta_Sans'] text-[10px] font-medium mt-1">Vaccines</span>
      </a>
      <a href="#" class="nav-item ${isNavActive('nutrition')} flex flex-col items-center justify-center w-16 rounded-xl py-1 active:scale-90 transition-all duration-150" data-page="nutrition">
          <span class="material-symbols-outlined text-[28px]" style="${isIconFilled('nutrition')}">restaurant</span>
          <span class="font-['Plus_Jakarta_Sans'] text-[10px] font-medium mt-1">Nutrition</span>
      </a>
  </nav>`;
}

// ═══ HOME PAGE ═══
function homePage() {
  if (!currentBaby) return registerPage();

  const b = currentBaby;
  const dob = new Date(b.dateOfBirth);
  const ageMonths = Math.floor((Date.now() - dob) / (30.44 * 24 * 60 * 60 * 1000));
  const progressPct = Math.round(Math.min(ageMonths, 12)/12*100);
  const strokeOffset = 282.7 - (282.7 * Math.min(ageMonths, 12) / 12);

  return `
  <main class="px-6 py-10 pb-32 max-w-2xl mx-auto space-y-10 animate-[fadeIn_0.4s_ease-out]">
      <section class="flex justify-between items-start">
          <div class="space-y-2">
              <h1 class="text-[32px] leading-tight text-on-surface">
                  <span class="font-light">Good Morning,</span><br/>
                  <span class="font-bold">${b.name}</span>
              </h1>
              <div class="inline-flex items-center px-3 py-1 bg-primary-container/20 text-primary rounded-full text-sm font-semibold tracking-wide">
                  ${ageMonths} months old
              </div>
          </div>
          <div class="h-14 w-14 rounded-full overflow-hidden shadow-sm border border-surface-container-high">
              <img class="h-full w-full object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuBBNxUZhzAfqOn68M4TkcSCTOMilRV6M9JuYHjcOVGFq6gVlqaHfm2m2Jq6-B-C8KT0BnrHNPM_CMHDzKSTpSIqXq_OxfG8Gxg7ersHZa3ySQdmJ5jVrwmBGy5CWbNVKcW-ByTZ8kll5UA-APR8bESkWrQru76BkhZO9njtKj3xq68UBvI0-EPklNNM-8AVPxukM0U12ZEwgJ51uVCUIn12wRwz1KfIEK993qtDa9N74PGSHuRtd3lq5WYL0CFt3cwB4wdba2aKBjo" />
          </div>
      </section>

      <section class="relative bg-gradient-to-br from-primary-fixed to-surface-container-lowest rounded-3xl p-6 shadow-[0_8px_30px_rgb(0,0,0,0.04)] border border-surface-container-high/50 overflow-hidden">
          <div class="absolute -right-8 -top-8 w-40 h-40 bg-white/40 rounded-full blur-3xl pointer-events-none"></div>
          <div class="flex justify-between items-center relative z-10">
              <div class="space-y-6">
                  <div>
                      <p class="text-on-surface-variant font-label-md text-sm uppercase tracking-wider mb-1">Birth Weight</p>
                      <div class="flex items-center gap-2">
                          <span class="font-headline-md text-2xl text-on-surface font-bold">${b.birthWeight} kg</span>
                          <div class="flex items-center bg-secondary-fixed/50 px-1.5 py-0.5 rounded text-secondary text-xs font-bold">
                              <span class="material-symbols-outlined text-[14px]">arrow_upward</span>
                          </div>
                      </div>
                  </div>
                  <div>
                      <p class="text-on-surface-variant font-label-md text-sm uppercase tracking-wider mb-1">Born</p>
                      <span class="font-headline-sm text-xl text-on-surface font-bold">${dob.toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: 'numeric' })}</span>
                  </div>
              </div>
              <div class="relative w-28 h-28 flex items-center justify-center">
                  <svg class="transform -rotate-90 w-28 h-28" viewBox="0 0 100 100">
                      <circle class="text-white" cx="50" cy="50" fill="transparent" r="45" stroke="currentColor" stroke-width="8"></circle>
                      <circle class="text-primary transition-all duration-1000 ease-out" cx="50" cy="50" fill="transparent" r="45" stroke="currentColor" stroke-dasharray="282.7" stroke-dashoffset="${strokeOffset}" stroke-linecap="round" stroke-width="8"></circle>
                  </svg>
                  <div class="absolute flex flex-col items-center justify-center text-center">
                      <span class="font-bold text-lg text-primary">${progressPct}%</span>
                      <span class="text-[10px] text-on-surface-variant font-medium leading-tight px-2">of 1st year</span>
                  </div>
              </div>
          </div>
      </section>
      
      <section class="grid grid-cols-2 gap-4">
          <div class="nav-item bg-surface-container-lowest rounded-3xl p-5 shadow-sm border border-surface-container-highest/50 hover:shadow-md transition-shadow cursor-pointer active:scale-95 flex flex-col" data-page="growth">
              <div class="w-10 h-10 bg-primary-fixed/50 text-primary rounded-xl flex items-center justify-center mb-3">
                  <span class="material-symbols-outlined text-[24px]">show_chart</span>
              </div>
              <h4 class="font-label-lg font-bold text-on-surface">Growth Tracker</h4>
              <p class="text-on-surface-variant text-xs mt-1">Log & track vitals</p>
          </div>
          <div class="nav-item bg-surface-container-lowest rounded-3xl p-5 shadow-sm border border-surface-container-highest/50 hover:shadow-md transition-shadow cursor-pointer active:scale-95 flex flex-col" data-page="vaccines">
              <div class="w-10 h-10 bg-secondary-fixed/50 text-secondary rounded-xl flex items-center justify-center mb-3">
                  <span class="material-symbols-outlined text-[24px]">shield</span>
              </div>
              <h4 class="font-label-lg font-bold text-on-surface">Vaccinations</h4>
              <p class="text-on-surface-variant text-xs mt-1">Upcoming schedules</p>
          </div>
          <div class="nav-item bg-surface-container-lowest rounded-3xl p-5 shadow-sm border border-surface-container-highest/50 hover:shadow-md transition-shadow cursor-pointer active:scale-95 flex flex-col" data-page="nutrition">
              <div class="w-10 h-10 bg-tertiary-fixed/50 text-tertiary rounded-xl flex items-center justify-center mb-3">
                  <span class="material-symbols-outlined text-[24px]">restaurant</span>
              </div>
              <h4 class="font-label-lg font-bold text-on-surface">Feeding Guide</h4>
              <p class="text-on-surface-variant text-xs mt-1">AI Nutrition advice</p>
          </div>
          <div class="nav-item bg-surface-container-lowest rounded-3xl p-5 shadow-sm border border-surface-container-highest/50 hover:shadow-md transition-shadow cursor-pointer active:scale-95 flex flex-col" data-page="register">
              <div class="w-10 h-10 bg-[#fef08a]/50 text-[#854d0e] rounded-xl flex items-center justify-center mb-3">
                  <span class="material-symbols-outlined text-[24px]">person_add</span>
              </div>
              <h4 class="font-label-lg font-bold text-on-surface">Add Baby</h4>
              <p class="text-on-surface-variant text-xs mt-1">Register new profile</p>
          </div>
      </section>
  </main>`;
}

// ═══ REGISTER PAGE ═══
function registerPage() {
  return `
  <main class="flex-1 px-margin-mobile pt-12 pb-32 z-20 animate-[fadeIn_0.4s_ease-out]">
      <div class="bg-surface-container-lowest rounded-2xl shadow-[0_12px_40px_-12px_rgba(161,62,47,0.15)] p-6 sm:p-8 w-full max-w-md mx-auto">
          <div class="mb-8 text-center">
              <div class="w-16 h-16 bg-primary-container/20 text-primary rounded-full flex items-center justify-center mx-auto mb-4 text-3xl">👶</div>
              <h2 class="text-headline-lg font-headline-lg text-on-surface mb-1">Welcome!</h2>
              <p class="text-body-md font-body-md text-on-surface-variant">Let's set up your baby's profile.</p>
          </div>
          <div class="space-y-6">
              <div class="flex flex-col gap-2">
                  <label class="text-label-md font-label-md text-on-surface">Baby's Full Name</label>
                  <input id="regName" class="w-full min-h-[56px] bg-surface-container border border-outline-variant rounded-xl px-4 text-body-md font-body-md text-on-surface placeholder:text-outline focus:border-primary-container focus:ring-2 focus:ring-primary-container/20 transition-all outline-none" placeholder="Enter name" type="text" />
              </div>
              <div class="flex flex-col gap-2">
                  <label class="text-label-md font-label-md text-on-surface">Date of Birth</label>
                  <input id="regDob" class="w-full min-h-[56px] bg-surface-container border border-outline-variant rounded-xl px-4 text-body-md font-body-md text-on-surface focus:border-primary-container focus:ring-2 focus:ring-primary-container/20 transition-all outline-none" type="date" />
              </div>
              <div class="flex flex-col gap-2">
                  <label class="text-label-md font-label-md text-on-surface">Birth Weight (kg)</label>
                  <input id="regWeight" class="w-full min-h-[56px] bg-surface-container border border-outline-variant rounded-xl px-4 text-body-md font-body-md text-on-surface focus:border-primary-container focus:ring-2 focus:ring-primary-container/20 transition-all outline-none" placeholder="0.0" step="0.1" type="number" />
              </div>
              <div class="pt-4 flex flex-col gap-4">
                  <button id="submitRegister" class="w-full min-h-[56px] bg-gradient-to-r from-primary-container to-primary text-white rounded-full flex items-center justify-center text-[18px] font-bold shadow-[0_4px_14px_rgba(232,116,97,0.4)] active:scale-[0.98] transition-transform">
                      Complete Profile
                  </button>
              </div>
          </div>
          
          ${babies.length > 0 ? `
          <div class="mt-8 pt-6 border-t border-surface-container-highest">
              <h3 class="text-label-lg font-bold text-on-surface mb-4">Switch Profiles</h3>
              <div class="space-y-3">
                  ${babies.map(b => `
                  <div class="flex items-center gap-4 p-3 bg-surface rounded-xl border border-outline-variant cursor-pointer hover:border-primary transition-colors" data-select-baby="${b.id}">
                      <div class="w-10 h-10 rounded-full bg-primary-container/20 text-primary flex items-center justify-center font-bold">${b.name.charAt(0)}</div>
                      <div>
                          <div class="font-label-md text-on-surface">${b.name}</div>
                          <div class="text-[12px] text-on-surface-variant">Born: ${b.dateOfBirth} · ${b.birthWeight}kg</div>
                      </div>
                  </div>
                  `).join('')}
              </div>
          </div>
          ` : ''}
      </div>
  </main>`;
}

// ═══ VACCINES PAGE ═══
function vaccinesPage() {
  if (!currentBaby) return nobabyState();
  return `
  <header class="bg-surface/80 backdrop-blur-md border-b border-surface-container-highest shadow-sm sticky top-0 z-40 flex items-center justify-between px-6 py-4 w-full">
      <h1 class="text-xl font-extrabold text-primary font-['Plus_Jakarta_Sans'] tracking-tight">Vaccination Schedule</h1>
  </header>
  <main class="max-w-3xl mx-auto px-margin-mobile pt-6 pb-32 animate-[fadeIn_0.4s_ease-out]">
      <div id="vaccineList"><div class="loader"><div class="loader-dots"><span></span><span></span><span></span></div></div></div>
  </main>`;
}

// ═══ GROWTH PAGE ═══
function growthPage() {
  if (!currentBaby) return nobabyState();
  return `
  <header class="bg-surface/80 backdrop-blur-md border-b border-surface-container-highest shadow-sm sticky top-0 z-40 flex items-center justify-between px-6 py-4 w-full">
      <h1 class="text-xl font-extrabold text-primary font-['Plus_Jakarta_Sans'] tracking-tight">Growth Tracker</h1>
  </header>
  <main class="max-w-2xl mx-auto px-4 pt-6 pb-32 space-y-6 animate-[fadeIn_0.4s_ease-out]">
      <button id="openLogModal" class="w-full bg-gradient-to-br from-primary to-[#E87461] text-white rounded-2xl shadow-lg flex items-center justify-center px-5 py-4 hover:scale-[1.02] active:scale-95 transition-transform gap-2 font-headline-sm mb-6">
          <span class="material-symbols-outlined text-[24px]">add</span>
          Log New Entry
      </button>
      
      <div class="relative border-l-2 border-surface-container ml-4 space-y-6" id="healthLogs">
          <div class="loader"><div class="loader-dots"><span></span><span></span><span></span></div></div>
      </div>
  </main>`;
}

// ═══ NUTRITION PAGE ═══
function nutritionPage() {
  if (!currentBaby) return nobabyState();
  const ingredients = ['🍚 Rice', '🫘 Dal', '🍌 Banana', '🥔 Potato', '🥕 Carrot', '🍠 Sweet Potato', '🥚 Egg', '🍎 Apple', '🥛 Milk', '🌾 Ragi'];
  const ageMonths = Math.floor((Date.now() - new Date(currentBaby.dateOfBirth)) / (30.44*24*60*60*1000));
  
  return `
  <header class="bg-surface/80 backdrop-blur-md border-b border-surface-container-highest shadow-sm sticky top-0 z-40 flex items-center justify-between px-6 py-4 w-full">
      <h1 class="text-xl font-extrabold text-primary font-['Plus_Jakarta_Sans'] tracking-tight">Nutrition Guide</h1>
      <div class="flex items-center gap-1.5 px-3 py-1.5 bg-primary-container/20 rounded-full border border-primary/20">
          <span class="material-symbols-outlined text-[16px] text-primary" style="font-variation-settings: 'FILL' 1;">auto_awesome</span>
          <span class="text-[11px] font-bold tracking-wider text-primary">AI Powered</span>
      </div>
  </header>
  <main class="max-w-md mx-auto px-4 pt-6 pb-32 space-y-6 animate-[fadeIn_0.4s_ease-out]">
      <section class="bg-white rounded-[24px] p-6 shadow-sm border border-surface-container-highest space-y-6">
          <div class="space-y-3">
              <label class="font-label-lg text-on-surface-variant block">Baby's Age (Months)</label>
              <input id="nutAge" class="w-full h-[56px] bg-surface-container-low border border-outline-variant rounded-2xl px-4 font-headline-sm text-primary focus:ring-2 focus:ring-primary-container focus:border-primary-container outline-none transition-all" type="number" value="${ageMonths || 6}"/>
          </div>
          <div class="space-y-3">
              <label class="font-label-lg text-on-surface-variant block">Select Ingredients</label>
              <div class="flex flex-wrap gap-2 pt-2">
                  ${ingredients.map(i => `<button class="chip flex items-center gap-2 bg-surface border border-outline-variant text-on-surface px-3 py-1.5 rounded-full font-label-md shadow-sm transition-all hover:bg-surface-variant" data-ingredient="${i.split(' ')[1]}"><span class="text-[16px]">${i.split(' ')[0]}</span> ${i.split(' ')[1]}</button>`).join('')}
              </div>
          </div>
          <button id="generateGuide" class="w-full h-[56px] bg-gradient-to-r from-primary to-primary-container text-white font-headline-sm rounded-2xl shadow-[0_8px_20px_rgba(232,116,97,0.25)] active:scale-[0.98] transition-all hover:opacity-95 flex items-center justify-center gap-2">
              <span class="material-symbols-outlined" style="font-variation-settings: 'FILL' 1;">auto_awesome</span>
              Generate Recipe
          </button>
      </section>
      <div id="nutritionResult"></div>
  </main>`;
}

function nobabyState() {
  return `
  <div class="px-6 py-20 text-center">
      <div class="w-20 h-20 bg-primary-container/20 text-primary rounded-full flex items-center justify-center mx-auto mb-6 text-4xl">👶</div>
      <h2 class="text-headline-md font-bold text-on-surface mb-2">No Baby Registered</h2>
      <p class="text-body-md text-on-surface-variant mb-8">Please register your baby to access this feature.</p>
      <button class="nav-item bg-primary text-white px-8 py-3 rounded-full font-bold shadow-lg" data-page="register">Register Baby</button>
  </div>`;
}

// ═══ Bind Events ═══
function bindEvents() {
  // Navigation
  document.querySelectorAll('.nav-item').forEach(el => {
    el.addEventListener('click', (e) => {
        e.preventDefault();
        navigate(el.dataset.page);
    });
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
    el.addEventListener('click', () => {
        el.classList.toggle('bg-primary');
        el.classList.toggle('text-white');
        el.classList.toggle('border-primary');
        el.classList.toggle('selected');
    });
  });
  const genBtn = document.getElementById('generateGuide');
  if (genBtn) genBtn.addEventListener('click', handleNutrition);
}

// ═══ Handlers ═══
async function handleRegister(e) {
  e.preventDefault();
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
    const container = document.getElementById('vaccineList');

    const html = vaccines.map(v => {
        const isCompleted = v.completed;
        const color = isCompleted ? 'secondary' : 'primary';
        const bg = isCompleted ? 'bg-secondary-container/50 text-secondary' : 'bg-primary-container/10 text-primary';
        const icon = isCompleted ? 'check_circle' : 'schedule';
        const ring = isCompleted ? 'border-surface-container-high' : 'ring-2 ring-primary-container/20';
        
        return `
        <div class="bg-white p-5 rounded-[24px] shadow-sm border ${ring} flex items-center gap-4 mb-4">
            <div class="w-12 h-12 rounded-2xl ${bg} flex items-center justify-center">
                <span class="material-symbols-outlined" style="font-variation-settings: 'FILL' 1;">${icon}</span>
            </div>
            <div class="flex-1">
                <h5 class="font-label-lg text-on-surface">${v.vaccineName}</h5>
                <p class="text-[11px] text-${color} ${isCompleted ? 'font-medium' : 'font-extrabold'} mt-1 uppercase">${isCompleted ? 'Given on' : 'Due'} ${new Date(v.dueDate).toLocaleDateString('en-IN', {day:'numeric',month:'short',year:'numeric'})}</p>
            </div>
            <button class="toggle-vaccine flex-shrink-0 w-10 h-10 rounded-full flex items-center justify-center transition-all ${isCompleted ? 'bg-secondary/10 text-secondary hover:bg-secondary/20' : 'bg-surface-container text-on-surface-variant hover:bg-primary/10 hover:text-primary'}" data-name="${v.vaccineName}" data-status="${isCompleted}">
                <span class="material-symbols-outlined text-[20px]">${isCompleted ? 'undo' : 'check'}</span>
            </button>
        </div>`;
    }).join('');
    
    container.innerHTML = `<div class="space-y-4 relative pl-4 border-l-2 border-outline-variant/30">${html}</div>`;

    // Bind event listeners for toggling vaccines
    document.querySelectorAll('.toggle-vaccine').forEach(btn => {
      btn.addEventListener('click', async (e) => {
        const btnEl = e.currentTarget;
        const vName = btnEl.dataset.name;
        const currentStatus = btnEl.dataset.status === 'true';
        
        btnEl.innerHTML = `<span class="material-symbols-outlined animate-spin text-[20px]">progress_activity</span>`;
        btnEl.disabled = true;

        try {
          await api.markVaccine(currentBaby.id, { vaccineName: vName, completed: !currentStatus });
          loadVaccines(); // Refresh list
        } catch (err) {
          toast('Failed to update vaccine status', 'error');
          loadVaccines(); // Reset UI
        }
      });
    });

  } catch (e) { document.getElementById('vaccineList').innerHTML = '<p class="text-center text-error font-bold mt-10">Failed to load vaccines.</p>'; }
}

async function loadHealthLogs() {
  try {
    const logs = await api.getHealthLogs(currentBaby.id);
    const container = document.getElementById('healthLogs');
    if (logs.length === 0) {
      container.innerHTML = `<div class="text-center py-10"><p class="text-on-surface-variant font-medium">No logs yet. Tap above to add!</p></div>`;
      return;
    }
    
    container.innerHTML = logs.map((l, i) => `
    <div class="relative pl-6">
        <div class="absolute -left-[9px] top-1 w-4 h-4 ${i === 0 ? 'bg-primary' : 'bg-surface-container-highest'} rounded-full border-4 border-surface shadow-sm"></div>
        <div class="bg-white rounded-2xl p-4 border border-surface-container-high shadow-sm hover:shadow-md transition-shadow">
            <div class="flex justify-between items-start mb-2">
                <span class="inline-block px-2.5 py-1 bg-surface-container text-on-surface-variant text-[11px] font-bold uppercase tracking-wider rounded-md">${new Date(l.date).toLocaleDateString('en-IN', {day:'numeric',month:'short'})}</span>
                <div class="flex gap-4 text-right">
                    <div>
                        <p class="text-[10px] text-outline uppercase font-bold">WT</p>
                        <p class="font-label-md text-on-surface">${l.weight}kg</p>
                    </div>
                    <div>
                        <p class="text-[10px] text-outline uppercase font-bold">HT</p>
                        <p class="font-label-md text-on-surface">${l.height}cm</p>
                    </div>
                </div>
            </div>
            ${l.milestoneAchieved ? `<p class="font-body-md text-on-surface-variant font-medium mt-1">${l.milestoneAchieved}</p>` : ''}
        </div>
    </div>`).join('');
  } catch (e) { document.getElementById('healthLogs').innerHTML = '<p class="text-center text-error mt-10">Failed to load logs.</p>'; }
}

function openLogModal() {
  const overlay = document.createElement('div');
  overlay.className = 'modal-overlay';
  overlay.innerHTML = `<div class="modal-sheet">
    <div class="modal-handle"></div>
    <h2 class="modal-title">Log Growth Entry</h2>
    <div class="form-group"><label class="form-label">Date</label><input class="form-input" id="logDate" type="date" value="${new Date().toISOString().split('T')[0]}" /></div>
    <div class="form-group"><label class="form-label">Weight (kg)</label><input class="form-input" id="logWeight" type="number" step="0.1" placeholder="e.g. 6.5" /></div>
    <div class="form-group"><label class="form-label">Height (cm)</label><input class="form-input" id="logHeight" type="number" step="0.1" placeholder="e.g. 65" /></div>
    <div class="form-group"><label class="form-label">Milestone (optional)</label><input class="form-input" id="logMilestone" type="text" placeholder="e.g. First tooth!" /></div>
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
      toast('Health log saved!', 'success');
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
  result.innerHTML = '<div class="loader"><div class="loader-dots"><span></span><span></span><span></span></div></div><p class="text-center text-on-surface-variant mt-2 font-medium">Gemini AI is generating recipe...</p>';

  try {
    const data = await api.getNutrition({ ageInMonths: age, ingredients: selected });
    const html = data.feedingGuide
      .replace(/\n\n/g, '<br><br>') // Ensure paragraph breaks
      .replace(/\n/g, '<br>');
      
    result.innerHTML = `
    <div class="bg-white rounded-[32px] overflow-hidden shadow-md border border-surface-container-highest mt-6">
        <div class="relative w-full aspect-[4/3] bg-surface-container">
            <img class="w-full h-full object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuCvnJMc0jzfrWkcdKwrCoMWybn2VBRS7wog2W1Koh5u-IFrcwab1oI2OqdCWvy-TWq0OaxkROnyYuOSJ6YMD6wzFUD81KZU0JpRIfA_C0d3RmBAPQFPcup4G7Rj6Ih1Gs_fNlaCYvhJDkm8Q7gnwOrGlCny3fi5Tyolzxj_JhvAk2SYSa7Ma6eGma46v-0PkKZ-OLwR_uYcnwTmlLmtouMr7dTX-wu6VGPyrRefuCdeEYQoJIv_W9q-tIox-Hy2Lgu4snrIkUGHGvw" />
            <div class="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent"></div>
            <div class="absolute bottom-4 left-4 right-4">
                <h2 class="font-headline-md text-white font-bold leading-tight drop-shadow-md">AI Nutrition Guide</h2>
            </div>
        </div>
        <div class="p-6 text-on-surface-variant font-body-md leading-relaxed">
            ${html}
        </div>
    </div>`;
  } catch (e) { result.innerHTML = '<p class="text-center text-error font-bold mt-6">Failed to generate guide.</p>'; }
}

// ═══ Init ═══
async function init() {
  try {
    babies = await api.getAllBabies();
    if (babies.length > 0) {
        currentBaby = babies[0];
    } else {
        currentPage = 'register';
    }
  } catch (e) { console.warn('Backend not reachable'); }
  render();
}

init();
