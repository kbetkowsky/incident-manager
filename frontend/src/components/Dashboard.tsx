import { useCallback, useEffect, useState } from 'react'
import { getDevices, getIncidents } from '../api'
import type { Device, Incident } from '../types'

interface Props {
  onLogout: () => void
}

const STATUS_COLORS: Record<string, string> = {
  OPEN: '#d13438',
  ACKNOWLEDGED: '#c07600',
  RESOLVED: '#2f8a3e',
  ACTIVE: '#2f8a3e',
  MAINTENANCE: '#c07600',
  INACTIVE: '#8a8f98',
}
const INCIDENT_ORDER: Record<string, number> = { OPEN: 0, ACKNOWLEDGED: 1, RESOLVED: 2 }
const DEVICE_ORDER: Record<string, number> = { MAINTENANCE: 0, INACTIVE: 1, ACTIVE: 2 }

function Status({ value }: { value: string }) {
  return (
    <span className="badge" style={{ color: STATUS_COLORS[value] ?? '#8a8f98' }}>
      {value}
    </span>
  )
}

function formatTime(iso: string): string {
  const d = new Date(iso)
  return Number.isNaN(d.getTime()) ? '-' : d.toLocaleString()
}

export default function Dashboard({ onLogout }: Props) {
  const [incidents, setIncidents] = useState<Incident[]>([])
  const [devices, setDevices] = useState<Device[]>([])
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(true)

  const load = useCallback(async () => {
    setLoading(true)
    try {
      const [inc, dev] = await Promise.all([getIncidents(), getDevices()])
      setIncidents(inc)
      setDevices(dev)
      setError(null)
    } catch {
      setError('Failed to load data from the API.')
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => {
    load()
  }, [load])

  const sortedIncidents = [...incidents].sort(
  (a, b) =>
    INCIDENT_ORDER[a.status] - INCIDENT_ORDER[b.status] ||
    new Date(b.lastOccurredAt).getTime() - new Date(a.lastOccurredAt).getTime(),
)
  const sortedDevices = [...devices].sort((a, b) => DEVICE_ORDER[a.status] - DEVICE_ORDER[b.status])

  return (
    <div>
      <header className="topbar">
        <span className="brand">Incident Manager</span>
        <div className="topbar-right">
          <button onClick={load} disabled={loading}>
            {loading ? 'Loading...' : 'Refresh'}
          </button>
          <button onClick={onLogout}>Sign out</button>
        </div>
      </header>

      <main className="container">
        {error && <p className="error">{error}</p>}

        <h2>Incidents</h2>
        <table>
          <thead>
            <tr>
              <th>Status</th>
              <th>Device</th>
              <th>Type</th>
              <th>Count</th>
              <th>Last seen</th>
            </tr>
          </thead>
          <tbody>
            {incidents.length === 0 ? (
              <tr>
                <td colSpan={5} className="muted">
                  {loading ? 'Loading...' : 'No incidents.'}
                </td>
              </tr>
            ) : (
              sortedIncidents.map((i) => (
                <tr key={i.id}>
                  <td>
                    <Status value={i.status} />
                  </td>
                  <td>{i.deviceName}</td>
                  <td>{i.eventType}</td>
                  <td>{i.occurrenceCount}</td>
                  <td className="muted">{formatTime(i.lastOccurredAt)}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>

        <h2>Devices</h2>
        <table>
          <thead>
            <tr>
              <th>Status</th>
              <th>Name</th>
              <th>ID</th>
            </tr>
          </thead>
          <tbody>
            {devices.length === 0 ? (
              <tr>
                <td colSpan={3} className="muted">
                  {loading ? 'Loading...' : 'No devices.'}
                </td>
              </tr>
            ) : (
              sortedDevices.map((d) => (
                <tr key={d.id}>
                  <td>
                    <Status value={d.status} />
                  </td>
                  <td>{d.name}</td>
                  <td className="muted">{d.id.slice(0, 8)}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </main>
    </div>
  )
}