import ChatBox from "../components/ChatBox";
import { ChatsList } from "../components/ChatsList";
import { TopBarChatPage } from "../components/TopBarChat";
import { useLoadChats } from "../hooks/useLoadChats";

export function ChatPage () {
    useLoadChats()
    
    return (
        <div className="chat-page">
            <TopBarChatPage />
            <div className="chats-container">
                <ChatsList />
                <ChatBox />
            </ div>
        </ div>
    )
}