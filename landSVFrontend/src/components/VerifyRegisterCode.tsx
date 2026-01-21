import { useState, type SetStateAction } from "react";
import type React from "react";
import type { CurrentRegisterPage } from "../pages/RegisterPage";
import { verifyCode } from "../store/thunks/userThunk";

export function VerifyCodeForm ({ setCurrentRegisterSection } : {setCurrentRegisterSection: React.Dispatch<SetStateAction<CurrentRegisterPage>>}) {
    const [code, setCode] = useState<string>('')
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState<boolean>(false)

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        if(code.trim() === '') return setError('The code is empty')

        verifyCode({ code, setCurrentRegisterSection, setError, setLoading })
    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newChar = e.currentTarget.value
        if(newChar.startsWith(' ')) return
        setCode(e.currentTarget.value)
    }

    return (
        <form className="verify-code-form" onSubmit={handleSubmit}>
            <h3>We sent the verification code to your account, please enter it</h3>
            <input placeholder="Verification code" value={code} onChange={handleChange} type="text" name="code" />
            <div className="register-options">
                <button disabled={loading}><a href="/login">Login</a></button>
                <button disabled={loading} type="submit">{loading ? 'Checking' : 'Check'}</button>
            </div>
            {error && !loading && <p className="error-register">{error}</p>}
        </form>
    )
}