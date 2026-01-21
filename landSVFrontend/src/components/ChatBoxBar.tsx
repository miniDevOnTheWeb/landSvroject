import { useSelectedChat } from "../hooks/useSelectedChat"
import { useChatSocket } from "../hooks/useSocketContext"
import { useUser } from "../hooks/useUser"
import type { MessageToSend } from "../types"

export function ChatBoxBar () {
    const { chat } = useSelectedChat()
    const { user } = useUser()
    const socket = useChatSocket()
    
    const contactUsername = chat?.creator === user?.username ? chat?.invited : chat?.creator

    const handleDelete = () => {
        if(chat === null || user === null) return
        const destinatary = chat.creatorId === user.id ? chat.invitedId : chat.creatorId

        const message: MessageToSend = {
            receiverId: destinatary,
            senderId: user.id,
            content: chat.id,
            createdAt: new Date().toISOString(),
            type: 'DELETE_CHAT'
        }

        socket.sendMessage(message)
    }

    const image = chat?.creatorId === user?.id ? chat?.invitedProfileImage : chat?.creatorProfileImage

    return (
        <>
            <div className="chat-box-topbar">
                <ul className="cbt-list">
                    <li className="cbt-list-item"><img className="chat-item-img" src={image || 'https://imgs.search.brave.com/4rnnJU7_ENkK60goDh2C1fAWVhiD56t5CXuDp6Bs92o/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9jZG4u/cGl4YWJheS5jb20v/cGhvdG8vMjAxMy8w/Ny8xMy8xMC80NC9t/YW4tMTU3Njk5XzY0/MC5wbmc'} /> {contactUsername}</li>
                    <li className="cbt-list-item"><button onClick={handleDelete} className="delete-chat-button">Eliminar chat</button></li>
                </ul>
            </div>
        </>
    )
}