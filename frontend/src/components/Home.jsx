import React from 'react';
import axios from 'axios';
import jj from '../assets/my_img.jpg'
import { useEffect } from 'react';


const Home = () => {
  useEffect(() => {
    async function fetchData() {
      const {data} = await axios.post('http://localhost:8080/search', {product: 'electronics'});
      console.log("Data:- " + data); 
    }
    fetchData();
  }, [])

  
  
  return (
    <div className='bg-yellow-500 pt-12 pb-3 flex'>
      <img className='rounded-[6.5rem] w-3/4 h-3/4 p-16' src={jj} alt="#1 in electronics" />
      <div className='flex-col'>

      <h1 className='text-black mt-16 pt-16 pb-7'>Find a perfect electronics to gift it to your family!!!</h1>
      <p className='text-black'>Lorem ipsum dolor sit amet consectetur, adipisicing elit. Voluptatum quibusdam mollitia dolorum aliquam dicta vero maiores doloribus. Totam, sint! Iure autem ipsam nemo mollitia, amet ut. Ratione provident temporibus illum!</p>
      </div>
    </div>
  );
};

export default Home;
