import type React from "react";
import type { SetStateAction } from "react";
import type { Post } from "../../types";
import type { AppDispatch } from "../store";
import { deletePostFromStore, setPosts, setPostsError, setPostsLoading } from "../slices/postsSlice";
import { addComment, setComments, setCommentsError, setCommentsLoading } from "../slices/commentsSlice";
import { commentPostApi, createPostApi, deletePostApi, getCommentsByPostApi, getMyPostsApi, getPostByIdApi, getPostBySearchApi } from "../../consts";

interface GetPostsInterface {
    max: number, 
    search: string,
    setError: React.Dispatch<SetStateAction<string | null>>,
    setPosts: React.Dispatch<SetStateAction<Post[] | null>>,
    setLoading: React.Dispatch<SetStateAction<boolean>>
}

export const getPostsByDepartment = async ({ max, search, setError, setPosts, setLoading} : GetPostsInterface) => {
    setLoading(true)

    try {
        const response = await fetch (`${getPostBySearchApi}?maxPrice=${max}&location=${search}` , {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('access_token')}`
                }
            })
    
            const data = await response.json()
            if(!response.ok) {
                return setError(data.message)
            }
            
            localStorage.setItem('last_search', search)
            localStorage.setItem('max_price', max.toString())
            setPosts(data.posts)
    } catch (e) {
        const error = e as Error
        setError(error.message)
    } finally {
        setLoading(false)
    }
}

export const getPostsByUserId = ({ userId } : { userId: string }) => async (dispatch: AppDispatch) => {
    dispatch(setPostsLoading(true))

    try {
        const response = await fetch (`${getMyPostsApi}${userId}` , {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('access_token')}`
                }
            })
    
            const data = await response.json()
            if(!response.ok) {
                return dispatch(setPostsError(data.message))
            }
            
            dispatch(setPosts(data.posts))
    } catch (e) {
        const error = e as Error
        dispatch(setPostsError(error.message))
    } finally {
        dispatch(setPostsLoading(false))
    }
}

interface GetPostSelectedInterface {
    postId: string,
    setError: React.Dispatch<SetStateAction<string | null>>,
    setPost: React.Dispatch<SetStateAction<Post | null>>,
    setLoading: React.Dispatch<SetStateAction<boolean>>
}

export const getPostSelected = async ({ postId, setPost, setError, setLoading } : GetPostSelectedInterface) => {
    setLoading(true)

    try {
        const response = await fetch (`${getPostByIdApi}${postId}` , {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('access_token')}`
                }
            })
    
            const data = await response.json()
            if(!response.ok) {
                return setError(data.message)
            }
            
            setPost(data.post)
    } catch (e) {
        const error = e as Error
        setError(error.message)
    } finally {
        setLoading(false)
    }
}

interface CreatePost {
    files: File[] | null,
    location: string,
    phoneNumber: string,
    description: string,
    price: string,
    userId: string,
    setError: React.Dispatch<SetStateAction<string | null>>,
    setLoading: React.Dispatch<SetStateAction<boolean>>
}

export const createPost = async ({ files, userId, location, phoneNumber, description, price, setError, setLoading } : CreatePost) => {
    try {
        setLoading(true)
        const formdata = new FormData()

        formdata.append('description', description)
        formdata.append('price', String(price))
        formdata.append('location', location)
        formdata.append('phoneNumber', phoneNumber)
        formdata.append('userId', userId)
        if(files) {
            files.forEach(file => {
                formdata.append('images', file)
            })
        }


        const response = await fetch (createPostApi , {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('access_token')}`
                },
                body: formdata
            })
    
            const data = await response.json()
            if(!response.ok) {
                return setError(data.message)
            }
            
            window.location.href = '/dashboard/profile'
    } catch (e) {
        const error = e as Error
        setError(error.message)
    } finally {
        setLoading(false)
    }
}

interface CommentPost {
    content: string,
    userId: string,
    postId: string,
    setError: React.Dispatch<SetStateAction<string | null>>,
    setLoading: React.Dispatch<SetStateAction<boolean>>
}

export const commentPost = ({ postId, content, userId, setError, setLoading } : CommentPost) => async (dispatch:AppDispatch) => {
    try {
        setLoading(true)

        const response = await fetch (commentPostApi , {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('access_token')}`,
                    'Content-Type' : 'application/json'
                },
                body: JSON.stringify({ userId, postId, content })
            })
    
            const data = await response.json()
            if(!response.ok) {
                return setError(data.message)
            }
            
            dispatch(addComment(data.comment))
    } catch (e) {
        const error = e as Error
        setError(error.message)
    } finally {
        setLoading(false)
    }
}

export const getCommentsByPost = ({ postId } : { postId: string }) => async (dispatch:AppDispatch) => {
    try {
        dispatch(setCommentsLoading(true))
        const response = await fetch (`${getCommentsByPostApi}${postId}` , {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('access_token')}`
                }
            })
    
            const data = await response.json()
            if(!response.ok) {
                return setCommentsError(data.message)
            }

            dispatch(setComments(data.comments))  
    } catch (e) {
        const error = e as Error
        setCommentsError(error.message)
    } finally {
        dispatch(setCommentsLoading(false))
    }
}

export const deletePost = ({ postId } : { postId: string }) => async (dispatch:AppDispatch) => {
    try {
        const response = await fetch (`${deletePostApi}${postId}` , {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('access_token')}`
                }
            })
    
            const data = await response.json()
            if(!response.ok) {
                return setPostsError(data.message)
            }

            dispatch(deletePostFromStore({ id: postId }))
    } catch (e) {
        const error = e as Error
        setPostsError(error.message)
    } 
}