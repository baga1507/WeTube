import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginForm from "./pages/login/LoginForm.jsx";
import Recommendations from "./pages/recommendations/Recommendations.jsx";
import ProtectedRoute from "./components/ProtectedRoute.jsx";

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<LoginForm/>} />

                <Route element={<ProtectedRoute/>}>
                    <Route path="/recommend" element={<Recommendations/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    </React.StrictMode>
);