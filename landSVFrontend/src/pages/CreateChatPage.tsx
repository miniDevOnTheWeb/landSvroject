import { useSearchParams } from "react-router-dom"
import { useUser } from "../hooks/useUser"
import { useDispatch } from "react-redux"
import type { AppDispatch } from "../store/store"
import { createChat } from "../store/thunks/chatsThunk"
import { useChats } from "../hooks/useChats"
import { useState } from "react"
import { setChatsError } from "../store/slices/chatsSlice"

export function CreateChatPage () {
    const {user} = useUser()
    const [searchParams] = useSearchParams()
    const invited = searchParams.get('invited')
    const invitedId = searchParams.get('invitedId')
    const dispatch = useDispatch<AppDispatch>()
    const { loading, error } = useChats()
    const [firstMessage, setFirstMessage] = useState<string>('')

    const handleCreateChat = () => {
        if(firstMessage.trim() === '') return dispatch(setChatsError('First message must be longer'))
        if(!invitedId || !user) return
        dispatch(createChat({ invitedId, creatorId: user.id, firstMessage }))
    }

    return (
        <div className="create-chat-page">
            <div className="create-chat-container">
                <h3>Create chat with {invited}</h3>
                <p>You are gonna create a chat with this new contact</p>
                <input type="text" placeholder="Send the initial message" value={firstMessage} onChange={(e) => setFirstMessage(e.currentTarget.value)} />
                <div className="options">
                    <button disabled={loading} onClick={() => history.back()}>Cancelar</button>
                    <button disabled={loading} onClick={handleCreateChat}>Aceptar</button>
                </div>
                {error && <p className="create-chat-error">{error}</p>}
            </div>
        </ div>
    )
}