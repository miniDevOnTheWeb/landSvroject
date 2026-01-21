import { useContext } from "react"
import { ChatSocketContext } from "../context/chatSocketContext"

export const useChatSocket = () => {
    const context = useContext(ChatSocketContext)
    if(!context) throw new Error('the socket must be used inside a chatSocketProvider')
    return context
}