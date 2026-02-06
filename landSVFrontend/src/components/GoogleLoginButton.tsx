import { GoogleLogin, GoogleOAuthProvider, type CredentialResponse } from '@react-oauth/google'
import { useDispatch } from 'react-redux'
import type { AppDispatch } from '../store/store'
import { setUser, setUserError, setUserLoading } from '../store/slices/userSlice'

export function GoogleLoginButton() {
    const dispatch = useDispatch<AppDispatch>()

    const handleSuccess = async (credentialResponse: CredentialResponse) => {
        const token = credentialResponse.credential

        try {
            dispatch(setUserLoading(true))

            const res = await fetch('/api/auth/google/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ token })
            })

            const data = await res.json()

            if (!res.ok) {
                return dispatch(setUserError(data.message))
            }

            localStorage.setItem('access_token', data.token)
            dispatch(setUser(data.user))
            window.location.href = '/dashboard/main-page'
        } catch (e) {
            const error = e as Error
            dispatch(setUserError(error.message))
        } finally {
            dispatch(setUserLoading(false))
        }
    }

    const handleError = () => {
        console.log('Error en el inicio de sesion con google')
    }

    const vite2 = import.meta.env.VITE_GOOGLE_CLIENT_ID_BY_ARG

    return (
        <GoogleOAuthProvider clientId={vite2}>
            <GoogleLogin onSuccess={handleSuccess} onError={handleError} />
        </GoogleOAuthProvider>
    )
}