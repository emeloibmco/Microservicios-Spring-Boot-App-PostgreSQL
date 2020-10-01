import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import User from './components/User';
import Empresa from './components/Empresa';
import Header from './components/Header';



ReactDOM.render(
  <>
    <Header />
    <Empresa />
    <User />
  </>,
  document.getElementById('root')
);

// ** Datos de persona o user: ******************
// nombre
// celular
// correo
// id_persona (documento)
// tipo_doc

// ** Datos de comercio: ******************
// empresa 
// factura
// nit
// valor
