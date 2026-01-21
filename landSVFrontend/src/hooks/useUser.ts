import { useSelector } from "react-redux"
import type { RootState } from "../store/store"

export const useUser = () => {
    const { user, loading, error } = useSelector((state:RootState) => state.userSlice)
    return { user, error, loading }
}