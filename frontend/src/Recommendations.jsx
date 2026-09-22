// src/Recommendations.jsx
import VideoRecommendation from "./VideoRecommendation";

function Recommendations() {
    return (
        <div style={{ backgroundColor: '#f8fafc', minHeight: '100vh', padding: '30px' }}>
            <h2 style={{
                color: '#1a1a1a',
                borderLeft: '4px solid #cc0000',
                paddingLeft: '12px',
                marginBottom: '24px'
            }}>
                Recommended Videos
            </h2>

            <div style={{
                display: 'grid',
                gridTemplateColumns: 'repeat(auto-fill, minmax(280px, 360px))',
                gap: '24px',
                justifyContent: 'start'
            }}>
                <VideoRecommendation />
                <VideoRecommendation title="Spring Boot 3 + React 19 Full Stack Guide" />
                <VideoRecommendation title="Building Custom CSS Grid Layouts" />
            </div>
        </div>
    );
}

export default Recommendations;