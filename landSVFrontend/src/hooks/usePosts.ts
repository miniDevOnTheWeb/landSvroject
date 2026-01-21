import { useSelector } from "react-redux"
import type { RootState } from "../store/store"

export const usePosts = () => {
    const { posts, error, loading } = useSelector((state:RootState) => state.postsSlice)
    return { posts, error, loading }
}