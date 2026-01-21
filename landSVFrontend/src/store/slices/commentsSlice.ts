import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { Comment } from "../../types";

interface State {
    comments: Comment[] | null,
    error: string | null,
    loading: boolean
}

const initialState:State = {
    comments: null,
    loading: false,
    error: null
}

const commentsSlice = createSlice ({
    name: 'comments',
    initialState,
    reducers: {
        setComments: (state, action: PayloadAction<Comment[] | null>) => {
            state.comments = action.payload
            state.error = null
            state.loading = false
        },
        setCommentsLoading: (state, action: PayloadAction<boolean>) => {
            state.error = null
            state.loading = action.payload
        },
        setCommentsError: (state, action: PayloadAction<string | null>) => {
            state.error = action.payload
            state.loading = false
        },
        addComment: (state, action: PayloadAction<Comment>) => {
            state.comments?.unshift(action.payload)
        }
    }
})

export const { setComments, setCommentsError, setCommentsLoading, addComment } = commentsSlice.actions
export default commentsSlice.reducer