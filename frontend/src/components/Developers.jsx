import React from 'react'
import Card from './Card'

import jj from '../assets/my_img.jpg'
import priyalpatel from '../assets/priyalpatel.jpeg'
import dharmilgandhi from '../assets/dharmilgandhi.jpg'
import shreyjani from '../assets/shreyjani.jpg'
import harshismalia from '../assets/harshismalia.jpeg'



// make another component for cards. 
const Developers = () => {
    return (
        <div className=' bg-red-500'>
            <h1 className='text-2xl font-bold text-center mb-3 text-gray-800'>Hello we are Developers from the University of Windsor</h1>
            <div className='flex'>

            <Card name='Dharmil Gandhi' description='This is a description of Dharmil Gandhi' img={dharmilgandhi} github="https://github.com" linkedin="https://www.linkedin.com" />

                <Card name='Priyal Patel' description='This is a description of Priyal Patel' img={priyalpatel} github="https://github.com" linkedin="https://www.linkedin.com" />

                <Card img={jj} name='Jaahanava Joshi' description='This is a description of Jaahanava Joshi' github="https://github.com" linkedin="https://www.linkedin.com" />

                <Card name='Shrey Jani' description='This is a description of Shrey Jani' img={shreyjani} github="https://github.com" linkedin="https://www.linkedin.com" />

                <Card name='Harsh Isamalia' description='This is a description of Harsh Isamalia' img={harshismalia} github="https://github.com" linkedin="https://www.linkedin.com" />

            </div>
        </div>
    )
}

export default Developers