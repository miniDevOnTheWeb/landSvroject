import { BackIcon, UserIcon } from "./Icons";

export function TopBarChatPage () {
    return (
        <>
            <nav className="nav-bar top-chats-bar">
                <ul className="navbar-list">
                    <li><a href="/dashboard/main-page"><BackIcon /> <span className="span-li">MAIN PAGE</span></a></li>
                    <li><a href="/dashboard/profile"><UserIcon /> <span className="span-li">PROFILE</span ></a></li>
                </ul>
            </nav>
        </>
    )
}