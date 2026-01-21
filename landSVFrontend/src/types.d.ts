export interface Comment {
    id: string,
    content: string,
    createdAt: Date,
    postId: string,
    creator: string,
    creatorId: string
}

export interface User {
    username: string,
    email: string,
    id: string,
    profileImage: string,
    posts: Post[]
}

export interface Message {
    id: string,
    content: string,
    senderId: string,
    chatId: string,
    receiverId: string,
    createdAt: Date,
    type: MessageType
}

export interface MessageToSend {
    content: string,
    senderId: string,
    receiverId: string,
    createdAt: string,
    type: MessageType
}

export interface Post {
    id: string,
    description: string,
    createdAt: string,
    images: string[],
    creator: string,
    creatorId: string,
    comments: Comment[],
    location: string,
    price: number,
    phoneNumber: string
}

export interface Chat {
    id: string,
    creator: string,
    creatorId: string,
    invited: string,
    invitedId: string,
    creatorProfileImage: string,
    invitedProfileImage: string,
    createdAt: string,
    messages: Message[],
    lastMessage: Date,
    hasUnseenMessages: boolean,
    lastSenderId: string
}

export type MessageType = 
    'PRIVATE' |
    'PUBLIC' |
    'NOTIFICATION' |
    'SEEN_CHAT' | 
    'DELETE_CHAT'

type Provider = 
    'LOCAL' |
    'GOOGLE'
