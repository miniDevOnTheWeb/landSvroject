import React, { useRef, useState, type ChangeEvent, type FormEvent } from "react"
import { useUser } from "../hooks/useUser"
import { useDispatch } from "react-redux"
import type { AppDispatch } from "../store/store"
import { editUser } from "../store/thunks/userThunk"
import { MinimunOverlay } from "../components/LoadingOverlay"

export function EditUserPage () {
    const { user } = useUser()
    const dropRef = useRef<HTMLDivElement | null>(null)
    const [imageProfile, setImageProfile] = useState<File | null>(null)
    const [prevImage, setPrevImage] = useState<string | null>(null)
    const hasProfileImage = Boolean(user?.profileImage);
    const [error, setError] = useState<string | null>(null)
    const [message, setMessage] = useState<string | null>(null)
    const [loading, setLoading] = useState<boolean>(false)
    const dispatch = useDispatch<AppDispatch>()

    const handleFiles = (file: File | null) => {
        if(!file) return
        if(!file.type.startsWith('image/')) return

        setImageProfile(file)
        setPrevImage(URL.createObjectURL(file))
    }

    const handleDragOver = (e: React.DragEvent<HTMLDivElement>) => {
        e.preventDefault()
    }

    const handleDrop = (e: React.DragEvent<HTMLDivElement>) => {
        console.log('drop')
        e.preventDefault()
        handleFiles(e.dataTransfer.files[0])
    }

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        handleFiles(e.currentTarget.files ? e.currentTarget.files[0] : null)
    }

    const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        if(!user) return

        const form = e.currentTarget
        const formdata = new FormData(form);

        const username = formdata.get('username') as string
        const password = formdata.get('password') as string

        if(!username || !password || username.trim() === '' || password.trim() === '')  {
            setMessage(null)
            return setError('Complete todos los datos')
        }

        dispatch(editUser({ userId: user?.id, setError, setLoading, setMessage, image: imageProfile, username, password }))
    }

    return (
        <div className="edit-page">
            <form onSubmit={handleSubmit} encType="multipart/form-data" className="edit-form">
                <div 
                    ref={dropRef}
                    onDrop={handleDrop}
                    onDragOver={handleDragOver}
                    onClick={() => dropRef.current?.querySelector('input')?.click()}
                    className="drag-drop-profilephoto"
                >
                    <input type="file" onChange={handleChange}/>
                    {prevImage ? 
                        <img className="prev-image" src={prevImage} /> 
                        : hasProfileImage 
                            ? <img src={user?.profileImage} className="prev-image"/> 
                            : <p className="message-edit">Select a photo</p>}
                </div>
                <input placeholder="New username" id="username" type="text" name="username" />
                <input placeholder="Password" id="password" type="text" name="password" />
                <button disabled={loading} className="save-button">{loading ?  <span className="saving-loading">Guardando <MinimunOverlay /></span> : 'Guardar'}</button>
                {error && !loading && <p className="error-register">{error}</p>}
                {message && <p className="register-message">{message}</p>}
            </form>
        </div>
    )
}