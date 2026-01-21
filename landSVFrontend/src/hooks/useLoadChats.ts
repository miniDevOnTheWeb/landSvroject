import { useDispatch } from "react-redux"
import type { AppDispatch } from "../store/store"
import { useEffect } from "react"
import { loadChats } from "../store/thunks/chatsThunk"
import { useUser } from "./useUser"
import { setSelectedChat } from "../store/slices/selectedChatSlice"

export const useLoadChats = () => {
    const dispatch = useDispatch<AppDispatch>()
    const { user } = useUser()
    
    useEffect(() => {
        if(!user) return
        dispatch(loadChats({ id: user.id }))
        dispatch(setSelectedChat(null))
    }, [user])
} 