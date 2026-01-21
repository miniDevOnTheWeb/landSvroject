import React, { createContext, useEffect, useRef } from "react";
import type { Message, MessageToSend } from "../types";
import { useUser } from "../hooks/useUser";
import { useDispatch } from "react-redux";
import type { AppDispatch } from "../store/store";
import { addMessage, setSelectedChat } from "../store/slices/selectedChatSlice";
import { useSelectedChat } from "../hooks/useSelectedChat";
import { addMessageToChat, deleteChat, setHasUnseenMessage } from "../store/slices/chatsSlice";
import { toast } from "sonner";

interface ChatSocketContexType {
    socket: WebSocket | null,
    sendMessage: (msg:MessageToSend) => void
}

export const ChatSocketContext = createContext<ChatSocketContexType | undefined>(undefined)

export const ChatSocketProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const socket = useRef<WebSocket | null>(null)
    const { user } = useUser()
    const { chat } = useSelectedChat()
    const dispatch = useDispatch<AppDispatch>()
    
    const selectedChatRef = useRef(chat) 

    useEffect(() => {
        selectedChatRef.current = chat
    }, [chat]) 

    useEffect(() => {
        if(!user) return

        const protocol = window.location.protocol === 'https' ? 'wss' : 'ws'

        const ws = new WebSocket(`${protocol}://${window.location.host}/ws/chats?userId=${user?.id}`)
        socket.current = ws

        ws.onopen = () => console.log('ready for use the socket')
        ws.onerror = () => console.error('an error ocurred with the socket')
        ws.onclose = () => console.warn('socket conection closed')
        
        ws.onmessage = (event) => {
            const message: Message = JSON.parse(event.data)
            
            switch(message.type) {
                case 'PRIVATE' :

                dispatch(addMessageToChat(message))

                if(message.chatId === selectedChatRef.current?.id) { 
                    dispatch(addMessage(message))
                }

                if(selectedChatRef?.current?.id === message.chatId && user.id != message.senderId) {
                    const destinatary = selectedChatRef.current.creatorId === user.id ? selectedChatRef.current.invitedId : selectedChatRef.current.creatorId
                    const message: MessageToSend = {
                        receiverId: destinatary,
                        senderId: user.id,
                        content: selectedChatRef.current.id,
                        createdAt: new Date().toISOString(),
                        type: 'SEEN_CHAT'
                    }

                    sendMessage(message)
                }
                break;
                
                case 'NOTIFICATION':
                    toast.success(message.content)
                    break;
                
                case 'SEEN_CHAT' :
                    dispatch(setHasUnseenMessage({ id: message.chatId }))
                    break
                
                case 'DELETE_CHAT' : 
                    dispatch(deleteChat({ id: message.chatId }))
                    if(message.chatId === selectedChatRef.current?.id) { dispatch(setSelectedChat(null)) }
                    break
            }
        }

        return () => ws.close()
    }, [user, dispatch])

    const sendMessage = (message: MessageToSend) => {
        if(socket.current?.readyState === WebSocket.OPEN) {
            socket.current.send(JSON.stringify(message))
        } else {
            console.error("No se pudo enviar el mensaje, el socket no está abierto (estado:", socket.current?.readyState, ")")
        }
    }

    return (
        <ChatSocketContext.Provider value={{socket: socket.current, sendMessage}}>
            {children}
        </ChatSocketContext.Provider>
    )
}