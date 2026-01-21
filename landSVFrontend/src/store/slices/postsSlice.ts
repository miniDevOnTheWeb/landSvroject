import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { Post } from "../../types.d.ts";

interface State {
    posts: Post[] | null,
    error: string | null,
    loading: boolean
}

const initialState:State = {
    posts: null,
    loading: false,
    error: null
}

const postsSlice = createSlice ({
    name: 'posts',
    initialState,
    reducers: {
        setPosts: (state, action: PayloadAction<Post[] | null>) => {
            state.posts = action.payload
            state.error = null
            state.loading = false
        },
        setPostsLoading: (state, action: PayloadAction<boolean>) => {
            state.error = null
            state.loading = action.payload
        },
        setPostsError: (state, action: PayloadAction<string | null>) => {
            state.error = action.payload
            state.loading = false
        },
        deletePostFromStore: (state, action: PayloadAction<{ id: string }>) => {
            if(!state.posts) return
            state.posts = state.posts?.filter(post => post.id !== action.payload.id)
        }
    }
})

export const { setPosts, setPostsError, deletePostFromStore, setPostsLoading } = postsSlice.actions
export default postsSlice.reducer