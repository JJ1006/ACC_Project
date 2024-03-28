import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

import Nav from './components/Nav'
import Developers from './components/Developers'
import Footer from './components/Footer'
import Place from './components/Place'
import Home from './components/Home'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <Nav />
      <Home />
      <Place />
      <Developers />
      <Footer />
    </>
  )
}

export default App
