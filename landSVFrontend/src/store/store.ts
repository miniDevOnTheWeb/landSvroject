import { configureStore } from '@reduxjs/toolkit'
import userSlice from './slices/userSlice.ts'
import chatsSlice from './slices/chatsSlice.ts'
import selectedChatSlice from './slices/selectedChatSlice.ts'
import postsSlice from './slices/postsSlice.ts'
import commentsSlice from './slices/commentsSlice.ts'

export const store = configureStore({
    reducer: {
        userSlice,
        postsSlice,
        chatsSlice,
        selectedChatSlice,
        commentsSlice
    }
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch