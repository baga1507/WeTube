import preview from "./assets/preview.png";
import "./VideoRecommendation.css";

function VideoRecommendation({
                                 thumbnailUrl = preview, // Defaults to imported preview image if not provided
                                 title = "Building a Secure Auth System with React and Spring Boot 3",
                                 channelName = "Dev Channel",
                                 channelAvatar = "https://via.placeholder.com/36",
                                 views = "120K views",
                                 uploadedAt = "2 days ago",
                                 duration = "14:20"
                             }) {
    return (
        <article className="video-card">
            <div className="thumbnail-wrapper">
                <img src={thumbnailUrl} alt={title} className="thumbnail" />
                <span className="duration-badge">{duration}</span>
            </div>

            <div className="video-details">
                <div className="video-meta">
                    <h3 className="video-title" title={title}>
                        {title}
                    </h3>
                    <p className="channel-name">{channelName}</p>
                    <p className="stats">
                        {views} • {uploadedAt}
                    </p>
                </div>
            </div>
        </article>
    );
}

export default VideoRecommendation;