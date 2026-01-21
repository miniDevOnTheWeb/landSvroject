import { useNavigate } from "react-router-dom"
import { useUser } from "../hooks/useUser"
import { usePosts } from "../hooks/usePosts"
import { transformDate } from "../consts"
import { useDispatch } from "react-redux"
import type { AppDispatch } from "../store/store"
import { deletePost } from "../store/thunks/postsThunks"
import { RemoveIcon } from "./Icons"
import swal from 'sweetalert2'
import { SmallerOverlay } from "./LoadingOverlay"

export function PostsList () {
    const { user } = useUser()
    const { posts, loading: postsLoading } = usePosts()
    const navigate = useNavigate()
    const dispatch = useDispatch<AppDispatch>()

    const handleDelete = async (id: string) => {
        const confirmed = await swal.fire({
            title: 'Eliminar',
            text: 'Se eliminara la publicacion.',
            icon: 'warning',
            cancelButtonText: 'Cancelar',
            confirmButtonText: 'Eliminar',
            confirmButtonColor: '#d00',
            cancelButtonColor: '#dd0'
        })

        if(confirmed.isConfirmed) {
            dispatch(deletePost({ postId: id }))
        }
    }

    return (
        <>
            {!user || posts?.length === 0 && <h2 className="empty-published" >No posts published</h2>}
            { postsLoading ? <SmallerOverlay /> : posts && posts.length > 0 && (
                <>
                <h3>Posts created ({posts.length})</h3>
                <ul className="created-posts">
                    {posts.map(post => (
                        <li onClick={() => navigate(`/dashboard/checkPost?postId=${post.id}`)} className="post" key={post.id}>
                            <p><strong>CREATED AT</strong>: {transformDate(post.createdAt)}</p>
                            <p><strong>LOCATION:</strong> {post.location}</p>
                            <p className="description"><strong>DESCRIPTION:</strong> {post.description}</p>
                            <p><strong>PRICE:</strong> ${post.price}</p>
                            <p><strong>CONTACT NUMBER:</strong> {post.phoneNumber}</p>
                            <img className="created-post-image" src={post.images[0]} alt="" />
                            <button className="delete-post-button" onClick={(e) => {
                                    e.stopPropagation()
                                    handleDelete(post.id)}
                                } 
                            ><RemoveIcon /></button>
                        </li>
                    ))}
                </ul>
                </>
            ) }
        </>
    )
}