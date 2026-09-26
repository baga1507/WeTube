import VideoRecommendation from "../../components/video_recommendation/VideoRecommendation.jsx";
import "./Recommendations.css"
import {useEffect, useState} from "react";
import api from "../../api/axios.js";

function Recommendations() {
    const [videos, setVideos] = useState([]);

    useEffect(() => {
        api.get("/videos/recommendations")
            .then(response => setVideos(response.data.videos))
            .catch(error => console.error(error));
    }, []);

    return (
        <div className="recommendations-container">
            <h2>
                Recommended Videos
            </h2>

            <div className="recommendation-list">
                {videos.map(v =>
                    <VideoRecommendation
                        key={v.id}
                        id={v.id}
                        title={v.title}
                        channelName={v.userDto.username}
                        views = {v.views}
                    />
                )}
            </div>
        </div>
    );
}

export default Recommendations;