import { useState, type SetStateAction } from "react";
import type React from "react";
import type { CurrentRegisterPage } from "../pages/RegisterPage";
import { registerUser } from "../store/thunks/userThunk";

export function RegisterForm ({ setCurrentRegisterSection } : {setCurrentRegisterSection: React.Dispatch<SetStateAction<CurrentRegisterPage>>}) {
    const [error, setError] = useState<string | null>(null)
    const [message, setMessage] = useState<string | null>(null)
    const [loading, setLoading] = useState<boolean>(false)

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        const form = e.currentTarget

        const formdata = new FormData(form)

        const username = formdata.get('username') as string
        const password = formdata.get('password') as string
        const confirmPassword = formdata.get('confirmPassword') as string

        if(!username || !password || !confirmPassword || username.trim() === '' || password.trim() === '' || confirmPassword.trim() === '') return setError('Enter the necesary data')

        if(password !== confirmPassword) return setError('The passwords doesnt match')

        const imageEntry = formdata.get('image') as File

        const image = imageEntry instanceof File && imageEntry.size > 0 ? imageEntry : null

        registerUser({ image, username, password, setMessage, setCurrentRegisterSection, setError, setLoading })

        if(!error) {
            form.reset()
        }
    }

    return (
        <form onSubmit={handleSubmit} className="register-form" encType="multipart/form-data">
            <h3>Which email do you wanna register</h3>
            <input placeholder="Username" type="text" name="username" />
            <input type="password" placeholder="password" name="password" />
            <input type="password" placeholder="Confirm Password" name="confirmPassword" />
            <label htmlFor="img">Profile image:</label>
            <input type="file" name="image" id="img" accept="image/*" />
            <div className="register-options">
                <button disabled={loading}><a href="/login">Login</a></button>
                <button disabled={loading}>{loading ? 'Registering' : 'Register'}</button>
            </div>
            {error && !loading && <p className="error-register">{error}</p>}
            {message && <p className="register-message">{message}</p>}
        </form>
    )
}