import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { Chat, Message } from "../../types.d.ts";

interface State {
    chat: Chat | null,
    error: string | null,
    loading: boolean
}

const initialState:State = {
    chat: null,
    loading: false,
    error: null
}

const chatsSlice = createSlice ({
    name: 'chats',
    initialState,
    reducers: {
        setSelectedChat: (state, action: PayloadAction<Chat | null>) => {
            state.chat = action.payload
            state.error = null
            state.loading = false
        }
    ,
        setChatLoading: (state, action: PayloadAction<boolean>) => {
            state.error = null
            state.loading = action.payload
        },
        setChatError: (state, action: PayloadAction<string | null>) => {
            state.error = action.payload
            state.loading = false
        },
        addMessage: (state, action: PayloadAction<Message>) => {
            state.chat?.messages.push(action.payload)
        }
    }
})

export const { setSelectedChat, setChatError, setChatLoading, addMessage } = chatsSlice.actions
export default chatsSlice.reducer