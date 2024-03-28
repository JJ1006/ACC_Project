import React from 'react'
import { FaCheck } from "react-icons/fa";

import bestelectronics from '../assets/bestelectronics.jpeg'

const Place = () => {
    return (
        <div className='bg-pink-500 '>
            <h1 className='text-black mt-16 pt-9 pb-7'>The best place to find the least price.</h1>
            <section className="flex text-center justify-center	">
                <img className='rounded-lg' src={bestelectronics} alt="#1 in electronics" />
                <div className=''>
                    <p className=''>Lorem ipsum dolor sit amet consectetur, adipisicing elit. Voluptatum quibusdam mollitia dolorum aliquam dicta vero maiores doloribus. Totam, sint! Iure autem ipsam nemo mollitia, amet ut. Ratione provident temporibus illum!</p>
                    <div className='flex-col'>
                    <FaCheck className='w-9 h-9 fill-green-500 p-2' />
                    <p>we are the best price all over world Lorem ipsum dolor sit amet consectetur adipisicing.</p>
                    <FaCheck className='w-9 h-9 fill-green-500 p-2' />
                    <p>we are the best price all over world Lorem ipsum dolor sit amet consectetur adipisicing.</p>
                    <FaCheck className='w-9 h-9 fill-green-500 p-2' />
                    <p>we are the best price all over world Lorem ipsum dolor sit amet consectetur adipisicing.</p>
                    </div>
                </div>
            </section>
        </div>
    )
}

export default Place