import React, { useState, type SetStateAction } from "react"
import { BackIcon, NextIcon, XIcon } from "./Icons"

export function ImageZoomed ({ images, currentImage, setVisible }: { images: string[], currentImage: number, setVisible: React.Dispatch<SetStateAction<boolean>> }) {
    const [crtIndex, setCrtIndex] = useState<number>(currentImage)
    
    const incrementIndex = () => {
        if(crtIndex < images.length) {
            setCrtIndex(prev => prev + 1)
        }
    }

    const decrementIndex = () => {
        if(crtIndex > 0) {
            setCrtIndex(prev => prev - 1)
        }
    }

    return (
        <div className="image-container">
            <button className="close-button" onClick={() => setVisible(false)}><XIcon /></button>
            <div className="image-options">
                <img src={images[crtIndex]} className="img-zoomed" />
                <div className="options">
                    <button className="move" disabled={crtIndex <= 0} onClick={decrementIndex}><BackIcon /></button>
                    <button className="move" disabled={crtIndex >= images.length - 1} onClick={incrementIndex}><NextIcon /></button>
                </div>
            </div>
        </div>
    )
}
