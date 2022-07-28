import '../App.css';
import React from "react";
import { useForm, Controller } from "react-hook-form";
import ReactSelect from "react-select";
import Button from '@material-ui/core/Button';
import {registrarTrans} from "../services/Transaccion.service"
import {empresadata, transdata} from "./Empresa";

const persona = {};
const transaccion={
  descripcion:"",
  valor:'',
  persona:{},
  empresa:{}
}

const Options =[
  { value: "CC", label: "Cédula de ciudadanía" },
  { value: "CE", label: "Cédula de extranjería" },
  { value: "NIT", label: "NIT" },
  { value: "Pasaporte", label: "Pasaporte" },
  { value: "TI", label: "Tarjeta de identidad" }
];

const defaultValues = {
  nombre: "Juan Pérez",
  correo: "ejemplo@mail.com",
  tipoDoc: { value: "cc", label: "Cédula de ciudadanía" },
  celular: 1234567890,
  idPersona: 1234567890,
};

const User = () => {

  const { handleSubmit, register, control, errors } = useForm({ defaultValues });
  
  const onSubmit = async persona => {
    persona.tipoDoc=persona.tipoDoc.value;
    persona.idPersona=parseInt(persona.idPersona, 10);
    persona.celular=parseInt(persona.celular, 10);
    transaccion.descripcion=transdata.descripcion;
    transaccion.valor=transdata.valor;
    transaccion.persona=persona;
    transaccion.empresa=empresadata;
    registrarTrans(transaccion);
  }

  return (
  
    <form onSubmit={handleSubmit(onSubmit)} className="form">
      <h3 className="h3"> Información del usuario </h3>
      <div className="container">
        <section>
          <label>Nombre completo</label>
          <input name="nombre" className="input" ref={register({ 
            required: true,
             maxLength: 50,
             minLength: 3  
            })} />
          {errors.nombre && <h4 className="h4">{"Nombre inválido"}</h4>}
        </section>

        <section>
          <label>Correo</label>
          <input name="correo" className="input" ref={register({
            required: "Required",
            pattern: {
            value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
            message: "Correo inválido"
            }})}/>
          {errors.correo && <h4 className="h4">{"Correo inválido"}</h4>}
        </section>

        <section>
          <label>Tipo de documento</label>
          <Controller 
            as={ReactSelect} 
            options={Options} 
            name="tipoDoc" 
            className="basic-single" 
            classNamePrefix="select"
            isClearable="true"
            isSearchable="true" 
            control={control} 
            />

        </section>
        
        <section>
          <label>Número de documento</label>
          <input name="idPersona" className="input" ref={register({ 
             required: true,
             maxLength: 10,
             minLength: 10  
            })}/>
          {errors.idPersona && <h4 className="h4">{"Documento inválido"}</h4>}
        </section>

        <section>
          <label>Número de teléfono</label> 
          <input name="celular" className="input" ref={register({ 
            required: true,
             maxLength: 10,
             minLength: 7  
            })}/>
          {errors.celular && <h4 className="h4">{"Celular inválido"}</h4>}
        </section>
      </div>

      <div className="button1">
      <Button variant="contained" color="primary" type="submit">Send</Button>  
      </div>
  
    </form>
    
    );   
}
export default User;

