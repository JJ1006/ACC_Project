import React from 'react'

import { FaGithubSquare, FaLinkedin } from 'react-icons/fa';

const Card = ({ img, name, description, github, linkedin }) => {
    return (
        <div className="mt-7 flex flex-col items-center">
            <img className="object-cover h-48 w-96 rounded-lg shadow-lg p-4 m-2" src={img} />
            <h1 className="mt-4 text-xl font-bold">{name}</h1>
            <p className="mt-2 text-gray-600">{description}</p>

            <div className="flex mt-4">
                {github && (
                    <a href={github} target="_blank" rel="noopener noreferrer" className="mr-2">
                        <FaGithubSquare className="text-2xl text-gray-700 hover:text-gray-900" />
                    </a>
                )}
                {linkedin && (
                    <a href={linkedin} target="_blank" rel="noopener noreferrer">
                        <FaLinkedin className="text-2xl text-gray-700 hover:text-gray-900" />
                    </a>
                )}
            </div>

        </div>

    )
}

export default Card


// <p href="default.asp"><img src={FaGithubSquare} alt="Github"></p>

{/* <div className='mt-7'>
            <img className='object-contain h-48 w-96' src={img} />
            <h1>{name}</h1>
            <p>{description}</p>
        </div> */}