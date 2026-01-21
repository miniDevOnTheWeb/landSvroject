import { useDispatch } from "react-redux"
import { useUser } from "./useUser"
import type { AppDispatch } from "../store/store"
import { useEffect } from "react"
import { getPostsByUserId } from "../store/thunks/postsThunks"

export const useLoadMyPosts = () => {
    const { user } = useUser()
    const dispatch = useDispatch<AppDispatch>()

    useEffect(() => {
        if(!user) return
        dispatch(getPostsByUserId({ userId: user.id}))
    }, [user])
}