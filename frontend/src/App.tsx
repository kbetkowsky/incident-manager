import { useState } from 'react'
import { clearToken, getToken } from './api'
import LoginView from './components/LoginView'
import Dashboard from './components/Dashboard'

export default function App() {
  const [authed, setAuthed] = useState<boolean>(() => getToken() !== null)

  function handleLogout() {
    clearToken()
    setAuthed(false)
  }

  return authed ? (
    <Dashboard onLogout={handleLogout} />
  ) : (
    <LoginView onAuthenticated={() => setAuthed(true)} />
  )
}