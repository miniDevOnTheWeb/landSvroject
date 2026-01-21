import { useComments } from "../hooks/useComments";
import { useUser } from "../hooks/useUser";

export function CommentsList () {
    const { comments } = useComments()
    const { user } = useUser()

    return (
        <>
            <ul className='comments-list'>
                {comments?.length === 0 && <p className="empty-comments">No comments yet</p>}
                { comments?.map(comment => {
                    const date = comment.createdAt.toString().split("T")[0]
                    return (
                        <li key={comment.id} className={comment.creatorId === user?.id ? 'own-comment comment' : 'received-comment comment'}>
                            <strong className="comment-creator">{comment.creator}</strong>
                            <div className="separator-comment"></div>
                            <p className='comment-content'>{comment.content}</p>
                            <span className="comment-date">{date}</span>
                        </li>
                    )
                })}
            </ul>
        </>
    )
}