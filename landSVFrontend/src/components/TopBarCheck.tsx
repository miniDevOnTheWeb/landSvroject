import { ChatIcon, UserIcon } from "./Icons";

export function TopBarCheckPost () {
    return (
        <>
                <nav className="nav-bar">
                    <ul className="navbar-list">
                        <li><a href="/dashboard/profile"><UserIcon /> <span className="span-li">PROFILE</span ></a></li>
                        <li><a href="/dashboard/chat-page"><ChatIcon /> <span className="span-li">CHATS</span></a></li>
                    </ul>
                </nav>
        </>
    )
}