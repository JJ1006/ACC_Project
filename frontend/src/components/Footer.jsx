import React from 'react'

import { FaLinkedin } from 'react-icons/fa';
import { FaFacebookSquare } from 'react-icons/fa';
import { FaGithubSquare } from 'react-icons/fa';
import { FaInstagram } from 'react-icons/fa';
import { FaTwitterSquare } from 'react-icons/fa';

const Footer = () => {
  return (
    <div className="left-0  bottom-0 px-4 text-gray-300 bg-stone-400">
      <div className='text-center justify-center'>
        <h1 className="text-center justify-center text-3xl font-bold text-[#c58146]">
          Contact Us
        </h1>
        <p className="text-center">Lorem ipsum dolor sit amet consectetur adipisicing elit. Ipsum perspiciatis ipsa dolorum laudantium doloremque. Sed pariatur quia blanditiis exercitationem nesciunt!</p>
        <div className="w-screen flex justify-center space-x-10 my-6 md:w-screen md:flex md:justify-center md:space-x-10 md:my-6.5 sm:w-screen sm:flex sm:justify-center sm:space-x-1.5 sm:my-1.5">
          <FaFacebookSquare size={30} href='www.facebook.com' />
          <FaInstagram size={30} href='www.intagram.com' />
          <FaTwitterSquare size={30} href='www.twitter.com' />
          <FaGithubSquare size={30} href='www.github.com' />
          <FaLinkedin size={30} href='www.facebook.com' />
        </div>
      </div>

    </div>
  );
}

export default Footer;