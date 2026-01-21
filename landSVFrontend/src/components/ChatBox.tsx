import { useSelectedChat } from "../hooks/useSelectedChat";
import { ChatBoxBar } from "./ChatBoxBar";
import { MessagesList } from "./MessageList";
import { NewMessageForm } from "./NewMessageForm";

export function ChatBox () {
  const { chat } = useSelectedChat()
  return (
    <>
      <div style={{ backgroundColor: '#333'}} className="chat-container">
        {!chat && <p className="empty-chat">Selecciona un chat</p>}
        { chat && <ChatBoxBar />}
        <MessagesList />
        { chat && <NewMessageForm />}
      </div>
    </>
  )
};

export default ChatBox;
