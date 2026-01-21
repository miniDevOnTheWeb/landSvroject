import React, { useState } from "react"
import { SendIcon } from "./Icons"
import { useChatSocket } from "../hooks/useSocketContext.ts"
import { useSelectedChat } from "../hooks/useSelectedChat.ts"
import { useUser } from "../hooks/useUser.ts"
import type { MessageToSend } from "../types"

export function NewMessageForm () {
    const [newMsg, setNewMsg] = useState<string>('')
    const socket = useChatSocket()
    const {chat} = useSelectedChat()
    const { user } = useUser()

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if(!user || !chat) return

        const destinatary = chat.creatorId === user.id ? chat.invitedId : chat.creatorId

        const message: MessageToSend = {
            receiverId: destinatary,
            senderId: user.id,
            content: newMsg,
            createdAt: new Date().toISOString(),
            type: 'PRIVATE'
        }

        setNewMsg('')
        socket.sendMessage(message)
    }

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setNewMsg(event.currentTarget.value)
    }

    return (
        <>
            <form onSubmit={handleSubmit} className="new-message-form">
                <input value={newMsg} className="new-msg" type="text" onChange={handleChange} placeholder="Write a message"/>
                <button><SendIcon /></button>
            </form>
        </>
    )
}