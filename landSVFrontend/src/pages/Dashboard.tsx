import { Route, Routes } from "react-router-dom";
import { ChatPage } from "./ChatPage";
import { MainPage } from "./MainPage";
import { CheckPostPage } from "./CheckPostPage";
import { CreateChatPage } from "./CreateChatPage";
import { ProfilePage } from "./ProfilePage";
import { CreatePost } from "./CreatePostPage";
import { EditUserPage } from "./EditUserPage";

export function Dashboard() {
    return (
        <Routes>
            <Route path="/main-page" element={<MainPage />} />
            <Route path="/profile/edit-user" element={<EditUserPage />} />
            <Route path="/create-post" element={<CreatePost />} />
            <Route path="/profile" element={<ProfilePage />} />
            <Route path="/chat-page" element={<ChatPage />} />
            <Route path="/checkPost" element={<CheckPostPage />} />
            <Route path="/createChat" element={<CreateChatPage />} />
        </Routes>
    )
}