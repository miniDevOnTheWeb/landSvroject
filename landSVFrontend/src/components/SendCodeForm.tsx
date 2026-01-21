import { useState, type SetStateAction } from "react";
import type React from "react";
import type { CurrentRegisterPage } from "../pages/RegisterPage";
import { sendVerificationCode } from "../store/thunks/userThunk";

export function SendCodeForm ({ setCurrentRegisterSection } : {setCurrentRegisterSection: React.Dispatch<SetStateAction<CurrentRegisterPage>>}) {
    const [email, setEmail] = useState<string>('')
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState<boolean>(false)

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        if(email.trim() === '') return setError('The email is empty')

        sendVerificationCode({ email, setCurrentRegisterSection, setError, setLoading })
    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newChar = e.currentTarget.value
        if(newChar.startsWith(' ')) return
        setEmail(e.currentTarget.value)
    }
    
    return (
        <form onSubmit={handleSubmit} className="send-code-form">
            <h3>Which email do you wanna register</h3>
            <input placeholder="Email" value={email} onChange={handleChange} type="email" name="email" />
            <div className="register-options">
                <button disabled={loading}><a href="/login">Login</a></button>
                <button type="submit" disabled={loading}>{loading ? 'Checking' : 'Check'}</button>
            </div>
            {error && !loading && <p className="error-register">{error}</p>}
        </form>
    )
}