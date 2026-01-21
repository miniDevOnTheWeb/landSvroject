import { useDispatch } from "react-redux";
import { ChatIcon, LogoutIcon, UserIcon } from "./Icons";
import type { AppDispatch } from "../store/store";
import { setUser } from "../store/slices/userSlice";
import { useNavigate } from "react-router-dom";

export function TopBarMainPage () {
    const dispatch = useDispatch<AppDispatch>()
    const navigate = useNavigate()
    const logout = () => {
        dispatch(setUser(null))
        localStorage.removeItem('access_token')
        navigate('/login')
    }

    return (
        <>
                <nav className="nav-bar">
                    <ul className="navbar-list">
                        <li><button onClick={logout} className="logout"><LogoutIcon /> <span className="span-li">LOGOUT</span></button></li>
                        <li><a href="/dashboard/profile"><UserIcon /> <span className="span-li">PROFILE</span></a></li>
                        <li><a href="/dashboard/chat-page"><ChatIcon /> <span className="span-li">CHATS</span></a></li>
                    </ul>
                </nav>
        </>
    )
}