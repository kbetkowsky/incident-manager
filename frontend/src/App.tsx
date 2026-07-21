import { useState } from 'react';

function App() {

        const [username, setUsername] = useState('');
        const [password, setPassword] = useState('');

        const handleSubmit = async (e: React.FormEvent) => {
          e.preventDefault();
          try {
            const response = await fetch('http://localhost:8080/auth/login', {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify({ username, password }),
            });
            const data = await response.json();
            localStorage.setItem('token', data.token);
            console.log(data.token);
            const token = localStorage.getItem('token');
            const devicesResponse = await fetch('http://localhost:8080/devices', {
              headers: { 'Authorization': `Bearer ${token}` },
            });
            const devices = await devicesResponse.json();
            console.log(devices);
          } catch (error) {
            console.log(error);
          }
        };

        return (
                <form onSubmit={handleSubmit}>
                    <input
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        />
                    <input
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        type="password"
                    />
                        <button type="submit">Zaloguj</button>
                </form>
            )
}

export default App;