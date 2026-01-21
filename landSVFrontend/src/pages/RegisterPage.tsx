import { useState } from "react"
import { SendCodeForm } from "../components/SendCodeForm"
import { VerifyCodeForm } from "../components/VerifyRegisterCode"
import { RegisterForm } from "../components/RegisterForm"

export type CurrentRegisterPage = 'VERIFY_CODE' | 'SEND_CODE' | 'REGISTER'

export function RegisterPage () {
    const [currentRegisterSection, setCurrentRegisterSection] = useState<CurrentRegisterPage>(() => {
        const savedSection = localStorage.getItem('current_register_section') as CurrentRegisterPage
        return savedSection ? savedSection : 'SEND_CODE'
    })

    return (
        <div className="register-page">
            {currentRegisterSection === 'SEND_CODE'
                ? <SendCodeForm  setCurrentRegisterSection={setCurrentRegisterSection}/> 
                : currentRegisterSection === 'VERIFY_CODE' 
                    ? <VerifyCodeForm setCurrentRegisterSection={setCurrentRegisterSection} />
                    : currentRegisterSection === 'REGISTER' 
                        ? <RegisterForm setCurrentRegisterSection={setCurrentRegisterSection} /> 
                        : <h2 className="not-found">Section not found</h2>
            }
        </div>
    )
}