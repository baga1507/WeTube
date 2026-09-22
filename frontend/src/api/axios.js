import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api/v1', // Ensure this matches your Spring Boot URL
    headers: {
        'Content-Type': 'application/json',
    },
});

api.interceptors.request.use(
    (config) => {
        if (!config.headers.Authorization) {
            const token = localStorage.getItem('token');

            if (token && token !== 'Basic Og==') {
                config.headers.Authorization = token;
            }
        }

        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default api;