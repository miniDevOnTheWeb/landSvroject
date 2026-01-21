import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { useNavigate, useSearchParams } from "react-router-dom";
import type { AppDispatch } from "../store/store";
import type { Post } from "../types";
import { commentPost, getCommentsByPost, getPostSelected } from "../store/thunks/postsThunks";
import { ChatIcon, SendIcon } from "../components/Icons";
import { ImageZoomed } from "../components/ImageZoom";
import { TopBarCheckPost } from "../components/TopBarCheck";
import { useUser } from "../hooks/useUser";
import { CommentsList } from "../components/CommentsList";
import { transformDate } from "../consts";

export function CheckPostPage () {
    const [searchParams] = useSearchParams()
    const postId = searchParams.get("postId")
    const dispatch = useDispatch<AppDispatch>()
    const [post, setPost] = useState<Post | null>(null)
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState<boolean>(false)
    const [loadingComments, setLoadingComments] = useState<boolean>(false)
    const [comment, setComment] = useState<string>('')
    const [visible, setVisible] = useState<boolean>(false)
    const [currentImage, setCurrentImage] = useState<number>(0)
    const navigate = useNavigate()
    const {user} = useUser()
 
    useEffect(() => {
        if(!postId) return
        getPostSelected({ postId, setPost, setError, setLoading })
        dispatch(getCommentsByPost({ postId }))
    }, [])

    const handleComment = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        if(comment.trim() === '' || !user || !postId) return
        dispatch(commentPost({ userId: user.id, postId, setError, setLoading: setLoadingComments, content: comment }))
        e.currentTarget.reset()
    }

    const handleClickImage = (index: number) => {
        setVisible(true)
        setCurrentImage(index)
    }

    return (
        <div className="check-page">      
            <TopBarCheckPost />
            {post === null && <h2 className="not-found">No result found</h2>}
            {post && (
                <div className="post-result">
                    {loading && <p>Loading...</p>}
                    {error && !loading && <p>{error}</p>}
                    {post.creatorId !== user?.id && (
                        <button className="create-chat-button" onClick={() => navigate(`/dashboard/createChat?invitedId=${post.creatorId}&invited=${post.creator}`)}><ChatIcon /></button>
                    )}
                    <h3>Property Data</h3>
                    <p className="check-location"><strong>LOCATION</strong>: {post.location}</p>
                    <p className="check-description"> <strong>DESCRIPTION: </strong> {post.description}</p>
                    <p className="check-strong"><strong>SELLER:</strong> {post.creator !== user?.username ? post.creator : 'Tu'}</p>
                        <p className="check-strong"><strong>PUBLISHED ON:</strong> {transformDate(post.createdAt)}</p>
                    <p className="check-price"><strong>PRICE:</strong> ${post.price}</p>
                    <p className="check-price"><strong>CONTACT NUMBER: </strong> {post.phoneNumber}</p>

                    <ul className="imagesList">
                        {post.images.map((img, index) => (
                            <li onClick={() => handleClickImage(index)} key={index}><img className="check-post-image" src={img} alt="" /></li>
                        ))}
                    </ul>
                    <form onSubmit={handleComment} className="comment-form">
                        <input onChange={(e) => setComment(e.currentTarget.value)} type="text" placeholder="new Message" />
                        <button className="send-button" disabled={loadingComments}><SendIcon /></button>
                    </form>
                    <CommentsList />                       
                    {visible && <ImageZoomed setVisible={setVisible} images={post.images} currentImage={currentImage} />}      
                </div>
            )}
        </ div>
    )
}
