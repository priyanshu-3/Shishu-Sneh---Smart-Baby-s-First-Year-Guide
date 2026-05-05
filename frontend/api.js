const API = '/api/baby';

export async function healthCheck() {
  const res = await fetch(`${API}/health`);
  return res.json();
}

export async function registerBaby(data) {
  const res = await fetch(`${API}/register`, {
    method: 'POST', headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  });
  return res.json();
}

export async function getBaby(id) {
  const res = await fetch(`${API}/${id}`);
  if (!res.ok) throw new Error('Baby not found');
  return res.json();
}

export async function getAllBabies() {
  const res = await fetch(`${API}/all`);
  return res.json();
}

export async function getVaccines(id) {
  const res = await fetch(`${API}/${id}/vaccines`);
  return res.json();
}

export async function addHealthLog(id, data) {
  const res = await fetch(`${API}/${id}/health-log`, {
    method: 'POST', headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  });
  return res.json();
}

export async function getHealthLogs(id) {
  const res = await fetch(`${API}/${id}/health-logs`);
  return res.json();
}

export async function getNutrition(data) {
  const res = await fetch(`${API}/nutrition`, {
    method: 'POST', headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  });
  return res.json();
}
