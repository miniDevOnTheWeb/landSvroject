import { useSelector } from "react-redux"
import type { RootState } from "../store/store"

export const useSelectedChat = () => {
    const { chat, error, loading } = useSelector((state:RootState) => state.selectedChatSlice)
    return { chat, error, loading }
}