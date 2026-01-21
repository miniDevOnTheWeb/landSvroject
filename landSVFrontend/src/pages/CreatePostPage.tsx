import type React from "react"
import { createPost } from "../store/thunks/postsThunks"
import { useUser } from "../hooks/useUser"
import { useRef, useState } from "react"
import { RemoveIcon } from "../components/Icons"
import { Overlay } from "../components/LoadingOverlay"

export function CreatePost () {
    const { user } = useUser()
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState<boolean>(false)
    const [files, setFiles] = useState<File[]>([])
    const [previews, setPreviews] = useState<string []>([])
    const dropRef = useRef<HTMLDivElement | null>(null)

    const handleFiles = (files: FileList | null) => {
        if(!files) return setError('Select the files')
        
        const validFiles = Array.from(files).filter(file => file.type.startsWith('image/'))

        setFiles(prev => [...prev, ...validFiles ])
        setPreviews(prev => [
            ...prev,  
            ...validFiles.map(file => URL.createObjectURL(file))
        ])
    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        handleFiles(e.currentTarget.files)
    }

    const handleDrop = (e: React.DragEvent<HTMLDivElement>) => {
        e.preventDefault()
        handleFiles(e.dataTransfer.files)
    }

    const handleDragOver = (e: React.DragEvent<HTMLDivElement>) => {
        e.preventDefault()
    }

    const handleSubmit = (e:React.FormEvent<HTMLFormElement>) => {
        if(!user) return
        e.preventDefault()

        const form = e.currentTarget
        const formdata = new FormData(form)

        const description = formdata.get('description') as string
        const phoneNumber = formdata.get('phoneNumber') as string
        const location = formdata.get('location') as string
        const price = formdata.get('price') as string

        createPost({ price, phoneNumber, location, description, files, userId: user.id, setError, setLoading })
    }

    return (
        <div className="create-post-page">
            <form className="create-post-form" onSubmit={handleSubmit} encType="multipart/form-data">
                <h4>Lets create a post</h4>
                <input type="text" name="location" placeholder="Location" />
                <textarea className="desc-area" name="description" id="" placeholder="Description" />
                <input type="text" name="phoneNumber" placeholder="Contact phone number" />
                <input type="number" name="price" placeholder="Price" />
                <div className="drop-zone">
                    <>
                        <div 
                            onDrop={handleDrop}
                            ref={dropRef}
                            onDragOver={handleDragOver}
                            className="drag-drop-images"
                            onClick={() => dropRef.current?.querySelector('input')?.click()}
                        >
                        {previews.length === 0 && (
                            <p>Drag or click for select the images</p>
                        )}
                            <input onChange={handleChange} type="file" multiple name="images" accept="image/*"/>
                            
                            {previews && previews.length > 0 && (
                                <>
                                    <ul className="preview-list">
                                        {previews.map((pr, index) => (
                                            <li key={index}><img className="preview-img" src={pr} /></li>
                                        ))}
                                    </ul>
                                    <button type="button" className="clean-files" onClick={(e) => { 
                                        e.stopPropagation()
                                        setFiles([]); 
                                        setPreviews([])
                                    }}><RemoveIcon /></button>
                                </>
                        )}
                        </div>
                        {loading && <Overlay />}
                    </>
                </div>
                <button type="submit" disabled={loading}>{loading ? 'Posting' : 'Post'}</button>
                {error && !loading && <p className="error">{error}</p>}
            </form>
        </div>
    )
}