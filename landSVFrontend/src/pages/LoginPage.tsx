import { useDispatch } from "react-redux"
import type { AppDispatch } from "../store/store"
import React, { useEffect, useState } from "react"
import { login } from "../store/thunks/userThunk"
import { useNavigate } from "react-router-dom"
import { useUser } from "../hooks/useUser"
import { setUserError } from "../store/slices/userSlice"
import { GoogleLoginButton } from "../components/GoogleLoginButton"

export function LoginPage () {
    const dispatch = useDispatch<AppDispatch>()
    const [username, setUsername] = useState<string>('')
    const [password, setPassword] = useState<string>('')
    const { error, loading } = useUser()
    const navigate = useNavigate()

    useEffect(() => {
        localStorage.clear()
    }, [])


    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if(username.trim() === '' || password.trim() === '') return dispatch(setUserError('Completa todos los datos'))
        dispatch(login({ username, password, navigate }))
    }

    return (
        <div className="login-page">
            <form onSubmit={handleSubmit} className="login-form">
                <input placeholder="Username" onChange={(e) => setUsername(e.currentTarget.value)} type="text" className="login-username" />
                <input placeholder="Password" onChange={(e) => setPassword(e.currentTarget.value)} type="text" className="login-password" />
                <button disabled={loading} className="login-button">{loading ? 'Iniciando...' : 'Iniciar'}</button>
                <p className="message">Si no tienes una cuenta <a href="register-page">Registrate aqui</a></p>
                <p className="message">O</p>
                <GoogleLoginButton />
                {error && !loading && <p className="error-login">{error}</p>}
            </form>
        </ div>
    )
}