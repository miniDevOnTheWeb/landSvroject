import { useSelector } from "react-redux"
import type { RootState } from "../store/store"

export const useChats = () => {
    const { chats, error, loading } = useSelector((state:RootState) => state.chatsSlice)
    return { chats, error, loading }
}