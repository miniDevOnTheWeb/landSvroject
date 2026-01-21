import React, { useEffect, useState } from "react"
import { getPostsByDepartment } from "../store/thunks/postsThunks"
import { useNavigate } from "react-router-dom"
import { TopBarMainPage } from "../components/TopBarMainPage"
import type { Post } from "../types"
import { SmallerOverlay } from "../components/LoadingOverlay"

export function MainPage () {
    const navigate = useNavigate()
    const [search, setSearch] = useState<string>(() => {
        const localSearch = localStorage.getItem('last_search')
        return localSearch !== null ? localSearch : ''
    })
    const [error, setError] = useState<string | null>(null)
    const [posts, setPosts] = useState<Post[] | null>(null)
    const [loading, setLoading] = useState<boolean>(false)
    const [max, setMax] = useState<number>(() => {
        const localMax = localStorage.getItem('max_price')
        return localMax !== null ? localMax as unknown as number : 0
    })

    useEffect(() => {
        if(max !== 0 && search.trim() !== '') {
            getPostsByDepartment({ search, max, setError, setLoading, setPosts })
        }
    }, [])

    const handleSubmit = (e:React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        if(search.trim() === '') return setError('complete the department search')
        if(max <= 0) return setError('max value must be bigger than 0')

        setError(null)
        getPostsByDepartment({ search, max, setError, setLoading, setPosts })
    }

    return (
        <>
        <div className="main-page">
        <TopBarMainPage />
            <div className="main-page-sep">
                <div className="search-form-container">
                    <form className="search-form" onSubmit={handleSubmit}>
                        <h3>Write the location and max price</h3>
                        <input value={search} onFocus={(e) => {
                            localStorage.removeItem('last_search')
                            localStorage.removeItem('max_price')
                            setSearch(e.currentTarget.value)
                            setMax(0)
                        }} onChange={(e) => setSearch(e.currentTarget.value)} type="text" placeholder="Wished department location" />
                        <input value={max} type="number" placeholder="Max price" onChange={(e) => setMax(e.currentTarget.value as unknown as number)} />
                        <button disabled={loading}>{loading ? 'Searching' : 'search'}</button>
                        {error && !loading && <p className="error">{error}</p>}
                    </form>
                </div>
                <div className="results-container">
                    {loading && <SmallerOverlay />}
                    {posts && posts.length === 0 && !loading && <p className="not-found">Results not found</p>}
                    {posts && posts.length > 0 && (
                        <>
                            <p className="results-message">Click on the result to see the details</p>
                            <ul className="posts-results-list">
                                {posts.map(post => (
                                    <li className="post" key={post.id} onClick={() => navigate(`/dashboard/checkPost?postId=${post.id}`)}>
                                        <img src={post.images[0]} className="result-post-image"/>
                                        <p className="location">{post.location}</p>
                                        <p className="description">{post.description}</p>
                                    </li>
                                ))}
                            </ul>
                        </>
                    )}
                </div>
            </div>
            </div>
        </>
        )
}