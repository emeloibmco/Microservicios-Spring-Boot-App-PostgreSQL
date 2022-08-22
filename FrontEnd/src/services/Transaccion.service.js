import axios from "axios";
const rutabase ="http://zuul-server-spring2.openshift-demo-dal-bb2658cfd9b67ad75139ae3de1b2980c-0000.us-south.containers.appdomain.cloud/api/transacciones"
export var mensaje="Asegurese de llenar todos los campos";
 

export function registrarTrans(transaccion) {
  axios.post(rutabase,transaccion, {
   headers: {
   'Content-Type': 'application/json'
   }
 })
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

  