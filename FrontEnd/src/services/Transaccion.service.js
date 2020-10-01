import axios from "axios";
const rutabase ="http://localhost:8090/api/transacciones/"

export var mensaje="Asegurese de llenar todos los campos";

export function registrarTrans(transaccion) {
 
  axios.post(rutabase,transaccion)
  .then(async(response) => {
     console.log(response); 
     console.log(transaccion);
     mensaje= await "Transacción enviada correctamente";
     alert("Transacción enviada");
  })
  .catch(async(error) => {
     console.log(error);
     console.log(transaccion);
     mensaje= await "Problema de conexión";
     alert("Problemas en la conexión, intente de nuevo");
  })
  
}

  