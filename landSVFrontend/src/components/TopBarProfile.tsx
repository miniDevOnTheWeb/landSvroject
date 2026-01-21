import { BackIcon, ChatIcon } from "./Icons";

export function TopBarProfile () {
    return (
        <>
                <nav className="nav-bar">
                    <ul className="navbar-list">
                        <li><a href="/dashboard/main-page"><BackIcon /> <span className="span-li">MAIN PAGE</span ></a></li>
                        <li><a href="/dashboard/chat-page"><ChatIcon /> <span className="span-li">CHATS</span></a></li>
                    </ul>
                </nav>
        </>
    )
}