import React, { useState } from 'react'

import icon from '../assets/icon.jpeg'

const Nav = () => {
  let Links = [
    { name: "Home", link: "/" },
    { name: "Search", link: "/" },
    { name: "About Us", link: "/" },
    // { name: "BLOG'S", link: "/" },
    // { name: "CONTACT", link: "/" },
  ];
  let [open, setOpen] = useState(false);
  return (
    <div className='shadow-md w-full fixed top-0 left-0'>
      <div className='md:flex items-center justify-between py-4 md:px-10 px-7 bg-sky-300'>
        <div className='font-bold text-2xl cursor-pointer flex items-center font-[Poppins] 
      text-gray-800'>
          <span className='text-3xl text-indigo-600 mr-2.5 pt-2'>
            {/* <ion-icon name="logo-ionic"></ion-icon> */}
            <img className='size-16' src={icon} alt="icon" />
          </span>
          ACC Project
        </div>

        <div onClick={() => setOpen(!open)} className='text-3xl absolute right-8 top-6 cursor-pointer md:hidden'>
          <ion-icon name={open ? 'close' : 'menu'}></ion-icon>
        </div>

      <div>
        <input
            type="text"
            placeholder="Search"
            // value={searchQuery}
            // onChange={handleSearchChange}
            className="px-1 py-1 rounded-md border-gray-400 border focus:outline-none focus:border-indigo-500 w-98 md:px-0 m"
          />
          <button className="ml-1 px-1 py-1 bg-indigo-600 text-white rounded-md" onClick={handleSearch}>Search</button>
        </div>


        {/* <ul className={`md:flex md:items-center md:pb-0 pb-12 absolute md:static bg-white md:z-auto z-[-1] left-0 w-full md:w-auto md:pl-0 pl-9 transition-all duration-500 ease-in ${open ? 'top-20 ' : 'top-[-490px]'}`}> */}
        <ul className={`md:flex md:items-end md:z-auto z-[-1] left-0 w-full md:w-auto md:pl-0 pl-9 transition-all duration-500 ease-in ${open ? 'top-20 flex flex-col bg-sky-200 md:flex-row md:bg-sky-300 md:pl-9' : 'top-[-490px] hidden'}`}>
          {
            Links.map((link) => (
              <li key={link.name} className='md:ml-8 text-xl md:my-0 my-7'>
                <a href={link.link} className='text-gray-800 hover:text-gray-400 duration-500'>{link.name}</a>
              </li>
            ))
          }
          
        </ul>
      </div>
    </div>
  )
}

export default Nav