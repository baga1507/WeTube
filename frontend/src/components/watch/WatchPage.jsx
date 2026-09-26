import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import api from '../../api/axios';
import './WatchPage.css';

function WatchPage() {
    const { id } = useParams();
    const [videoData, setVideoData] = useState(null);
    const [loading, setLoading] = useState(true);

    const [likes, setLikes] = useState(0);
    const [isLiked, setIsLiked] = useState(false);
    const [showFullDescription, setShowFullDescription] = useState(false);

    useEffect(() => {
        const fetchVideo = async () => {
            try {
                const response = await api.get(`/videos/${id}`);
                const data = response.data;
                const isLiked = await api.get(`/videos/${id}/like`).then(response => response.data);

                setVideoData(data);
                setIsLiked(isLiked);
                setLikes(data.likeCount);
                console.log(response.data);
            } catch (error) {
                console.error('Failed to fetch video details:', error);
            } finally {
                setLoading(false);
            }
        };

        if (id) {
            fetchVideo();
        }
    }, [id]);

    const handleLikeToggle = async () => {
        const nextIsLiked = !isLiked;
        setIsLiked(nextIsLiked);
        setLikes(prev => (nextIsLiked ? prev + 1 : prev - 1));

        try {
            if (!isLiked) {
                await api.post(`/videos/${id}/like`);
            } else {
                await api.delete(`/videos/${id}/like`);
            }
        } catch (error) {
            console.error('Failed to update like status on server:', error);
        }
    };

    if (loading) return <div className="watch-status">Loading video...</div>;
    if (!videoData) return <div className="watch-status">Video not found.</div>;

    const uploaderName = typeof videoData.channelName === 'object'
        ? videoData.channelName?.username
        : videoData.user?.username || videoData.channelName || 'WeTube Creator';

    const token = localStorage.getItem('token');
    const streamUrl = `http://localhost:8080/api/v1/videos/${id}/stream?token=${encodeURIComponent(token)}`;

    return (
        <div className="watch-page-container">
            <div className="watch-content-wrapper">

                <div className="player-container">
                    <video
                        controls
                        autoPlay
                        className="wetube-player"
                        src={streamUrl}
                    >
                        Your browser does not support HTML5 video streaming.
                    </video>
                </div>

                <h1 className="watch-title">{videoData.title}</h1>

                <div className="watch-actions-bar">
                    <div className="channel-info">
                        <div className="channel-avatar-placeholder">
                            {uploaderName.charAt(0).toUpperCase()}
                        </div>
                        <div className="channel-text">
                            <span className="channel-title">{uploaderName}</span>
                            <span className="channel-subscribers">Subscribers</span>
                        </div>
                    </div>

                    <div className="interaction-buttons">
                        <button
                            className={`like-btn ${isLiked ? 'liked' : ''}`}
                            onClick={handleLikeToggle}
                        >
                            <svg className="like-icon" viewBox="0 0 24 24" width="20" height="20">
                                <path fill="currentColor" d="M1 21h4V9H1v12zm22-11c0-1.1-.9-2-2-2h-6.31l.95-4.57.03-.32c0-.41-.17-.79-.44-1.06L14.17 1 7.58 7.59C7.22 7.95 7 8.45 7 9v10c0 1.1.9 2 2 2h9c.83 0 1.54-.5 1.84-1.22l3.02-7.05c.09-.23.14-.47.14-.73v-2z"/>
                            </svg>
                            <span>{likes.toLocaleString()}</span>
                        </button>
                    </div>
                </div>

                <div className="description-card">
                    <div className="description-stats">
                        <span>{videoData.views || '0'} views</span>
                        <span className="stats-dot">•</span>
                        <span>{videoData.uploadedAt || 'Recently uploaded'}</span>
                    </div>

                    <p className={`description-text ${showFullDescription ? 'expanded' : ''}`}>
                        {videoData.description || 'No description provided for this video.'}
                    </p>

                    <button
                        className="toggle-description-btn"
                        onClick={() => setShowFullDescription(!showFullDescription)}
                    >
                        {showFullDescription ? 'Show less' : 'Show more'}
                    </button>
                </div>

            </div>
        </div>
    );
}

export default WatchPage;