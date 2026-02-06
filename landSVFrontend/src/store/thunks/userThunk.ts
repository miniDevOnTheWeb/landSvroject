import type React from "react";
import { setUser, setUserError, setUserLoading } from "../slices/userSlice";
import type { AppDispatch } from "../store";
import type { SetStateAction } from "react";
import type { CurrentRegisterPage } from "../../pages/RegisterPage";
import { toast } from "sonner";
import { createVerificationCodeApi, editUserApi, loginApi, registerApi, verifyCodeApi } from "../../consts";

export const login = ({ username, password, navigate }: { username: string, password: string, navigate: (to: string) => void }) => async (dispatch: AppDispatch) => {
    try {
        dispatch(setUserLoading(true))

        const response = await fetch(loginApi, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        })

        const data = await response.json()

        if (!response.ok) {
            return dispatch(setUserError(data.message))
        }

        localStorage.setItem('access_token', data.token)
        dispatch(setUser(data.user))
        navigate('/dashboard/main-page')
    } catch (error) {
        const e = error as Error
        dispatch(setUserError(e.message))
    }
}

interface SendVerificationCode {
    email: string,
    setError: React.Dispatch<SetStateAction<string | null>>,
    setCurrentRegisterSection: React.Dispatch<SetStateAction<CurrentRegisterPage>>,
    setLoading: React.Dispatch<SetStateAction<boolean>>
}

export const sendVerificationCode = async ({ email, setCurrentRegisterSection, setError, setLoading }: SendVerificationCode) => {
    try {
        setLoading(true)

        console.log('console log de prueba')
        const response = await fetch(`${createVerificationCodeApi}${email}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })

        const data = await response.json()

        if (!response.ok) {
            return setError(data.message)
        }
        console.log('console log de confirmacion')

        localStorage.setItem('email_to_register', email)
        localStorage.setItem('current_register_section', 'VERIFY_CODE')
        setCurrentRegisterSection('VERIFY_CODE')
    } catch (error) {
        const e = error as Error
        setError(e.message)
    } finally { setLoading(false) }
}

interface VerifyCode {
    code: string,
    setError: React.Dispatch<SetStateAction<string | null>>,
    setCurrentRegisterSection: React.Dispatch<SetStateAction<CurrentRegisterPage>>,
    setLoading: React.Dispatch<SetStateAction<boolean>>
}

export const verifyCode = async ({ code, setCurrentRegisterSection, setError, setLoading }: VerifyCode) => {
    try {
        const email = localStorage.getItem('email_to_register')
        if (!email) return setError('The email is empty, start again')

        setLoading(true)

        const response = await fetch(`${verifyCodeApi}?email=${email}&code=${code}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })

        const data = await response.json()
        console.log(data)
        if (!response.ok) {
            return setError(data.message)
        }

        setCurrentRegisterSection('REGISTER')
        localStorage.setItem('current_register_section', 'REGISTER')
        localStorage.setItem('register_code', code)
    } catch (error) {
        const e = error as Error
        setError(e.message)
    } finally { setLoading(false) }
}

interface RegisterInterface {
    username: string,
    password: string,
    image: File | null,
    setError: React.Dispatch<SetStateAction<string | null>>,
    setCurrentRegisterSection: React.Dispatch<SetStateAction<CurrentRegisterPage>>,
    setLoading: React.Dispatch<SetStateAction<boolean>>
    setMessage: React.Dispatch<SetStateAction<string | null>>
}

export const registerUser = async ({ username, password, image, setMessage, setCurrentRegisterSection, setError, setLoading }: RegisterInterface) => {
    try {
        const email = localStorage.getItem('email_to_register')
        const code = localStorage.getItem('register_code')
        if (!email) return setError('The email is empty, start again')
        if (!code) return setError('The code is empty')

        setLoading(true)
        setMessage(null)

        console.log(image)
        const formdata = new FormData()
        formdata.append('username', username)
        formdata.append('password', password)
        formdata.append('email', email)
        formdata.append('code', code)

        if (image) formdata.append('image', image)

        const response = await fetch(registerApi, {
            method: 'POST',
            body: formdata
        })

        const data = await response.json()
        if (!response.ok) {
            return setError(data.message)
        }

        setError(null)
        setCurrentRegisterSection('REGISTER')
        setMessage(data.message)
        localStorage.clear()
    } catch (error) {
        const e = error as Error
        setError(e.message)
    } finally { setLoading(false) }
}

interface EditUserInterface {
    username: string,
    password: string,
    image: File | null,
    setError: React.Dispatch<SetStateAction<string | null>>,
    setLoading: React.Dispatch<SetStateAction<boolean>>
    setMessage: React.Dispatch<SetStateAction<string | null>>,
    userId: string
}

export const editUser = ({ userId, username, password, image, setMessage, setError, setLoading }: EditUserInterface) => async (dispatch: AppDispatch) => {
    try {
        setLoading(true)

        console.log(image)
        const formdata = new FormData()
        formdata.append('username', username)
        formdata.append('password', password)
        formdata.append('userId', userId)
        if (image) formdata.append('image', image)

        const response = await fetch(editUserApi, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('access_token')}`
            },
            body: formdata
        })

        const data = await response.json()
        if (!response.ok) {
            setMessage(null)
            return setError(data.message)
        }

        setError(null)
        setMessage(data.message)
        toast.success('Inicia sesion para ver los cambios')
        dispatch(setUser(data.user))
    } catch (error) {
        const e = error as Error
        setError(e.message)
    } finally { setLoading(false) }
}