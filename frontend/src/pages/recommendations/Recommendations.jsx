// src/Recommendations.jsx
import VideoRecommendation from "../../components/videos/VideoRecommendation.jsx";
import "./Recommendations.css"

function Recommendations() {
    return (
        <div className="recommendations-container">
            <h2>
                Recommended Videos
            </h2>

            <div className="recommendation-list">
                <VideoRecommendation />
                <VideoRecommendation title="Spring Boot 3 + React 19 Full Stack Guide" />
                <VideoRecommendation title="Building Custom CSS Grid Layouts" />
            </div>
        </div>
    );
}

export default Recommendations;