
import { useDispatch } from 'react-redux'
import { useChats } from '../hooks/useChats'
import { useUser } from '../hooks/useUser'
import type { Chat, MessageToSend } from '../types'
import type { AppDispatch } from '../store/store'
import { setSelectedChat } from '../store/slices/selectedChatSlice'
import { useSelectedChat } from '../hooks/useSelectedChat'
import { useChatSocket } from '../hooks/useSocketContext'
import { SmallerOverlay } from './LoadingOverlay'
import { MenuIcon } from './Icons'
import { useRef } from 'react'

export function ChatsList () {
    const socket = useChatSocket()
    const { chats, error, loading } = useChats()
    const { user } = useUser()
    const { chat: selectedChat } = useSelectedChat()
    const dispatch = useDispatch<AppDispatch>()
    const checkRef = useRef<HTMLInputElement | null>(null)

    const handleSelect = (chat: Chat) => {
        if(selectedChat?.id === chat.id) return
        dispatch(setSelectedChat(chat))
         if(checkRef.current) {
            checkRef.current.checked = false;
            checkRef.current.click()
         }

        if(user && user.id !== chat.lastSenderId && chat.hasUnseenMessages) {
            const destinatary = chat.creatorId === user.id ? chat.invitedId : chat.creatorId
            const message: MessageToSend = {
                receiverId: destinatary,
                senderId: user.id,
                content: chat.id,
                createdAt: new Date().toISOString(),
                type: 'SEEN_CHAT'
            }

            socket.sendMessage(message)
        }
    }

    return (
        <>
        <label htmlFor="check-bar" className="check-label"><MenuIcon /></label>
        <input type="checkbox" id="check-bar" ref={checkRef}/>        
        <div className='chats-side-menu'>
            {!loading && <h4>Your chats ({ chats?.length })</h4>}
            {chats && chats.length > 0 && (
                <>
                    <ul className='chats-list'>
                        { chats.map(chat => {
                            const isSelected = selectedChat?.id === chat.id
                            const hasUnseenMessages = chat.hasUnseenMessages === true

                            const className = hasUnseenMessages === true
                                ? "chat unseen-messages" 
                                : isSelected 
                                    ? "selected-chat"
                                    : "chat"
                                    
                            const image = chat.creatorId === user?.id ? chat.invitedProfileImage : chat.creatorProfileImage
                            return (
                                <li onClick={() => handleSelect(chat)} key={chat.id} className={className}>
                                    <div className="chat-data">
                                        <img src={image || 'https://imgs.search.brave.com/4rnnJU7_ENkK60goDh2C1fAWVhiD56t5CXuDp6Bs92o/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9jZG4u/cGl4YWJheS5jb20v/cGhvdG8vMjAxMy8w/Ny8xMy8xMC80NC9t/YW4tMTU3Njk5XzY0/MC5wbmc'} className='chat-item-img' alt="" />
                                    <p>{chat.creator === user?.username ? chat.invited : chat.creator }</p>
                                    </div>
                                    <span>{chat.messages.length}</span>
                                </li>
                            )
                        })}
                    </ul>
                </>
            )}
            {error && !loading && <p>{error}</p>}
            {loading && <p className='error'> <SmallerOverlay /></p>}
        </ div>
        </>
    )
}