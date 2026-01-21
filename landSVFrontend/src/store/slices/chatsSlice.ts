import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { Chat, Message } from "../../types.d.ts";

interface State {
    chats: Chat[] | null,
    error: string | null,
    loading: boolean
}

const initialState:State = {
    chats: null,
    loading: false,
    error: null
}

const chatsSlice = createSlice ({
    name: 'chats',
    initialState,
    reducers: {
        setChats: (state, action: PayloadAction<Chat[] | null>) => {
            state.chats = action.payload
            state.error = null
            state.loading = false
        },
        setChatsLoading: (state, action: PayloadAction<boolean>) => {
            state.error = null
            state.loading = action.payload
        },
        setChatsError: (state, action: PayloadAction<string | null>) => {
            state.error = action.payload
            state.loading = false
        }, 
        addMessageToChat: (state, action:PayloadAction<Message>) => {
            const { chatId } = action.payload
            const chat = state.chats?.find(chat => chat.id === chatId)

            if(!chat) return

            chat.messages = chat.messages || []
            chat.messages.push(action.payload)

            chat.lastMessage = action.payload.createdAt
            chat.hasUnseenMessages = true
            chat.lastSenderId = action.payload.senderId

            state.chats?.sort((a, b) => {
                console.log(a.messages)
                const aLast = a.lastMessage || ( a.messages.length > 0
                    ? a.messages[a.messages.length - 1].createdAt 
                    : 0)

                const bLast = b.lastMessage ||  (b.messages.length > 0
                    ? b.messages[b.messages.length - 1].createdAt
                    : 0)

                return new Date(bLast).getTime() - new Date(aLast).getTime()
            })
        },
        setHasUnseenMessage: (state, action: PayloadAction<{ id: string }>) => {
            if(!state.chats) return
            const chat = state.chats.find(c => c.id === action.payload.id)
            if (chat) {
                chat.hasUnseenMessages = false
            }
        },
        deleteChat: (state, action:PayloadAction<{ id: string }>) => {
            if(!state.chats) return
            state.chats = state.chats.filter(chat => chat.id !== action.payload.id)
        }
    }
})

export const { deleteChat, setChats, setChatsError, setHasUnseenMessage, setChatsLoading, addMessageToChat } = chatsSlice.actions
export default chatsSlice.reducer