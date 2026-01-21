import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { User } from "../../types.d.ts";

interface State {
    user: User | null,
    error: string | null,
    loading: boolean
}

const initialState:State = {
    user: null,
    loading: false,
    error: null
}

const userSlice = createSlice ({
    name: 'users',
    initialState,
    reducers: {
        setUser: (state, action: PayloadAction<User | null>) => {
            state.user = action.payload
            state.error = null
            state.loading = false
        }
    ,
        setUserLoading: (state, action: PayloadAction<boolean>) => {
            state.error = null
            state.loading = action.payload
        },
        setUserError: (state, action: PayloadAction<string | null>) => {
            state.error = action.payload
            state.loading = false
        }
    }
})

export const { setUser, setUserError, setUserLoading } = userSlice.actions
export default userSlice.reducer