export const transformDate = (isoDate: string) => {
    const date = new Date(isoDate)

    return date.toLocaleDateString('es-ES', { day: 'numeric', year: 'numeric', month: 'short'})
}

// ------------------> routes <------------------------

export const loginRoute = '/login'
export const chatsPageRoute = '/dashboard/chat-page'
export const mainPageRoute = '/dashboard/main-page'
export const loginApi = '/api/auth/login'
export const createVerificationCodeApi = '/api/auth/createVerificationCode?email='
export const verifyCodeApi = '/api/auth/verifyCode'
export const registerApi = '/api/users/register'
export const editUserApi = '/api/users/editUser'
export const getPostBySearchApi = '/api/posts/getPostsByData'
export const getMyPostsApi = '/api/posts/getMyPosts/'
export const getPostByIdApi = '/api/posts/getPostById/'
export const createPostApi = '/api/posts/create'
export const commentPostApi = '/api/posts/comment'
export const getCommentsByPostApi = '/api/posts/getComments/'
export const verifyTokenApi = '/api/verifyData/verifyToken'
export const deletePostApi = '/api/posts/'