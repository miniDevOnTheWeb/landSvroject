import { useEffect, useRef } from "react";
import { useSelectedChat } from "../hooks/useSelectedChat";
import { useUser } from "../hooks/useUser";

export function MessagesList () {
    const { chat } = useSelectedChat()
    const { user } = useUser()
    const endMessageRef = useRef<HTMLDivElement>(null)

    const messages = chat?.messages

    const scrollToEnd = () => {
        endMessageRef.current?.scrollIntoView({behavior: 'instant'})
    }

    useEffect(() => {
        scrollToEnd()
    }, [ chat, chat?.messages.length ])

    return (
        <>
            <ul className='messages-list'>
                {messages?.length === 0 && <p className="empty-chat">No messages yet</p>}
                { chat?.messages.map(message => {
                    const date = message.createdAt < new Date()
                        ? message.createdAt.toString().split("T")[1].slice(0, 5)
                        : message.createdAt.toString().split("T")[0]
                    return (
                        <li key={message.id} className={message.senderId === user?.id ? 'own-message message' : 'received-message message'}>
                            <p className='message-content'>{message.content}</p>
                            <span>{date}</span>
                        </li>
                    )
                })}
                <div className="end-messages-ref" ref={endMessageRef}></div>
            </ul>
        </>
    )
}