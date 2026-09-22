import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../api/axios.js';
import "./LoginForm.css";

function LoginForm() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    async function handleLogin(event) {
        event.preventDefault();
        setErrorMessage('');

        const form = event.target;
        const enteredUsername = (username || form.elements.username?.value || '').trim();
        const enteredPassword = password || form.elements.password?.value || '';

        console.log("Submitting values -> Username:", enteredUsername, "Password length:", enteredPassword.length);

        if (!enteredUsername || !enteredPassword) {
            setErrorMessage('Please enter both username and password');
            return;
        }

        const rawString = `${enteredUsername}:${enteredPassword}`;
        const encoded = btoa(rawString);
        const authHeader = `Basic ${encoded}`;

        console.log("Raw credentials string:", rawString);
        console.log("Generated Header:", authHeader);

        try {
            await api.post(
                '/users/login',
                { username: enteredUsername, password: enteredPassword },
                {
                    headers: {
                        'Authorization': authHeader,
                        'Content-Type': 'application/json'
                    }
                }
            );

            localStorage.setItem('token', authHeader);
            navigate('/recommend');
        } catch (error) {
            console.error('Login failed:', error.response?.status);
            setErrorMessage('Invalid username or password');
        }
    }

    return (
        <div className="login-container">
            <form onSubmit={handleLogin}>
                <h2>Sign In</h2>
                {errorMessage && <p className="error">{errorMessage}</p>}
                <input
                    type="text"
                    name="username" /* Added name attribute for DOM fallback */
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />
                <input
                    type="password"
                    name="password" /* Added name attribute for DOM fallback */
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <button type="submit">Login</button>
            </form>
        </div>
    );
}

export default LoginForm;