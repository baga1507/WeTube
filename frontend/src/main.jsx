import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginForm from "./LoginForm.jsx";
import Recommendations from "./Recommendations.jsx";

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<LoginForm/>} />
                <Route path="/recommend" element={<Recommendations/>}/>
            </Routes>
        </BrowserRouter>
    </React.StrictMode>
);