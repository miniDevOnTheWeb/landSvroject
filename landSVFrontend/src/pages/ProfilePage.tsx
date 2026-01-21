import { useNavigate } from "react-router-dom"
import { useLoadMyPosts } from "../hooks/useLoadMyPosts"
import { useUser } from "../hooks/useUser"
import { TopBarProfile } from "../components/TopBarProfile"
import { AddIcon } from "../components/Icons"
import { PostsList } from "../components/PostsList"
import { SmallerOverlay } from "../components/LoadingOverlay"

export function ProfilePage () {
    const { user, loading } = useUser()
    const navigate = useNavigate()
    
    useLoadMyPosts()

    return (
        <div className="profile-page">
            <TopBarProfile />
            <div className="profile-section">
                <div className="profile-data">
                    {loading && <SmallerOverlay />}
                    {user && !loading && (
                        <>
                            <img src={user?.profileImage || 'https://imgs.search.brave.com/4rnnJU7_ENkK60goDh2C1fAWVhiD56t5CXuDp6Bs92o/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9jZG4u/cGl4YWJheS5jb20v/cGhvdG8vMjAxMy8w/Ny8xMy8xMC80NC9t/YW4tMTU3Njk5XzY0/MC5wbmc'} alt="" className="profile-image" />
                            <p className="username">{user?.username} <button className="edit-button" onClick={() => navigate('edit-user')}>edit</button></p>
                            <p className="email">{user?.email}</p>
                        </>
                    ) }
                </div>
                <div className="posts-created">
                    <PostsList />
                </div>
            </div>
            <button onClick={() => navigate('/dashboard/create-post')} className="add-button"><AddIcon /></button>
        </ div>
    )
}