import { useSelector } from "react-redux"
import type { RootState } from "../store/store"

export const useComments = () => {
    const { comments, error, loading } = useSelector((state:RootState) => state.commentsSlice)
    return { comments, error, loading }
}