import type { Chat } from "../../types";
import { setChats, setChatsError, setChatsLoading, setHasUnseenMessage } from "../slices/chatsSlice";
import type { AppDispatch } from "../store";

export const loadChats = ({ id }: { id: string }) => async (dispatch: AppDispatch) => {
    try {
        dispatch(setChatsLoading(true))

        const response = await fetch(`/api/chats/getUserChats/${id}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('access_token')}`
            }
        })

        const data = await response.json()
        if (!response.ok) {
            return dispatch(setChatsError(data.message))
        }

        dispatch(setChats(data.chats))
    } catch (error) {
        const e = error as Error
        dispatch(setChatsError(e.message))
    }
}

export const markAsSeen = ({ chat }: { chat: Chat }) => async (dispatch: AppDispatch) => {
    try {
        dispatch(setChatsLoading(true))

        const response = await fetch(`/api/chats/setSeenMessages/${chat.id}`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('access_token')}`
            }
        })

        const data = await response.json()
        if (!response.ok) {
            return dispatch(setChatsError(data.message))
        }

        dispatch(setHasUnseenMessage(chat))
    } catch (error) {
        const e = error as Error
        dispatch(setChatsError(e.message))
    }
}
export const createChat = ({ creatorId, invitedId, firstMessage }: { creatorId: string, invitedId: string, firstMessage: string }) => async (dispatch: AppDispatch) => {
    try {
        dispatch(setChatsLoading(true))

        const response = await fetch(`/api/chats/create`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('access_token')}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ user1: creatorId, user2: invitedId, firstMessage })
        })

        const data = await response.json()
        console.log(data)
        if (!response.ok) {
            return dispatch(setChatsError(data.message))
        }

        window.location.href = '/dashboard/chat-page'
    } catch (error) {
        const e = error as Error
        dispatch(setChatsError(e.message))
    }
}
//dieacxdev