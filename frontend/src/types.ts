export type DeviceStatus = 'ACTIVE' | 'INACTIVE' | 'MAINTENANCE'

export type IncidentStatus = 'OPEN' | 'ACKNOWLEDGED' | 'RESOLVED'

export interface Device {
  id: string
  name: string
  status: DeviceStatus
}

export interface Incident {
  id: string
  deviceId: string
  deviceName: string
  eventType: string
  status: IncidentStatus
  occurrenceCount: number
  firstOccurredAt: string
  lastOccurredAt: string
}