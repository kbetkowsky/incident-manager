import type { Device, Incident } from './types'

const TOKEN_KEY = 'im_token'

export function getToken(): string | null {
  return localStorage.getItem(TOKEN_KEY)
}
export function setToken(token: string): void {
  localStorage.setItem(TOKEN_KEY, token)
}
export function clearToken(): void {
  localStorage.removeItem(TOKEN_KEY)
}

function authHeader(): Record<string, string> {
  const token = getToken()
  return token ? { Authorization: `Bearer ${token}` } : {}
}

export async function login(username: string, password: string): Promise<string> {
  const res = await fetch('/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password }),
  })
  if (!res.ok) throw new Error('Login failed')
  const data = await res.json()
  return data.token
}

export async function getIncidents(): Promise<Incident[]> {
  const res = await fetch('/incidents', { headers: authHeader() })
  if (!res.ok) throw new Error('Failed to load incidents')
  return res.json()
}

export async function getDevices(): Promise<Device[]> {
  const res = await fetch('/devices', { headers: authHeader() })
  if (!res.ok) throw new Error('Failed to load devices')
  return res.json()
}