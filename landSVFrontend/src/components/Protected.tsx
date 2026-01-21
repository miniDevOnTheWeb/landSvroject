import type React from "react";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch } from 'react-redux'
import type { AppDispatch } from "../store/store";
import { setUser, setUserLoading } from "../store/slices/userSlice";
import { toast } from 'sonner'
import { verifyTokenApi } from "../consts";

export const Protected: React.FC<{children: React.ReactNode}> = ({ children }) => {
    const navigate = useNavigate()
    const dispatch = useDispatch<AppDispatch>()

    useEffect(() => {
        (async () => {
            try {
                const token = localStorage.getItem('access_token')
                if(!token) {
                    toast.error('no token provided')
                    return navigate('/login')
                }

                dispatch(setUserLoading(true))
                const response = await fetch (verifyTokenApi, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                })

                const data = await response.json()

                if(!response.ok) {
                    toast.error(data.message)
                    return navigate('/login')
                }

                dispatch(setUser(data.user))

            } catch (error) {
                const e = error as Error
                console.log(e)
                toast.error(e.message)
                navigate('/login')
            } finally {
                dispatch(setUserLoading(false))
            }
        })()
    })

    return children
}